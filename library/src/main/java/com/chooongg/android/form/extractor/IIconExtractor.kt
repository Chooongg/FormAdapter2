package com.chooongg.android.form.extractor

import android.content.Context
import android.graphics.drawable.Drawable

interface IIconExtractor {
    fun getIcon(context: Context, icon: Any?): Drawable?
}