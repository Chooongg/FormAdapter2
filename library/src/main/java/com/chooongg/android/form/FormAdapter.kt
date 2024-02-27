package com.chooongg.android.form

import androidx.annotation.IntRange
import androidx.recyclerview.widget.RecyclerView
import com.chooongg.android.form.data.FormPartData
import com.chooongg.android.form.part.FormPart
import com.chooongg.android.form.style.AbstractStyle

class FormAdapter(isEnabled: Boolean = false) : AbstractFormAdapter() {

    val formPool = RecyclerView.RecycledViewPool()

    /**
     * 是否启用
     */
    var isEnabled: Boolean = isEnabled
        set(value) {
            if (field != value) {
                field = value
                update()
            }
        }

    /**
     * 列数
     */
    @IntRange(from = 1)
    var columnCount: Int? = null
        set(value) {
            if (field != value) {
                field = value
                update()
            }
        }

    var onItemClickListener: FormOnItemClickListener? = null
        private set

    var onMenuClickListener: FormOnMenuClickListener? = null
        private set

    fun setOnItemClickListener(listener: FormOnItemClickListener?) {
        onItemClickListener = listener
    }

    fun setOnMenuClickListener(listener: FormOnMenuClickListener?) {
        onMenuClickListener = listener
    }

    internal var isCustomColumn: Boolean = false

    internal fun setColumnCountInternal(column: Int) {
        if (!isCustomColumn) columnCount = column
    }

    fun setColumnCount(column: Int) {
        val isDifference = columnCount != column
        columnCount = column
        if (isDifference) update()
        isCustomColumn = true
    }

    fun addPart(
        style: AbstractStyle = FormManager.defaultStyle,
        block: FormPartData.() -> Unit
    ): FormPart {
        val part = FormPart(this, style, FormPartData().apply(block))
        concatAdapter.addAdapter(part)
        part.update()
        return part
    }

    fun addPart(
        style: AbstractStyle = FormManager.defaultStyle,
        data: FormPartData
    ): FormPart {
        val part = FormPart(this, style, data)
        concatAdapter.addAdapter(part)
        part.update()
        return part
    }

    fun executeLinkage(isIgnoreUpdate: Boolean = false) {
        partAdapters.forEach {
            it.executeLinkage(isIgnoreUpdate)
        }
    }
}