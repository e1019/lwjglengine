package gameEngine.renderer.objects;

import gameEngine.io.Debug;
import gameEngine.io.Window;
import gameEngine.math.Matrix4;
import gameEngine.math.Vector3;

public class Camera extends Object {
    private float FOV;
    private float minPlane;
    private float maxPlane;

    private Window tgtWindow;

    public Camera(float FOV){
        super();

        this.FOV = FOV;
        minPlane = 0.002f;
        maxPlane = 1000f;
    }

    public void setWindow(Window window){
        this.tgtWindow = window;
    }

    public Matrix4 getPerspective(){
        return Matrix4.fromPerspective(FOV, (tgtWindow.getWidth()*1f) / (tgtWindow.getHeight()*1f), minPlane, maxPlane);
    }


    public Matrix4 getViewMatrix(){
        Matrix4 viewMatrix = new Matrix4();
        viewMatrix.rotate((float) Math.toRadians(getEulerRotation().Y), new Vector3(0, 1, 0));
        viewMatrix.rotate((float) Math.toRadians(getEulerRotation().X), new Vector3(1, 0, 0));

        viewMatrix.translate(getPosition());

        return viewMatrix;
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
