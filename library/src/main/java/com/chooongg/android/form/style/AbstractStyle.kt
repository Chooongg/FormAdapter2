package com.chooongg.android.form.style

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.core.view.updateLayoutParams
import com.chooongg.android.form.R
import com.chooongg.android.form.boundary.Boundary
import com.chooongg.android.form.boundary.FormSizeInfo
import com.chooongg.android.form.config.EmptyConfig
import com.chooongg.android.form.config.FormConfig
import com.chooongg.android.form.holder.FormViewHolder
import com.chooongg.android.form.item.BaseForm
import com.chooongg.android.ktx.attrResourcesId
import com.chooongg.android.ktx.isLayoutRtl
import com.chooongg.android.ktx.resDimensionPixelSize
import com.google.android.material.shape.AbsoluteCornerSize
import com.google.android.material.shape.ShapeAppearanceModel

/**
 * 样式
 */
abstract class AbstractStyle(val config: FormConfig = EmptyConfig()) {

    /**
     * 外边距信息
     */
    var margin: FormSizeInfo = FormSizeInfo()

    /**
     * 内边距信息
     */
    var padding: FormSizeInfo = FormSizeInfo()

    /**
     * 是否为独立的项目
     */
    var isIndependentItem = false

    /**
     * 是否装饰空项目
     */
    open fun isDecorateNoneItem(): Boolean = true

    private var isInstanceSizeInfo: Boolean = false

    protected lateinit var shapeAppearanceModel: ShapeAppearanceModel

    internal fun createSizeInfo(context: Context) {
        if (isInstanceSizeInfo) return
        margin = onCreateMarginSize(context)
        padding = onCreatePaddingSize(context)
        isInstanceSizeInfo = true
    }

    protected fun onCreateMarginSize(context: Context): FormSizeInfo = FormSizeInfo(
        context.resDimensionPixelSize(R.dimen.formMarginStart),
        context.resDimensionPixelSize(R.dimen.formMarginTop),
        context.resDimensionPixelSize(R.dimen.formMarginEnd),
        context.resDimensionPixelSize(R.dimen.formMarginBottom),
        context.resDimensionPixelSize(R.dimen.formMarginStartMedium),
        context.resDimensionPixelSize(R.dimen.formMarginTopMedium),
        context.resDimensionPixelSize(R.dimen.formMarginEndMedium),
        context.resDimensionPixelSize(R.dimen.formMarginBottomMedium),
    )

    protected fun onCreatePaddingSize(context: Context): FormSizeInfo = FormSizeInfo(
        context.resDimensionPixelSize(R.dimen.formPaddingStart),
        context.resDimensionPixelSize(R.dimen.formPaddingTop),
        context.resDimensionPixelSize(R.dimen.formPaddingEnd),
        context.resDimensionPixelSize(R.dimen.formPaddingBottom),
        context.resDimensionPixelSize(R.dimen.formPaddingStartMedium),
        context.resDimensionPixelSize(R.dimen.formPaddingTopMedium),
        context.resDimensionPixelSize(R.dimen.formPaddingEndMedium),
        context.resDimensionPixelSize(R.dimen.formPaddingBottomMedium),
    )

    abstract fun onCreateViewHolder(parent: ViewGroup): ViewGroup?

    protected abstract fun addView(parent: ViewGroup, child: View)

    open fun onViewAttachedToWindow(holder: FormViewHolder) = Unit

    open fun initializeShapeAppearanceModel(holder: FormViewHolder) {
        if (!this::shapeAppearanceModel.isInitialized) {
            val resId = if (config.shapeAppearanceResId == null) {
                holder.itemView.attrResourcesId(
                    R.attr.formShapeAppearanceCorner, holder.itemView.attrResourcesId(
                        com.google.android.material.R.attr.shapeAppearanceCornerMedium,
                        0
                    )
                )
            } else config.shapeAppearanceResId!!
            shapeAppearanceModel =
                ShapeAppearanceModel.builder(holder.itemView.context, resId, 0).build()
        }
    }

    open fun onBindViewHolderBefore(
        holder: FormViewHolder,
        item: BaseForm<*>,
        adapterEnabled: Boolean
    ) {
        var _marginStart = 0
        var _marginTop = 0
        var _marginEnd = 0
        var _marginBottom = 0
        var _paddingStart = 0
        var _paddingTop = 0
        var _paddingEnd = 0
        var _paddingBottom = 0
        when (item.boundary.start) {
            Boundary.GLOBAL -> {
                _marginStart = 0
                _paddingStart = padding.start - padding.startMedium
            }

            Boundary.MIDDLE -> {
                _marginStart = margin.startMedium
                _paddingStart = padding.start - padding.startMedium
            }

            Boundary.NONE -> {
                _marginStart = 0
                _marginStart = 0
            }
        }
        when (item.boundary.end) {
            Boundary.GLOBAL -> {
                _marginEnd = 0
                _paddingEnd = padding.end - padding.endMedium
            }

            Boundary.MIDDLE -> {
                _marginEnd = margin.startMedium
                _paddingEnd = padding.end - padding.endMedium
            }

            Boundary.NONE -> {
                _marginEnd = 0
                _paddingEnd = 0
            }
        }
        when (item.boundary.top) {
            Boundary.GLOBAL -> {
                _marginTop = margin.top
                _paddingTop = padding.top - padding.topMedium
            }

            Boundary.MIDDLE -> {
                _marginTop = margin.topMedium
                _paddingTop = padding.top - padding.topMedium
            }

            Boundary.NONE -> {
                _marginTop = 0
                _paddingTop = 0
            }
        }
        when (item.boundary.bottom) {
            Boundary.GLOBAL -> {
                _marginBottom = margin.bottom
                _paddingBottom = padding.bottom - padding.bottomMedium
            }

            Boundary.MIDDLE -> {
                _marginBottom = margin.bottomMedium
                _paddingBottom = padding.bottom - padding.bottomMedium
            }

            Boundary.NONE -> {
                _marginBottom = 0
                _paddingBottom = 0
            }
        }
        holder.itemView.setPaddingRelative(_paddingStart, _paddingTop, _paddingEnd, _paddingBottom)
        holder.itemView.updateLayoutParams<ViewGroup.MarginLayoutParams> {
            if (holder.itemView.isLayoutRtl) {
                setMargins(_marginEnd, _marginTop, _marginStart, _marginBottom)
            } else {
                setMargins(_marginStart, _marginTop, _marginEnd, _marginBottom)
            }
        }
    }

    abstract fun onBindViewHolder(
        holder: FormViewHolder,
        item: BaseForm<*>,
        layout: ViewGroup?,
        adapterEnabled: Boolean
    )

    open fun onBindViewHolderAfter(
        holder: FormViewHolder,
        item: BaseForm<*>,
        adapterEnabled: Boolean
    ) = Unit

    open fun onViewDetachedFromWindow(holder: FormViewHolder) = Unit

    open fun onViewRecycled(holder: FormViewHolder) = Unit

    fun executeAddView(parent: ViewGroup, child: View) = addView(parent, child)

    protected fun getShapeAppearanceModel(view: View, item: BaseForm<*>) =
        ShapeAppearanceModel.builder().apply {
            if (view.layoutDirection != View.LAYOUT_DIRECTION_RTL) {
                setTopLeftCornerSize(
                    if (item.boundary.top != 0 && item.boundary.start != 0) {
                        shapeAppearanceModel.topLeftCornerSize
                    } else AbsoluteCornerSize(0f)
                )
                setTopRightCornerSize(
                    if (item.boundary.top != 0 && item.boundary.end != 0) {
                        shapeAppearanceModel.topRightCornerSize
                    } else AbsoluteCornerSize(0f)
                )
                setBottomLeftCornerSize(
                    if (item.boundary.bottom != 0 && item.boundary.start != 0) {
                        shapeAppearanceModel.bottomLeftCornerSize
                    } else AbsoluteCornerSize(0f)
                )
                setBottomRightCornerSize(
                    if (item.boundary.bottom != 0 && item.boundary.end != 0) {
                        shapeAppearanceModel.bottomRightCornerSize
                    } else AbsoluteCornerSize(0f)
                )
            } else {
                setTopLeftCornerSize(
                    if (item.boundary.top != 0 && item.boundary.start != 0) {
                        shapeAppearanceModel.topRightCornerSize
                    } else AbsoluteCornerSize(0f)
                )
                setTopRightCornerSize(
                    if (item.boundary.top != 0 && item.boundary.end != 0) {
                        shapeAppearanceModel.topLeftCornerSize
                    } else AbsoluteCornerSize(0f)
                )
                setBottomLeftCornerSize(
                    if (item.boundary.bottom != 0 && item.boundary.start != 0) {
                        shapeAppearanceModel.bottomRightCornerSize
                    } else AbsoluteCornerSize(0f)
                )
                setBottomRightCornerSize(
                    if (item.boundary.bottom != 0 && item.boundary.end != 0) {
                        shapeAppearanceModel.bottomLeftCornerSize
                    } else AbsoluteCornerSize(0f)
                )
            }
        }.build()

    override fun equals(other: Any?): Boolean {
        if (other !is AbstractStyle) return false
        if (other.javaClass != javaClass) return false
        return other.config == config
    }

    override fun hashCode(): Int = javaClass.hashCode()
}