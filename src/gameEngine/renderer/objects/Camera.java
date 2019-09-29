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

        Matrix4 tmp = new Matrix4();
        Matrix4 viewMatrix = new Matrix4();
        viewMatrix.toIdentity();

        Matrix4.RotateY(tmp, (float) Math.toRadians(getEulerRotation().Y));
        Matrix4.Mul(viewMatrix, viewMatrix, tmp);

        Matrix4.RotateX(tmp, (float) Math.toRadians(getEulerRotation().X));
        Matrix4.Mul(viewMatrix, viewMatrix, tmp);

        Matrix4.Translate(tmp, getPosition().X, getPosition().Y, getPosition().Z);
        Matrix4.Mul(viewMatrix, viewMatrix, tmp);

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
