package com.chooongg.android.form.data

import com.chooongg.android.form.item.BaseForm
import com.chooongg.android.form.item.InternalFormGroupTitle

class FormPartData : AbstractPartData(), IFormGroupData {

    private val _items = mutableListOf<BaseForm<*>>()

    override fun getItems(): MutableList<BaseForm<*>> = _items
}