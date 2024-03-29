package com.chooongg.android.form.provider.detail

import android.view.View
import android.view.ViewGroup
import com.chooongg.android.form.FormAdapter
import com.chooongg.android.form.helper.FormTextAppearanceHelper
import com.chooongg.android.form.holder.FormViewHolder
import com.chooongg.android.form.item.FormDetail
import com.chooongg.android.form.style.AbstractStyle
import kotlinx.coroutines.CoroutineScope

abstract class AbstractDetailProvider : FormTextAppearanceHelper {

    abstract fun onCreateViewHolder(style: AbstractStyle, parent: ViewGroup): View

    open fun onViewAttachedToWindow(holder: FormViewHolder) = Unit

    abstract fun onBindViewHolder(
        scope: CoroutineScope,
        holder: FormViewHolder,
        view: View,
        item: FormDetail,
        adapterEnabled: Boolean
    )

    abstract fun onBindViewItemClick(
        adapter: FormAdapter,
        scope: CoroutineScope,
        holder: FormViewHolder,
        item: FormDetail,
        adapterEnabled: Boolean
    )

    open fun onViewDetachedFromWindow(holder: FormViewHolder) = Unit

    open fun onViewRecycled(holder: FormViewHolder) = Unit

    override fun equals(other: Any?): Boolean {
        return javaClass == other?.javaClass
    }

    override fun hashCode(): Int = javaClass.hashCode()
}