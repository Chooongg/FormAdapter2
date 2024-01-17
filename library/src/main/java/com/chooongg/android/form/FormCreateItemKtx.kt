package com.chooongg.android.form

import com.chooongg.android.form.data.IFormGroupData
import com.chooongg.android.form.item.FormText

fun IFormGroupData.addText(
    name: Any?, field: String? = null, block: (FormText.() -> Unit)? = null
) = addItem(FormText(name, field).apply { block?.invoke(this) })