package com.chooongg.android.form.view

import android.graphics.Color
import android.graphics.Rect
import android.os.Bundle
import android.view.MenuItem
import android.view.MotionEvent
import android.view.Window
import android.widget.EditText
import android.widget.FrameLayout
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.app.ActivityCompat.finishAfterTransition
import androidx.core.app.ActivityCompat.setEnterSharedElementCallback
import androidx.core.app.ActivityCompat.setExitSharedElementCallback
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updateLayoutParams
import androidx.core.view.updatePadding
import com.chooongg.android.form.FormAdapter
import com.chooongg.android.form.FormManager
import com.chooongg.android.form.FormView
import com.chooongg.android.form.R
import com.chooongg.android.form.data.FormPartData
import com.chooongg.android.form.item.FormDetail
import com.chooongg.android.ktx.attrColor
import com.chooongg.android.ktx.contentView
import com.chooongg.android.ktx.gone
import com.chooongg.android.ktx.hideIME
import com.chooongg.android.ktx.resInteger
import com.chooongg.android.ktx.resString
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.transition.platform.MaterialArcMotion
import com.google.android.material.transition.platform.MaterialContainerTransform
import com.google.android.material.transition.platform.MaterialContainerTransformSharedElementCallback
import com.google.android.material.transition.platform.MaterialSharedAxis

class FormDetailActivity : AppCompatActivity() {

    internal object Controller {
        var adapterEnabled: Boolean? = null
        var formDetail: FormDetail? = null
        var resultBlock: (() -> Unit)? = null
    }

    private val formAdapter = FormAdapter(Controller.adapterEnabled ?: false)

    private var isHasActionBar = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        configWindow()
        setContentView(R.layout.activity_form_detail)
        isHasActionBar = supportActionBar != null
        with(findViewById<MaterialToolbar>(R.id.toolbar)) {
            if (isHasActionBar) gone() else setSupportActionBar(this)
        }
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                Controller.resultBlock?.invoke()
                Controller.adapterEnabled = null
                Controller.formDetail = null
                Controller.resultBlock = null
                finishAfterTransition()
            }
        })
        title = FormManager.extractText(this, Controller.formDetail?.name)
            ?: resString(R.string.detail)
        supportActionBar?.apply {
            setHomeButtonEnabled(true)
            setDisplayHomeAsUpEnabled(true)
        }
        formAdapter.columnCount =
            resInteger(Controller.formDetail?.detailColumnRes ?: R.integer.formDetailColumn)
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        findViewById<FormView>(R.id.formView).apply {
            adapter = formAdapter
            setOnClickListener {
                hideIME()
                focusedChild.clearFocus()
            }
        }
        Controller.formDetail?.content?.getDetailParts()?.forEach {
            when (it.second) {
                is FormPartData -> formAdapter.addPart(it.first, it.second as FormPartData)
            }
        }
    }

    private fun configWindow() {
        with(window) {
            requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS)
            sharedElementsUseOverlay = false
            enterTransition = MaterialSharedAxis(MaterialSharedAxis.Y, true)
            exitTransition = null
            returnTransition = MaterialSharedAxis(MaterialSharedAxis.Y, false)
            reenterTransition = null
        }
        createEdgeToEdge()
        setExitSharedElementCallback(MaterialContainerTransformSharedElementCallback())
        findViewById<FrameLayout>(android.R.id.content).transitionName = "FormDetail"
        setEnterSharedElementCallback(MaterialContainerTransformSharedElementCallback())
        window.sharedElementEnterTransition = buildContainerTransform(true)
        window.sharedElementReturnTransition = buildContainerTransform(false)
    }

    private fun createEdgeToEdge() {
        val backgroundColor = attrColor(android.R.attr.colorBackground)
        if (isHasActionBar) {
            window.statusBarColor = backgroundColor
            window.navigationBarColor = backgroundColor
            return
        }
        WindowCompat.setDecorFitsSystemWindows(window, false)
        window.statusBarColor = Color.TRANSPARENT
        window.navigationBarColor = Color.TRANSPARENT
        ViewCompat.setOnApplyWindowInsetsListener(contentView) { _, insets ->
            val inset =
                insets.getInsets(WindowInsetsCompat.Type.systemBars() or WindowInsetsCompat.Type.displayCutout())
            findViewById<CoordinatorLayout>(R.id.rootView).apply {
                clipChildren = false
                clipToPadding = false
                setPadding(inset.left, 0, inset.right, 0)
            }
            findViewById<AppBarLayout>(R.id.appBarLayout).apply {
                updatePadding(left = inset.left, right = inset.right)
                updateLayoutParams<CoordinatorLayout.LayoutParams> {
                    leftMargin = -inset.left
                    rightMargin = -inset.right
                }
            }
            insets
        }
    }

    private fun buildContainerTransform(entering: Boolean): MaterialContainerTransform {
        val transform = MaterialContainerTransform(this, entering)
        transform.addTarget(android.R.id.content)
//        transform.duration = 5000
        transform.duration
        transform.fadeMode = MaterialContainerTransform.FADE_MODE_CROSS
        transform.pathMotion = MaterialArcMotion()
        return transform
    }

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        if (ev.action == MotionEvent.ACTION_DOWN) {
            val view = currentFocus
            if (view is EditText) {
                val out = Rect()
                view.getGlobalVisibleRect(out)
                if (!out.contains(ev.rawX.toInt(), ev.y.toInt())) {
                    view.clearFocus()
                    hideIME()
                }
            }
        }
        return super.dispatchTouchEvent(ev)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressedDispatcher.onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}