package com.chooongg.android.form.item

import android.view.View
import android.view.ViewGroup
import androidx.annotation.IntegerRes
import com.chooongg.android.form.FormAdapter
import com.chooongg.android.form.FormContentFormatter
import com.chooongg.android.form.data.FormDetailData
import com.chooongg.android.form.holder.FormViewHolder
import com.chooongg.android.form.provider.detail.AbstractDetailProvider
import com.chooongg.android.form.style.AbstractStyle
import kotlinx.coroutines.CoroutineScope

class FormDetail(name: Any?, field: String?) : InternalFormGroupTitle<FormDetailData>() {

    /**
     * 详情列数资源
     */
    @IntegerRes
    var detailColumnRes: Int? = null

    /**
     * 内容格式化工具
     */
    var contentFormatter: FormContentFormatter? = null
        private set

    /**
     * 提供器
     */
    var provider: AbstractDetailProvider? = null

    override var loneLine: Boolean = false

    override var isRespondToClickEvents: Boolean = true

    init {
        this.name = name
        this.field = field
    }

    fun content(block: FormDetailData.() -> Unit) {
        content = FormDetailData().apply(block)
    }

    /**
     * 内容格式化工具
     */
    fun contentFormatter(block: FormContentFormatter?) {
        contentFormatter = block
    }

    override fun copyEmptyItem(): BaseForm<FormDetailData> = FormDetail(null, null)

    override fun onCreateViewHolder(style: AbstractStyle, parent: ViewGroup): View =
        (provider ?: style.config.detailTitleProvider).onCreateViewHolder(style, parent)

    override fun onViewAttachedToWindow(holder: FormViewHolder) {
        (provider ?: holder.style.config.detailTitleProvider).onViewAttachedToWindow(holder)
    }

    override fun onBindViewHolder(
        scope: CoroutineScope,
        holder: FormViewHolder,
        view: View,
        adapterEnabled: Boolean
    ) {
        (provider ?: holder.style.config.detailTitleProvider).onBindViewHolder(
            scope, holder, view, this, adapterEnabled
        )
    }

    override fun onBindViewItemClick(
        adapter: FormAdapter,
        scope: CoroutineScope,
        holder: FormViewHolder,
        adapterEnabled: Boolean
    ) {
        (provider ?: holder.style.config.detailTitleProvider).onBindViewItemClick(
            adapter, scope, holder, this, adapterEnabled
        )
    }

    override fun onViewDetachedFromWindow(holder: FormViewHolder) {
        (provider ?: holder.style.config.detailTitleProvider).onViewDetachedFromWindow(holder)
    }

    override fun onViewRecycled(holder: FormViewHolder) {
        (provider ?: holder.style.config.detailTitleProvider).onViewRecycled(holder)
    }
}