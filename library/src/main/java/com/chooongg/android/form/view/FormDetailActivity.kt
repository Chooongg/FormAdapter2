package com.chooongg.android.form.view

import android.graphics.Color
import android.os.Bundle
import android.view.Window
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import com.chooongg.android.form.FormView
import com.chooongg.android.form.R
import com.chooongg.android.ktx.attrColor
import com.chooongg.android.ktx.contentView
import com.chooongg.android.ktx.gone
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.motion.MotionUtils
import com.google.android.material.transition.platform.MaterialArcMotion
import com.google.android.material.transition.platform.MaterialContainerTransform
import com.google.android.material.transition.platform.MaterialContainerTransformSharedElementCallback
import com.google.android.material.transition.platform.MaterialSharedAxis

class FormDetailActivity : AppCompatActivity() {

    private var isHasActionBar = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        configWindow()
        setContentView(R.layout.activity_form_detail)
        isHasActionBar = supportActionBar != null
        with(findViewById<MaterialToolbar>(R.id.toolbar)) {
            if (isHasActionBar) gone() else setSupportActionBar(this)
        }
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)

    }

    private fun configWindow() {
        with(window) {
            requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS)
            sharedElementsUseOverlay = false
            enterTransition = MaterialSharedAxis(MaterialSharedAxis.Y, true)
            exitTransition = null
            returnTransition = MaterialSharedAxis(MaterialSharedAxis.Y, false)
            reenterTransition = null
            transitionBackgroundFadeDuration = MotionUtils.resolveThemeDuration(
                context, com.google.android.material.R.attr.motionDurationLong1, -1
            ).toLong()
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
            findViewById<CoordinatorLayout>(R.id.rootView)
                .setPadding(inset.left, 0, inset.right, 0)
            findViewById<FormView>(R.id.formView)
                .setPadding(inset.left, 0, inset.right, inset.bottom)
            insets
        }
    }

    private fun buildContainerTransform(entering: Boolean): MaterialContainerTransform {
        val transform = MaterialContainerTransform(this, entering)
        transform.addTarget(android.R.id.content)
        transform.duration = MotionUtils.resolveThemeDuration(
            this, com.google.android.material.R.attr.motionDurationLong1, -1
        ).toLong()
        transform.fadeMode = MaterialContainerTransform.FADE_MODE_CROSS
        transform.pathMotion = MaterialArcMotion()
        return transform
    }
}