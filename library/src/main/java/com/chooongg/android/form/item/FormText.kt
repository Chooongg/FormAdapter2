package com.chooongg.android.form.item

import android.text.TextUtils
import android.view.View
import android.view.ViewGroup
import com.chooongg.android.form.FormManager
import com.chooongg.android.form.R
import com.chooongg.android.form.holder.FormViewHolder
import com.chooongg.android.form.style.AbstractStyle
import com.google.android.material.textview.MaterialTextView
import kotlinx.coroutines.CoroutineScope

open class FormText(name: Any?, field: String?) : BaseForm<Any>(name, field) {

    open var minLines: Int = 0

    open var maxLines: Int = Int.MAX_VALUE

    open var ellipsize: TextUtils.TruncateAt = TextUtils.TruncateAt.END

    override fun copyEmptyItem(): BaseForm<Any> = FormText(null, null)

    override fun onCreateViewHolder(style: AbstractStyle, parent: ViewGroup): View =
        MaterialTextView(parent.context).apply {
            setTextAppearance(formTextAppearance(R.attr.formTextAppearanceContent))
            setPaddingRelative(
                style.padding.startMedium,
                style.padding.topMedium,
                style.padding.endMedium,
                style.padding.bottomMedium
            )
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
            it.gravity = obtainContentGravity(holder)
        }
    }
}