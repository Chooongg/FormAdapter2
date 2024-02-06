package com.chooongg.android.form.item

import android.view.View
import android.view.ViewGroup
import com.chooongg.android.form.data.AbstractPartData
import com.chooongg.android.form.holder.FormViewHolder
import com.chooongg.android.form.style.AbstractStyle
import kotlinx.coroutines.CoroutineScope

class FormDetail(name: Any?, field: String?) : BaseForm<AbstractPartData>(name, field) {
    override fun copyEmptyItem(): BaseForm<AbstractPartData> {
        TODO("Not yet implemented")
    }

    override fun onCreateViewHolder(style: AbstractStyle, parent: ViewGroup): View {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(
        scope: CoroutineScope,
        holder: FormViewHolder,
        view: View,
        adapterEnabled: Boolean
    ) {
        TODO("Not yet implemented")
    }
}