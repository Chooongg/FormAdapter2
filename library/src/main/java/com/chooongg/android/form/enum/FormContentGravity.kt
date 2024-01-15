package com.chooongg.android.form.enum

import android.view.Gravity
import androidx.annotation.GravityInt

/**
 * 内容重力
 */
data class FormContentGravity(
    @GravityInt val gravity: Int = Gravity.NO_GRAVITY,
    @GravityInt val multiColumnGravity: Int = gravity
)
