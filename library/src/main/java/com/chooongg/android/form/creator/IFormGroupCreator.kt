package com.chooongg.android.form.creator

import com.chooongg.android.form.item.BaseForm

interface IFormGroupCreator {

    fun getItems(): MutableList<BaseForm<*>>

    fun addItem(item: BaseForm<*>) = getItems().add(item)


}