package com.chooongg.android.form.item

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

class FormInput(name: Any?, field: String?) : FormText(name, field) {

    override fun copyEmptyItem(): BaseForm<Any> = FormInput(null, null)

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