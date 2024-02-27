package com.chooongg.android.form.provider.detail

import android.content.Intent
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.app.ActivityOptionsCompat
import androidx.core.view.isVisible
import com.chooongg.android.form.FormAdapter
import com.chooongg.android.form.FormManager
import com.chooongg.android.form.R
import com.chooongg.android.form.boundary.Boundary
import com.chooongg.android.form.data.AbstractPartData
import com.chooongg.android.form.holder.FormViewHolder
import com.chooongg.android.form.item.FormDetail
import com.chooongg.android.form.part.AbstractPart
import com.chooongg.android.form.style.AbstractStyle
import com.chooongg.android.form.view.FormDetailActivity
import com.chooongg.android.form.view.FormMenuView
import com.chooongg.android.ktx.doOnClick
import com.chooongg.android.ktx.getActivity
import com.chooongg.android.ktx.gone
import com.chooongg.android.ktx.resDrawable
import com.chooongg.android.ktx.visible
import com.google.android.material.button.MaterialButton
import com.google.android.material.textview.MaterialTextView
import com.google.android.material.transition.platform.MaterialContainerTransformSharedElementCallback
import kotlinx.coroutines.CoroutineScope

class NormalDetailProvider : AbstractDetailProvider() {
    override fun onCreateViewHolder(style: AbstractStyle, parent: ViewGroup): View =
        FrameLayout(parent.context).also { root ->
            root.addView(LinearLayoutCompat(root.context).also { layout ->
                layout.orientation = LinearLayoutCompat.VERTICAL
                layout.addView(LinearLayoutCompat(layout.context).also {
                    it.id = R.id.formInternalTitleLayout
                    it.orientation = LinearLayoutCompat.HORIZONTAL
                    it.addView(MaterialButton(it.context).apply {
                        id = R.id.formInternalNameView
                        isClickable = false
                        gravity = Gravity.NO_GRAVITY
                        background = null
                        minWidth = 0
                        minHeight = 0
                        minimumWidth = 0
                        minimumHeight = 0
                        setTextAppearance(formTextAppearance(R.attr.formTextAppearanceDetailTitle))
                        iconSize = FormManager.getFontRealHeight(this)
                        iconPadding = 0
                        setPaddingRelative(
                            style.padding.startMedium, style.padding.topMedium,
                            style.padding.endMedium, style.padding.bottomMedium
                        )
                    }, LinearLayoutCompat.LayoutParams(-2, -2))
                    it.addView(FormMenuView(it.context, style).apply {
                        id = R.id.formInternalMenuView
                    }, LinearLayoutCompat.LayoutParams(-2, -2))
                }, LinearLayoutCompat.LayoutParams(-1, -2))
                layout.addView(MaterialTextView(layout.context).apply {
                    id = R.id.formInternalContentView
                    setTextAppearance(formTextAppearance(R.attr.formTextAppearanceContent))
                    setPaddingRelative(
                        style.padding.startMedium, style.padding.topMedium,
                        style.padding.endMedium, style.padding.bottomMedium
                    )
                }, LinearLayoutCompat.LayoutParams(-1, -2))
            }, FrameLayout.LayoutParams(-1, -2))
        }

    override fun onBindViewHolder(
        scope: CoroutineScope,
        holder: FormViewHolder,
        view: View,
        item: FormDetail,
        adapterEnabled: Boolean
    ) {
        if (holder.itemView.foreground != null) {
            (holder.itemView as ViewGroup).getChildAt(0).foreground = holder.itemView.foreground
            holder.itemView.foreground = null
        }
        val layout = view.findViewById<LinearLayoutCompat>(R.id.formInternalTitleLayout)
        layout.findViewById<MaterialButton>(R.id.formInternalNameView).apply {
            iconGravity = item.iconGravity
            if (item.icon != null) {
                val drawable = resDrawable(item.icon!!)
                icon = drawable
                iconTint = item.iconTint?.invoke(context) ?: textColors
            } else icon = null
            text = FormManager.extractText(context, item.name)
        }
        layout.findViewById<FormMenuView>(R.id.formInternalMenuView)
            ?.configMenu(holder, item, adapterEnabled)
        with(view.findViewById<MaterialTextView>(R.id.formInternalContentView)) {
            val parts = if (item.content != null) ArrayList<AbstractPartData>().apply {
                item.content!!.getDetailParts().forEach { add(it.second) }
            } else null
            val detail = item.contentFormatter?.invoke(context, parts)
            if (detail != null) visible() else gone()
            text = detail
            layout.setPaddingRelative(
                when (item.boundary.start) {
                    Boundary.NONE -> 0
                    else -> holder.style.padding.start - holder.style.padding.startMedium
                }, when (item.boundary.top) {
                    Boundary.NONE -> 0
                    else -> holder.style.padding.top - holder.style.padding.topMedium
                }, when (item.boundary.end) {
                    Boundary.NONE -> 0
                    else -> holder.style.padding.end - holder.style.padding.endMedium
                }, if (isVisible) 0 else when (item.boundary.bottom) {
                    Boundary.NONE -> 0
                    else -> holder.style.padding.bottom - holder.style.padding.bottomMedium
                }
            )
            setPaddingRelative(
                when (item.boundary.start) {
                    Boundary.NONE -> holder.style.padding.startMedium
                    else -> holder.style.padding.start
                }, 0, when (item.boundary.end) {
                    Boundary.NONE -> holder.style.padding.endMedium
                    else -> holder.style.padding.end
                }, when (item.boundary.bottom) {
                    Boundary.NONE -> holder.style.padding.bottomMedium
                    else -> holder.style.padding.bottom
                }
            )
        }
    }

    override fun onBindViewItemClick(
        adapter: FormAdapter,
        scope: CoroutineScope,
        holder: FormViewHolder,
        item: FormDetail,
        adapterEnabled: Boolean
    ) {
        val activity = holder.itemView.context.getActivity()
        val root = holder.itemView as ViewGroup
        if (activity == null) {
            root.getChildAt(0).setOnClickListener(null)
            return
        }
        root.getChildAt(0).doOnClick {
            activity.setExitSharedElementCallback(MaterialContainerTransformSharedElementCallback())
            FormDetailActivity.Controller.adapterEnabled = adapterEnabled
            FormDetailActivity.Controller.formDetail = item
            FormDetailActivity.Controller.resultBlock = {
                val part = holder.bindingAdapter as? AbstractPart<*>
                if (part != null) {
                    val position = part.indexOfShow(item)
                    if (position >= 0) {
                        part.update()
                    }
                }
            }
            val intent = Intent(activity, FormDetailActivity::class.java)
            activity.startActivity(
                intent, ActivityOptionsCompat.makeSceneTransitionAnimation(
                    activity, holder.itemView, "FormDetail"
                ).toBundle()
            )
        }
    }
}