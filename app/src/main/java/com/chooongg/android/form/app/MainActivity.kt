package com.chooongg.android.form.app

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.chooongg.android.form.app.viewModel.MainViewModel
import com.chooongg.android.form.helper.FormTextAppearanceHelper
import com.chooongg.android.formAdapter.app.databinding.ActivityMainBinding
import com.chooongg.android.ktx.showToast

class MainActivity : AppCompatActivity(), FormTextAppearanceHelper {

    private val model by viewModels<MainViewModel>()

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        model.initData()
        binding.formView.adapter = model.formAdapter
        model.formAdapter.setOnItemClickListener { _, item ->
            if (item.name == "Button") {
                startActivity(Intent(this, FlexBoxDemoActivity::class.java))
                return@setOnItemClickListener
            }
            showToast("点击了${item.name}")
        }
    }
}