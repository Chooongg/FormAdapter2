package com.chooongg.android.form.boundary

import androidx.annotation.IntDef

data class Boundary(
    @BoundaryInt val start: Int,
    @BoundaryInt val top: Int,
    @BoundaryInt val end: Int,
    @BoundaryInt val bottom: Int
) {
    companion object {
        const val NONE = 0
        const val MIDDLE = 2
        const val GLOBAL = 3
    }

    @IntDef(NONE, MIDDLE, GLOBAL)
    annotation class BoundaryInt

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Boundary) return false

        if (start != other.start) return false
        if (top != other.top) return false
        if (end != other.end) return false
        return bottom == other.bottom
    }

    override fun toString(): String {
        return "[start: ${start}, top:${top}, end:${end}, bottom:${bottom}]"
    }

    override fun hashCode(): Int {
        var result = start
        result = 31 * result + top
        result = 31 * result + end
        result = 31 * result + bottom
        return result
    }
}