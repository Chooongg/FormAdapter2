package com.chooongg.android.form

import android.content.Context
import android.content.res.ColorStateList
import android.view.View
import com.chooongg.android.form.item.BaseForm
import com.chooongg.android.form.linkage.FormLinkage

/**
 * 颜色Block
 */
typealias FormColorBlock = Context.() -> Int?

/**
 * 颜色状态列表Block
 */
typealias FormColorStateListBlock = Context.() -> ColorStateList?

/**
 * 项目点击时的监听
 */
typealias FormItemOnClickListener = (item: BaseForm<*>, view: View) -> Unit

/**
 * 联动Block
 */
typealias FormLinkageBlock = FormLinkage.() -> Unit