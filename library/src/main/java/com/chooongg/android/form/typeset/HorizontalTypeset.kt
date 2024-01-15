package com.chooongg.android.form.typeset

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

class HorizontalTypeset : AbstractTypeset() {

    override var emsMode: FormEmsMode = FormEmsMode(FormEmsMode.FIXED)

    override fun onCreateViewHolder(style: AbstractStyle, parent: ViewGroup): ViewGroup =
        LinearLayoutCompat(parent.context).also {
            it.showDividers
            it.orientation = LinearLayoutCompat.HORIZONTAL
            it.addView(MaterialTextView(it.context).apply {
                id = R.id.formInternalNameView
                setTextAppearance(formTextAppearance(R.attr.formTextAppearanceName))
//                setPaddingRelative(
//                    style.insideInfo.middleStart, style.insideInfo.middleTop,
//                    style.insideInfo.middleEnd, style.insideInfo.middleBottom
//                )
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
        adapterEnabled: Boolean
    ) {
        holder.typesetLayout!!.findViewById<MaterialTextView>(R.id.formInternalNameView).apply {
            setNameViewEms(holder, this)
            text = (nameFormatter ?: FormManager.default.nameFormatter).format(context, item)
        }
        holder.typesetLayout.findViewById<FormMenuView>(R.id.formInternalMenuView)
            ?.configMenu(holder, item, adapterEnabled)
    }
}