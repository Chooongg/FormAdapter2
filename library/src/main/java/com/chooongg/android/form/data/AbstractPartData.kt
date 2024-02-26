package com.chooongg.android.form.data

import android.content.Context
import androidx.annotation.MenuRes
import com.chooongg.android.form.FormColorStateListBlock
import com.chooongg.android.form.FormOnMenuItemClickListener
import com.chooongg.android.form.enum.FormEnableMode
import com.chooongg.android.form.enum.FormVisibilityMode
import com.chooongg.android.form.item.InternalFormGroupTitle
import com.chooongg.android.form.listener.FormOnMenuCreatedListener
import com.google.android.material.button.MaterialButton

abstract class AbstractPartData : AbstractId(), IFormPart, IFormMenu {

    override var partEnabled: Boolean = true

    override var partField: String? = null

    override var partName: Any? = null

    override var icon: Int? = null

    override var iconTint: FormColorStateListBlock? = null

    @MaterialButton.IconGravity
    override var iconGravity: Int = MaterialButton.ICON_GRAVITY_TEXT_START

    override var iconSize: Int? = null

    @MenuRes
    override var menu: Int? = null

    override var menuVisibilityMode: FormVisibilityMode = FormVisibilityMode.ENABLED

    override var menuEnableMode: FormEnableMode = FormEnableMode.ENABLED

    override var onMenuCreatedListener: FormOnMenuCreatedListener? = null

    override var onMenuItemClickListener: FormOnMenuItemClickListener? = null

    abstract fun getGroupTitleItem(context: Context): InternalFormGroupTitle?

}