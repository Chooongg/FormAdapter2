package com.chooongg.android.form.style

import android.view.View
import android.view.ViewGroup
import com.chooongg.android.form.holder.FormViewHolder
import com.chooongg.android.form.item.BaseForm
import com.chooongg.android.form.provider.AbstractGroupTitleViewProvider
import com.chooongg.android.form.typeset.AbstractTypeset

abstract class AbstractStyle {

    /**
     * 排版
     */
    open var typeset: AbstractTypeset? = null

    /**
     * 组标题提供器
     */
    open var groupTitleViewProvider: AbstractGroupTitleViewProvider? = null

    /**
     * 子项是否为独立的
     */
    open fun IsIndependentItem() = false

    abstract fun onCreateViewHolder(parent: ViewGroup): ViewGroup?

    open fun onViewAttachedToWindow(holder: FormViewHolder) = Unit

    abstract fun onBindViewHolder(holder: FormViewHolder, layout: ViewGroup, item: BaseForm<*>)

    open fun onViewDetachedFromWindow(holder: FormViewHolder) = Unit

    open fun onViewRecycled(holder: FormViewHolder) = Unit

    protected abstract fun addView(parent: ViewGroup, child: View)

    fun executeAddView(parent: ViewGroup, child: View) = addView(parent, child)

    override fun equals(other: Any?): Boolean {
        if (other !is AbstractStyle) return false
        if (javaClass != other.javaClass) return false
        if (groupTitleViewProvider != other.groupTitleViewProvider) return false
        if (groupChildNameProvider != other.groupChildNameProvider) return false
        if (typeset != other.typeset) return false
        if (horizontalIsSeparateItem != other.horizontalIsSeparateItem) return false
        return true
    }

    override fun hashCode(): Int = javaClass.hashCode()
}