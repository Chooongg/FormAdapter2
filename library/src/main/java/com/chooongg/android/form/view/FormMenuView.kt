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
            ) {
                val isIntercept = item.onMenuItemClickListener?.onMenuItemClick(context, it, item)
                if (isIntercept == true) true else {
                    val isUse = (holder.bindingAdapter as? AbstractPart)?.adapter
                        ?.onMenuItemClickListener?.onMenuItemClick(context, it, item)
                    isUse == true
                }
            }
        } else clearMenu()
    }

    private fun inflateMenu(
        @MenuRes resId: Int,
        enabled: Boolean,
        menuOnCreatedListener: FormOnMenuCreatedListener?,
        menuOnItemClickListener: MenuItem.OnMenuItemClickListener
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
        private var onItemClickListener: MenuItem.OnMenuItemClickListener? = null

        private class Holder(view: View) : ViewHolder(view)

        @SuppressLint("NotifyDataSetChanged")
        fun setMenu(
            items: ArrayList<MenuItemImpl>?,
            enabled: Boolean,
            onItemClickListener: MenuItem.OnMenuItemClickListener?
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
                minWidth = 0
                minHeight = 0
                minimumWidth = 0
                minimumHeight = 0
                iconPadding = 0
                setTextAppearance(formTextAppearance(R.attr.formTextAppearanceContent))
                setPaddingRelative(
                    style.padding.startMedium, style.padding.topMedium,
                    style.padding.endMedium, style.padding.bottomMedium
                )
                iconSize = FormManager.getFontRealHeight(this)
            })

        override fun onBindViewHolder(holder: Holder, position: Int) {
            val item = items[position]
            with(holder.itemView as MaterialButton) {
                isEnabled = enabled && item.isEnabled
                text = if (icon == null) item.titleCondensed else null
//                iconTint = item.iconTintList ?: ColorStateList.valueOf(
//                    context.obtainStyledAttributes(
//                        intArrayOf(com.google.android.material.R.attr.colorOutline)
//                    ).use { it.getColor(0, Color.GRAY) }
//                )
//                iconTintMode = item.iconTintMode
                ViewCompat.setTooltipText(this, item.tooltipText ?: item.title)
                setOnClickListener { onItemClickListener?.onMenuItemClick(item) }
            }
        }
    }
}