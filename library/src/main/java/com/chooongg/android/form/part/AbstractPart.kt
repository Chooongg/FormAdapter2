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
import com.chooongg.android.form.holder.FormViewHolder
import com.chooongg.android.form.item.BaseForm
import com.chooongg.android.form.style.AbstractStyle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel

abstract class AbstractPart(val adapter: FormAdapter, val style: AbstractStyle) :
    RecyclerView.Adapter<FormViewHolder>() {

    private var recyclerView: RecyclerView? = null

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
        override fun areContentsTheSame(oldItem: BaseForm<*>, newItem: BaseForm<*>) =
            oldItem.id == newItem.id

        override fun areItemsTheSame(oldItem: BaseForm<*>, newItem: BaseForm<*>) =
            oldItem.id == newItem.id && oldItem.typeset == newItem.typeset
    }).build())

    protected val showItemList: List<BaseForm<*>> get() = asyncDiffer.currentList

    private val updateRunnableList = ArrayList<Runnable>()

    fun update() {
        val runnable = object : Runnable {
            override fun run() {
                internalUpdate()
                updateRunnableList.remove(this)
            }
        }
        if (recyclerView != null) {
            updateRunnableList.add(runnable)
            recyclerView!!.post(runnable)
        } else {
            internalUpdate()
        }
    }

    fun internalUpdate() {

    }

    abstract operator fun get(field: String): BaseForm<*>?

    abstract operator fun contains(field: String): Boolean

    abstract operator fun contains(item: BaseForm<*>): Boolean

    operator fun get(position: Int): BaseForm<*> = showItemList[position]

    override fun getItemCount(): Int = showItemList.size

    override fun getItemViewType(position: Int): Int =
        adapter.getItemViewType4Pool(style, get(position))

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FormViewHolder {
        val style = adapter.getStyle4ItemViewType(viewType)
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
        get(holder.bindingAdapterPosition).onViewAttachedToWindow(holder)
    }

    override fun onBindViewHolder(holder: FormViewHolder, position: Int) {
        val item = get(position)
        holder.style.onBindViewHolder(holder, item, adapter.isEnabled)
        holder.typeset.onBindViewHolder(holder, item, adapter.isEnabled)
        item.onBindViewHolder(adapterScope, holder, adapter.isEnabled)
    }

    override fun onBindViewHolder(
        holder: FormViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        if (payloads.isEmpty()) {
            super.onBindViewHolder(holder, position, payloads)
            return
        }
        val item = get(position)
    }

    override fun onViewDetachedFromWindow(holder: FormViewHolder) {
        holder.style.onViewDetachedFromWindow(holder)
        holder.typeset.onViewDetachedFromWindow(holder)
        get(holder.bindingAdapterPosition).onViewDetachedFromWindow(holder)
    }

    override fun onViewRecycled(holder: FormViewHolder) {
        holder.style.onViewRecycled(holder)
        holder.typeset.onViewRecycled(holder)
        get(holder.bindingAdapterPosition).onViewRecycled(holder)
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        this.recyclerView = recyclerView
        update()
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        adapterScope.cancel()
        adapterScope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)
        updateRunnableList.forEach {
            recyclerView.removeCallbacks(it)
        }
        updateRunnableList.clear()
        asyncDiffer.submitList(emptyList())
        this.recyclerView = null
    }
}