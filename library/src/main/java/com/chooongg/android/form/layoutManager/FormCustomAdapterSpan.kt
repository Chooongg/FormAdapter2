package com.chooongg.android.form.layoutManager

interface FormCustomAdapterSpanLookup {
    fun getSpanSize(position: Int): Int
    fun getSpanIndex(position: Int): Int
}