package com.chooongg.android.form.item

import android.animation.AnimatorInflater
import android.content.Context
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.res.use
import androidx.core.view.updateLayoutParams
import com.chooongg.android.form.FormAdapter
import com.chooongg.android.form.FormManager
import com.chooongg.android.form.R
import com.chooongg.android.form.holder.FormViewHolder
import com.chooongg.android.form.style.AbstractStyle
import com.chooongg.android.form.typeset.AbstractTypeset
import com.chooongg.android.form.typeset.NoneTypeset
import com.chooongg.android.ktx.doOnClick
import com.google.android.material.button.MaterialButton
import com.google.android.material.shape.ShapeAppearanceModel
import com.google.android.material.theme.overlay.MaterialThemeOverlay
import kotlinx.coroutines.CoroutineScope

class FormButton(name: Any?, field: String?) : BaseForm<Any>(name, field) {

    enum class ButtonStyle {
        ELEVATED, UN_ELEVATED,
        PRIMARY, PRIMARY_TONAL, TEXT, PRIMARY_OUTLINED,
        SECONDARY, TONAL, SECONDARY_TEXT, OUTLINED,
        TERTIARY, TERTIARY_TONAL, TERTIARY_TEXT, TERTIARY_OUTLINED,
        ERROR, ERROR_TONAL, ERROR_TEXT, ERROR_OUTLINED,
        CUSTOM1, CUSTOM2, CUSTOM3
    }

    /**
     * 按钮主题
     */
    var buttonStyle: ButtonStyle? = null

    /**
     * 文本重力
     */
    var textGravity: Int? = null

    /**
     * 高度
     */
    var elevation: Float? = null

    override var typeset: AbstractTypeset? = NoneTypeset()

    override var gravity: Int? = Gravity.NO_GRAVITY

    override fun copyEmptyItem(): BaseForm<Any> = FormButton(null, null)

    override fun onCreateViewHolder(style: AbstractStyle, parent: ViewGroup) =
        ConstraintLayout(parent.context).also {
            it.addView(MaterialButton(parent.context).apply {
                id = R.id.formInternalContentView
                layoutParams = ConstraintLayout.LayoutParams(0, -2).apply {
                    matchConstraintMaxWidth = maxWidth
                    startToStart = ConstraintLayout.LayoutParams.PARENT_ID
                    endToEnd = ConstraintLayout.LayoutParams.PARENT_ID
                    topMargin = style.padding.topMedium - insetTop
                    bottomMargin = style.padding.bottomMedium - insetBottom
                    marginStart = style.padding.startMedium
                    marginEnd = style.padding.endMedium
                }
            })
        }

    override fun onBindViewHolder(
        scope: CoroutineScope,
        holder: FormViewHolder,
        view: View,
        adapterEnabled: Boolean
    ) {
        view.findViewById<MaterialButton>(R.id.formInternalContentView).also {
            it.isEnabled = isEnable(adapterEnabled)
            it.text = FormManager.extractText(it.context, name)
            it.hint = FormManager.extractText(it.context, hint)
            it.gravity = textGravity ?: Gravity.CENTER
            it.icon = FormManager.extractIcon(it.context, icon)
            it.iconSize = iconSize ?: FormManager.getFontRealHeight(it)
            it.iconGravity = iconGravity
            configButtonGravity(it, obtainContentGravity(holder))
            configButtonStyle(it)
        }
    }

    private fun configButtonGravity(view: MaterialButton, contentGravity: Int) {
        view.updateLayoutParams<ConstraintLayout.LayoutParams> {
            when {
                contentGravity == Gravity.TOP -> {
                    width = ConstraintLayout.LayoutParams.MATCH_CONSTRAINT
                    startToStart = ConstraintLayout.LayoutParams.PARENT_ID
                    endToEnd = ConstraintLayout.LayoutParams.PARENT_ID
                    topToTop = ConstraintLayout.LayoutParams.PARENT_ID
                    bottomToBottom = ConstraintLayout.LayoutParams.UNSET
                }

                contentGravity == Gravity.BOTTOM -> {
                    width = ConstraintLayout.LayoutParams.MATCH_CONSTRAINT
                    startToStart = ConstraintLayout.LayoutParams.PARENT_ID
                    endToEnd = ConstraintLayout.LayoutParams.PARENT_ID
                    topToTop = ConstraintLayout.LayoutParams.UNSET
                    bottomToBottom = ConstraintLayout.LayoutParams.PARENT_ID
                }

                contentGravity == Gravity.START -> {
                    width = ConstraintLayout.LayoutParams.WRAP_CONTENT
                    startToStart = ConstraintLayout.LayoutParams.PARENT_ID
                    endToEnd = ConstraintLayout.LayoutParams.UNSET
                    topToTop = ConstraintLayout.LayoutParams.PARENT_ID
                    bottomToBottom = ConstraintLayout.LayoutParams.PARENT_ID
                }

                contentGravity == Gravity.END -> {
                    width = ConstraintLayout.LayoutParams.WRAP_CONTENT
                    startToStart = ConstraintLayout.LayoutParams.UNSET
                    endToEnd = ConstraintLayout.LayoutParams.PARENT_ID
                    topToTop = ConstraintLayout.LayoutParams.PARENT_ID
                    bottomToBottom = ConstraintLayout.LayoutParams.PARENT_ID
                }

                contentGravity == Gravity.START or Gravity.TOP -> {
                    width = ConstraintLayout.LayoutParams.WRAP_CONTENT
                    startToStart = ConstraintLayout.LayoutParams.PARENT_ID
                    endToEnd = ConstraintLayout.LayoutParams.UNSET
                    topToTop = ConstraintLayout.LayoutParams.PARENT_ID
                    bottomToBottom = ConstraintLayout.LayoutParams.UNSET
                }

                contentGravity == Gravity.START or Gravity.BOTTOM -> {
                    width = ConstraintLayout.LayoutParams.WRAP_CONTENT
                    startToStart = ConstraintLayout.LayoutParams.PARENT_ID
                    endToEnd = ConstraintLayout.LayoutParams.UNSET
                    topToTop = ConstraintLayout.LayoutParams.UNSET
                    bottomToBottom = ConstraintLayout.LayoutParams.PARENT_ID
                }

                contentGravity == Gravity.END or Gravity.TOP -> {
                    width = ConstraintLayout.LayoutParams.WRAP_CONTENT
                    startToStart = ConstraintLayout.LayoutParams.UNSET
                    endToEnd = ConstraintLayout.LayoutParams.PARENT_ID
                    topToTop = ConstraintLayout.LayoutParams.PARENT_ID
                    bottomToBottom = ConstraintLayout.LayoutParams.UNSET
                }

                contentGravity == Gravity.END or Gravity.BOTTOM -> {
                    width = ConstraintLayout.LayoutParams.WRAP_CONTENT
                    startToStart = ConstraintLayout.LayoutParams.UNSET
                    endToEnd = ConstraintLayout.LayoutParams.PARENT_ID
                    topToTop = ConstraintLayout.LayoutParams.UNSET
                    bottomToBottom = ConstraintLayout.LayoutParams.PARENT_ID
                }

                contentGravity and Gravity.CENTER == Gravity.CENTER -> {
                    width = ConstraintLayout.LayoutParams.WRAP_CONTENT
                    startToStart = ConstraintLayout.LayoutParams.PARENT_ID
                    endToEnd = ConstraintLayout.LayoutParams.PARENT_ID
                    topToTop = ConstraintLayout.LayoutParams.PARENT_ID
                    bottomToBottom = ConstraintLayout.LayoutParams.PARENT_ID
                }

                contentGravity and Gravity.CENTER_HORIZONTAL == Gravity.CENTER_HORIZONTAL -> {
                    width = ConstraintLayout.LayoutParams.WRAP_CONTENT
                    startToStart = ConstraintLayout.LayoutParams.PARENT_ID
                    endToEnd = ConstraintLayout.LayoutParams.PARENT_ID
                    if (contentGravity and Gravity.TOP == Gravity.TOP) {
                        topToTop = ConstraintLayout.LayoutParams.PARENT_ID
                        bottomToBottom = ConstraintLayout.LayoutParams.UNSET
                    } else if (contentGravity and Gravity.BOTTOM == Gravity.BOTTOM) {
                        topToTop = ConstraintLayout.LayoutParams.UNSET
                        bottomToBottom = ConstraintLayout.LayoutParams.PARENT_ID
                    } else {
                        topToTop = ConstraintLayout.LayoutParams.PARENT_ID
                        bottomToBottom = ConstraintLayout.LayoutParams.PARENT_ID
                    }
                }

                contentGravity and Gravity.CENTER_VERTICAL == Gravity.CENTER_VERTICAL -> {
                    width = ConstraintLayout.LayoutParams.WRAP_CONTENT
                    topToTop = ConstraintLayout.LayoutParams.PARENT_ID
                    bottomToBottom = ConstraintLayout.LayoutParams.PARENT_ID
                    if (contentGravity and Gravity.START == Gravity.START) {
                        startToStart = ConstraintLayout.LayoutParams.PARENT_ID
                        endToEnd = ConstraintLayout.LayoutParams.UNSET
                    } else if (contentGravity and Gravity.END == Gravity.END) {
                        startToStart = ConstraintLayout.LayoutParams.UNSET
                        endToEnd = ConstraintLayout.LayoutParams.PARENT_ID
                    } else {
                        startToStart = ConstraintLayout.LayoutParams.PARENT_ID
                        endToEnd = ConstraintLayout.LayoutParams.PARENT_ID
                    }
                }

                else -> {
                    width = ConstraintLayout.LayoutParams.MATCH_CONSTRAINT
                    startToStart = ConstraintLayout.LayoutParams.PARENT_ID
                    endToEnd = ConstraintLayout.LayoutParams.PARENT_ID
                    topToTop = ConstraintLayout.LayoutParams.PARENT_ID
                    bottomToBottom = ConstraintLayout.LayoutParams.PARENT_ID
                }
            }
        }
    }

    private fun configButtonStyle(view: MaterialButton) {
        val style = getButtonStyle(view.context, buttonStyle)
        val wrap = MaterialThemeOverlay.wrap(view.context, null, 0, style)
        view.setTextColor(wrap.obtainStyledAttributes(
            style, intArrayOf(android.R.attr.textColor)
        ).use { it.getColorStateList(0) })
        view.iconTint = if (iconTint == null) {
            wrap.obtainStyledAttributes(
                style, intArrayOf(androidx.appcompat.R.attr.iconTint)
            ).use { it.getColorStateList(0) }
        } else iconTint!!.invoke(view.context)
        view.backgroundTintList = wrap.obtainStyledAttributes(
            style, intArrayOf(androidx.appcompat.R.attr.backgroundTint)
        ).use { it.getColorStateList(0) }
        view.strokeColor = wrap.obtainStyledAttributes(
            style, intArrayOf(com.google.android.material.R.attr.strokeColor)
        ).use { it.getColorStateList(0) }
        view.strokeWidth = wrap.obtainStyledAttributes(
            style, intArrayOf(com.google.android.material.R.attr.strokeWidth)
        ).use { it.getDimensionPixelSize(0, 0) }
        view.rippleColor = wrap.obtainStyledAttributes(
            style, intArrayOf(com.google.android.material.R.attr.rippleColor)
        ).use { it.getColorStateList(0) }
        view.elevation = elevation ?: wrap.obtainStyledAttributes(
            style, intArrayOf(com.google.android.material.R.attr.elevation)
        ).use { it.getDimension(0, 0f) }
        view.shapeAppearanceModel =
            ShapeAppearanceModel.builder(
                view.context, wrap.obtainStyledAttributes(
                    style, intArrayOf(com.google.android.material.R.attr.shapeAppearance)
                ).use {
                    it.getResourceId(
                        0, com.google.android.material.R.style.ShapeAppearance_Material3_Corner_Full
                    )
                }, 0
            ).build()
        val stateListId = wrap.obtainStyledAttributes(
            style, intArrayOf(android.R.attr.stateListAnimator)
        ).use { it.getResourceId(0, 0) }
        view.stateListAnimator = if (stateListId != 0 && elevation == null) {
            AnimatorInflater.loadStateListAnimator(wrap, stateListId)
        } else null
    }

    override fun onBindViewItemClick(
        adapter: FormAdapter,
        scope: CoroutineScope,
        holder: FormViewHolder,
        adapterEnabled: Boolean
    ) {
        val view = holder.itemView.findViewById<MaterialButton>(R.id.formInternalContentView)
        if (isEnable(adapterEnabled)) {
            view.doOnClick { adapter.onItemClickListener?.invoke(it, this) }
        } else view.setOnClickListener(null)
    }

    protected fun getButtonStyle(context: Context, buttonStyle: ButtonStyle?): Int {
        val attr = when (buttonStyle) {
            ButtonStyle.ELEVATED -> R.attr.formButtonElevatedStyle
            ButtonStyle.UN_ELEVATED -> R.attr.formButtonUnelevatedStyle
            ButtonStyle.PRIMARY -> R.attr.formButtonPrimaryStyle
            ButtonStyle.PRIMARY_TONAL -> R.attr.formButtonPrimaryTonalStyle
            ButtonStyle.TEXT -> R.attr.formButtonTextStyle
            ButtonStyle.PRIMARY_OUTLINED -> R.attr.formButtonPrimaryOutlinedStyle
            ButtonStyle.SECONDARY -> R.attr.formButtonSecondaryStyle
            ButtonStyle.SECONDARY_TEXT -> R.attr.formButtonSecondaryTextStyle
            ButtonStyle.TONAL -> R.attr.formButtonTonalStyle
            ButtonStyle.OUTLINED -> R.attr.formButtonOutlinedStyle
            ButtonStyle.TERTIARY -> R.attr.formButtonTertiaryStyle
            ButtonStyle.TERTIARY_TONAL -> R.attr.formButtonTertiaryTonalStyle
            ButtonStyle.TERTIARY_TEXT -> R.attr.formButtonTertiaryTextStyle
            ButtonStyle.TERTIARY_OUTLINED -> R.attr.formButtonTertiaryOutlinedStyle
            ButtonStyle.ERROR -> R.attr.formButtonErrorStyle
            ButtonStyle.ERROR_TONAL -> R.attr.formButtonErrorTonalStyle
            ButtonStyle.ERROR_TEXT -> R.attr.formButtonErrorTextStyle
            ButtonStyle.ERROR_OUTLINED -> R.attr.formButtonErrorOutlinedStyle
            ButtonStyle.CUSTOM1 -> R.attr.formButtonCustom1Style
            ButtonStyle.CUSTOM2 -> R.attr.formButtonCustom2Style
            ButtonStyle.CUSTOM3 -> R.attr.formButtonCustom3Style
            else -> R.attr.formButtonStyle
        }
        val default = when (buttonStyle) {
            ButtonStyle.ELEVATED -> R.style.Form_Button_ElevatedButton
            ButtonStyle.UN_ELEVATED -> R.style.Form_Button_UnelevatedButton
            ButtonStyle.PRIMARY -> R.style.Form_Button_PrimaryButton
            ButtonStyle.PRIMARY_TONAL -> R.style.Form_Button_PrimaryTonalButton
            ButtonStyle.TEXT -> R.style.Form_Button_TextButton
            ButtonStyle.PRIMARY_OUTLINED -> R.style.Form_Button_PrimaryOutlinedButton
            ButtonStyle.SECONDARY -> R.style.Form_Button_SecondaryButton
            ButtonStyle.TONAL -> R.style.Form_Button_TonalButton
            ButtonStyle.SECONDARY_TEXT -> R.style.Form_Button_SecondaryTextButton
            ButtonStyle.OUTLINED -> R.style.Form_Button_OutlinedButton
            ButtonStyle.TERTIARY -> R.style.Form_Button_TertiaryButton
            ButtonStyle.TERTIARY_TONAL -> R.style.Form_Button_TertiaryTonalButton
            ButtonStyle.TERTIARY_TEXT -> R.style.Form_Button_TertiaryTextButton
            ButtonStyle.TERTIARY_OUTLINED -> R.style.Form_Button_TertiaryOutlinedButton
            ButtonStyle.ERROR -> R.style.Form_Button_ErrorButton
            ButtonStyle.ERROR_TONAL -> R.style.Form_Button_ErrorTonalButton
            ButtonStyle.ERROR_TEXT -> R.style.Form_Button_ErrorTextButton
            ButtonStyle.ERROR_OUTLINED -> R.style.Form_Button_ErrorOutlinedButton
            else -> R.style.Form_Button
        }
        return context.obtainStyledAttributes(intArrayOf(attr)).use { it.getResourceId(0, default) }
    }
}