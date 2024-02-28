package com.chooongg.android.form.data

import androidx.annotation.MenuRes
import com.chooongg.android.form.FormOnMenuItemClickListener
import com.chooongg.android.form.enum.FormEnableMode
import com.chooongg.android.form.enum.FormVisibilityMode
import com.chooongg.android.form.item.InternalFormGroupTitle
import com.chooongg.android.form.listener.FormOnMenuCreatedListener

abstract class AbstractGroupData : AbstractId(), IFormPart, IFormMenu {

    @MenuRes
    override var menu: Int? = null

    override var menuVisibilityMode: FormVisibilityMode = FormVisibilityMode.ENABLED

    override var menuEnableMode: FormEnableMode = FormEnableMode.ENABLED

    override var onMenuCreatedListener: FormOnMenuCreatedListener? = null

    override var onMenuItemClickListener: FormOnMenuItemClickListener? = null

    private var _groupTitleItem: InternalFormGroupTitle<Any>? = null

    open fun getGroupTitleItem(): InternalFormGroupTitle<*>? {
        return if (partName != null) {
            if (_groupTitleItem == null) _groupTitleItem = InternalFormGroupTitle()
            _groupTitleItem!!.also {
                it.name = partName
                it.icon = icon
                it.iconGravity = iconGravity
                it.iconTint = iconTint
                it.iconSize = iconSize
                it.menu = menu
                it.menuVisibilityMode = menuVisibilityMode
                it.menuEnableMode = menuEnableMode
                it.onMenuCreatedListener = onMenuCreatedListener
                it.onMenuItemClickListener = onMenuItemClickListener
            }
        } else {
            _groupTitleItem = null
            null
        }
    }

}