package com.chooongg.android.form

import androidx.recyclerview.widget.RecyclerView
import com.chooongg.android.form.data.FormPartData
import com.chooongg.android.form.item.BaseForm
import com.chooongg.android.form.listener.FormOnMenuItemClickListener
import com.chooongg.android.form.part.AbstractPart
import com.chooongg.android.form.part.FormPart
import com.chooongg.android.form.style.AbstractStyle
import com.chooongg.android.form.style.EmptyStyle

class FormAdapter(
    isEnabled: Boolean = false,
    val default: FormManager.Default = FormManager.default
) : AbstractFormAdapter() {

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
        concatAdapter.addAdapter(part)
        part.data = FormPartData().apply(block)
        return part
    }

    fun addPart(part: AbstractPart) {

    }
}