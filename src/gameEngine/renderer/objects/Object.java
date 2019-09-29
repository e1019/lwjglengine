package gameEngine.renderer.objects;

import gameEngine.math.Matrix4;
import gameEngine.math.Vector3;

public abstract class Object {
    private Vector3 Position;
    private Vector3 EulerRotation;
    private Vector3 Scale;

    public Object(){
        Position = new Vector3();
        EulerRotation = new Vector3();
        Scale = new Vector3(1, 1, 1);
    }


    public Vector3 getPosition() {
        return Position;
    }

    public void setPosition(Vector3 position) {
        Position = position;
    }

    public Vector3 getEulerRotation() {
        return EulerRotation;
    }

    public void setEulerRotation(Vector3 eulerRotation) {
        EulerRotation = eulerRotation;
    }

    public Vector3 getScale() {
        return Scale;
    }

    public void setScale(Vector3 scale) {
        Scale = scale;
    }

    public Matrix4 getTransformationMatrix(){
        return Matrix4.createTransformation(Position, EulerRotation, Scale);
    }

    public void translate(Vector3 translation) {
        setPosition(Position.add(translation));
    }
    public void rotate(Vector3 rotate){
        setEulerRotation(EulerRotation.add(rotate));
    }
}
