package com.chooongg.android.form.data

import com.chooongg.android.form.FormColorStateListBlock
import com.chooongg.android.form.item.BaseForm
import com.google.android.material.button.MaterialButton

class FormPartData : AbstractGroupData(), IFormGroupData, IFormPart {

    private val _items = mutableListOf<BaseForm<*>>()

    override var partEnabled: Boolean = true

    override var partField: String? = null

    override var partName: Any? = null

    override var icon: Int? = null

    override var iconTint: FormColorStateListBlock? = null

    @MaterialButton.IconGravity
    override var iconGravity: Int = MaterialButton.ICON_GRAVITY_TEXT_START

    override var iconSize: Int? = null

    override fun getItems(): MutableList<BaseForm<*>> = _items
}