package com.chooongg.android.form.boundary

data class FormSizeInfo(
    val start: Int,
    val top: Int,
    val end: Int,
    val bottom: Int,
    val startMedium: Int,
    val topMedium: Int,
    val endMedium: Int,
    val bottomMedium: Int
) {
    constructor() : this(0, 0, 0, 0, 0, 0, 0, 0)
}