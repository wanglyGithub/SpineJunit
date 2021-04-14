package com.android.libgdx_spine.util

import com.android.libgdx_spine.bean.Pair
import com.badlogic.gdx.files.FileHandle
import com.badlogic.gdx.utils.JsonReader

object JsonFileParseUtil {


    @JvmStatic
    fun readSkeletonSize(file:FileHandle):Pair<Float,Float>{
        var result = Pair<Float,Float>(1f, 1f)
        val root = JsonReader().parse(file)
        val skeletonMap = root.get("skeleton")
        skeletonMap?.let {jsonValue ->
            val width = jsonValue.getFloat("width", 1f)
            val height = jsonValue.getFloat("height", 1f)
            result = Pair<Float,Float>(width,height)
        }

        return result

    }
}