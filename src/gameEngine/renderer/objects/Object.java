package gameEngine.renderer.objects;

import gameEngine.io.Debug;
import gameEngine.math.CFrame;
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
        CFrame cf = getCFrame();
        Matrix4 m = cf.toMatrix4();
        Vector3 lScale = Scale.mul(cf.lookVector());


        //m = m.multiply(Matrix4.scale(lScale.X, lScale.Y, lScale.Z));

        return m;
    }


    public CFrame getCFrame(){
        return ( new CFrame(getPosition()) ).mul(CFrame.Anglesd(getEulerRotation()));
    }

    public void translate(Vector3 translation) {
        setPosition(Position.add(translation));
    }

    public void translateLocally(Vector3 translation) {
        /*
        Vector3 translatedZ = getCFrame().lookVector().mul(new Vector3(translation.Z));
        Vector3 translatedY = getCFrame().upVector().mul(new Vector3(translation.Y));
        Vector3 translatedX = getCFrame().rightVector().mul(new Vector3(translation.X));

        translate(translatedZ.add(translatedY).add(translatedX));

         */



        CFrame rot = CFrame.Anglesd(getEulerRotation());
        translate(rot.mul(new CFrame(translation)).getTranslation());
    }

    public void rotate(Vector3 rotate){
        setEulerRotation(EulerRotation.add(rotate));
    }
}
