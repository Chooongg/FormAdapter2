package com.chooongg.android.form.app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.chooongg.android.form.FormAdapter
import com.chooongg.android.form.addText
import com.chooongg.android.form.helper.FormTextAppearanceHelper
import com.chooongg.android.formAdapter.app.R
import com.chooongg.android.formAdapter.app.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), FormTextAppearanceHelper {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    private val formAdapter = FormAdapter(true)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        formAdapter.setOnItemClickListener { view, item ->

        }
        formAdapter.addPart {
            for (i in 0..10) {
                addText("Text") {
                    content = "Test"
                    menu = R.menu.main
                }
            }
        }
        binding.formView.adapter = formAdapter
    }
}