package com.chooongg.android.form.style

import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.RippleDrawable
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import androidx.annotation.ColorRes
import androidx.annotation.DimenRes
import com.chooongg.android.form.holder.FormViewHolder
import com.chooongg.android.form.item.BaseForm
import com.chooongg.android.form.view.OutlinedCutoutDrawable
import com.chooongg.android.ktx.attrColor
import com.chooongg.android.ktx.isLayoutRtl
import com.google.android.material.shape.MaterialShapeDrawable

class CardOutlineStyle : AbstractStyle() {

    @ColorRes
    var strokeColorResId: Int? = null

    @DimenRes
    var strokeWidthResId: Int? = null

    override fun onCreateViewHolder(parent: ViewGroup): ViewGroup? = null
    override fun addView(parent: ViewGroup, child: View) = Unit
    override fun onBindViewHolder(
        holder: FormViewHolder,
        item: BaseForm<*>,
        layout: ViewGroup?,
        adapterEnabled: Boolean
    ) {
        holder.itemView.clipToOutline = true
        val shape = getShapeAppearanceModel(holder.itemView, item)
        val shapeDrawable =
            OutlinedCutoutDrawable(shape, item.boundary, holder.itemView.isLayoutRtl)
        val defaultStyle = TypedValue().also {
            holder.itemView.context.theme.resolveAttribute(
                com.google.android.material.R.attr.materialCardViewOutlinedStyle, it, true
            )
        }
        val strokeWidth = if (strokeWidthResId != null) {
            holder.itemView.context.resources.getDimension(strokeWidthResId!!)
        } else with(defaultStyle) {
            holder.itemView.context.obtainStyledAttributes(
                resourceId, intArrayOf(com.google.android.material.R.attr.strokeWidth)
            ).use { it.getDimension(0, 3f) }
        }
        val strokeColor = if (strokeColorResId != null) {
            holder.itemView.context.resources.getColor(
                strokeColorResId!!, holder.itemView.context.theme
            )
        } else with(defaultStyle) {
            holder.itemView.context.obtainStyledAttributes(
                resourceId, intArrayOf(com.google.android.material.R.attr.strokeColor)
            ).use { it.getColor(0, Color.GRAY) }
        }
        shapeDrawable.setStroke(strokeWidth, strokeColor)
        holder.itemView.background = shapeDrawable
        holder.itemView.foreground = if (item.isRespondToClickEvents) {
            RippleDrawable(
                ColorStateList.valueOf(holder.itemView.attrColor(android.R.attr.colorControlHighlight)),
                null, MaterialShapeDrawable(getClickShapeAppearanceModel(holder.itemView, item))
            )
        } else null
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is CardOutlineStyle) return false
        if (!super.equals(other)) return false

        if (strokeColorResId != other.strokeColorResId) return false
        return strokeWidthResId == other.strokeWidthResId
    }

    override fun hashCode(): Int = javaClass.hashCode()
}