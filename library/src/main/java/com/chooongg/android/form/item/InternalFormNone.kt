package com.chooongg.android.form.item

import android.view.View
import android.view.ViewGroup
import com.chooongg.android.form.holder.FormViewHolder
import com.chooongg.android.form.style.AbstractStyle
import com.chooongg.android.form.typeset.AbstractTypeset
import com.chooongg.android.form.typeset.NoneTypeset
import kotlinx.coroutines.CoroutineScope

class InternalFormNone internal constructor(spanIndex: Int, spanSize: Int) :
    BaseForm<Any>(null, null) {

    override val id: String = ""

    override var typeset: AbstractTypeset? = NoneTypeset()

    init {
        this.spanIndex = spanIndex
        this.spanSize = spanSize
    }

    override fun copyEmptyItem(): BaseForm<Any> = this

    override fun onCreateViewHolder(style: AbstractStyle, parent: ViewGroup): View =
        View(parent.context)

    override fun onBindViewHolder(
        scope: CoroutineScope,
        holder: FormViewHolder,
        view: View,
        adapterEnabled: Boolean
    ) = Unit
}