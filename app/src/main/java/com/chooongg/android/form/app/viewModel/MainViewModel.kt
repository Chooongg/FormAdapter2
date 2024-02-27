package com.chooongg.android.form.app.viewModel

import androidx.lifecycle.ViewModel
import com.chooongg.android.form.FormAdapter
import com.chooongg.android.form.addButton
import com.chooongg.android.form.addDetail
import com.chooongg.android.form.addInput
import com.chooongg.android.form.addText
import com.chooongg.android.form.data.FormPartData
import com.chooongg.android.form.enum.FormEnableMode
import com.chooongg.android.formAdapter.app.R

class MainViewModel : ViewModel() {

    lateinit var formAdapter: FormAdapter

    fun initData() {
        if (!this::formAdapter.isInitialized) {
            formAdapter = FormAdapter(true)
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
        }
    }
}