package com.chooongg.android.form.app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.chooongg.android.form.FormManager
import com.chooongg.android.formAdapter.app.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        FormManager.setDefault {
            centerSmoothScroll = true
        }
    }
}