package com.chooongg.android.form.view

import android.annotation.SuppressLint
import android.content.Context
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.annotation.MenuRes
import androidx.appcompat.view.menu.MenuBuilder
import androidx.appcompat.view.menu.MenuItemImpl
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chooongg.android.form.FormManager
import com.chooongg.android.form.R
import com.chooongg.android.form.helper.FormTextAppearanceHelper
import com.chooongg.android.form.holder.FormViewHolder
import com.chooongg.android.form.item.BaseForm
import com.chooongg.android.form.listener.FormOnMenuCreatedListener
import com.chooongg.android.form.part.AbstractPart
import com.chooongg.android.form.style.AbstractStyle
import com.chooongg.android.ktx.doOnClick
import com.chooongg.android.ktx.gone
import com.chooongg.android.ktx.visible
import com.google.android.material.button.MaterialButton

@SuppressLint("RestrictedApi", "ViewConstructor")
class FormMenuView(
    context: Context,
    style: AbstractStyle
) : RecyclerView(context) {

    private val menuAdapter = Adapter(style)

    init {
        overScrollMode = View.OVER_SCROLL_NEVER
        isNestedScrollingEnabled = false
        layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        adapter = menuAdapter
    }

    fun configMenu(holder: FormViewHolder, item: BaseForm<*>, adapterEnabled: Boolean) {
        if (item.menu != null && item.isMenuVisible(adapterEnabled)) {
            inflateMenu(
                item.menu!!,
                item.isMenuEnable(adapterEnabled),
                item.onMenuCreatedListener
            ) { menuView, menuItem ->
                val isIntercept =
                    item.onMenuItemClickListener?.invoke(holder.itemView, menuView, menuItem)
                if (isIntercept != true) {
                    (holder.bindingAdapter as? AbstractPart<*>)?.adapter?.onMenuClickListener
                        ?.invoke(holder.itemView, menuView, menuItem, item)
                }
            }
        } else clearMenu()
    }

    private fun inflateMenu(
        @MenuRes resId: Int,
        enabled: Boolean,
        menuOnCreatedListener: FormOnMenuCreatedListener?,
        menuOnItemClickListener: (menuView: View, menuItem: MenuItem) -> Unit
    ) {
        val menu = MenuBuilder(context)
        MenuInflater(context).inflate(resId, menu)
        menuOnCreatedListener?.onMenuCreated(menu)
        val visibleItems = menu.visibleItems
        menuAdapter.setMenu(visibleItems, enabled, menuOnItemClickListener)
        if (visibleItems.isEmpty()) gone() else visible()
    }

    private fun clearMenu() {
        menuAdapter.setMenu(null, false, null)
        gone()
    }

    private class Adapter(
        private val style: AbstractStyle
    ) : RecyclerView.Adapter<Adapter.Holder>(), FormTextAppearanceHelper {

        private var items = ArrayList<MenuItemImpl>()
        private var enabled = false
        private var onItemClickListener: ((menuView: View, menuItem: MenuItem) -> Unit)? = null

        private class Holder(view: View) : ViewHolder(view)

        @SuppressLint("NotifyDataSetChanged")
        fun setMenu(
            items: ArrayList<MenuItemImpl>?,
            enabled: Boolean,
            onItemClickListener: ((menuView: View, menuItem: MenuItem) -> Unit)?
        ) {
            this.items = items ?: ArrayList()
            this.enabled = enabled
            this.onItemClickListener = onItemClickListener
            notifyDataSetChanged()
        }

        override fun getItemCount(): Int = items.size

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = Holder(
            MaterialButton(
                parent.context, null, com.google.android.material.R.attr.borderlessButtonStyle
            ).apply {
                insetTop = 0
                insetBottom = 0
                iconPadding = 0
                setTextAppearance(formTextAppearance(R.attr.formTextAppearanceContent))
                val textHeight = FormManager.getFontRealHeight(this)
                minWidth = textHeight + style.padding.startMedium + style.padding.endMedium
                minHeight = textHeight + style.padding.topMedium + style.padding.bottomMedium
                minimumWidth = textHeight + style.padding.startMedium + style.padding.endMedium
                minimumHeight = textHeight + style.padding.topMedium + style.padding.bottomMedium
                iconSize = textHeight
                setPaddingRelative(
                    style.padding.startMedium, style.padding.topMedium,
                    style.padding.endMedium, style.padding.bottomMedium
                )
            })

        override fun onBindViewHolder(holder: Holder, position: Int) {
            val item = items[position]
            with(holder.itemView as MaterialButton) {
                isEnabled = enabled && item.isEnabled
                icon = item.icon
                text = if (icon == null) item.titleCondensed else null
                ViewCompat.setTooltipText(this, item.tooltipText ?: item.title)
                doOnClick { onItemClickListener?.invoke(this, item) }
            }
        }
    }
}