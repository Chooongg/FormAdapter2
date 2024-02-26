package com.chooongg.android.form.data

import com.chooongg.android.form.FormColorStateListBlock

interface IFormPart {

    /**
     * 是否启用片段
     */
    var partEnabled: Boolean

    /**
     * 片段字段
     */
    var partField: String?

    /**
     * 片段名称
     */
    var partName: Any?

    /**
     * 图标
     */
    var icon: Int?

    /**
     * 图标着色
     */
    var iconTint: FormColorStateListBlock?

    /**
     * 图标重力
     */
    var iconGravity: Int

    /**
     * 图标大小
     */
    var iconSize: Int?
}