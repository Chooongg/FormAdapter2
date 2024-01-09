package com.chooongg.android.form.extractor

import android.content.Context

interface ITextExtractor {
    fun extract(context: Context, text: Any?): CharSequence?
}