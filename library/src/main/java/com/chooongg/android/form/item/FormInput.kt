package com.chooongg.android.form.item

import android.text.TextUtils
import android.view.View
import android.view.ViewGroup
import com.chooongg.android.form.FormColorStateListBlock
import com.chooongg.android.form.FormManager
import com.chooongg.android.form.R
import com.chooongg.android.form.holder.FormViewHolder
import com.chooongg.android.form.style.AbstractStyle
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.android.material.textview.MaterialTextView
import kotlinx.coroutines.CoroutineScope

open class FormInput(name: Any?, field: String?) : BaseForm<CharSequence>(name, field) {

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
        TextInputLayout(parent.context).also {
            it.addView(TextInputEditText(it.context).apply {
                setTextAppearance(formTextAppearance(R.attr.formTextAppearanceContent))
            })
        }

    override fun onBindViewHolder(
        scope: CoroutineScope,
        holder: FormViewHolder,
        view: View,
        adapterEnabled: Boolean
    ) {
        (view as MaterialTextView).also {
            it.minLines = minLines
            it.maxLines = maxLines
            it.ellipsize = ellipsize
            it.text = FormManager.extractText(it.context, content)
            it.hint = FormManager.extractText(it.context, hint)
        }
    }
}