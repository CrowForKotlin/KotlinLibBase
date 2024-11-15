package com.mordecai.base.ui.dialog

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Paint.FontMetrics
import android.graphics.RenderEffect
import android.graphics.Shader
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.FrameLayout
import androidx.core.graphics.withSave
import com.mordecai.base.R
import com.mordecai.base.tools.extensions.getColorStateListFromAttr
import com.mordecai.base.tools.extensions.resolveDp

class BaseLoadingFragmentFrameLayout : FrameLayout {

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    private val mPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var mText: String = "正在加载中..."
    private val mFill = context.getColorStateListFromAttr(com.google.android.material.R.attr.colorPrimaryContainer)!!.withAlpha(140)
    private val mStroke = context.getColorStateListFromAttr(com.google.android.material.R.attr.colorPrimary)!!.withAlpha(140)

    init {
        mPaint.textSize = resources.getDimension(R.dimen.base_sp12)
        mPaint.strokeWidth = context.resources.resolveDp(2f)
        mPaint.strokeJoin = Paint.Join.ROUND
        mPaint.strokeCap = Paint.Cap.ROUND
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        super.dispatchTouchEvent(ev)
        requestDisallowInterceptTouchEvent(true)
        return true
    }

    override fun dispatchDraw(canvas: Canvas) {
        super.dispatchDraw(canvas)
        canvas.withSave {
            val textWidth = mPaint.measureText(mText)
            val x = measuredWidth / 2f - textWidth / 2f
            val y = measuredHeight - getTextHeight(mPaint.fontMetrics) - paddingBottom
            mPaint.style = Paint.Style.STROKE
            mPaint.color = mStroke!!.defaultColor
            drawText(mText, x, y, mPaint)
            mPaint.style = Paint.Style.FILL
            mPaint.color = mFill!!.defaultColor
            drawText(mText, x, y, mPaint)
        }
    }
    fun getTextHeight(fontMetrics: FontMetrics) : Float {
        return fontMetrics.descent -fontMetrics.ascent
    }
}