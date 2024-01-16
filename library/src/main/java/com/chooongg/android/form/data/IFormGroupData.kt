package com.chooongg.android.form.data

import com.chooongg.android.form.item.BaseForm

interface IFormGroupData {

    fun getItems(): MutableList<BaseForm<*>>

    fun addItem(item: BaseForm<*>) = getItems().add(item)

}