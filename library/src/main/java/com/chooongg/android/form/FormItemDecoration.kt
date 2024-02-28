package com.chooongg.android.form

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import com.chooongg.android.form.boundary.Boundary
import com.chooongg.android.form.layoutManager.AbstractFormLayoutManager
import com.chooongg.android.ktx.isLayoutRtl

class FormItemDecoration : ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val holder = parent.getChildViewHolder(view) ?: return
        val adapter = parent.adapter as? FormAdapter ?: return
        val style = adapter.getStyle4ItemViewType(holder.itemViewType)
        if (holder.absoluteAdapterPosition < 0) return
        val item = adapter.getFormItem(holder.absoluteAdapterPosition) ?: return
        val layoutManager = parent.layoutManager as AbstractFormLayoutManager
        val spanGroupIndex = layoutManager.spanSizeLookup.getSpanGroupIndex(
            holder.absoluteAdapterPosition,
            layoutManager.spanCount
        )
        val top = if (spanGroupIndex == 0) {
            style.margin.top - style.margin.topMedium
        } else 0
        val bottom = if (
            layoutManager.spanSizeLookup.getSpanGroupIndex(
                adapter.itemCount - 1, layoutManager.spanCount
            ) == spanGroupIndex
        ) {
            style.margin.bottom - style.margin.bottomMedium
        } else 0
        val start = if (style.isIndependentItem) {
            when (item.boundary.start) {
                Boundary.GLOBAL -> 0
                else -> {
                    val columns = 27720 / item.spanSize
                    val index = item.spanIndex / item.spanSize
                    (style.margin.startMedium + style.margin.endMedium) / columns * index
                }
            }
        } else 0
        val end = if (style.isIndependentItem) {
            when (item.boundary.end) {
                Boundary.GLOBAL -> 0
                else -> {
                    val columns = 27720 / item.spanSize
                    val index = item.spanIndex / item.spanSize
                    (style.margin.startMedium + style.margin.endMedium) / columns * (columns - 1 - index)
                }
            }
        } else 0
        if (view.isLayoutRtl) {
            outRect.set(end, top, start, bottom)
        } else {
            outRect.set(start, top, end, bottom)
        }
    }
}