package com.chooongg.android.form.data

import androidx.annotation.IntRange

/**
 * Ems值
 */
data class FormEmsSize(
    @IntRange(from = 1) val size: Int,
    @IntRange(from = 1) val multiColumnSize: Int = size
)