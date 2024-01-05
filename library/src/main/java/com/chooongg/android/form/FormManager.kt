package com.chooongg.android.form

import android.content.Context
import com.chooongg.android.form.extractor.AbstractTextExtractor
import com.chooongg.android.form.extractor.NormalTextExtractor
import com.chooongg.android.form.formatter.name.AbstractNameFormatter
import com.chooongg.android.form.formatter.name.NormalNameFormatter
import com.chooongg.android.form.typeset.AbstractTypeset
import com.chooongg.android.form.typeset.HorizontalTypeset

object FormManager {

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
            internal set

        /**
         * 文本提取器
         */
        var textExtractor: AbstractTextExtractor = NormalTextExtractor()

        /**
         * 名称格式化程序
         */
        var nameFormatter: AbstractNameFormatter = NormalNameFormatter()

        /**
         * 排版
         */
        var typeset: AbstractTypeset = HorizontalTypeset()
    }

    /**
     * 默认值配置
     */
    class DefaultConfig internal constructor() : Default() {
        fun centerSmoothScroll(boolean: Boolean) {
            centerSmoothScroll = boolean
        }
    }
}