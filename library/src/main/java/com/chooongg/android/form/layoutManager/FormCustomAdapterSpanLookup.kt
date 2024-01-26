package com.chooongg.android.form.layoutManager

import android.util.SparseIntArray

interface FormCustomAdapterSpanLookup {

    val spanCount get() = 27720

    fun getSpanIndexCache(): SparseIntArray

    fun invalidateSpanIndexCache() {
        getSpanIndexCache().clear()
    }

    fun getSpanSize(position: Int, formColumn: Int): Int

    fun getSpanIndex(position: Int, formColumn: Int): Int {
        val positionSpanSize = getSpanSize(position, formColumn)
        if (positionSpanSize == spanCount) {
            return 0 // quick return for full-span items
        }
        var span = 0
        var startPos = 0
        // If caching is enabled, try to jump
        // If caching is enabled, try to jump
        val prevKey = findFirstKeyLessThan(getSpanIndexCache(), position)
        if (prevKey >= 0) {
            span = getSpanIndexCache().get(prevKey) + getSpanSize(prevKey, formColumn)
            startPos = prevKey + 1
        }
        for (i in startPos until position) {
            val size = getSpanSize(i, formColumn)
            span += size
            if (span == spanCount) {
                span = 0
            } else if (span > spanCount) {
                // did not fit, moving to next row / column
                span = size
            }
        }
        if (span + positionSpanSize <= spanCount) {
            return span
        }
        return 0
    }

    fun findFirstKeyLessThan(cache: SparseIntArray, position: Int): Int {
        var lo = 0
        var hi = cache.size() - 1
        while (lo <= hi) {
            // Using unsigned shift here to divide by two because it is guaranteed to not
            // overflow.
            val mid = lo + hi ushr 1
            val midVal = cache.keyAt(mid)
            if (midVal < position) {
                lo = mid + 1
            } else {
                hi = mid - 1
            }
        }
        val index = lo - 1
        return if (index >= 0 && index < cache.size()) {
            cache.keyAt(index)
        } else -1
    }
}