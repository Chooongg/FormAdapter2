package com.chooongg.android.form.provider.groupTitle

import android.view.View
import android.view.ViewGroup
import com.chooongg.android.form.helper.FormTextAppearanceHelper
import com.chooongg.android.form.holder.FormViewHolder
import com.chooongg.android.form.item.BaseForm
import com.chooongg.android.form.item.InternalFormGroupTitle
import com.chooongg.android.form.style.AbstractStyle
import kotlinx.coroutines.CoroutineScope

abstract class AbstractGroupTitleProvider : FormTextAppearanceHelper {

    abstract fun onCreateViewHolder(style: AbstractStyle, parent: ViewGroup): View

    open fun onViewAttachedToWindow(holder: FormViewHolder) = Unit

    abstract fun onBindViewHolder(
        scope: CoroutineScope,
        holder: FormViewHolder,
        view: View,
        item: InternalFormGroupTitle,
        adapterEnabled: Boolean
    )

    open fun onViewDetachedFromWindow(holder: FormViewHolder) = Unit

    open fun onViewRecycled(holder: FormViewHolder) = Unit

    override fun equals(other: Any?): Boolean {
        return javaClass == other?.javaClass
    }

    override fun hashCode(): Int = javaClass.hashCode()
}