package com.chooongg.android.form.app

import android.content.Context
import androidx.startup.Initializer
import com.chooongg.android.ChooonggUtilsInitializer
import com.facebook.flipper.android.AndroidFlipperClient
import com.facebook.flipper.android.utils.FlipperUtils
import com.facebook.flipper.plugins.inspector.DescriptorMapping
import com.facebook.flipper.plugins.inspector.InspectorFlipperPlugin
import com.facebook.soloader.SoLoader

@Suppress("unused")
class FlipperInitializer : Initializer<String> {
    override fun create(context: Context): String {
        SoLoader.init(context, false)
        if (FlipperUtils.shouldEnableFlipper(context)) {
            val client = AndroidFlipperClient.getInstance(context)
            client.addPlugin(InspectorFlipperPlugin(context, DescriptorMapping.withDefaults()))
            client.start()
        }
        return "Flipper is initialized"
    }

    override fun dependencies() = mutableListOf(ChooonggUtilsInitializer::class.java)
}