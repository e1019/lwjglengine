package gameEngine.renderer.objects;

import gameEngine.io.Debug;
import gameEngine.math.CFrame;
import gameEngine.math.Matrix4;
import gameEngine.math.Vector3;
import gameEngine.math.Vector4;
import javafx.geometry.Pos;

public abstract class Object {

    private CFrame cFrame;

    private Vector3 Scale;

    private static int id = 0;

    private int oid;

    public Object(){
        Scale = new Vector3(1, 1, 1);

        cFrame = new CFrame();

        oid = id++;
    }


    public Vector3 getPosition() {
        return cFrame.getTranslation();
    }

    public void setPosition(Vector3 position) {
        cFrame = new CFrame(cFrame.getRotation(), position);
    }

    public Vector3 getEulerRotation() {
        return cFrame.toEulerAnglesXYZ();
    }

    public void setEulerRotation(Vector3 eulerRotation) {
        cFrame = new CFrame(CFrame.Anglesd(eulerRotation).getRotation(), cFrame.getTranslation());
    }

    public Vector3 getScale() {
        return Scale;
    }

    public void setScale(Vector3 scale) {
        Scale = scale;
    }

    public void scale(Vector3 scale){
        Scale = Scale.add(scale);
    }

    public Matrix4 getTransformationMatrix(){
        CFrame cf = getCFrame();
        Matrix4 m = cf.toMatrix4();

        CFrame rot = new CFrame(cf.getRotation());

        rot = rot.mul(new CFrame(Scale));

        Vector3 sc_l = Scale;//rot.getTranslation();

        m = m.multiply(Matrix4.scale(sc_l.X, sc_l.Y, sc_l.Z));


        return m;
    }




    public CFrame getCFrame(){
        return this.cFrame;
    }

    public void setCFrame(CFrame to){
        this.cFrame = to;
    }

    public void translate(Vector3 translation) {
        setPosition(getPosition().add(translation));
    }

    public void translateLocally(Vector3 translation) {
        CFrame rot = getCFrame();

        rot = rot.mul(new CFrame(translation));

        setPosition(rot.getTranslation());
    }

    public void rotate(Vector3 rotate){
        cFrame = cFrame.mul(CFrame.Anglesd(rotate));
    }
}
