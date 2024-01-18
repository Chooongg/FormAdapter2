package com.chooongg.android.form

import androidx.recyclerview.widget.RecyclerView
import com.chooongg.android.form.data.FormPartData
import com.chooongg.android.form.listener.FormOnMenuItemClickListener
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
            }
        }

    /**
     * 列数
     */
    var columnCount = 1
        set(value) {
            if (field != value) {
                field = value
            }
        }

    var onMenuItemClickListener: FormOnMenuItemClickListener? = null
        private set


    fun setOnMenuItemCLickListener(listener: FormOnMenuItemClickListener?) {
        onMenuItemClickListener = listener
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