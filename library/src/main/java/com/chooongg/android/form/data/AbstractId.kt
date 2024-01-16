package com.chooongg.android.form.data

import java.util.UUID

abstract class AbstractId {
    /**
     * 唯一标识
     */
    open val id = UUID.randomUUID().toString()
}