package com.chooongg.android.form.item

import android.view.View
import androidx.annotation.CallSuper
import com.chooongg.android.form.FormOptionLoader
import com.chooongg.android.form.enum.FormOptionLoadMode
import com.chooongg.android.form.holder.FormViewHolder
import com.chooongg.android.form.option.OptionLoadResult
import com.chooongg.android.form.part.AbstractPart
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.cancellation.CancellationException

abstract class BaseOptionForm<CONTENT>(name: Any?, field: String?) :
    BaseForm<CONTENT>(name, field) {

    companion object {
        const val CHANGE_OPTION_PAYLOAD_FLAG = "CHANGE_OPTION"
    }

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
        get() = localOptions ?: (optionLoadResult as? OptionLoadResult.Success<CONTENT>)?.options
        set(value) {
            localOptions = value
        }

    protected fun isNeedToLoadOption(holder: FormViewHolder): Boolean {
        if (localOptions != null) return false
        if (optionLoader == null) return false
        if (holder.job?.isActive == true) return false
        return when (optionLoadMode) {
            FormOptionLoadMode.ALWAYS -> true
            FormOptionLoadMode.EMPTY -> options.isNullOrEmpty()
        }
    }

    @CallSuper
    override fun onBindViewHolder(
        scope: CoroutineScope,
        holder: FormViewHolder,
        view: View,
        adapterEnabled: Boolean
    ) {
        if (isNeedToLoadOption(holder)) {
            if (optionLoader == null) {
                optionLoadResult = OptionLoadResult.Wait()
                configOption(scope, holder, view, adapterEnabled)
                return
            }
            val adapter = holder.bindingAdapter as? AbstractPart<*> ?: return
            optionLoadResult = OptionLoadResult.Loading()
            notifyOptionChanged(holder)
            holder.job = adapter.adapterScope.launch {
                try {
                    val result = withContext(Dispatchers.IO) {
                        optionLoader!!.invoke(this@BaseOptionForm)
                    }
                    withContext(Dispatchers.Main) {
                        optionLoadResult = if (result.isNullOrEmpty()) {
                            OptionLoadResult.Empty()
                        } else OptionLoadResult.Success(result)
                        notifyOptionChanged(holder)
                    }
                } catch (e: CancellationException) {
                    optionLoadResult = OptionLoadResult.Wait()
                    holder.job = null
                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        optionLoadResult = OptionLoadResult.Error(e)
                        notifyOptionChanged(holder)
                    }
                }
            }
        }
    }

    override fun onBindViewHolderOther(
        scope: CoroutineScope,
        holder: FormViewHolder,
        view: View,
        adapterEnabled: Boolean,
        payload: Any
    ) {
        if (payload == CHANGE_OPTION_PAYLOAD_FLAG) {
            configOption(scope, holder, view, adapterEnabled)
        }
    }

    private fun notifyOptionChanged(holder: FormViewHolder) {
        val adapter = holder.bindingAdapter as? AbstractPart<*> ?: return
        val position = adapter.indexOfShow(this)
        if (position >= 0) {
            adapter.notifyItemChanged(position, CHANGE_OPTION_PAYLOAD_FLAG)
        }
    }

    abstract fun configOption(
        scope: CoroutineScope,
        holder: FormViewHolder,
        view: View,
        adapterEnabled: Boolean
    )
}