package com.chooongg.android.form

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.RecyclerView
import com.chooongg.android.form.layoutManager.AbstractFormLayoutManager
import com.chooongg.android.form.layoutManager.FormLayoutManager
import com.chooongg.android.ktx.resDimensionPixelSize

class FormView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : RecyclerView(context, attrs, defStyleAttr) {

    private var column = 0
    private var layoutMarginStart = 0
    private var layoutMarginEnd = 0

    init {
        val a = context.obtainStyledAttributes(attrs, R.styleable.FormView, defStyleAttr, 0)
        layoutMarginStart = a.getDimensionPixelSize(
            R.styleable.FormView_formMarginStart,
            resDimensionPixelSize(R.dimen.formMarginStart)
        )
        layoutMarginEnd = a.getDimensionPixelSize(
            R.styleable.FormView_formMarginEnd,
            resDimensionPixelSize(R.dimen.formMarginEnd)
        )
        column = a.getInteger(R.styleable.FormView_formColumn, 1)
        a.recycle()
        super.setLayoutManager(FormLayoutManager(context).apply {
            marginStart = layoutMarginStart
            marginEnd = layoutMarginEnd
        })
        addItemDecoration(FormItemDecoration())
    }

    override fun setAdapter(adapter: Adapter<*>?) {
        if (adapter is FormAdapter) adapter.setColumnCountInternal(column)
        super.setAdapter(adapter)
    }

    override fun setLayoutManager(layout: LayoutManager?) {
        if (layout is AbstractFormLayoutManager) {
            layout.marginStart = layoutMarginStart
            layout.marginEnd = layoutMarginEnd
        }
        super.setLayoutManager(layout)
    }

    fun setFormMargin(start: Int, end: Int) {
        layoutMarginStart = start
        layoutMarginEnd = end
        if (layoutManager is AbstractFormLayoutManager) {
            val manager = layoutManager as AbstractFormLayoutManager
            manager.marginStart = layoutMarginStart
            manager.marginEnd = layoutMarginEnd
            layoutManager = manager
        }
    }
}