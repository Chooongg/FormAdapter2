package com.chooongg.android.form.layoutManager

import android.content.Context
import com.chooongg.android.form.FormAdapter
import com.chooongg.android.form.part.AbstractPart

class FormLayoutManager(context: Context) : AbstractFormLayoutManager(context) {
    override fun onGetSpanSize(position: Int): Int {
        val formAdapter = adapter as? FormAdapter ?: return spanCount
        val pair = formAdapter.getWrappedAdapterAndPosition(position)
        return when (val childAdapter = pair.first) {
            is AbstractPart -> childAdapter[pair.second].spanSize
            is FormCustomAdapterSpanLookup -> childAdapter.getSpanSize(
                position,
                formAdapter.columnCount
            )

            else -> spanCount
        }
    }

    override fun onGetSpanIndex(position: Int, spanCount: Int): Int {
        val formAdapter = adapter as? FormAdapter ?: return spanCount
        val pair = formAdapter.getWrappedAdapterAndPosition(position)
        return when (val childAdapter = pair.first) {
            is AbstractPart -> childAdapter[pair.second].spanIndex
            is FormCustomAdapterSpanLookup -> childAdapter.getSpanIndex(
                position,
                formAdapter.columnCount
            )

            else -> spanCount
        }
    }
}