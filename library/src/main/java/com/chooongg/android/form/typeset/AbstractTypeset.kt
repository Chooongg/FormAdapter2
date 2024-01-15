package com.chooongg.android.form.typeset

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.chooongg.android.form.FormManager
import com.chooongg.android.form.enum.FormContentGravity
import com.chooongg.android.form.enum.FormEmsMode
import com.chooongg.android.form.enum.FormEmsSize
import com.chooongg.android.form.formatter.name.AbstractNameFormatter
import com.chooongg.android.form.helper.FormTextAppearanceHelper
import com.chooongg.android.form.holder.FormViewHolder
import com.chooongg.android.form.item.BaseForm
import com.chooongg.android.form.part.AbstractPart
import com.chooongg.android.form.style.AbstractStyle

/**
 * 排版
 */
abstract class AbstractTypeset : FormTextAppearanceHelper {

    /**
     * Ems模式
     */
    abstract var emsMode: FormEmsMode

    /**
     * Ems值
     */
    open var emsSize: FormEmsSize? = null

    /**
     * 名称格式化程序
     */
    open var nameFormatter: AbstractNameFormatter? = null

    open var contentGravity: FormContentGravity? = null

    abstract fun onCreateViewHolder(style: AbstractStyle, parent: ViewGroup): ViewGroup?

    protected abstract fun addView(style: AbstractStyle, parent: ViewGroup, child: View)

    open fun onViewAttachedToWindow(holder: FormViewHolder) = Unit

    abstract fun onBindViewHolder(
        holder: FormViewHolder,
        item: BaseForm<*>,
        adapterEnabled: Boolean
    )

    open fun onViewDetachedFromWindow(holder: FormViewHolder) = Unit

    open fun onViewRecycled(holder: FormViewHolder) = Unit

    fun executeAddView(style: AbstractStyle, parent: ViewGroup, child: View) =
        addView(style, parent, child)

    fun setNameViewEms(holder: FormViewHolder, textView: TextView) {
        val part = holder.bindingAdapter as? AbstractPart
        val size = emsSize ?: FormManager.default.emsSize
        val isMultiColumn = (part?.adapter?.columnCount ?: 1) > 1
        when (if (isMultiColumn) emsMode.multiColumnMode else emsMode.mode) {
            FormEmsMode.MIN -> {
                textView.minEms = if (isMultiColumn) size.multiColumnSize else size.size
                textView.maxWidth = Int.MAX_VALUE
            }

            FormEmsMode.MAX -> {
                textView.minWidth = Int.MAX_VALUE
                textView.maxEms = if (isMultiColumn) size.multiColumnSize else size.size
            }

            FormEmsMode.FIXED -> {
                textView.setEms(if (isMultiColumn) size.multiColumnSize else size.size)
            }

            else -> {
                textView.minWidth = 0
                textView.maxWidth = Int.MAX_VALUE
            }
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is AbstractTypeset) return false
        if (javaClass != other.javaClass) return false
        if (emsMode != other.emsMode) return false
        if (emsSize != other.emsSize) return false
        if (nameFormatter != other.nameFormatter) return false
        return contentGravity == other.contentGravity
    }

    override fun hashCode(): Int = javaClass.hashCode()
}