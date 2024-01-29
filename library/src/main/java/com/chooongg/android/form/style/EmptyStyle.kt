package com.chooongg.android.form.style

import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.graphics.drawable.RippleDrawable
import android.view.View
import android.view.ViewGroup
import com.chooongg.android.form.R
import com.chooongg.android.form.holder.FormViewHolder
import com.chooongg.android.form.item.BaseForm
import com.chooongg.android.ktx.attrColor
import com.chooongg.android.ktx.attrResourcesId
import com.google.android.material.shape.MaterialShapeDrawable
import com.google.android.material.shape.ShapeAppearanceModel

class EmptyStyle : AbstractStyle() {

    private lateinit var shapeBackground: Drawable

    override fun onCreateViewHolder(parent: ViewGroup): ViewGroup? {
        if (!this::shapeBackground.isInitialized) {
            RippleDrawable(
                ColorStateList.valueOf(parent.context.attrColor(android.R.attr.colorControlHighlight)),
                null,
                MaterialShapeDrawable(shapeAppearanceModel)
            )
        }
        config.getNameFormatter()
        return null
    }

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
        if (holder.itemView.background == null) holder.itemView.background = shapeBackground
    }
}