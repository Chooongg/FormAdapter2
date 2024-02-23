package com.chooongg.android.form.option

abstract class AbstractOption : IOption {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is IOption) return false

        if (getValue() == null) return false
        if (other.getValue() == null) return false
        return getValue() == other.getValue()
    }

    override fun hashCode(): Int {
        return javaClass.hashCode()
    }
}