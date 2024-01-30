package com.chooongg.android.form

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.TypedValue
import android.widget.TextView
import com.chooongg.android.form.actuator.FormDataActuator
import com.chooongg.android.form.config.FormGlobalConfig
import com.chooongg.android.form.extractor.IIconExtractor
import com.chooongg.android.form.extractor.ITextExtractor
import com.chooongg.android.form.extractor.NormalIconExtractor
import com.chooongg.android.form.extractor.NormalTextExtractor
import com.chooongg.android.form.formatter.name.AbstractNameFormatter
import com.chooongg.android.form.formatter.name.NormalNameFormatter
import com.chooongg.android.form.item.BaseForm
import java.lang.reflect.ParameterizedType


object FormManager {

    const val FLAG_PAYLOAD_UPDATE_CONTENT = "form_flag_update_content"
    const val FLAG_PAYLOAD_UPDATE_BOUNDARY = "form_flag_update_boundary"
    const val FLAG_PAYLOAD_ERROR_NOTIFY = "form_flag_error_notify"

    /**
     * 居中平滑滚动
     */
    var centerSmoothScroll: Boolean = true

    /**
     * 文本提取器
     */
    private var textExtractor: ITextExtractor = NormalTextExtractor()

    /**
     * 图标提取器
     */
    private var iconExtractor: IIconExtractor = NormalIconExtractor()

    /**
     * 数据执行器
     */
    private val itemDataActuators = HashMap<Class<*>, FormDataActuator<*>>()

    var config: FormGlobalConfig = FormGlobalConfig()

    /**
     * 获取字体实际高度
     */
    fun getFontRealHeight(textView: TextView): Int {
        val tempView = TextView(textView.context)
        tempView.setTextSize(TypedValue.COMPLEX_UNIT_PX, textView.textSize)
        tempView.measure(0, 0)
        return tempView.measuredHeight
    }

    /**
     * 设置文本提取器
     */
    fun setTextExtractor(textExtractor: ITextExtractor) {
        this.textExtractor = textExtractor
    }

    /**
     * 提取文本
     */
    fun extractText(context: Context, text: Any?): CharSequence? =
        textExtractor.extract(context, text)

    /**
     * 设置图标提取器
     */
    fun setIconExtractor(iconExtractor: IIconExtractor) {
        this.iconExtractor = iconExtractor
    }

    /**
     * 提取图标
     */
    fun extractIcon(context: Context, icon: Any?): Drawable? = iconExtractor.extract(context, icon)

    /**
     * 添加数据执行器
     */
    fun putItemDataActuator(actuator: FormDataActuator<*>) {
        val parameterizedType = actuator.javaClass.genericSuperclass as ParameterizedType
        val clazz = parameterizedType.actualTypeArguments[0] as Class<*>
        itemDataActuators[clazz] = actuator
    }

    /**
     * 清除数据执行器
     */
    fun clearItemDataActuator() {
        itemDataActuators.clear()
    }

    /**
     * 查找数据执行器
     */
    internal fun findItemDataActuator(clazz: Class<out BaseForm<*>>): FormDataActuator<*>? {
        return itemDataActuators[clazz]
    }
}