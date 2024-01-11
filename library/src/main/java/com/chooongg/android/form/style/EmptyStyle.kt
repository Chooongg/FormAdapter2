package com.chooongg.android.form.style

import android.view.View
import android.view.ViewGroup
import com.chooongg.android.form.holder.FormViewHolder
import com.chooongg.android.form.item.BaseForm

class EmptyStyle : AbstractStyle() {
    override fun onCreateViewHolder(parent: ViewGroup): ViewGroup? = null
    override fun addView(parent: ViewGroup, child: View) = Unit
    override fun onBindViewHolder(holder: FormViewHolder, layout: ViewGroup, item: BaseForm<*>) {

    }
}