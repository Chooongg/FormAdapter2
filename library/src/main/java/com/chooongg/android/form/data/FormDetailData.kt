package com.chooongg.android.form.data

import com.chooongg.android.form.FormManager
import com.chooongg.android.form.style.AbstractStyle

class FormDetailData {

    private val parts = ArrayList<Pair<AbstractStyle, AbstractGroupData>>()

    fun initPart(
        style: AbstractStyle = FormManager.defaultStyle,
        block: FormPartData.() -> Unit
    ) {
        parts.add(Pair(style, FormPartData().apply(block)))
    }

    fun getDetailParts() = parts

    fun clear() {
        parts.clear()
    }
}