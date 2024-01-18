package com.chooongg.android.form.layoutManager

import android.content.Context
import com.chooongg.android.form.FormAdapter
import com.chooongg.android.form.part.AbstractPart

class FormLayoutManager(context: Context) : AbstractFormLayoutManager(context) {
    override fun onGetSpanSize(position: Int): Int {
        val formAdapter = adapter as? FormAdapter ?: return spanCount
        val pair = formAdapter.getWrappedAdapterAndPosition(position)
        val childAdapter = pair.first
        return when (childAdapter) {
            is AbstractPart -> {
                childAdapter[pair.second].positionInGroup
                spanCount
            }

            is FormCustomAdapterSpanLookup -> {
                childAdapter.getSpanIndex(position)
            }

            else -> spanCount
        }
    }

    override fun onGetSpanIndex(position: Int, spanCount: Int): Int {
        return 1
    }
}