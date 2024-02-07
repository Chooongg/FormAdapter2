package com.chooongg.android.form.provider

import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.LinearLayoutCompat
import com.chooongg.android.form.FormManager
import com.chooongg.android.form.R
import com.chooongg.android.form.holder.FormViewHolder
import com.chooongg.android.form.item.BaseForm
import com.chooongg.android.form.style.AbstractStyle
import com.chooongg.android.form.view.FormMenuView
import com.chooongg.android.ktx.resDrawable
import com.google.android.material.button.MaterialButton
import kotlinx.coroutines.CoroutineScope

class NormalGroupTitleProvider : AbstractGroupTitleProvider() {
    override fun onCreateViewHolder(style: AbstractStyle, parent: ViewGroup): View =
        LinearLayoutCompat(parent.context).also {
            it.orientation = LinearLayoutCompat.HORIZONTAL
            it.addView(MaterialButton(it.context).apply {
                id = R.id.formInternalNameView
                gravity = Gravity.NO_GRAVITY
                background = null
                minWidth = 0
                minHeight = 0
                minimumWidth = 0
                minimumHeight = 0
                setTextAppearance(formTextAppearance(R.attr.formTextAppearanceGroupTitle))
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
        }

    override fun onBindViewHolder(
        scope: CoroutineScope,
        holder: FormViewHolder,
        view: View,
        item: BaseForm<*>,
        adapterEnabled: Boolean
    ) {
        view.findViewById<MaterialButton>(R.id.formInternalNameView).apply {
            iconGravity = item.iconGravity
            if (item.icon != null) {
                val drawable = resDrawable(item.icon!!)
                icon = drawable
                iconTint = item.iconTint?.invoke(context) ?: textColors
            } else icon = null
            text = FormManager.extractText(context, item.name)
        }
        view.findViewById<FormMenuView>(R.id.formInternalMenuView)
            ?.configMenu(holder, item, adapterEnabled)
    }
}