package com.chooongg.android.form

import androidx.recyclerview.widget.RecyclerView

class FormAdapter(
    isEnabled: Boolean = false,
    val default: FormManager.Default = FormManager.default
) : AbstractFormAdapter() {

    val formPool = RecyclerView.RecycledViewPool()

    var isEnabled: Boolean = isEnabled
        set(value) {
            if (field != value) {
                field = value
            }
        }


}