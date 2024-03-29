package com.chooongg.android.form

import com.chooongg.android.form.data.IFormGroupData
import com.chooongg.android.form.item.FormButton
import com.chooongg.android.form.item.FormDetail
import com.chooongg.android.form.item.FormInput
import com.chooongg.android.form.item.FormText

fun IFormGroupData.addButton(
    name: Any?, field: String? = null, block: (FormButton.() -> Unit)? = null
) = addItem(FormButton(name, field).apply { block?.invoke(this) })

fun IFormGroupData.addDetail(
    name: Any?, field: String? = null, block: (FormDetail.() -> Unit)? = null
) = addItem(FormDetail(name, field).apply { block?.invoke(this) })

fun IFormGroupData.addInput(
    name: Any?, field: String? = null, block: (FormInput.() -> Unit)? = null
) = addItem(FormInput(name, field).apply { block?.invoke(this) })

fun IFormGroupData.addText(
    name: Any?, field: String? = null, block: (FormText.() -> Unit)? = null
) = addItem(FormText(name, field).apply { block?.invoke(this) })
