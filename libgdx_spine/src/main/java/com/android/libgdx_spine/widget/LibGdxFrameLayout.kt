package com.android.libgdx_spine.widget

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.FrameLayout

class LibGdxFrameLayout(context: Context, attrs: AttributeSet?) : FrameLayout(context, attrs) {

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        return false
    }
}