package com.chooongg.android.form.app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.setPadding
import com.chooongg.android.form.FormAdapter
import com.chooongg.android.form.FormView
import com.chooongg.android.form.addText
import com.chooongg.android.form.helper.FormTextAppearanceHelper
import com.chooongg.android.form.layoutManager.FormLayoutManager
import com.chooongg.android.formAdapter.app.R
import com.google.android.material.button.MaterialButton

class MainActivity : AppCompatActivity(), FormTextAppearanceHelper {

    private val formAdapter = FormAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<MaterialButton>(R.id.btn).apply {
            background = null
            minWidth = 0
            minHeight = 0
            minimumWidth = 0
            minimumHeight = 0
            setPadding(0)
            setTextAppearance(formTextAppearance(com.chooongg.android.form.R.attr.formTextAppearanceName))
        }
        formAdapter.addPart {
            addText("Text") {
                content = "Test"
            }
            for (i in 0..50) {
                addText("Text") {
                    icon = com.chooongg.android.form.R.drawable.ic_form_add
                    content = "Test"
                }
            }
        }
        findViewById<FormView>(R.id.formView).apply {
            layoutManager = FormLayoutManager(context)
            adapter = formAdapter
        }
    }
}