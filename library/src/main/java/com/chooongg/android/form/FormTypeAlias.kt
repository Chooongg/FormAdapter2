package com.chooongg.android.form

import android.content.Context
import android.content.res.ColorStateList
import android.view.MenuItem
import android.view.View
import com.chooongg.android.form.data.AbstractPartData
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
typealias FormOnItemClickListener = (view: View, item: BaseForm<*>) -> Unit

/**
 * 菜单点击时的监听
 */
typealias FormOnMenuItemClickListener = (view: View, menuView: View, menuItem: MenuItem) -> Boolean

/**
 * 菜单点击时的监听
 */
typealias FormOnMenuClickListener = (view: View, menuView: View, menuItem: MenuItem, item: BaseForm<*>) -> Unit

/**
 * 联动Block
 */
typealias FormLinkageBlock = FormLinkage.() -> Unit

/**
 * 内容格式化程序
 */
typealias FormContentFormatter = Context.(parts: List<AbstractPartData>?) -> CharSequence?