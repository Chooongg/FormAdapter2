package com.chooongg.android.form.config

import android.view.Gravity
import com.chooongg.android.form.enum.FormContentGravity
import com.chooongg.android.form.enum.FormEmsSize
import com.chooongg.android.form.formatter.name.AbstractNameFormatter
import com.chooongg.android.form.formatter.name.NormalNameFormatter
import com.chooongg.android.form.provider.AbstractGroupTitleProvider
import com.chooongg.android.form.provider.NormalGroupTitleProvider
import com.chooongg.android.form.typeset.AbstractTypeset
import com.chooongg.android.form.typeset.HorizontalTypeset

/**
 * 全局配置
 */
open class FormGlobalConfig {

    /**
     * 名称格式化程序
     */
    open var nameFormatter: AbstractNameFormatter = NormalNameFormatter()
        protected set

    /**
     * 组标题视图提供器
     */
    open var groupTitleProvider: AbstractGroupTitleProvider = NormalGroupTitleProvider()
        protected set

    /**
     * EMS 值
     */
    open var emsSize: FormEmsSize = FormEmsSize(5)
        protected set

    /**
     * 内容重力
     */
    open var contentGravity: FormContentGravity = FormContentGravity(Gravity.END, Gravity.START)
        protected set

    /**
     * 排版
     */
    open var typeset: AbstractTypeset = HorizontalTypeset()
        protected set
}