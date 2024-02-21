package com.chooongg.android.form.item

import com.chooongg.android.form.FormOptionLoader
import com.chooongg.android.form.enum.FormOptionLoadMode
import com.chooongg.android.form.holder.FormViewHolder
import com.chooongg.android.form.option.OptionLoadResult

abstract class BaseOptionForm<CONTENT>(name: Any?, field: String?) :
    BaseForm<CONTENT>(name, field) {

    /**
     * 具有打开操作
     */
    protected abstract val hasOpenOperation: Boolean

    /**
     * 禁用提示: Int(StringRes), String, CharSequence
     */
    var disableHint: Any? = null

    /**
     * 本地设置的选项
     */
    private var localOptions: List<CONTENT>? = null

    /**
     * 选项加载模式
     */
    open var optionLoadMode = FormOptionLoadMode.EMPTY

    /**
     * 选项加载器
     */
    private var optionLoader: FormOptionLoader<CONTENT>? = null

    /**
     * 选项加载结果
     */
    var optionLoadResult: OptionLoadResult<CONTENT> = OptionLoadResult.Wait()
        protected set

    /**
     * 选项
     */
    var options: List<CONTENT>?
        get() = localOptions ?: (optionLoadResult as? OptionLoadResult.Success<T>)?.options
        set(value) {
            localOptions = value
        }

    fun isNeedToLoadOption(holder: FormViewHolder): Boolean {
        if (localOptions != null) return false
        if (optionLoader == null) return false
        if (holder.job?.isActive == true) return false
        return when (optionLoadMode) {
            FormOptionLoadMode.ALWAYS -> true
            FormOptionLoadMode.EMPTY -> options.isNullOrEmpty()
        }
    }
}