package com.chooongg.android.form.item

import android.text.TextUtils
import android.view.View
import android.view.ViewGroup
import com.chooongg.android.form.FormManager
import com.chooongg.android.form.R
import com.chooongg.android.form.holder.FormViewHolder
import com.chooongg.android.form.style.AbstractStyle
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.android.material.textview.MaterialTextView
import kotlinx.coroutines.CoroutineScope

open class FormInput(name: Any?, field: String?) : BaseForm<CharSequence>(name, field) {

    open var minLines: Int = 0

    open var maxLines: Int = Int.MAX_VALUE

    open var ellipsize: TextUtils.TruncateAt = TextUtils.TruncateAt.END

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