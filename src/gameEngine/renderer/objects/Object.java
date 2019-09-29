package gameEngine.renderer.objects;

import gameEngine.io.Debug;
import gameEngine.math.Matrix4;
import gameEngine.math.Vector3;
import gameEngine.math.Vector4;
import javafx.geometry.Pos;

public abstract class Object {
    private Vector3 Position;
    private Vector3 EulerRotation;
    private Vector3 Scale_p;
    private Vector3 Scale;

    private static int id = 0;

    private int oid;

    public Object(){
        Position = new Vector3();
        EulerRotation = new Vector3();
        Scale_p = new Vector3(1, 1, 1);
        Scale = new Vector3(1, 1, 1);

        oid = id++;
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
        Matrix4 tmp = new Matrix4();
        Matrix4 m = new Matrix4();
        m.toIdentity();

        Matrix4.Scale(tmp, Scale.X, Scale.Y, Scale.Z);
        Matrix4.Mul(m, m, tmp);
        Matrix4.RotateZ(tmp, (float)Math.toRadians(-EulerRotation.Z));
        Matrix4.Mul(m, m, tmp);
        Matrix4.RotateX(tmp, (float)Math.toRadians(-EulerRotation.X));
        Matrix4.Mul(m, m, tmp);
        Matrix4.RotateY(tmp, (float)Math.toRadians(-EulerRotation.Y));
        Matrix4.Mul(m, m, tmp);
        Matrix4.Translate(tmp, Position.X, Position.Y, Position.Z);
        Matrix4.Mul(m, m, tmp);

        return m;
    }

    public Matrix4 getRotationMatrix(){
        Matrix4 tmp = new Matrix4();
        Matrix4 m = new Matrix4();
        m.toIdentity();


        Matrix4.RotateZ(tmp, (float)Math.toRadians(-EulerRotation.Z));
        Matrix4.Mul(m, m, tmp);
        Matrix4.RotateX(tmp, (float)Math.toRadians(-EulerRotation.X));
        Matrix4.Mul(m, m, tmp);
        Matrix4.RotateY(tmp, (float)Math.toRadians(-EulerRotation.Y));
        Matrix4.Mul(m, m, tmp);

        return m;
    }

    public void translate(Vector3 translation) {
        setPosition(Position.add(translation));
    }

    public void translateLocally(Vector3 translation) {
        setPosition(Position.add(translation));
    }

    public void rotate(Vector3 rotate){
        setEulerRotation(EulerRotation.add(rotate));
    }
}
