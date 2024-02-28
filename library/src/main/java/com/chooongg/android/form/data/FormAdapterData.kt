package com.chooongg.android.form.data

import com.chooongg.android.form.FormAdapter
import com.chooongg.android.form.FormManager
import com.chooongg.android.form.part.FormPart
import com.chooongg.android.form.style.AbstractStyle

class FormAdapterData(private val adapter: FormAdapter) {

    private val parts = ArrayList<AbstractGroupData>()

    fun initPart(
        style: AbstractStyle = FormManager.defaultStyle,
        block: FormPartData.() -> Unit
    ) {
        val part = FormPart(adapter, style, FormPartData().apply(block))
//        parts.add(part)
    }

}