package com.chooongg.android.form.app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.slidingpanelayout.widget.SlidingPaneLayout
import androidx.window.layout.WindowInfoTracker
import androidx.window.layout.WindowLayoutInfo
import com.chooongg.android.form.FormAdapter
import com.chooongg.android.form.addText
import com.chooongg.android.form.helper.FormTextAppearanceHelper
import com.chooongg.android.formAdapter.app.R
import com.chooongg.android.formAdapter.app.databinding.ActivityMainBinding
import com.chooongg.android.ktx.launchMain

class MainActivity : AppCompatActivity(), FormTextAppearanceHelper {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    private lateinit var windowInfoTracker: WindowInfoTracker

    private val formAdapter = FormAdapter(true)

    private val formDetailAdapter = FormAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        windowInfoTracker = WindowInfoTracker.getOrCreate(this)
        lifecycleScope.launchMain {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                windowInfoTracker.windowLayoutInfo(this@MainActivity).collect {
                    updateUI(it)
                }
            }
        }
        formAdapter.setOnItemClickListener { view, item ->

        }
        formAdapter.addPart {
            addText("Text") {
                content = "Test"
            }
            for (i in 0..50) {
                addText("Text") {
                    icon = com.chooongg.android.form.R.drawable.ic_form_add
                    content = "Test"
                    menu = R.menu.main
                }
            }
        }
        binding.formView.adapter = formAdapter
        binding.recyclerDetailView.adapter = formDetailAdapter
    }

    private fun updateUI(newLayoutInfo: WindowLayoutInfo) {
        if (newLayoutInfo.displayFeatures.isNotEmpty()) {
            binding.slidingPaneLayout.lockMode = SlidingPaneLayout.LOCK_MODE_UNLOCKED
        } else {
            binding.slidingPaneLayout.closePane()
            binding.slidingPaneLayout.lockMode = SlidingPaneLayout.LOCK_MODE_LOCKED
        }
    }
}