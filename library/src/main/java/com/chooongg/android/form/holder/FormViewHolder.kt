package com.chooongg.android.form.holder

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.chooongg.android.form.style.AbstractStyle
import com.chooongg.android.form.typeset.AbstractTypeset
import kotlinx.coroutines.Job

class FormViewHolder(
    val style: AbstractStyle,
    val styleLayout: ViewGroup?,
    val typeset: AbstractTypeset,
    val typesetLayout: ViewGroup?,
    val view: View
) : RecyclerView.ViewHolder(styleLayout ?: typesetLayout ?: view) {
    var job: Job? = null

    init {
        if (styleLayout != null && typesetLayout != null) {
            style.executeAddView(styleLayout, typesetLayout)
        }
        if (styleLayout != null && typesetLayout == null) {
            style.executeAddView(styleLayout, view)
        }
        if (typesetLayout != null) {
            typeset.executeAddView(style, typesetLayout, view)
        }
    }
}