package com.chooongg.android.form.app

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import com.chooongg.android.form.FormAdapter
import com.chooongg.android.form.addDetail
import com.chooongg.android.form.addText
import com.chooongg.android.form.data.FormDetailData
import com.chooongg.android.form.helper.FormTextAppearanceHelper
import com.chooongg.android.form.view.FormDetailActivity
import com.chooongg.android.formAdapter.app.R
import com.chooongg.android.formAdapter.app.databinding.ActivityMainBinding
import com.google.android.material.transition.platform.MaterialArcMotion
import com.google.android.material.transition.platform.MaterialContainerTransform
import com.google.android.material.transition.platform.MaterialContainerTransformSharedElementCallback

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
                addDetail("Detail") {
                    content {
                        initPart {
                            addText("Text") {
                                content = "Test"
                                menu = R.menu.main
                            }
                            addText("Text") {
                                content = "Test"
                                menu = R.menu.main
                            }
                            addText("Text") {
                                content = "Test"
                                menu = R.menu.main
                            }
                        }
                        initPart {
                            addText("Text") {
                                content = "Test"
                                menu = R.menu.main
                            }
                            addText("Text") {
                                content = "Test"
                                menu = R.menu.main
                            }
                            addText("Text") {
                                content = "Test"
                                menu = R.menu.main
                            }
                        }
                    }
                }
            }
        }
        binding.formView.adapter = formAdapter
    }

    private fun buildContainerTransform(entering: Boolean): MaterialContainerTransform {
        val transform = MaterialContainerTransform(this, entering)
        transform.addTarget(android.R.id.content)
        transform.fadeMode = MaterialContainerTransform.FADE_MODE_CROSS
        transform.pathMotion = MaterialArcMotion()
        return transform
    }
}