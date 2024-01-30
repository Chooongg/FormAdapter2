package com.chooongg.android.form.style

import android.content.res.ColorStateList
import android.view.View
import android.view.ViewGroup
import androidx.annotation.DimenRes
import com.chooongg.android.form.R
import com.chooongg.android.form.holder.FormViewHolder
import com.chooongg.android.form.item.BaseForm
import com.chooongg.android.ktx.attrColor
import com.chooongg.android.ktx.resDimension
import com.google.android.material.shape.MaterialShapeDrawable

class CardElevatedStyle : AbstractStyle() {

    @DimenRes
    var elevationResId: Int? = null

    override fun onCreateViewHolder(parent: ViewGroup): ViewGroup? = null
    override fun addView(parent: ViewGroup, child: View) = Unit
    override fun onBindViewHolder(
        holder: FormViewHolder,
        item: BaseForm<*>,
        layout: ViewGroup,
        adapterEnabled: Boolean
    ) {
        holder.itemView.elevation = if (elevationResId != null) {
            holder.itemView.resDimension(elevationResId!!)
        } else holder.itemView.resDimension(R.dimen.formCardElevation)
        val shapeDrawable = MaterialShapeDrawable(getShapeAppearanceModel(holder.itemView, item))
        shapeDrawable.fillColor = ColorStateList.valueOf(
            holder.itemView.attrColor(com.google.android.material.R.attr.colorSurfaceContainerLow)
        )
        holder.itemView.background = shapeDrawable
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is CardElevatedStyle) return false
        if (!super.equals(other)) return false
        return elevationResId == other.elevationResId
    }

    override fun hashCode(): Int = javaClass.hashCode()
}