package com.chooongg.android.form.part

import com.chooongg.android.form.FormAdapter
import com.chooongg.android.form.data.FormPartData
import com.chooongg.android.form.item.BaseForm
import com.chooongg.android.form.style.AbstractStyle

class FormPart(adapter: FormAdapter, style: AbstractStyle) : AbstractPart(adapter, style) {

    internal var data = FormPartData().apply { partEnabled = false }

    override fun get(field: String): BaseForm<*> {
        TODO("Not yet implemented")
    }

    override fun contains(field: String): Boolean {
        TODO("Not yet implemented")
    }

    override fun contains(item: BaseForm<*>): Boolean {
        TODO("Not yet implemented")
    }
}