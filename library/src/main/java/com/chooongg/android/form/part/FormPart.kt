package com.chooongg.android.form.part

import com.chooongg.android.form.FormAdapter
import com.chooongg.android.form.data.FormPartData
import com.chooongg.android.form.item.BaseForm
import com.chooongg.android.form.linkage.FormLinkage
import com.chooongg.android.form.style.AbstractStyle

class FormPart(adapter: FormAdapter, style: AbstractStyle, data: FormPartData) :
    AbstractPart<FormPartData>(adapter, style, data) {

    override fun getOriginalItemList(): List<List<BaseForm<*>>> = if (data.partEnabled) {
        val list = ArrayList<BaseForm<*>>()
        val title = data.getGroupTitleItem() {

        }
        if (title != null) list.add(title)
        listOf(data.getItems())
    } else emptyList()

    override fun executeLinkage(isIgnoreUpdate: Boolean) {
        data.getItems().forEach { it.linkageBlock?.invoke(FormLinkage(this, isIgnoreUpdate)) }
    }

    override fun get(field: String): BaseForm<*>? = data.getItems().find { it.field == field }

    override fun contains(field: String): Boolean = data.getItems().any { it.field == field }

    override fun contains(item: BaseForm<*>): Boolean = data.getItems().contains(item)

}