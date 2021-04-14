package com.android.libgdx_spine.base

import com.android.libgdx_spine.model.SpineFitMode
import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.utils.Array
import com.esotericsoftware.spine.*
import com.esotericsoftware.spine.utils.TwoColorPolygonBatch
import java.util.ArrayList

abstract class BaseSpineAdapter(
    /**
     * 图集地址
     */
    private var atlasPath: String,
    /**
     * json配置文件地址
     */
    private var jsonPath: String,
    /**
     * 控件宽
     */
    private var parentW: Int,
    /**
     * 控件高
     */
    private var parentH: Int,
    private val fitMode: SpineFitMode
) :
    ApplicationAdapter() {
    /**
     * spine系统绘制对象
     */
    var camera: OrthographicCamera? = null
    var batch: TwoColorPolygonBatch? = null
    var renderer: SkeletonRenderer? = null
    var debugRenderer: SkeletonRendererDebug? = null

    /**
     * spine 数据管理对象
     */
    private var atlas: TextureAtlas? = null
    /**
     * 骨骼管理对象
     */
     var skeleton: Skeleton? = null

    /**
     * 动画管理对象
     */
      var animationState: AnimationState? = null

    var animationsNameList: MutableList<String> = ArrayList()

    var skeletonData: SkeletonData? = null


    override fun create(){
        camera = OrthographicCamera()
        batch = TwoColorPolygonBatch()
        renderer = SkeletonRenderer()
        renderer?.premultipliedAlpha = true
        debugRenderer = SkeletonRendererDebug()

        atlas = TextureAtlas(Gdx.files.internal(atlasPath))
        val skeletonJson = SkeletonJson(atlas) // This loads skeleton JSON data, which is stateless.
//        val skeletonJson =  SkeletonBinary(atlas); // This loads skeleton JSON data, which is stateless.

        if (skeletonData == null) {
            skeletonData = skeletonJson.readSkeletonData(Gdx.files.internal(jsonPath))
        }

        val scale = fitMode.getScale(
            parentW.toFloat(),
            parentH.toFloat(),
            skeletonData?.width ?:0f,
            skeletonData?.height ?:0f
        )

        skeleton = Skeleton(skeletonData) // Skeleton holds skeleton state (bone positions, slot attachments, etc).
        skeleton?.setScale(scale, scale)
        val position = fitMode.getPosition(
            parentW.toFloat(),
            parentH.toFloat(),
            skeletonData?.width ?: 0f,
            skeletonData?.height ?: 0f
        )
        skeleton?.setPosition(position[0], position[1])

        val stateData = AnimationStateData(skeletonData) // Defines mixing (crossfading) between animations.
        animationState = AnimationState(stateData) // Holds the animation state for a skeleton (current animation, time, etc).
        animationState?.timeScale = 1f // Slow all animations down to 30% speed.

        init()
        animationTrack(animationState)


    }

    abstract fun init()

    abstract fun animationTrack(animationState: AnimationState?)


    override fun render() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
        animationState?.update(Gdx.graphics.deltaTime) // Update the animation time.

        if (animationState?.apply(skeleton) == true){        // Poses skeleton using current animations. This sets the bones' local SRT.

            skeleton?.updateWorldTransform() // Uses the bones' local SRT to compute their world SRT.

        }
        batch?.projectionMatrix?.set(camera?.combined)
        batch?.begin()

        renderer?.draw(batch, skeleton) // Draw the skeleton images.
        batch?.end()

    }

    override fun resize(width: Int, height: Int) {
        camera?.setToOrtho(false) // Update camera with new size.

    }

    override fun dispose() {
        atlas?.dispose()
    }

    fun setAnimation(animationName: String) {
        animationState?.setAnimation(0, animationName, true)
    }

    fun setSkin(skinName: String) {
        skeleton?.setSkin(skinName)
        skeleton?.setSlotsToSetupPose()
    }


    /**
     * 获取动画合集
     * @return
     */
    fun getAnimations(): List<String> {
        if (animationsNameList.isEmpty()) {
            val animations = skeleton?.data?.animations
            if (animations != null) {
                for (animation in animations){
                    animationsNameList.add(animation.name)
                }
            }
        }
        return animationsNameList
    }


    /**
     * 获取皮肤合集
     * @return
     */
    fun getSkins(): Array<Skin>? {
        return skeleton?.data?.skins
    }

    /**
     * 获取插槽合集
     * @return
     */
    fun getSlots(): Array<SlotData>? {
        return skeleton?.data?.slots
    }


}