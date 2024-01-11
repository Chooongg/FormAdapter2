package com.chooongg.android.form

import android.content.Context
import android.util.TypedValue
import android.widget.TextView
import com.chooongg.android.form.data.FormContentGravity
import com.chooongg.android.form.data.FormEmsSize
import com.chooongg.android.form.extractor.IIconExtractor
import com.chooongg.android.form.extractor.ITextExtractor
import com.chooongg.android.form.extractor.NormalIconExtractor
import com.chooongg.android.form.extractor.NormalTextExtractor
import com.chooongg.android.form.formatter.name.AbstractNameFormatter
import com.chooongg.android.form.formatter.name.NormalNameFormatter
import com.chooongg.android.form.typeset.AbstractTypeset
import com.chooongg.android.form.typeset.HorizontalTypeset

object FormManager {

    const val FLAG_PAYLOAD_UPDATE_CONTENT = "form_flag_update_content"
    const val FLAG_PAYLOAD_UPDATE_BOUNDARY = "form_flag_update_boundary"
    const val FLAG_PAYLOAD_ERROR_NOTIFY = "form_flag_error_notify"

    /**
     * 默认值
     */
    var default: Default = Default()

    /**
     * 提取文本
     */
    fun extractText(context: Context, text: Any?): CharSequence? =
        default.textExtractor.extract(context, text)

    /**
     * 获取字体实际高度
     */
    fun getFontRealHeight(textView: TextView): Int {
        val tempView = TextView(textView.context)
        tempView.setTextSize(TypedValue.COMPLEX_UNIT_PX, textView.textSize)
        tempView.measure(0, 0)
        return tempView.measuredHeight
//        val fm = textView.paint.fontMetricsInt
//        return textView.paint.getFontMetricsInt(fm)
    }

    /**
     * 默认值
     */
    open class Default internal constructor() {
        /**
         * 居中平滑滚动
         */
        var centerSmoothScroll: Boolean = true
            protected set

        /**
         * 文本提取器
         */
        var textExtractor: ITextExtractor = NormalTextExtractor()
            protected set

        /**
         * 图标提取器
         */
        var iconExtractor: IIconExtractor = NormalIconExtractor()
            protected set

        /**
         * 名称格式化程序
         */
        var nameFormatter: AbstractNameFormatter = NormalNameFormatter()
            protected set

        /**
         * 水平中间填充模式
         */
        var horizontalMiddlePaddingMode: Int = 0
            protected set

        /**
         * EMS 值
         */
        var emsSize: FormEmsSize = FormEmsSize(5)
            protected set

        /**
         * 内容重力
         */
        var contentGravity: FormContentGravity = FormContentGravity()
            protected set

        /**
         * 排版
         */
        var typeset: AbstractTypeset = HorizontalTypeset()
            protected set
    }
}