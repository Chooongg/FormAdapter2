package com.chooongg.android.form.part

import com.chooongg.android.form.FormAdapter
import com.chooongg.android.form.data.FormPartData
import com.chooongg.android.form.item.BaseForm
import com.chooongg.android.form.style.AbstractStyle

class FormPart(adapter: FormAdapter, style: AbstractStyle) : AbstractPart(adapter, style) {

    internal var data = FormPartData().apply { partEnabled = false }

    override fun get(field: String): BaseForm<*>? = data.getItems().find { it.field == field }

    override fun contains(field: String): Boolean = data.getItems().any { it.field == field }

    override fun contains(item: BaseForm<*>): Boolean = data.getItems().contains(item)
}