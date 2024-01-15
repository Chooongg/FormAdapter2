package com.chooongg.android.form.actuator

import com.chooongg.android.form.FormDataVerificationException
import com.chooongg.android.form.item.BaseForm
import org.json.JSONObject

interface FormDataActuator<T : BaseForm<*>> {

    /**
     * 是否覆盖数据验证
     */
    fun isOverwriteOriginalDataVerification(): Boolean

    /**
     * 是否覆盖输出数据
     */
    fun isOverwriteOriginalOutputData(): Boolean

    /**
     * 是否覆盖输出额外数据
     */
    fun isOverwriteOriginalOutputExtensionData(): Boolean

    /**
     * 数据验证
     */
    @Throws(FormDataVerificationException::class)
    fun dataVerification(item: BaseForm<*>) = Unit

    /**
     * 输出数据
     */
    fun outputData(item: BaseForm<*>, json: JSONObject) = Unit

    /**
     * 输出扩展数据
     */
    fun outputExtensionData(item: BaseForm<*>, json: JSONObject) = Unit
}