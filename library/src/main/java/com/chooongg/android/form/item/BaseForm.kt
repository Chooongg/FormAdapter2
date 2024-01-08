package com.chooongg.android.form.item

import android.view.View
import android.view.ViewGroup
import com.chooongg.android.form.holder.FormViewHolder
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

    //</editor-fold>

    //<editor-fold desc="扩展 Extend">

    /**
     * 扩展字段和内容
     */
    private var extensionFieldAndContent: HashMap<String, Any?>? = null

    /**
     * 放置扩展内容
     */
    fun putExtensionContent(key: String, value: Any?) {
        if (value != null) {
            if (extensionFieldAndContent == null) extensionFieldAndContent = HashMap()
            extensionFieldAndContent!![key] = value
        } else if (extensionFieldAndContent != null) {
            extensionFieldAndContent!!.remove(key)
            if (extensionFieldAndContent!!.isEmpty()) extensionFieldAndContent = null
        }
    }

    /**
     * 获取扩展内容
     */
    fun getExtensionContent(key: String): Any? = extensionFieldAndContent?.get(key)

    /**
     * 是否有扩展内容
     */
    fun hasExtensionContent(key: String) = extensionFieldAndContent?.containsKey(key) ?: false

    /**
     * 快照扩展字段和内容
     */
    fun snapshotExtensionFieldAndContent() = extensionFieldAndContent ?: emptyMap()

    /**
     * 删除扩展字段
     */
    fun removeExtensionContent(key: String) = extensionFieldAndContent?.remove(key)

    //</editor-fold>

    //<editor-fold desc="视图 View">

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

    //</editor-fold>
}