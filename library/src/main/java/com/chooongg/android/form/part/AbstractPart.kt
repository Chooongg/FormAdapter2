package com.chooongg.android.form.part

import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ListUpdateCallback
import androidx.recyclerview.widget.RecyclerView
import com.chooongg.android.form.FormAdapter
import com.chooongg.android.form.FormManager
import com.chooongg.android.form.boundary.Boundary
import com.chooongg.android.form.data.AbstractPartData
import com.chooongg.android.form.holder.FormViewHolder
import com.chooongg.android.form.item.BaseForm
import com.chooongg.android.form.item.InternalFormNone
import com.chooongg.android.form.style.AbstractStyle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel

abstract class AbstractPart<DATA : AbstractPartData>(
    val adapter: FormAdapter,
    val style: AbstractStyle,
    var data: DATA
) : RecyclerView.Adapter<FormViewHolder>() {

    protected open var needBlankFill: Boolean = true

    private val spanCount = 27720

    private var recyclerView: RecyclerView? = null

    internal var columnCount: Int = 0
        get() {
            return if (field <= 0) adapter.columnCount ?: 1 else field
        }

    var adapterScope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)
        internal set

    private val asyncDiffer = AsyncListDiffer(object : ListUpdateCallback {
        override fun onChanged(position: Int, count: Int, payload: Any?) = Unit

        override fun onRemoved(position: Int, count: Int) =
            notifyItemRangeRemoved(position, count)

        override fun onInserted(position: Int, count: Int) =
            notifyItemRangeInserted(position, count)

        override fun onMoved(fromPosition: Int, toPosition: Int) =
            notifyItemMoved(fromPosition, toPosition)

    }, AsyncDifferConfig.Builder(object : DiffUtil.ItemCallback<BaseForm<*>>() {
        override fun areContentsTheSame(oldItem: BaseForm<*>, newItem: BaseForm<*>) = true
        override fun areItemsTheSame(oldItem: BaseForm<*>, newItem: BaseForm<*>) =
            oldItem.id == newItem.id && oldItem.typeset == newItem.typeset
    }).build())

    protected val showItemList: List<BaseForm<*>> get() = asyncDiffer.currentList

    fun update() {
        executeUpdate()
    }

    private fun executeUpdate() {
        val groups = getOriginalItemList()
        val ignoreListCount = getIgnoreListCount()
        val tempList = ArrayList<ArrayList<BaseForm<*>>>()
        groups.forEach { group ->
            val tempGroup = ArrayList<BaseForm<*>>()
            group.forEach { item ->
                item.enabled = item.isEnable(adapter.isEnabled)
                item.resetInternalData()
                item.initialize()
                if (item.isVisible(adapter.isEnabled)) {
                    when (item) {
                        else -> tempGroup.add(item)
                    }
                }
            }
            while (tempGroup.firstOrNull()?.showAtEdge == false) {
                tempGroup.removeFirst()
            }
            while (tempGroup.lastOrNull()?.showAtEdge == false) {
                tempGroup.removeLast()
            }
            tempList.add(tempGroup)
        }
        val tempList2 = ArrayList<List<BaseForm<*>>>()
        tempList.forEach { group ->
            val tempGroup = ArrayList<BaseForm<*>>()
            var spanIndex = 0
            group.forEachIndexed { position, item ->
                item.spanIndex = spanIndex
                item.spanSize = when {
                    item.loneLine -> {
                        spanIndex = 0
                        item.spanIndex = 0
                        spanCount
                    }

                    else -> spanCount / columnCount
                }
                if (position > 0 && item.spanIndex == 0) {
                    val lastItem = group[position - 1]
                    if (lastItem.spanIndex + lastItem.spanSize < spanCount) {
                        if (lastItem.autoFill) {
                            lastItem.spanSize = spanCount - lastItem.spanIndex
                        } else if (style.isDecorateNoneItem()) {
                            val noneIndex = lastItem.spanIndex + lastItem.spanSize
                            tempGroup.add(InternalFormNone(noneIndex, spanCount - noneIndex))
                        }
                    }
                }
                spanIndex = if (spanIndex + item.spanSize < spanCount) {
                    spanIndex + item.spanSize
                } else 0
                tempGroup.add(item)
                if (position == group.lastIndex && item.spanIndex + item.spanSize < spanCount) {
                    if (item.autoFill) {
                        item.spanSize = spanCount - item.spanIndex
                    } else if (needBlankFill) {
                        val noneIndex = item.spanIndex + item.spanSize
                        tempGroup.add(InternalFormNone(noneIndex, spanCount - noneIndex))
                    }
                }
            }
            tempList2.add(tempGroup)
        }
        var localPosition = 0
        tempList2.forEachIndexed { index, group ->
            group.forEachIndexed { position, item ->
                item.groupCount = tempList2.size - ignoreListCount
                item.groupIndex = index
                item.countInGroup = group.size
                item.positionInGroup = position
                item.localPosition = localPosition
                localPosition++
            }
        }
        asyncDiffer.submitList(ArrayList<BaseForm<*>>().apply { tempList2.forEach { addAll(it) } }) {
            calculateBoundary()
            showItemList.forEachIndexed { index, item ->
                if (item.lastEnabled != item.enabled) {
                    notifyItemChanged(index)
                } else {
                    notifyItemChanged(index, FormManager.FLAG_PAYLOAD_UPDATE_CONTENT)
                    if (item.lastBoundary != item.boundary) {
                        notifyItemChanged(index, FormManager.FLAG_PAYLOAD_UPDATE_BOUNDARY)
                    }
                }
            }
        }
    }

    private fun calculateBoundary() {
        showItemList.forEachIndexed { index, item ->
            // Start
            val boundary = Boundary()
            boundary.start = if (item.spanIndex == 0) {
                Boundary.GLOBAL
            } else if (style.isIndependentItem) {
                Boundary.MIDDLE
            } else {
                Boundary.NONE
            }
            // End
            val isGroupLast = item.positionInGroup == item.countInGroup - 1
            boundary.end = if ((item.spanIndex + item.spanSize >= spanCount || isGroupLast)) {
                Boundary.GLOBAL
            } else if (style.isIndependentItem) {
                Boundary.MIDDLE
            } else {
                Boundary.NONE
            }
            // Top
            boundary.top = if (item.positionInGroup == 0) {
                Boundary.MIDDLE
            } else if (item.spanIndex == 0) {
                Boundary.NONE
            } else {
                var beginIndex = index - 1
                var beginItem = get(beginIndex)
                while (beginIndex < showItemList.lastIndex && beginItem.spanIndex != 0 && item.positionInGroup != 0) {
                    beginIndex--
                    beginItem = get(beginIndex)
                }
                beginItem.boundary.top
            }
            item.boundary = boundary
        }
        for (index in showItemList.lastIndex downTo 0) {
            val item = get(index)
            // Bottom
            item.boundary.bottom = if (item.countInGroup - 1 - item.positionInGroup == 0) {
                Boundary.MIDDLE
            } else if (item.spanIndex + item.spanIndex >= spanCount) {
                Boundary.NONE
            } else {
                var lastIndex = index
                var lastItem = get(lastIndex)
                while (lastIndex < showItemList.lastIndex && get(lastIndex + 1).spanIndex != 0 && lastItem.countInGroup - 1 - lastItem.positionInGroup != 0) {
                    lastIndex++
                    lastItem = get(lastIndex)
                }
                lastItem.boundary.bottom
            }
        }
    }

    protected abstract fun getOriginalItemList(): List<List<BaseForm<*>>>

    protected open fun getIgnoreListCount(): Int = 0

    abstract fun executeLinkage(isIgnoreUpdate: Boolean = false)

    operator fun get(position: Int): BaseForm<*> = showItemList[position]

    abstract operator fun get(field: String): BaseForm<*>?

    abstract operator fun contains(field: String): Boolean

    abstract operator fun contains(item: BaseForm<*>): Boolean

    override fun getItemCount(): Int = showItemList.size

    override fun getItemViewType(position: Int): Int =
        adapter.getItemViewType4Pool(style, get(position))

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FormViewHolder {
        val style = adapter.getStyle4ItemViewType(viewType)
        style.createSizeInfo(parent.context)
        val typeset = adapter.getTypeset4ItemViewType(viewType)
        val item = adapter.getItem4ItemViewType(viewType)
        val styleLayout = style.onCreateViewHolder(parent)?.apply {
            clipChildren = false
            clipToPadding = false
        }
        val typesetLayout = typeset.onCreateViewHolder(style, styleLayout ?: parent)?.apply {
            clipChildren = false
            clipToPadding = false
        }
        val view = item.onCreateViewHolder(style, typesetLayout ?: styleLayout ?: parent)
        val holder = FormViewHolder(style, styleLayout, typeset, typesetLayout, view)
        holder.itemView.textAlignment = TextView.TEXT_ALIGNMENT_VIEW_START
        holder.itemView.textDirection = TextView.TEXT_DIRECTION_LOCALE
        holder.itemView.layoutParams = if (holder.itemView.layoutParams != null) {
            GridLayoutManager.LayoutParams(holder.itemView.layoutParams!!)
        } else GridLayoutManager.LayoutParams(-1, -2)
        return holder
    }

    override fun onViewAttachedToWindow(holder: FormViewHolder) {
        holder.style.onViewAttachedToWindow(holder)
        holder.typeset.onViewAttachedToWindow(holder)
        adapter.getItem4ItemViewType(holder.itemViewType).onViewAttachedToWindow(holder)
    }

    override fun onBindViewHolder(holder: FormViewHolder, position: Int) {
        val item = get(position)
        item.globalPosition = holder.absoluteAdapterPosition
        item.localPosition = holder.bindingAdapterPosition
        holder.style.initializeShapeAppearanceModel(holder)
        holder.style.onBindViewHolderBefore(holder, item, adapter.isEnabled)
        holder.style.onBindViewHolder(holder, item, holder.styleLayout, adapter.isEnabled)
        if (holder.typesetLayout != null) {
            holder.typeset.onBindViewHolder(holder, item, holder.typesetLayout, adapter.isEnabled)
        }
        holder.itemView.tag = item.spanSize
        item.onBindViewItemClick(adapter, adapterScope, holder, adapter.isEnabled)
        item.onBindViewHolder(adapterScope, holder, holder.view, adapter.isEnabled)
        holder.style.onBindViewHolderAfter(holder, item, adapter.isEnabled)
    }

    override fun onBindViewHolder(
        holder: FormViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        if (payloads.isEmpty()) {
            onBindViewHolder(holder, position)
            return
        }
        val item = get(position)
        item.onBindViewItemClick(adapter, adapterScope, holder, adapter.isEnabled)
        payloads.forEach {
            when (it) {
                FormManager.FLAG_PAYLOAD_UPDATE_CONTENT -> {
                    item.onBindViewHolder(adapterScope, holder, holder.view, adapter.isEnabled)
                }

                FormManager.FLAG_PAYLOAD_UPDATE_BOUNDARY -> {
                    val style = adapter.getStyle4ItemViewType(holder.itemViewType)
                    style.onBindViewHolderBefore(holder, item, adapter.isEnabled)
                    style.onBindViewHolder(holder, item, holder.styleLayout, adapter.isEnabled)
                    style.onBindViewHolderAfter(holder, item, adapter.isEnabled)
                }

                else -> item.onBindViewHolderOther(
                    adapterScope, holder, holder.view, adapter.isEnabled, it
                )
            }
        }
        item.globalPosition = holder.absoluteAdapterPosition
        item.localPosition = holder.bindingAdapterPosition

    }

    override fun onViewDetachedFromWindow(holder: FormViewHolder) {
        holder.style.onViewDetachedFromWindow(holder)
        holder.typeset.onViewDetachedFromWindow(holder)
        adapter.getItem4ItemViewType(holder.itemViewType).onViewDetachedFromWindow(holder)
    }

    override fun onViewRecycled(holder: FormViewHolder) {
        holder.style.onViewRecycled(holder)
        holder.typeset.onViewRecycled(holder)
        adapter.getItem4ItemViewType(holder.itemViewType).onViewRecycled(holder)
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        this.recyclerView = recyclerView
        update()
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        adapterScope.cancel()
        adapterScope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)
        asyncDiffer.submitList(emptyList())
        this.recyclerView = null
    }
}