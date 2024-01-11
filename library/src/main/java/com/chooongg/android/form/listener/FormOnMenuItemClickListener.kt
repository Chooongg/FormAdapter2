package com.chooongg.android.form.listener

import android.content.Context
import android.view.MenuItem
import com.chooongg.android.form.item.BaseForm

/**
 * 菜单点击时的监听
 */
interface FormOnMenuItemClickListener {
    fun onMenuItemClick(context: Context, menu: MenuItem, item: BaseForm<*>): Boolean
}