package com.chooongg.android.form.item

import androidx.annotation.MenuRes
import com.chooongg.android.form.enum.FormEnableMode
import com.chooongg.android.form.enum.FormVisibilityMode
import com.chooongg.android.form.listener.FormOnMenuCreatedListener
import com.chooongg.android.form.listener.FormOnMenuItemClickListener
import java.util.UUID

abstract class AbstractForm {

    //<editor-fold desc="基础 Basic">

    /**
     * 项目唯一标识
     */
    open val id = UUID.randomUUID().toString()

    /**
     * 可见模式
     */
    var visibilityMode: FormVisibilityMode = FormVisibilityMode.ALWAYS

    /**
     * 启用模式
     */
    var enableMode: FormEnableMode = FormEnableMode.ENABLED

    /**
     * 菜单
     */
    @MenuRes
    var menu: Int? = null

    /**
     * 菜单可见模式
     */
    var menuVisibilityMode: FormVisibilityMode = FormVisibilityMode.ENABLED

    /**
     * 菜单启用模式
     */
    var menuEnableMode: FormEnableMode = FormEnableMode.ENABLED

    /**
     * 菜单已创建的监听
     */
    var onMenuCreatedListener: FormOnMenuCreatedListener? = null

    /**
     * 菜单点击时的监听
     */
    var onMenuItemClickListener: FormOnMenuItemClickListener? = null

    /**
     * 是否可见
     */
    fun isVisible(adapterEnabled: Boolean): Boolean {
        return when (visibilityMode) {
            FormVisibilityMode.ALWAYS -> true
            FormVisibilityMode.ENABLED -> adapterEnabled
            FormVisibilityMode.DISABLED -> !adapterEnabled
            FormVisibilityMode.NEVER -> false
        }
    }

    /**
     * 是否可用
     */
    fun isEnable(adapterEnabled: Boolean): Boolean {
        return when (enableMode) {
            FormEnableMode.ALWAYS -> true
            FormEnableMode.ENABLED -> adapterEnabled
            FormEnableMode.DISABLED -> !adapterEnabled
            FormEnableMode.NEVER -> false
        }
    }

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

    fun setOnMenuCreatedListener(listener: FormOnMenuCreatedListener?) {
        onMenuCreatedListener = listener
    }

    fun setOnMenuItemCLickListener(listener: FormOnMenuItemClickListener?) {
        onMenuItemClickListener = listener
    }

    //</editor-fold>

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is AbstractForm) return false
        return id == other.id
    }

    override fun hashCode(): Int = id.hashCode()
}