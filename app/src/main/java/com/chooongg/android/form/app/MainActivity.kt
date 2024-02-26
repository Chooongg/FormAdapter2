package com.chooongg.android.form.app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.chooongg.android.form.FormAdapter
import com.chooongg.android.form.addButton
import com.chooongg.android.form.addDetail
import com.chooongg.android.form.addInput
import com.chooongg.android.form.addText
import com.chooongg.android.form.data.FormPartData
import com.chooongg.android.form.enum.FormEnableMode
import com.chooongg.android.form.helper.FormTextAppearanceHelper
import com.chooongg.android.formAdapter.app.R
import com.chooongg.android.formAdapter.app.databinding.ActivityMainBinding
import com.google.android.material.transition.platform.MaterialArcMotion
import com.google.android.material.transition.platform.MaterialContainerTransform

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
            partName = "Part"
            addButton("Button") {
                loneLine = true
                enableMode = FormEnableMode.ALWAYS
                icon = com.chooongg.android.form.R.drawable.ic_form_add
            }
            addText("Text") {
                content = "Test"
                menu = R.menu.main
            }
            addDetail("Detail") {
                content {
                    initPart {
                        addInput("Input")
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
                        addInput("Input")
                        addText("Text") {
                            content = "Test"
                            menu = R.menu.main
                        }
                    }
                }
                contentFormatter {
                    val strings = ArrayList<Any>()
                    it?.forEach {
                        when (it) {
                            is FormPartData -> {
                                val find = it.getItems().find { it.name == "Input" }
                                if (find?.content != null) {
                                    strings.add(find.content!!)
                                }
                            }
                        }
                    }
                    val string = buildString {
                        strings.forEachIndexed { index, s ->
                            if (index != 0) append('\n')
                            append("第${index + 1}个字符串：").append(s)
                        }
                    }
                    if (string.isEmpty()) "null" else string
                }
            }

            addDetail("Detail") {
                content {
                    initPart {
                        addInput("Input")
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
                        addInput("Input")
                        addText("Text") {
                            content = "Test"
                            menu = R.menu.main
                        }
                    }
                }
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