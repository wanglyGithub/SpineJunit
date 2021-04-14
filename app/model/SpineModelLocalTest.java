package com.junit.spine.junit.model;

import android.util.Log;

import com.android.libgdx_spine.base.BaseSpineAdapter;
import com.android.libgdx_spine.model.SpineFitMode;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.esotericsoftware.spine.AnimationState;
import com.esotericsoftware.spine.Skin;
import com.esotericsoftware.spine.Slot;
import com.esotericsoftware.spine.attachments.Attachment;
import com.esotericsoftware.spine.attachments.RegionAttachment;

import org.jetbrains.annotations.Nullable;

import java.util.concurrent.ConcurrentLinkedQueue;

public class SpineModelLocalTest extends BaseSpineAdapter {
    int animationIndex = 0;
    int skinIndex = 0;
    public SpineModelLocalTest(String atlasPath, String jsonPath, int parentW, int parentH) {
        super(atlasPath, jsonPath, parentW, parentH, SpineFitMode.FIT_HEIGHT);
    }

    ConcurrentLinkedQueue<String> animationQueue = new ConcurrentLinkedQueue<>();

    @Override
    public void init() {

        getAnimationState().setAnimation(0, getAnimations().get(0), false);
        setSkin(getSkins().get(getSkins().size - 1).getName());
        setAnimationListener();
    }

    private void setAnimationListener() {
        this.getAnimationState().addListener(new AnimationState.AnimationStateAdapter() {
            @Override
            public void start(AnimationState.TrackEntry entry) {
                Log.i("animationState:", "start:" + entry.getAnimation().getName());
            }

            @Override
            public void end(AnimationState.TrackEntry entry) {
                Log.i("animationState:", "end:" + entry.getAnimation().getName());
               

            }

            @Override
            public void complete(AnimationState.TrackEntry entry) {
                Log.i("animationState:", "complete:" + entry.getAnimation().getName());
                loadNextAnimation(entry);
            }

        });
    }

   

    @Override
    public void animationTrack(@Nullable AnimationState animationState) {

    }
}
