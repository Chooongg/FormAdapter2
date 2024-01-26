package com.chooongg.android.form.data

import com.chooongg.android.form.FormOnMenuItemClickListener
import com.chooongg.android.form.enum.FormEnableMode
import com.chooongg.android.form.enum.FormVisibilityMode
import com.chooongg.android.form.listener.FormOnMenuCreatedListener

interface IFormMenu {

    /**
     * 菜单
     */
    var menu: Int?

    /**
     * 菜单可见模式
     */
    var menuVisibilityMode: FormVisibilityMode

    /**
     * 菜单启用模式
     */
    var menuEnableMode: FormEnableMode

    /**
     * 菜单已创建的监听
     */
    var onMenuCreatedListener: FormOnMenuCreatedListener?

    /**
     * 菜单点击时的监听
     */
    var onMenuItemClickListener: FormOnMenuItemClickListener?

    /**
     * 菜单那是否可见
     */
    fun isMenuVisible(adapterEnabled: Boolean): Boolean {
        return when (menuVisibilityMode) {
            FormVisibilityMode.ALWAYS -> true
            FormVisibilityMode.ENABLED -> adapterEnabled
            FormVisibilityMode.DISABLED -> !adapterEnabled
            FormVisibilityMode.NEVER -> false
        }
    }

    /**
     * 菜单是否可用
     */
    fun isMenuEnable(adapterEnabled: Boolean): Boolean {
        return when (menuEnableMode) {
            FormEnableMode.ALWAYS -> true
            FormEnableMode.ENABLED -> adapterEnabled
            FormEnableMode.DISABLED -> !adapterEnabled
            FormEnableMode.NEVER -> false
        }
    }

    fun setMenuOnCreatedListener(listener: FormOnMenuCreatedListener?) {
        onMenuCreatedListener = listener
    }

    fun setMenuOnItemCLickListener(listener: FormOnMenuItemClickListener?) {
        onMenuItemClickListener = listener
    }
}