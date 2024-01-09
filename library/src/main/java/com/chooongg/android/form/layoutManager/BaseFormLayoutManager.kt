package com.chooongg.android.form.layoutManager

import android.content.Context
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.RecyclerView
import com.chooongg.android.form.FormManager
import com.chooongg.android.form.part.AbstractPart
import kotlin.math.max

open class BaseFormLayoutManager(context: Context) : GridLayoutManager(context, 27720) {

    protected var adapter: RecyclerView.Adapter<*>? = null

    init {
        configSpanSizeLookup()
    }

    protected open fun configSpanSizeLookup() {
        spanSizeLookup = object : SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                val part = adapter as? AbstractPart ?: return spanCount
                return TODO()
            }

            override fun getSpanIndex(position: Int, spanCount: Int): Int {
                return super.getSpanIndex(position, spanCount)
            }
        }
    }

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

    override fun smoothScrollToPosition(
        recyclerView: RecyclerView,
        state: RecyclerView.State,
        position: Int
    ) {
        if (FormManager.default.centerSmoothScroll) {
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