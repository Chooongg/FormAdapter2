package com.chooongg.android.form.item

import android.view.View
import android.view.ViewGroup
import com.chooongg.android.form.holder.FormViewHolder
import com.chooongg.android.form.style.AbstractStyle
import com.chooongg.android.form.typeset.AbstractTypeset
import com.chooongg.android.form.typeset.NoneTypeset
import kotlinx.coroutines.CoroutineScope

open class InternalFormGroupTitle<CONTENT : Any> internal constructor() :
    BaseForm<CONTENT>(null, null) {

    override var loneLine: Boolean = true

    override var fillEdges: Boolean = false

    override var typeset: AbstractTypeset? = NoneTypeset()

    override fun copyEmptyItem(): BaseForm<CONTENT> = InternalFormGroupTitle()

    override fun onCreateViewHolder(style: AbstractStyle, parent: ViewGroup): View {
        return style.config.groupTitleProvider.onCreateViewHolder(style, parent)
    }

    override fun onViewAttachedToWindow(holder: FormViewHolder) {
        holder.style.config.groupTitleProvider.onViewAttachedToWindow(holder)
    }

    override fun onBindViewHolder(
        scope: CoroutineScope,
        holder: FormViewHolder,
        view: View,
        adapterEnabled: Boolean
    ) {
        holder.style.config.groupTitleProvider.onBindViewHolder(
            scope, holder, view, this, adapterEnabled
        )
    }

    override fun onViewDetachedFromWindow(holder: FormViewHolder) {
        holder.style.config.groupTitleProvider.onViewDetachedFromWindow(holder)
    }

    override fun onViewRecycled(holder: FormViewHolder) {
        holder.style.config.groupTitleProvider.onViewRecycled(holder)
    }
}