package com.chooongg.android.form.item

import android.content.Intent
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.app.ActivityOptionsCompat
import com.chooongg.android.form.FormAdapter
import com.chooongg.android.form.R
import com.chooongg.android.form.data.FormAdapterData
import com.chooongg.android.form.holder.FormViewHolder
import com.chooongg.android.form.style.AbstractStyle
import com.chooongg.android.form.typeset.AbstractTypeset
import com.chooongg.android.form.typeset.NoneTypeset
import com.chooongg.android.form.view.FormDetailActivity
import com.chooongg.android.ktx.getActivity
import com.google.android.material.textview.MaterialTextView
import com.google.android.material.transition.platform.MaterialContainerTransformSharedElementCallback
import kotlinx.coroutines.CoroutineScope

class FormDetail(name: Any?, field: String?) : BaseForm<FormAdapterData>(name, field) {

    override var isRespondToClickEvents: Boolean = true

    override var typeset: AbstractTypeset? = NoneTypeset()

    override fun copyEmptyItem(): BaseForm<FormAdapterData> = FormDetail(null, null)

    override fun onCreateViewHolder(style: AbstractStyle, parent: ViewGroup): View =
        LinearLayoutCompat(parent.context).also {
            it.orientation = LinearLayoutCompat.VERTICAL
            it.addView(
                style.config.groupTitleProvider.onCreateViewHolder(style, it),
                LinearLayoutCompat.LayoutParams(-1, -2)
            )
            it.addView(
                MaterialTextView(it.context).apply {
                    id = R.id.formInternalContentView
                    setTextAppearance(formTextAppearance(R.attr.formTextAppearanceContent))
                    setPaddingRelative(
                        style.padding.startMedium, style.padding.topMedium,
                        style.padding.endMedium, style.padding.bottomMedium
                    )
                }, LinearLayoutCompat.LayoutParams(-1, -2)
            )
        }

    override fun onBindViewHolder(
        scope: CoroutineScope,
        holder: FormViewHolder,
        view: View,
        adapterEnabled: Boolean
    ) {
        holder.style.config.groupTitleProvider.onBindViewHolder(
            scope,
            holder,
            view,
            this,
            adapterEnabled
        )
        with(view.findViewById<MaterialTextView>(R.id.formInternalContentView)) {
            text = "这是详情"
        }
    }

    override fun onBindViewItemClick(
        adapter: FormAdapter,
        scope: CoroutineScope,
        holder: FormViewHolder,
        adapterEnabled: Boolean
    ) {
        val activity = holder.itemView.context.getActivity()
        if (activity == null) {
            holder.itemView.setOnClickListener(null)
            return
        }
        holder.itemView.setOnClickListener {
            activity.setExitSharedElementCallback(MaterialContainerTransformSharedElementCallback())
            val intent = Intent(activity, FormDetailActivity::class.java)
            activity.startActivity(
                intent, ActivityOptionsCompat.makeSceneTransitionAnimation(
                    activity, holder.itemView, "FormDetail"
                ).toBundle()
            )
        }
    }
}