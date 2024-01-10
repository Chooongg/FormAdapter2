package com.chooongg.android.form

import android.content.Context
import androidx.annotation.IntRange
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

    var default: Default = Default()
        internal set

    /**
     * 配置默认值
     */
    fun configDefault(block: DefaultConfig.() -> Unit) {
        default = DefaultConfig().apply(block)
    }

    fun extractText(context: Context, text: Any?): CharSequence? =
        default.textExtractor.extract(context, text)

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
         * EMS值
         */
        var emsSize: Int = 5
            protected set

        /**
         * 排版
         */
        var typeset: AbstractTypeset = HorizontalTypeset()
            protected set
    }

    /**
     * 默认值配置
     */
    class DefaultConfig internal constructor() : Default() {

        fun centerSmoothScroll(boolean: Boolean) {
            centerSmoothScroll = boolean
        }


        fun textExtractor(extractor: ITextExtractor) {
            textExtractor = extractor
        }


        fun iconExtractor(extractor: IIconExtractor) {
            iconExtractor = extractor
        }


        fun nameFormatter(formatter: AbstractNameFormatter) {
            nameFormatter = formatter
        }


        fun emsSize(@IntRange(from = 1, to = 20) size: Int) {
            emsSize = size
        }


        fun typeset(typeset: AbstractTypeset) {
            this.typeset = typeset
        }
    }
}