package gameEngine.renderer.objects;

import gameEngine.math.Matrix4;
import gameEngine.math.Vector3;
import gameEngine.math.Vector4;

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

    public Matrix4 getRotationMatrix(){
        return Matrix4.createTransformation(new Vector3(), EulerRotation, Scale);
    }

    public void translate(Vector3 translation) {
        setPosition(Position.add(translation));
    }

    public void translateLocally(Vector3 translation) {
        Vector4 vec4 = new Vector4(translation.X, translation.Y, translation.Z, 1f);
        vec4 = getRotationMatrix().multiply(vec4);

        setPosition(Position.add(new Vector3(vec4.X, vec4.Y, vec4.Z)));
    }

    public void rotate(Vector3 rotate){
        setEulerRotation(EulerRotation.add(rotate));
    }
}
