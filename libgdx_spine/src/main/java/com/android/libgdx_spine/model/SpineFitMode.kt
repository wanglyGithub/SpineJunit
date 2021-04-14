package com.android.libgdx_spine.model

enum class SpineFitMode {
    /**
     * 适配高度
     */
    FIT_HEIGHT {
        override fun getScale(viewW: Float, viewH: Float, jsonW: Float, jsonH: Float): Float {
            return viewH / jsonH
        }

        override fun getPosition(
            viewW: Float,
            viewH: Float,
            jsonW: Float,
            jsonH: Float
        ): FloatArray {
            return floatArrayOf(viewW / 2, 0f)
        }
    },

    /**
     * 居中适配
     */
    FIT_INSIDE {
        override fun getScale(viewW: Float, viewH: Float, jsonW: Float, jsonH: Float): Float {
            return Math.min(viewW / jsonW, viewH / jsonH)
        }

        override fun getPosition(
            viewW: Float,
            viewH: Float,
            jsonW: Float,
            jsonH: Float
        ): FloatArray {
            return floatArrayOf(
                viewW / 2, if (viewW / jsonW > viewH / jsonH)
                    0f
                else
                    (viewH - jsonH * getScale(viewW, viewH, jsonW, jsonH)) / 2
            )
        }
    };

    /**
     * 获取缩放比
     * @param viewW view宽度
     * @param viewH view高度
     * @param jsonW 配置宽度
     * @param jsonH 配置高低
     * @return 比例
     */
    internal abstract fun getScale(viewW: Float, viewH: Float, jsonW: Float, jsonH: Float): Float

    /**
     * 获取位置
     * @param viewW view宽度
     * @param viewH view高度
     * @param jsonW 配置宽度
     * @param jsonH 配置高低
     * @return 位置
     */
    internal abstract fun getPosition(
        viewW: Float,
        viewH: Float,
        jsonW: Float,
        jsonH: Float
    ): FloatArray
}
