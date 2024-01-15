package com.chooongg.android.form.item

import android.view.View
import android.view.ViewGroup
import androidx.annotation.GravityInt
import com.chooongg.android.form.FormDataVerificationException
import com.chooongg.android.form.FormManager
import com.chooongg.android.form.data.FormExtensionMap
import com.chooongg.android.form.enum.FormContentGravity
import com.chooongg.android.form.enum.FormOutputMode
import com.chooongg.android.form.enum.FormValidateMode
import com.chooongg.android.form.helper.FormTextAppearanceHelper
import com.chooongg.android.form.holder.FormViewHolder
import com.chooongg.android.form.part.AbstractPart
import com.chooongg.android.form.style.AbstractStyle
import com.chooongg.android.form.typeset.AbstractTypeset
import kotlinx.coroutines.CoroutineScope
import org.json.JSONObject

abstract class BaseForm<CONTENT : Any>(
    /**
     * 名称
     */
    var name: Any?,
    /**
     * 字段
     */
    var field: String?,
) : AbstractForm(), FormTextAppearanceHelper {

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

    //<editor-fold desc="验证 Validate">

    open var validateMode: FormValidateMode = FormValidateMode.OUTPUT

    /**
     * 执行数据验证
     */
    @Throws(FormDataVerificationException::class)
    fun executeDataVerification(adapterEnabled: Boolean) {
        when (validateMode) {
            FormValidateMode.ALWAYS -> Unit
            FormValidateMode.OUTPUT -> if (!isOutput(adapterEnabled)) return
            FormValidateMode.NEVER -> return
        }
        FormManager.findItemDataActuator(javaClass)?.also {
            if (it.isOverwriteOriginalDataVerification()) {
                it.dataVerification(this)
                return
            }
        }
        dataVerification()
    }

    /**
     * 数据验证
     */
    @Throws(FormDataVerificationException::class)
    protected open fun dataVerification() {
        if (required && content == null) {
            throw FormDataVerificationException(this, FormDataVerificationException.ErrorType.Empty)
        }
    }

    //</editor-fold>

    //<editor-fold desc="输出 Output">

    /**
     * 输出模式
     */
    open var outputMode: FormOutputMode = FormOutputMode.VISIBLE

    /**
     * 是否输出
     */
    fun isOutput(adapterEnabled: Boolean) = when (outputMode) {
        FormOutputMode.ALWAYS -> true
        FormOutputMode.VISIBLE -> isVisible(adapterEnabled)
        FormOutputMode.VISIBLE_ENABLED -> isVisible(adapterEnabled) && isEnable(adapterEnabled)
        FormOutputMode.VISIBLE_DISABLE -> isVisible(adapterEnabled) && !isEnable(adapterEnabled)
        FormOutputMode.INVISIBLE -> !isVisible(adapterEnabled)
        FormOutputMode.INVISIBLE_ENABLE -> !isVisible(adapterEnabled) && isEnable(adapterEnabled)
        FormOutputMode.INVISIBLE_DISABLE -> !isVisible(adapterEnabled) && !isEnable(adapterEnabled)
        FormOutputMode.ENABLED -> isEnable(adapterEnabled)
        FormOutputMode.DISABLE -> !isEnable(adapterEnabled)
        FormOutputMode.NEVER -> false
    }

    /**
     * 执行输出
     */
    fun executeOutput(adapterEnabled: Boolean, json: JSONObject) {
        if (!isOutput(adapterEnabled)) return
        val actuator = FormManager.findItemDataActuator(javaClass)
        if (actuator?.isOverwriteOriginalOutputData() == true) {
            actuator.outputData(this, json)
        } else outputData(json)
        if (actuator?.isOverwriteOriginalOutputExtensionData() == true) {
            actuator.outputExtensionData(this, json)
        } else outputExtensionData(json)
    }

    /**
     * 输出数据
     */
    protected open fun outputData(json: JSONObject) {
        if (field != null && content != null) json.put(field!!, content)
    }

    /**
     * 输出扩展数据
     */
    protected open fun outputExtensionData(json: JSONObject) {
        extensionContent.forEach { field, content -> json.putOpt(field, content) }
    }

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