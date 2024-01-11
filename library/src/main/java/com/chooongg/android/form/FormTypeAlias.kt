package com.chooongg.android.form

import android.content.Context
import android.content.res.ColorStateList
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.chooongg.android.form.item.BaseForm

/**
 * 颜色 Block
 */
typealias FormColorBlock = Context.() -> Int?

/**
 * 颜色状态列表 Block
 */
typealias FormColorStateListBlock = Context.() -> ColorStateList?

/**
 * 项目点击时的监听
 */
typealias FormItemOnClickListener = (item: BaseForm<*>, view: View) -> Unit