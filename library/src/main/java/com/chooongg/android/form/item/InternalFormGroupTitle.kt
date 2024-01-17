package com.chooongg.android.form.item

import android.view.View
import android.view.ViewGroup
import com.chooongg.android.form.FormManager
import com.chooongg.android.form.holder.FormViewHolder
import com.chooongg.android.form.style.AbstractStyle
import kotlinx.coroutines.CoroutineScope
import java.util.UUID

class InternalFormGroupTitle(id: String?) : BaseForm<CharSequence>(null, null) {

    override val id: String = id ?: UUID.randomUUID().toString()

    override fun copyEmptyItem() = InternalFormGroupTitle(null)

    override fun onCreateViewHolder(style: AbstractStyle, parent: ViewGroup): View {
        val provider = style.groupTitleProvider ?: FormManager.default.groupTitleProvider
        return provider.onCreateViewHolder(style, parent)
    }

    override fun onBindViewHolder(
        scope: CoroutineScope,
        holder: FormViewHolder,
        adapterEnabled: Boolean
    ) {
        val provider = holder.style.groupTitleProvider ?: FormManager.default.groupTitleProvider
        provider.onBindViewHolder(scope, holder, this, adapterEnabled)
    }
}