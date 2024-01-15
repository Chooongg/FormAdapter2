package com.chooongg.android.form.enum

/**
 * 输出模式
 */
enum class FormOutputMode {
    /**
     * 总是
     */
    ALWAYS,

    /**
     * 可见时
     */
    VISIBLE,

    /**
     * 可见且启用时
     */
    VISIBLE_ENABLED,

    /**
     * 可见且禁用时
     */
    VISIBLE_DISABLE,

    /**
     * 不可见时
     */
    INVISIBLE,

    /**
     * 不可见且启用时
     */
    INVISIBLE_ENABLE,

    /**
     * 不可见且禁用时
     */
    INVISIBLE_DISABLE,

    /**
     * 启用时
     */
    ENABLED,

    /**
     * 禁用时
     */
    DISABLE,

    /**
     * 从不
     */
    NEVER
}