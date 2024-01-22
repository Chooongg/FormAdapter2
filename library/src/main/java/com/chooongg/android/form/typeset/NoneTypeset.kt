package com.chooongg.android.form.typeset

import android.view.View
import android.view.ViewGroup
import com.chooongg.android.form.enum.FormEmsMode
import com.chooongg.android.form.holder.FormViewHolder
import com.chooongg.android.form.item.BaseForm
import com.chooongg.android.form.style.AbstractStyle

object NoneTypeset : AbstractTypeset() {

    override var emsMode: FormEmsMode = FormEmsMode(FormEmsMode.NONE)

    override fun onCreateViewHolder(style: AbstractStyle, parent: ViewGroup): ViewGroup? = null

    override fun addView(style: AbstractStyle, parent: ViewGroup, child: View) = Unit

    override fun onBindViewHolder(
        holder: FormViewHolder,
        item: BaseForm<*>,
        layout: ViewGroup,
        adapterEnabled: Boolean
    ) = Unit
}