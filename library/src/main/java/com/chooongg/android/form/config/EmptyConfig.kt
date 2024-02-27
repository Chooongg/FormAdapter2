package com.chooongg.android.form.config

import com.chooongg.android.form.enum.FormContentGravity
import com.chooongg.android.form.enum.FormEmsSize
import com.chooongg.android.form.formatter.name.AbstractNameFormatter
import com.chooongg.android.form.provider.detail.AbstractDetailProvider
import com.chooongg.android.form.provider.groupTitle.AbstractGroupTitleProvider
import com.chooongg.android.form.typeset.AbstractTypeset

class EmptyConfig : FormConfig() {
    override val _nameFormatter: AbstractNameFormatter? = null
    override val _groupTitleProvider: AbstractGroupTitleProvider? = null
    override val _detailTitleProvider: AbstractDetailProvider? = null
    override val _emsSize: FormEmsSize? = null
    override val _contentGravity: FormContentGravity? = null
    override val _typeset: AbstractTypeset? = null
}