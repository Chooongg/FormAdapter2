package com.chooongg.android.form.enum

import androidx.annotation.IntDef

/**
 * Ems模式
 */
data class FormEmsMode(
    @EmsMode val mode: Int,
    @EmsMode val multiColumnMode: Int = mode
) {
    companion object {
        /**
         * 无限制
         */
        const val NONE = 0

        /**
         * 限制最小EMS
         */
        const val MIN = 1

        /**
         * 限制最大EMS
         */
        const val MAX = 2

        /**
         * 固定的EMS
         */
        const val FIXED = 3
    }

    @IntDef(NONE, MIN, MAX, FIXED)
    private annotation class EmsMode
}