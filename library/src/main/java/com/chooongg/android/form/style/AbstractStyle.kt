package com.chooongg.android.form.style

import android.content.Context
import android.view.View
import android.view.ViewGroup
import com.chooongg.android.form.R
import com.chooongg.android.form.boundary.FormSizeInfo
import com.chooongg.android.form.holder.FormViewHolder
import com.chooongg.android.form.item.BaseForm
import com.chooongg.android.form.provider.AbstractGroupTitleProvider
import com.chooongg.android.form.typeset.AbstractTypeset
import com.chooongg.android.ktx.resDimensionPixelSize

/**
 * 样式
 */
abstract class AbstractStyle {

    /**
     * 排版
     */
    open var typeset: AbstractTypeset? = null

    /**
     * 组标题视图提供器
     */
    open var groupTitleProvider: AbstractGroupTitleProvider? = null

    /**
     *
     */
    var margin: FormSizeInfo = FormSizeInfo()

    var padding: FormSizeInfo = FormSizeInfo()

    /**
     * 是否为独立的项目
     */
    open fun IsIndependentItem() = false

    /**
     * 是否装饰空项目
     */
    open fun isDecorateNoneItem(): Boolean = true

    private var isInstanceSizeInfo: Boolean = false

    internal fun createSizeInfo(context: Context) {
        if (isInstanceSizeInfo) return
        margin = onCreateMarginSize(context)
        padding = onCreatePaddingSize(context)
        isInstanceSizeInfo = true
    }

    protected fun onCreateMarginSize(context: Context): FormSizeInfo = FormSizeInfo(
        context.resDimensionPixelSize(R.dimen.formMarginStart),
        context.resDimensionPixelSize(R.dimen.formMarginTop),
        context.resDimensionPixelSize(R.dimen.formMarginEnd),
        context.resDimensionPixelSize(R.dimen.formMarginBottom),
        context.resDimensionPixelSize(R.dimen.formMarginStartMedium),
        context.resDimensionPixelSize(R.dimen.formMarginTopMedium),
        context.resDimensionPixelSize(R.dimen.formMarginEndMedium),
        context.resDimensionPixelSize(R.dimen.formMarginBottomMedium),
    )

    protected fun onCreatePaddingSize(context: Context): FormSizeInfo = FormSizeInfo(
        context.resDimensionPixelSize(R.dimen.formPaddingStart),
        context.resDimensionPixelSize(R.dimen.formPaddingTop),
        context.resDimensionPixelSize(R.dimen.formPaddingEnd),
        context.resDimensionPixelSize(R.dimen.formPaddingBottom),
        context.resDimensionPixelSize(R.dimen.formPaddingStartMedium),
        context.resDimensionPixelSize(R.dimen.formPaddingTopMedium),
        context.resDimensionPixelSize(R.dimen.formPaddingEndMedium),
        context.resDimensionPixelSize(R.dimen.formPaddingBottomMedium),
    )

    abstract fun onCreateViewHolder(parent: ViewGroup): ViewGroup?

    protected abstract fun addView(parent: ViewGroup, child: View)

    open fun onViewAttachedToWindow(holder: FormViewHolder) = Unit

    abstract fun onBindViewHolder(
        holder: FormViewHolder,
        item: BaseForm<*>,
        layout: ViewGroup,
        adapterEnabled: Boolean
    )

    open fun onViewDetachedFromWindow(holder: FormViewHolder) = Unit

    open fun onViewRecycled(holder: FormViewHolder) = Unit

    fun executeAddView(parent: ViewGroup, child: View) = addView(parent, child)

    override fun equals(other: Any?): Boolean {
        if (other !is AbstractStyle) return false
        if (javaClass != other.javaClass) return false
        if (groupTitleProvider != other.groupTitleProvider) return false
//        if (groupChildNameProvider != other.groupChildNameProvider) return false
        return typeset == other.typeset
    }

    override fun hashCode(): Int = javaClass.hashCode()
}