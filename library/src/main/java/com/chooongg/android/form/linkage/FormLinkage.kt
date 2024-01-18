package com.chooongg.android.form.linkage

import com.chooongg.android.form.item.BaseForm
import com.chooongg.android.form.part.AbstractPart

class FormLinkage internal constructor(
    private val part: AbstractPart,
    private val isIgnoreUpdate: Boolean = false
) {

    /**
     * 查找项目，不建议进行数据变更
     */
    fun find(field: String, isGlobal: Boolean = false) =
        if (isGlobal) part.adapter[field]?.second else part[field]

    /**
     * 查找并更新项目
     */
    fun findAndUpdate(
        field: String,
        isGlobal: Boolean = false,
        block: (BaseForm<*>) -> Unit
    ) {
        if (isGlobal) {
            part.adapter[field]?.also {
                block(it.second)
                if (!isIgnoreUpdate) it.first.update()
            }
        } else {
            part[field]?.also {
                block(it)
                if (!isIgnoreUpdate) part.update()
            }
        }
    }
}