package com.chooongg.android.form.data

import androidx.annotation.IntRange
import com.chooongg.android.form.FormColorStateListBlock
import com.chooongg.android.form.R
import com.google.android.material.button.MaterialButton

class FormDynamicPartData : AbstractGroupData(), IFormPart {

    override var partEnabled: Boolean = true

    override var partField: String? = null

    override var partName: Any? = null

    override var icon: Int? = null

    override var iconTint: FormColorStateListBlock? = null

    @MaterialButton.IconGravity
    override var iconGravity: Int = MaterialButton.ICON_GRAVITY_TEXT_START

    override var iconSize: Int? = null

    /**
     * 最小组数量
     */
    @IntRange(from = 0)
    var minGroupCount: Int = 1

    /**
     * 最大组数量
     */
    @IntRange(from = 1)
    var maxGroupCount: Int = 1

    /**
     * 新增图标
     */
    var addIcon: Any? = R.drawable.ic_form_add

    /**
     * 新增图标重力
     */
    @MaterialButton.IconGravity
    var addIconGravity: Int = MaterialButton.ICON_GRAVITY_TEXT_START
}