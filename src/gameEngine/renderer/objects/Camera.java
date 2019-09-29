package gameEngine.renderer.objects;

import gameEngine.math.Matrix4;

public class Camera extends Object {
    private float FOV;
    private float minPlane;
    private float maxPlane;

    public Camera(float FOV){
        super();

        this.FOV = FOV;
        minPlane = 0.5f;
        maxPlane = 1000f;
    }

    public Matrix4 getPerspective(){
        return Matrix4.fromPerspective(FOV, 1, minPlane, maxPlane);
    }

    public float getFOV() {
        return FOV;
    }

    public void setFOV(float FOV) {
        this.FOV = FOV;
    }

    public float getMinPlane() {
        return minPlane;
    }

    public void setMinPlane(float minPlane) {
        this.minPlane = minPlane;
    }

    public float getMaxPlane() {
        return maxPlane;
    }

    public void setMaxPlane(float maxPlane) {
        this.maxPlane = maxPlane;
    }
}
