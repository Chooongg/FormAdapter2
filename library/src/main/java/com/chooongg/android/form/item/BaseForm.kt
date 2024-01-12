package com.chooongg.android.form.item

import android.view.View
import android.view.ViewGroup
import androidx.annotation.GravityInt
import com.chooongg.android.form.FormManager
import com.chooongg.android.form.data.FormContentGravity
import com.chooongg.android.form.data.FormExtensionMap
import com.chooongg.android.form.holder.FormViewHolder
import com.chooongg.android.form.part.AbstractPart
import com.chooongg.android.form.style.AbstractStyle
import com.chooongg.android.form.typeset.AbstractTypeset
import kotlinx.coroutines.CoroutineScope

abstract class BaseForm<CONTENT : Any>(
    /**
     * 名称
     */
    var name: Any?,
    /**
     * 字段
     */
    var field: String?,
) : AbstractForm() {

    //<editor-fold desc="基础 Basic">

    /**
     * 初始化
     */
    open fun initialize() = Unit

    /**
     * 提示: Int(StringRes), String, CharSequence
     */
    var hint: Any? = null

    /**
     * 内容
     */
    var content: CONTENT? = null

    /**
     * 扩展内容
     */
    val extensionContent = FormExtensionMap()

    /**
     * 是否为必填项
     */
    var required: Boolean = false

    //</editor-fold>

    //<editor-fold desc="排版 Typeset">

    /**
     * 排版
     */
    open var typeset: AbstractTypeset? = null

    /**
     * 独占一行
     */
    open var loneLine = false

    /**
     * 在边缘显示
     */
    open var showAtEdge = true

    /**
     * 自动填充
     */
    open var autoFill = true

    /**
     * 重力
     */
    @GravityInt
    open var gravity: Int? = null

    /**
     * 内容重力
     */
    open var contentGravity: FormContentGravity? = null

    //</editor-fold>

    //<editor-fold desc="视图 View">

    /**
     * 复制空的项目用于缓存ItemViewType并创建View
     */
    abstract fun copyEmptyItem(): BaseForm<CONTENT>

    abstract fun onCreateViewHolder(style: AbstractStyle, parent: ViewGroup): View

    open fun onViewAttachedToWindow(holder: FormViewHolder) = Unit

    abstract fun onBindViewHolder(
        scope: CoroutineScope,
        holder: FormViewHolder,
        adapterEnabled: Boolean
    )

    open fun onBindViewHolderErrorNotify(
        scope: CoroutineScope,
        holder: FormViewHolder,
        adapterEnabled: Boolean
    ) = Unit

    open fun onBindViewHolderOther(
        scope: CoroutineScope,
        holder: FormViewHolder,
        adapterEnabled: Boolean,
        payload: Any,
    ) = Unit

    open fun onViewDetachedFromWindow(holder: FormViewHolder) = Unit

    open fun onViewRecycled(holder: FormViewHolder) = Unit

    /**
     * 获取内容重力
     */
    @GravityInt
    protected fun obtainContentGravity(holder: FormViewHolder): Int {
        val columnCount = (holder.bindingAdapter as? AbstractPart)?.adapter?.columnCount ?: 1
        return if (columnCount > 1) contentGravity?.multiColumnGravity
            ?: gravity
            ?: holder.typeset.contentGravity?.multiColumnGravity
            ?: FormManager.default.contentGravity.multiColumnGravity
        else contentGravity?.gravity
            ?: gravity
            ?: holder.typeset.contentGravity?.gravity
            ?: FormManager.default.contentGravity.gravity
    }

    //</editor-fold>
}