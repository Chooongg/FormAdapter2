package com.chooongg.android.form

import android.view.ViewGroup
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.RecyclerView
import com.chooongg.android.form.item.BaseForm
import com.chooongg.android.form.part.AbstractPart
import com.chooongg.android.form.provider.AbstractFormProvider
import com.chooongg.android.form.style.AbstractStyle
import com.chooongg.android.form.typeset.AbstractTypeset

abstract class AbstractFormAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val concatAdapter = ConcatAdapter(
        ConcatAdapter.Config.Builder().setIsolateViewTypes(false).build()
    )

    private val dataObserver = object : RecyclerView.AdapterDataObserver() {
        override fun onItemRangeChanged(positionStart: Int, itemCount: Int) {
            notifyItemRangeChanged(positionStart, itemCount)
        }

        override fun onItemRangeChanged(positionStart: Int, itemCount: Int, payload: Any?) {
            notifyItemRangeChanged(positionStart, itemCount, payload)
        }

        override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
            notifyItemRangeInserted(positionStart, itemCount)
        }

        override fun onItemRangeRemoved(positionStart: Int, itemCount: Int) {
            notifyItemRangeRemoved(positionStart, itemCount)
        }

        override fun onItemRangeMoved(fromPosition: Int, toPosition: Int, itemCount: Int) {
            notifyItemMoved(fromPosition, toPosition)
        }

        override fun onStateRestorationPolicyChanged() {
            stateRestorationPolicy = concatAdapter.stateRestorationPolicy
        }
    }

    init {
        concatAdapter.registerAdapterDataObserver(dataObserver)
    }

    val partAdapters get() = concatAdapter.adapters.filterIsInstance<AbstractPart>()

    //<editor-fold desc="覆写 Overwrite">

    override fun getItemViewType(position: Int) =
        concatAdapter.getItemViewType(position)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        concatAdapter.onCreateViewHolder(parent, viewType)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) =
        concatAdapter.onBindViewHolder(holder, position)

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) =
        concatAdapter.onBindViewHolder(holder, position, payloads)

    override fun getItemId(position: Int) =
        concatAdapter.getItemId(position)

    override fun getItemCount() =
        concatAdapter.itemCount

    override fun onFailedToRecycleView(holder: RecyclerView.ViewHolder) =
        concatAdapter.onFailedToRecycleView(holder)

    override fun onViewAttachedToWindow(holder: RecyclerView.ViewHolder) =
        concatAdapter.onViewAttachedToWindow(holder)

    override fun onViewDetachedFromWindow(holder: RecyclerView.ViewHolder) =
        concatAdapter.onViewDetachedFromWindow(holder)

    override fun onViewRecycled(holder: RecyclerView.ViewHolder) =
        concatAdapter.onViewRecycled(holder)

    //</editor-fold>

    //<editor-fold desc="类型池 TypePool">

    private val stylePool = ArrayList<AbstractStyle>()
    private val typesetPool = ArrayList<AbstractTypeset>()
    private val providerPool = ArrayList<AbstractFormProvider<*>>()
    private val itemTypePool = ArrayList<Triple<Int, Int, Int>>()

    @Suppress("DEPRECATION")
    internal fun getItemViewType4Pool(
        style: AbstractStyle,
        item: BaseForm<*>
    ): Int {
        val styleIndex = stylePool.indexOf(style).let {
            if (it < 0) {
                stylePool.add(style)
                stylePool.lastIndex
            } else it
        }
        val typeset = item.typeset ?: style.typeset ?: FormManager.default.typeset
        val typesetIndex = typesetPool.indexOf(typeset).let {
            if (it < 0) {
                typesetPool.add(typeset)
                typesetPool.lastIndex
            } else it
        }
        val providerClass = item.getProviderClass(this)
        val providerIndex = providerPool.indexOfFirst { it::class == providerClass }.let {
            if (it < 0) {
                providerPool.add(providerClass.java.newInstance())
                providerPool.lastIndex
            } else it
        }
        val typeInfo = Triple(styleIndex, typesetIndex, providerIndex)
        return itemTypePool.indexOf(typeInfo).let {
            if (it < 0) {
                itemTypePool.add(typeInfo)
                itemTypePool.lastIndex
            } else it
        }
    }

    internal fun getStyle4ItemViewType(viewType: Int): AbstractStyle {
        return stylePool[itemTypePool[viewType].first]
    }

    internal fun getTypeset4ItemViewType(viewType: Int): AbstractTypeset {
        return typesetPool[itemTypePool[viewType].second]
    }

    internal fun getProvider4ItemViewType(viewType: Int): AbstractFormProvider<*> {
        return providerPool[itemTypePool[viewType].third]
    }

    //</editor-fold>
}