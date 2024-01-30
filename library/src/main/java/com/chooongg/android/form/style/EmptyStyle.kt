package com.chooongg.android.form.style

import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.graphics.drawable.RippleDrawable
import android.view.View
import android.view.ViewGroup
import com.chooongg.android.form.holder.FormViewHolder
import com.chooongg.android.form.item.BaseForm
import com.chooongg.android.ktx.attrColor
import com.google.android.material.shape.MaterialShapeDrawable

class EmptyStyle : AbstractStyle() {

    override fun onCreateViewHolder(parent: ViewGroup): ViewGroup? = null

    override fun addView(parent: ViewGroup, child: View) = Unit
    override fun onBindViewHolder(
        holder: FormViewHolder,
        item: BaseForm<*>,
        layout: ViewGroup,
        adapterEnabled: Boolean
    ) = Unit

    override fun onBindViewHolderAfter(
        holder: FormViewHolder,
        item: BaseForm<*>,
        adapterEnabled: Boolean
    ) {
        if (holder.itemView.background == null) {
            holder.itemView.background = RippleDrawable(
                ColorStateList.valueOf(holder.itemView.attrColor(android.R.attr.colorControlHighlight)),
                null,
                MaterialShapeDrawable(shapeAppearanceModel)
            )
        }
    }
}