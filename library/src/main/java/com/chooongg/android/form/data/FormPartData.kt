package com.chooongg.android.form.data

import android.content.Context
import com.chooongg.android.form.item.BaseForm
import com.chooongg.android.form.item.InternalFormGroupTitle

class FormPartData : AbstractPartData(), IFormGroupData {

    private val _items = mutableListOf<BaseForm<*>>()

    override fun getItems(): MutableList<BaseForm<*>> = _items

    override fun getGroupTitleItem(block: (InternalFormGroupTitle) -> Unit): InternalFormGroupTitle? {
        return if (partName != null) {
            InternalFormGroupTitle(id).also {
                it.name = partName
                it.menu = menu
                it.menuVisibilityMode = menuVisibilityMode
                it.menuEnableMode = menuEnableMode
                it.onMenuCreatedListener = onMenuCreatedListener
                it.onMenuItemClickListener = onMenuItemClickListener
            }
        } else null
    }
}