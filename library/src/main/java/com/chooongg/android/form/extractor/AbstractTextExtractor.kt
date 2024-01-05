package com.chooongg.android.form.extractor

import android.content.Context

abstract class AbstractTextExtractor {

    abstract fun extract(context: Context, text: Any?): CharSequence?

    override fun equals(other: Any?) =
        other is AbstractTextExtractor && javaClass == other.javaClass

    override fun hashCode() = javaClass.hashCode()
}