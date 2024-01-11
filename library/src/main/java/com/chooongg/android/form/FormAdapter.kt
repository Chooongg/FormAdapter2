package com.chooongg.android.form

import androidx.recyclerview.widget.RecyclerView
import com.chooongg.android.form.listener.FormOnMenuItemClickListener

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
}