package com.chooongg.android.form.helper

import android.content.Context
import android.view.View
import androidx.annotation.AttrRes
import androidx.core.content.res.use

interface FormTextAppearanceHelper {

    fun View.formTextAppearance(@AttrRes resId: Int): Int = context.formTextAppearance(resId)

    fun Context.formTextAppearance(@AttrRes resId: Int): Int =
        obtainStyledAttributes(intArrayOf(resId)).use {
            it.getResourceId(
                0, when (resId) {
                    else -> 0
                }
            )
        }
}