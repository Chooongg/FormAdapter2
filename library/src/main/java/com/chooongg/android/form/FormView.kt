package com.chooongg.android.form

import android.content.Context
import android.util.AttributeSet
import androidx.core.view.updatePaddingRelative
import androidx.recyclerview.widget.RecyclerView
import com.chooongg.android.ktx.resDimensionPixelSize

class FormView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : RecyclerView(context, attrs, defStyleAttr) {

    init {
        val a = context.obtainStyledAttributes(
            attrs,
            R.styleable.FormView,
            defStyleAttr,
            R.style.FormView
        )
        updatePaddingRelative(
            start = if (paddingStart != 0) paddingStart else resDimensionPixelSize(R.dimen.formMarginStart),
            end = if (paddingEnd != 0) paddingEnd else resDimensionPixelSize(R.dimen.formMarginEnd)
        )
        a.recycle()
    }

    override fun onMeasure(widthSpec: Int, heightSpec: Int) {
        super.onMeasure(widthSpec, heightSpec)
    }
}