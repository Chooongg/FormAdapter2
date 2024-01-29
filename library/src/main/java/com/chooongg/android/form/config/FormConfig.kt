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
    open val _nameFormatter: AbstractNameFormatter? = null

    /**
     * 组标题视图提供器
     */
    open val _groupTitleProvider: AbstractGroupTitleProvider? = null

    /**
     * EMS 值
     */
    open val _emsSize: FormEmsSize? = null

    /**
     * 内容重力
     */
    open val _contentGravity: FormContentGravity? = null

    /**
     * 排版
     */
    open val _typeset: AbstractTypeset? = null


}