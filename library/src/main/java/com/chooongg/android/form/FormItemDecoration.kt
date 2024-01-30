package com.chooongg.android.form

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration

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
        val item = adapter.getFormItem(holder.absoluteAdapterPosition)
//        val start = if (style.getHorizontalIsSeparateItem()) {
//            when (item.marginBoundary.start) {
//                Boundary.GLOBAL -> 0
//                else -> {
//                    val columns = 27720 / item.spanSize
//                    val index = item.spanIndex / item.spanSize
//                    (style.marginInfo.middleStart + style.marginInfo.middleEnd) / columns * index
//                }
//            }
//        } else 0
//        val end = if (style.getHorizontalIsSeparateItem()) {
//            when (item.marginBoundary.end) {
//                Boundary.GLOBAL -> 0
//                else -> {
//                    val columns = 27720 / item.spanSize
//                    val index = item.spanIndex / item.spanSize
//                    (style.marginInfo.middleStart + style.marginInfo.middleEnd) / columns * (columns - 1 - index)
//                }
//            }
//        } else 0
//        if (view.isLayoutRtl) {
//            outRect.set(end, top, start, bottom)
//        } else {
//            outRect.set(start, top, end, bottom)
//        }
    }
}