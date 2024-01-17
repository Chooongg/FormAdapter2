package com.chooongg.android.form.linkage

import com.chooongg.android.form.item.BaseForm
import com.chooongg.android.form.part.AbstractPart

class FormLinkage internal constructor(private val part: AbstractPart) {

    fun find(field: String, isGlobal: Boolean = false) =
        if (isGlobal) part.adapter[field]?.second else part[field]

    fun findAndUpdate(
        field: String,
        isGlobal: Boolean = false,
        block: (BaseForm<*>) -> Unit
    ) {
        if (isGlobal) {
            part.adapter[field]?.also {
                block(it.second)
                it.first.update()
            }
        } else {
            part[field]?.also {
                block(it)
                part.update()
            }
        }
    }
}