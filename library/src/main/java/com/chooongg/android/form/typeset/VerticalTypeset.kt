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
import com.google.android.material.textview.MaterialTextView

class VerticalTypeset : AbstractTypeset() {

    override var emsMode: FormEmsMode = FormEmsMode(FormEmsMode.NONE)

    override fun onCreateViewHolder(style: AbstractStyle, parent: ViewGroup): ViewGroup =
        LinearLayoutCompat(parent.context).also {
            it.orientation = LinearLayoutCompat.VERTICAL
            it.addView(LinearLayoutCompat(it.context).also { child ->
                child.clipChildren = false
                child.clipToPadding = false
                child.orientation = LinearLayoutCompat.HORIZONTAL
                child.gravity = Gravity.CENTER_VERTICAL
                child.addView(MaterialTextView(child.context).apply {
                    id = R.id.formInternalNameView
                    setTextAppearance(formTextAppearance(R.attr.formTextAppearanceName))
                    setPaddingRelative(
                        style.padding.startMedium, style.padding.topMedium,
                        style.padding.endMedium, style.padding.bottomMedium
                    )
                }, LinearLayoutCompat.LayoutParams(0, -2).apply { weight = 1f })
                child.addView(FormMenuView(child.context, style).apply {
                    id = R.id.formInternalMenuView
                }, LinearLayoutCompat.LayoutParams(-2, -2))
            }, LinearLayoutCompat.LayoutParams(-1, -2))
        }

    override fun addView(style: AbstractStyle, parent: ViewGroup, child: View) {
        parent.addView(child, LinearLayoutCompat.LayoutParams(-1, -2).apply {
//            topMargin = TODO()
        })
    }

    override fun onBindViewHolder(
        holder: FormViewHolder,
        item: BaseForm<*>,
        layout: ViewGroup,
        adapterEnabled: Boolean
    ) {
        layout.findViewById<MaterialTextView>(R.id.formInternalNameView).apply {
            setNameViewEms(holder, this)
            text = (nameFormatter ?: FormManager.default.nameFormatter).format(context, item)
        }
        layout.findViewById<FormMenuView>(R.id.formInternalMenuView)
            ?.configMenu(holder, item, adapterEnabled)
    }
}