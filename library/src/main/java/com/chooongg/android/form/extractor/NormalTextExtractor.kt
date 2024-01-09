package com.chooongg.android.form.extractor

import android.content.Context

class NormalTextExtractor : ITextExtractor {
    override fun extract(context: Context, text: Any?): CharSequence? = when (text) {
        null -> null
        is Int -> context.getString(text)
        is CharSequence -> text
        else -> text.toString()
    }
}