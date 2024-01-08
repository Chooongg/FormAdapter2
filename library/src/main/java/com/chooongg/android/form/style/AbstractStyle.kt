package com.chooongg.android.form.style

import android.view.View
import android.view.ViewGroup
import com.chooongg.android.form.holder.FormViewHolder
import com.chooongg.android.form.item.BaseForm
import com.chooongg.android.form.typeset.AbstractTypeset

abstract class AbstractStyle {

    /**
     * 排版
     */
    open var typeset: AbstractTypeset? = null

    abstract fun onCreateViewHolder(parent: ViewGroup): ViewGroup?

    open fun onViewAttachedToWindow(holder: FormViewHolder) = Unit

    abstract fun onBindViewHolder(holder: FormViewHolder, layout: ViewGroup, item: BaseForm<*>)

    open fun onViewDetachedFromWindow(holder: FormViewHolder) = Unit

    open fun onViewRecycled(holder: FormViewHolder) = Unit

    protected abstract fun addView(parent: ViewGroup, child: View)

    fun executeAddView(parent: ViewGroup, child: View) = addView(parent, child)

}