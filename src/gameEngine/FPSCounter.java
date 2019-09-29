package gameEngine;

import gameEngine.io.Debug;

public class FPSCounter {
    private long time;
    private float fps;
    private int frames;

    public FPSCounter(){
        time = System.currentTimeMillis();
        frames = 0;
        fps = 0;
    }

    public float getFps(){


        frames++;
        if(System.currentTimeMillis() - time > 256){
            fps = 1f/((System.currentTimeMillis() - time)/1000f) * frames;
            time = System.currentTimeMillis();
            frames = 0;
        }


        return fps;
    }
}
