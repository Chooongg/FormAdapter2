package com.chooongg.android.form.item

import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.LayerDrawable
import android.graphics.drawable.RippleDrawable
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.OvalShape
import android.text.InputFilter
import android.text.InputType
import android.text.TextUtils
import android.text.TextWatcher
import android.view.ContextThemeWrapper
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.widget.TooltipCompat
import androidx.core.view.updateLayoutParams
import androidx.core.view.updatePadding
import androidx.core.widget.doAfterTextChanged
import com.chooongg.android.form.FormColorStateListBlock
import com.chooongg.android.form.FormManager
import com.chooongg.android.form.R
import com.chooongg.android.form.boundary.Boundary
import com.chooongg.android.form.holder.FormViewHolder
import com.chooongg.android.form.inputMode.InputMode
import com.chooongg.android.form.inputMode.InputModeText
import com.chooongg.android.form.option.FormArrayAdapter
import com.chooongg.android.form.option.OptionLoadResult
import com.chooongg.android.form.style.AbstractStyle
import com.chooongg.android.ktx.resDimensionPixelSize
import com.google.android.material.internal.CheckableImageButton
import com.google.android.material.progressindicator.CircularProgressIndicatorSpec
import com.google.android.material.progressindicator.IndeterminateDrawable
import com.google.android.material.textfield.MaterialAutoCompleteTextView
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.CoroutineScope
import kotlin.math.min

open class FormInput(name: Any?, field: String?) : BaseOptionForm<CharSequence>(name, field) {

    /**
     * 输入模式
     */
    var inputMode: InputMode = InputModeText()

    /**
     * 最小行数
     */
    open var minLines: Int = 0

    /**
     * 最大行数
     */
    open var maxLines: Int = Int.MAX_VALUE

    /**
     * 省略号
     */
    open var ellipsize: TextUtils.TruncateAt = TextUtils.TruncateAt.END

    /**
     * 最小长度
     */
    var minLength: Int? = null

    /**
     * 最大长度
     */
    var maxLength: Int? = null

    /**
     * 前缀: Int(StringRes), String, CharSequence
     */
    var prefix: Any? = null

    /**
     * 后缀: Int(StringRes), String, CharSequence
     */
    var suffix: Any? = null

    /**
     * 占位: Int(StringRes), String, CharSequence
     */
    var placeholder: Any? = null

    /**
     * 显示清除图标
     */
    var showClearIcon: Boolean = true

    /**
     * 显示计数器
     */
    var showCounter: Boolean = false

    /**
     * 计数器最大长度
     */
    var counterMaxLength: Int? = null

    /**
     * 起始图标
     */
    var startIcon: Any? = null

    /**
     * 起始图标色调 (如果不想使用默认色调, 请实现Block返回null)
     */
    var startIconTint: FormColorStateListBlock? = null

    private var startIconOnClickBlock: ((item: FormInput, view: View) -> Unit)? = null

    /**
     * 其实图标点击事件
     */
    fun setStartIconOnClickListener(listener: ((item: FormInput, view: View) -> Unit)?) {
        startIconOnClickBlock = listener
    }

    override fun copyEmptyItem(): BaseForm<CharSequence> = FormInput(null, null)

    override fun onCreateViewHolder(style: AbstractStyle, parent: ViewGroup): View =
        TextInputLayout(
            parent.context, null,
            com.google.android.material.R.attr.textInputOutlinedExposedDropdownMenuStyle
        ).also {
            val editText = MaterialAutoCompleteTextView(
                ContextThemeWrapper(
                    it.context,
                    com.google.android.material.R.style.ThemeOverlay_Material3_AutoCompleteTextView
                )
            ).apply {
                id = R.id.formInternalContentChildView
                isHorizontalFadingEdgeEnabled = true
                isVerticalFadingEdgeEnabled = true
                setFadingEdgeLength(resDimensionPixelSize(R.dimen.formFadingEdgeLength))
                setTextAppearance(formTextAppearance(R.attr.formTextAppearanceContent))
                setPaddingRelative(
                    style.padding.startMedium, style.padding.topMedium,
                    style.padding.endMedium, style.padding.bottomMedium
                )
            }
            it.addView(editText)
            onCreateInputLayout(
                it, editText,
                style.padding.startMedium, style.padding.topMedium,
                style.padding.endMedium, style.padding.bottomMedium
            )
            it.isHintEnabled = false
            it.boxBackgroundMode = TextInputLayout.BOX_BACKGROUND_OUTLINE
            it.boxStrokeWidth = 0
            it.boxStrokeWidthFocused = 0
            it.setPaddingRelative(0, 0, 0, 0)
            it.getChildAt(0).updateLayoutParams<LinearLayout.LayoutParams> {
                topMargin = 0
            }
        }

    protected fun onCreateInputLayout(
        layout: TextInputLayout,
        editText: MaterialAutoCompleteTextView,
        start: Int,
        top: Int,
        end: Int,
        bottom: Int
    ) {
        layout.id = R.id.formInternalContentView
        layout.setHintTextAppearance(layout.formTextAppearance(R.attr.formTextAppearanceHint))
        layout.setPrefixTextAppearance(layout.formTextAppearance(R.attr.formTextAppearancePrefix))
        layout.setSuffixTextAppearance(layout.formTextAppearance(R.attr.formTextAppearanceSuffix))
        layout.placeholderTextAppearance =
            layout.formTextAppearance(R.attr.formTextAppearancePlaceholder)
        layout.setCounterTextAppearance(
            layout.formTextAppearance(R.attr.formTextAppearanceCounter)
        )
        layout.setCounterOverflowTextAppearance(
            layout.formTextAppearance(R.attr.formTextAppearanceCounter)
        )
        layout.tag = editText.textColors
        // StartIcon
        layout.findViewById<CheckableImageButton>(
            com.google.android.material.R.id.text_input_start_icon
        ).apply {
            val iconPadding = resDimensionPixelSize(R.dimen.formIconPadding)
            scaleType = ImageView.ScaleType.CENTER_INSIDE
            minimumHeight = 0
            minimumWidth = 0
            setPaddingRelative(start, top, iconPadding, bottom)
            val fontHeight = FormManager.getFontRealHeight(editText)
            val realWidth = fontHeight + iconPadding + start
            updateLayoutParams<ViewGroup.MarginLayoutParams> {
                marginEnd = 0
                width = realWidth
                height = fontHeight + top + bottom
            }
            val a =
                context.obtainStyledAttributes(intArrayOf(com.google.android.material.R.attr.colorControlHighlight))
            val color = a.getColorStateList(0) ?: ColorStateList.valueOf(Color.GRAY)
            a.recycle()
            background = RippleDrawable(color, null, LayerDrawable(arrayOf(ShapeDrawable().apply {
                paint.color = Color.GRAY
                shape = OvalShape()
            })).apply {
                setLayerGravity(0, Gravity.END or Gravity.CENTER_VERTICAL)
                val size = fontHeight + min(iconPadding, start) * 2
                setLayerWidth(0, size)
                setLayerHeight(0, size)
            })
        }
        // EndIcon
        layout.setEndIconTintList(editText.hintTextColors)
        layout.findViewById<CheckableImageButton>(
            com.google.android.material.R.id.text_input_end_icon
        ).apply {
            val iconPadding = resources.getDimensionPixelSize(R.dimen.formIconPadding)
            background = null
            scaleType = ImageView.ScaleType.CENTER_INSIDE
            minimumHeight = 0
            minimumWidth = 0
            setPaddingRelative(iconPadding, top, end, bottom)
            val fontHeight = FormManager.getFontRealHeight(editText)
            val realWidth = fontHeight + iconPadding + end
            updateLayoutParams<ViewGroup.MarginLayoutParams> {
                marginStart = 0
                width = realWidth
                height = fontHeight + top + top
            }
            editText.setAdapter(
                FormArrayAdapter<CharSequence>(Boundary(start, top, realWidth, bottom))
            )
        }
    }

    override fun onBindViewHolder(
        scope: CoroutineScope,
        holder: FormViewHolder,
        view: View,
        adapterEnabled: Boolean
    ) {
        (view as TextInputLayout).also { layout ->
            layout.isEnabled = isEnable(adapterEnabled)
            layout.prefixText = FormManager.extractText(layout.context, prefix)
            layout.suffixText = FormManager.extractText(layout.context, suffix)
            layout.placeholderText = FormManager.extractText(layout.context, placeholder)
            if (showCounter) {
                layout.isCounterEnabled = true
                layout.counterMaxLength = counterMaxLength ?: maxLength ?: -1
                layout.getChildAt(1)
                    .updatePadding(top = 0, bottom = holder.style.padding.bottomMedium)
            } else layout.isCounterEnabled = false
            val startIcon = FormManager.extractIcon(layout.context, startIcon)
            if (startIcon != null) {
                layout.startIconDrawable = startIcon
                if (startIconTint != null) {
                    layout.setStartIconTintList(startIconTint!!.invoke(layout.context))
                } else {
                    layout.setStartIconTintList(layout.tag as? ColorStateList)
                }
                if (startIconOnClickBlock != null) {
                    layout.setStartIconOnClickListener {
                        startIconOnClickBlock!!.invoke(this, layout)
                    }
                } else layout.setStartIconOnClickListener(null)
            } else {
                layout.startIconDrawable = null
                layout.setStartIconOnClickListener(null)
            }
        }
        view.findViewById<MaterialAutoCompleteTextView>(R.id.formInternalContentChildView)
            .also { editView ->
                if (editView.tag is TextWatcher) editView.removeTextChangedListener(editView.tag as TextWatcher)
                editView.setText(content)
                editView.gravity = obtainContentGravity(holder)
                if (maxLines <= 1) {
                    editView.setSingleLine()
                } else {
                    editView.minLines = minLines
                    editView.maxLines = maxLines
                }
                editView.inputType = if (maxLines <= 1) {
                    inputMode.getInputType()
                } else inputMode.getInputType() or InputType.TYPE_TEXT_FLAG_MULTI_LINE
                editView.transformationMethod = inputMode.getTransformationMethod()
                editView.filters = inputMode.getFilters().apply {
                    if (maxLength != null) add(InputFilter.LengthFilter(maxLength!!))
                }.toTypedArray()
                val watcher = editView.doAfterTextChanged { editable ->
                    changeContentAndNotifyLinkage(
                        holder, if (editable.isNullOrEmpty()) null else editable
                    )
                    val adapter = editView.adapter as FormArrayAdapter<*>
                    adapter.current = editable?.toString()
                }
                editView.tag = watcher
            }
        super.onBindViewHolder(scope, holder, view, adapterEnabled)
    }

    @Suppress("UNCHECKED_CAST")
    override fun configOption(
        scope: CoroutineScope,
        holder: FormViewHolder,
        view: View,
        adapterEnabled: Boolean
    ) {
        val editText =
            view.findViewById<MaterialAutoCompleteTextView>(R.id.formInternalContentChildView)
        val adapter = editText.adapter as FormArrayAdapter<CharSequence>
        adapter.hint = editText.hint
        adapter.setNewData(options, editText.gravity)
        val fontHeight = FormManager.getFontRealHeight(editText)
        with(view as TextInputLayout) {
            when (val result = optionLoadResult) {
                is OptionLoadResult.Wait, is OptionLoadResult.Success -> {
                    TooltipCompat.setTooltipText(this, null)
                    if (isEnable(adapterEnabled) && options != null) {
                        endIconMode = TextInputLayout.END_ICON_DROPDOWN_MENU
                        endIconDrawable = FormManager.extractIconAndChangeSize(
                            context, R.drawable.ic_form_arrow_dropdown, fontHeight
                        )
                    } else if (inputMode.isNeedPasswordToggle()) {
                        endIconMode = TextInputLayout.END_ICON_PASSWORD_TOGGLE
                    } else if (showClearIcon) {
                        endIconMode = TextInputLayout.END_ICON_CLEAR_TEXT
                        endIconDrawable = FormManager.extractIconAndChangeSize(
                            context, R.drawable.ic_form_close, fontHeight
                        )
                    } else endIconMode = TextInputLayout.END_ICON_NONE
                }

                is OptionLoadResult.Loading -> {
                    TooltipCompat.setTooltipText(this, null)
                    val drawable = IndeterminateDrawable.createCircularDrawable(context,
                        CircularProgressIndicatorSpec(context, null).apply {
                            trackThickness = fontHeight / 10
                            indicatorSize = fontHeight / 2
                        })
                    endIconMode = TextInputLayout.END_ICON_CUSTOM
                    endIconDrawable = drawable
                    drawable.start()
                }

                is OptionLoadResult.Error -> {
                    TooltipCompat.setTooltipText(this, result.e.message)
                    endIconMode = TextInputLayout.END_ICON_CUSTOM
                    endIconDrawable = FormManager.extractIconAndChangeSize(
                        context, R.drawable.ic_form_error, fontHeight
                    )
                    setEndIconOnClickListener { loadOption(scope, holder, view, adapterEnabled) }
                }

                is OptionLoadResult.Empty -> {
                    TooltipCompat.setTooltipText(this, null)
                    if (inputMode.isNeedPasswordToggle()) {
                        endIconMode = TextInputLayout.END_ICON_PASSWORD_TOGGLE
                    } else if (showClearIcon) {
                        endIconMode = TextInputLayout.END_ICON_CLEAR_TEXT
                        endIconDrawable = FormManager.extractIconAndChangeSize(
                            context, R.drawable.ic_form_close, fontHeight
                        )
                    } else endIconMode = TextInputLayout.END_ICON_NONE
                }
            }
        }
    }
}