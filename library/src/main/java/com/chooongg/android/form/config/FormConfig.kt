package com.chooongg.android.form.config

import com.chooongg.android.form.FormManager
import com.chooongg.android.form.enum.FormContentGravity
import com.chooongg.android.form.enum.FormEmsSize
import com.chooongg.android.form.formatter.name.AbstractNameFormatter
import com.chooongg.android.form.provider.AbstractGroupTitleProvider
import com.chooongg.android.form.typeset.AbstractTypeset

/**
 * 配置: 属性为空时使用全局配置
 */
abstract class FormConfig {

    /**
     * 名称格式化程序
     */
    abstract val _nameFormatter: AbstractNameFormatter?

    /**
     * 组标题视图提供器
     */
    abstract val _groupTitleProvider: AbstractGroupTitleProvider?

    /**
     * EMS 值
     */
    abstract val _emsSize: FormEmsSize?

    /**
     * 内容重力
     */
    abstract val _contentGravity: FormContentGravity?

    /**
     * 排版
     */
    abstract val _typeset: AbstractTypeset?


    val nameFormatter: AbstractNameFormatter
        get() = _nameFormatter ?: FormManager.globalConfig.nameFormatter

    val groupTitleProvider: AbstractGroupTitleProvider
        get() = _groupTitleProvider ?: FormManager.globalConfig.groupTitleProvider

    val emsSize: FormEmsSize
        get() = _emsSize ?: FormManager.globalConfig.emsSize

    val contentGravity: FormContentGravity
        get() = _contentGravity ?: FormManager.globalConfig.contentGravity

    val typeset: AbstractTypeset
        get() = _typeset ?: FormManager.globalConfig.typeset

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is FormConfig) return false
        return other.javaClass == javaClass
    }

    override fun hashCode(): Int = javaClass.hashCode()
}