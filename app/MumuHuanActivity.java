package com.junit.spine.junit.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;



import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.badlogic.gdx.backends.android.SpineViewCompatViewCompat;
import com.junit.spine.R;
import com.junit.spine.junit.model.SpineModelLocal;
import com.junit.spine.junit.model.SpineModelLocalTest;


public class MumuHuanActivity extends AppCompatActivity {

    SpineModelLocalTest dragon;
//    SpineModelLocal dragon;
    View dragonView;
    ViewGroup flContainer;

    int viewSizeHeight;
    int viewSizeWidth;

    SpineViewCompatViewCompat controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blank);
        flContainer = findViewById(R.id.fl_container);
        viewSizeWidth = getScreenWidth()  - 300;
        viewSizeHeight = viewSizeWidth + 300;

        AndroidApplicationConfiguration cfg = new AndroidApplicationConfiguration();
        cfg.r = cfg.g = cfg.b = cfg.a = 8;
        cfg.useTextureView = true;
        dragon = new SpineModelLocalTest("skin/tuziV2.0.atlas" ,"skin/tuziV2.0.json", viewSizeWidth, viewSizeHeight);
         controller = new SpineViewCompatViewCompat(this);
        dragonView = controller.initializeForView(dragon, cfg);
        addDragon();
    }

    public void addDragon() {
        flContainer.addView(dragonView, 0, new ViewGroup.LayoutParams(viewSizeWidth, viewSizeHeight));
    }



    public void onClick(View view) {
        if(dragon == null){
            return;
        }
        if(view.getId() == R.id.button1){
            dragon.setSkin(dragon.getNextSkinName());
        }else  if(view.getId() == R.id.button2){
            dragon.setAnimation(dragon.getNextAnimationName());
        }

    }



    public  int getScreenWidth() {
        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return  outMetrics.widthPixels;
    }



}
