package com.chooongg.android.form.view

import android.os.Bundle
import android.view.Window
import androidx.appcompat.app.AppCompatActivity

class FormDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        val hasActionBar = window.hasFeature(Window.FEATURE_ACTION_BAR)
        super.onCreate(savedInstanceState)
        actionBar
        supportActionBar
    }
}