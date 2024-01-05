package com.chooongg.android.form.formatter.name

import android.content.Context
import com.chooongg.android.form.item.BaseForm

abstract class AbstractNameFormatter {

    abstract fun format(context: Context, item: BaseForm<*>): CharSequence?

    override fun equals(other: Any?) =
        other is AbstractNameFormatter && javaClass == other.javaClass

    override fun hashCode() = javaClass.hashCode()
}