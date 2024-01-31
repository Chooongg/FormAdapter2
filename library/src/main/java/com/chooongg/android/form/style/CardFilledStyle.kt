package com.chooongg.android.form.style

import android.content.res.ColorStateList
import android.view.View
import android.view.ViewGroup
import androidx.annotation.AttrRes
import androidx.annotation.ColorRes
import com.chooongg.android.form.holder.FormViewHolder
import com.chooongg.android.form.item.BaseForm
import com.chooongg.android.ktx.attrColor
import com.chooongg.android.ktx.resColor
import com.google.android.material.shape.MaterialShapeDrawable

class CardFilledStyle : AbstractStyle() {

    /**
     * 颜色资源Id
     */
    @ColorRes
    var colorResId: Int? = null

    /**
     * 颜色属性资源Id
     */
    @AttrRes
    var colorAttrResId: Int? = null

    override fun onCreateViewHolder(parent: ViewGroup): ViewGroup? = null
    override fun addView(parent: ViewGroup, child: View) = Unit
    override fun onBindViewHolder(
        holder: FormViewHolder,
        item: BaseForm<*>,
        layout: ViewGroup?,
        adapterEnabled: Boolean
    ) {
        holder.itemView.clipToOutline = true
        val shapeDrawable = MaterialShapeDrawable(getShapeAppearanceModel(holder.itemView, item))
        shapeDrawable.fillColor = if (colorResId != null) {
            ColorStateList.valueOf(holder.itemView.resColor(colorResId!!))
        } else if (colorAttrResId != null) {
            ColorStateList.valueOf(holder.itemView.attrColor(colorAttrResId!!))
        } else {
            ColorStateList.valueOf(
                holder.itemView.attrColor(com.google.android.material.R.attr.colorSurfaceContainerLow)
            )
        }
        holder.itemView.background = shapeDrawable
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is CardFilledStyle) return false
        if (!super.equals(other)) return false
        if (colorResId != other.colorResId) return false
        return colorAttrResId == other.colorAttrResId
    }

    override fun hashCode(): Int = javaClass.hashCode()
}