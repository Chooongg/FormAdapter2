package com.chooongg.android.form.item

import android.view.View
import android.view.ViewGroup
import com.chooongg.android.form.FormManager
import com.chooongg.android.form.R
import com.chooongg.android.form.holder.FormViewHolder
import com.chooongg.android.form.style.AbstractStyle
import com.google.android.material.textview.MaterialTextView
import kotlinx.coroutines.CoroutineScope

class FormText(name: Any?, field: String?) : BaseForm<Any>(name, field) {

    override fun copyEmptyItem(): BaseForm<Any> = FormText(null, null)

    override fun onCreateViewHolder(style: AbstractStyle, parent: ViewGroup): View =
        MaterialTextView(parent.context).apply {
            setTextAppearance(formTextAppearance(R.attr.formTextAppearanceContent))
        }

    override fun onBindViewHolder(
        scope: CoroutineScope,
        holder: FormViewHolder,
        adapterEnabled: Boolean
    ) {
        (holder.view as MaterialTextView).also {
            it.text = FormManager.extractText(it.context, content)
            it.hint = FormManager.extractText(it.context, hint)
        }
    }
}