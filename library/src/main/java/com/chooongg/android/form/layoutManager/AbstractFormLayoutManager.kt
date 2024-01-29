package com.chooongg.android.form.layoutManager

import android.content.Context
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.RecyclerView
import com.chooongg.android.form.FormAdapter
import com.chooongg.android.form.FormManager

abstract class AbstractFormLayoutManager(context: Context) : GridLayoutManager(context, 27720) {

    protected var adapter: RecyclerView.Adapter<*>? = null

    var marginStart: Int = 0
    var marginEnd: Int = 0

    init {
        spanSizeLookup = object : SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int = onGetSpanSize(position)

            override fun getSpanIndex(position: Int, spanCount: Int): Int {
                val index = onGetSpanIndex(position, spanCount)
                return if (index < 0) super.getSpanIndex(position, spanCount) else index
            }

            override fun getSpanGroupIndex(adapterPosition: Int, spanCount: Int): Int {
                return super.getSpanGroupIndex(adapterPosition, spanCount)
            }
        }
    }

    open fun invalidateSpanIndexCache() {}

    abstract fun onGetSpanSize(position: Int): Int

    abstract fun onGetSpanIndex(position: Int, spanCount: Int): Int

    override fun onAttachedToWindow(view: RecyclerView) {
        super.onAttachedToWindow(view)
        adapter = view.adapter
    }

    override fun onAdapterChanged(
        oldAdapter: RecyclerView.Adapter<*>?,
        newAdapter: RecyclerView.Adapter<*>?
    ) {
        adapter = newAdapter
    }

    override fun onDetachedFromWindow(view: RecyclerView?, recycler: RecyclerView.Recycler?) {
        super.onDetachedFromWindow(view, recycler)
        adapter = null
    }

    override fun getPaddingLeft(): Int {
        return super.getPaddingLeft() + (if (isLayoutRTL) marginEnd else marginStart)
    }

    override fun getPaddingRight(): Int {
        return super.getPaddingRight() + (if (isLayoutRTL) marginStart else marginEnd)
    }

    override fun getPaddingStart(): Int {
        return super.getPaddingStart() + marginStart
    }

    override fun getPaddingEnd(): Int {
        return super.getPaddingEnd() + marginEnd
    }

    override fun smoothScrollToPosition(
        recyclerView: RecyclerView,
        state: RecyclerView.State,
        position: Int
    ) {
        if (FormManager.centerSmoothScroll) {
            startSmoothScroll(CenterSmoothScroller(recyclerView.context).apply {
                targetPosition = position
            })
        } else super.smoothScrollToPosition(recyclerView, state, position)
    }

    private class CenterSmoothScroller(context: Context) : LinearSmoothScroller(context) {
        override fun calculateDtToFit(
            viewStart: Int, viewEnd: Int, boxStart: Int, boxEnd: Int, snapPreference: Int
        ) = (boxStart + (boxEnd - boxStart) / 2) - (viewStart + (viewEnd - viewStart) / 2)
    }
}