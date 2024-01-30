package com.chooongg.android.form

import androidx.annotation.IntRange
import androidx.recyclerview.widget.RecyclerView
import com.chooongg.android.form.data.FormPartData
import com.chooongg.android.form.part.FormPart
import com.chooongg.android.form.style.AbstractStyle
import com.chooongg.android.form.style.EmptyStyle

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

    fun addPart(style: AbstractStyle = EmptyStyle(), block: FormPartData.() -> Unit): FormPart {
        val part = FormPart(this, style)
        part.data = FormPartData().apply(block)
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