package com.chooongg.android.form.typeset

import android.view.View
import android.view.ViewGroup
import com.chooongg.android.form.holder.FormViewHolder
import com.chooongg.android.form.item.BaseForm
import com.chooongg.android.form.style.AbstractStyle

abstract class AbstractTypeset {

    abstract fun onCreateViewHolder(style: AbstractStyle, parent: ViewGroup): ViewGroup?

    open fun onViewAttachedToWindow(holder: FormViewHolder) = Unit

    abstract fun onBindViewHolder(
        holder: FormViewHolder,
        layout: ViewGroup,
        item: BaseForm<*>,
        adapterEnabled: Boolean
    )

    open fun onViewDetachedFromWindow(holder: FormViewHolder) = Unit

    open fun onViewRecycled(holder: FormViewHolder) = Unit

    protected abstract fun addView(style: AbstractStyle, parent: ViewGroup, child: View)

    fun executeAddView(style: AbstractStyle, parent: ViewGroup, child: View) =
        addView(style, parent, child)

}