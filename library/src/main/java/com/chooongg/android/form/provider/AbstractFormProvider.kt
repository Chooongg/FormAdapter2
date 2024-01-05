package com.chooongg.android.form.provider

import android.view.View
import android.view.ViewGroup
import com.chooongg.android.form.holder.FormViewHolder
import com.chooongg.android.form.item.BaseForm
import com.chooongg.android.form.style.AbstractStyle
import kotlinx.coroutines.CoroutineScope

abstract class AbstractFormProvider<T : BaseForm<*>> {

    abstract fun onCreateViewHolder(style: AbstractStyle, parent: ViewGroup): View

    abstract fun onBindViewHolder(
        scope: CoroutineScope,
        holder: FormViewHolder,
        item: T,
        enabled: Boolean
    )
}