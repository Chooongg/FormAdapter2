package com.chooongg.android.form.typeset

import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.LinearLayoutCompat
import com.chooongg.android.form.FormManager
import com.chooongg.android.form.R
import com.chooongg.android.form.enum.FormEmsMode
import com.chooongg.android.form.holder.FormViewHolder
import com.chooongg.android.form.item.BaseForm
import com.chooongg.android.form.style.AbstractStyle
import com.chooongg.android.form.view.FormMenuView
import com.chooongg.android.ktx.resDrawable
import com.google.android.material.button.MaterialButton

class HorizontalTypeset : AbstractTypeset() {

    override var emsMode: FormEmsMode = FormEmsMode(FormEmsMode.FIXED)

    override fun onCreateViewHolder(style: AbstractStyle, parent: ViewGroup): ViewGroup =
        LinearLayoutCompat(parent.context).also {
            it.orientation = LinearLayoutCompat.HORIZONTAL
            it.addView(MaterialButton(it.context).apply {
                id = R.id.formInternalNameView
                isClickable = false
                gravity = Gravity.NO_GRAVITY
                background = null
                minWidth = 0
                minHeight = 0
                minimumWidth = 0
                minimumHeight = 0
                setTextAppearance(formTextAppearance(R.attr.formTextAppearanceName))
                iconSize = FormManager.getFontRealHeight(this)
                iconPadding = 0
                setPaddingRelative(
                    style.padding.startMedium, style.padding.topMedium,
                    style.padding.endMedium, style.padding.bottomMedium
                )
            }, LinearLayoutCompat.LayoutParams(-2, -2))
            it.addView(FormMenuView(it.context, style).apply {
                id = R.id.formInternalMenuView
            }, LinearLayoutCompat.LayoutParams(-2, -2))
        }

    override fun addView(style: AbstractStyle, parent: ViewGroup, child: View) {
        parent.addView(child, 1, LinearLayoutCompat.LayoutParams(0, -2).apply {
            weight = 1f
        })
    }

    override fun onBindViewHolder(
        holder: FormViewHolder,
        item: BaseForm<*>,
        layout: ViewGroup,
        adapterEnabled: Boolean
    ) {
        layout.findViewById<MaterialButton>(R.id.formInternalNameView).apply {
            iconGravity = item.iconGravity
            if (item.icon != null) {
                val drawable = resDrawable(item.icon!!)
                icon = drawable
                iconTint = item.iconTint?.invoke(context) ?: textColors
            } else icon = null
            setNameViewEms(holder, this)
            text = holder.style.config.nameFormatter.format(context, item)
        }
        layout.findViewById<FormMenuView>(R.id.formInternalMenuView)
            ?.configMenu(holder, item, adapterEnabled)
    }
}