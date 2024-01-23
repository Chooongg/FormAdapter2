package com.chooongg.android.form.extractor

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.core.content.res.ResourcesCompat

class NormalIconExtractor : IIconExtractor {
    override fun extract(context: Context, icon: Any?): Drawable? {
        return when (icon) {
            is Drawable -> icon
            is Int -> ResourcesCompat.getDrawable(context.resources, icon, context.theme)
            else -> null
        }
    }
}