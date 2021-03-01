package com.assign3.game;

import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.utils.TimeUtils;

//this is what I really needed - see touchDown and touchUp
//but to construct, I needed a GestureListener, thus the "useless"/dummy MyGestureListener
public class MyGestureDetector extends GestureDetector {
    private long start;

    public MyGestureDetector(GestureListener listener) {
        super(listener);
    }

    @Override
    public boolean touchDown(float x, float y, int pointer, int button){
        System.out.println("down");
        //start = TimeUtils.nanoTime();
        return false;
    }

    @Override
    public boolean touchUp(float x, float y, int pointer, int button){
        System.out.println("up");
        TimeUtils.nanoTime();
        return false;
    }

}
