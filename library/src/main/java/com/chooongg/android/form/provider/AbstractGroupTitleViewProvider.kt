package com.chooongg.android.form.provider

abstract class AbstractGroupTitleViewProvider {

    override fun equals(other: Any?): Boolean {
        return javaClass == other?.javaClass
    }

    override fun hashCode(): Int = javaClass.hashCode()
}