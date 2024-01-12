package com.chooongg.android.form.data

import android.system.Os.remove

class FormExtensionMap {

    private val map = HashMap<String, Any>()

    operator fun set(field: String, content: Any?) {
        if (content == null) {
            remove(field)
        } else map[field] = content
    }

    operator fun get(field: String): Any? {
        return map[field]
    }

    fun has(field: String): Boolean = map.containsKey(field)

    fun forEach(block: (field: String, content: Any) -> Unit) {
        map.forEach(block)
    }

    fun clear() = map.clear()
}