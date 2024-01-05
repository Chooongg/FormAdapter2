package com.chooongg.android.form.item

import com.chooongg.android.form.enum.FormEnableMode
import com.chooongg.form.enum.FormVisibilityMode
import java.util.UUID

abstract class AbstractForm {

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
     * 真实的可见性
     */
    fun isRealVisible(isEnabled: Boolean): Boolean {
        return when (visibilityMode) {
            FormVisibilityMode.ALWAYS -> true
            FormVisibilityMode.ENABLED -> isEnabled
            FormVisibilityMode.DISABLED -> !isEnabled
            FormVisibilityMode.NEVER -> false
        }
    }

    /**
     * 真实的可用性
     */
    fun isRealEnable(isEnabled: Boolean): Boolean {
        return when (enableMode) {
            FormEnableMode.ALWAYS -> true
            FormEnableMode.ENABLED -> isEnabled
            FormEnableMode.DISABLED -> !isEnabled
            FormEnableMode.NEVER -> false
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is AbstractForm) return false
        return id == other.id
    }

    override fun hashCode(): Int = id.hashCode()
}