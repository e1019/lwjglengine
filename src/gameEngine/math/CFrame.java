package gameEngine.math;

public class CFrame {
    Matrix3 rotation;

    Vector3 translation;

    public CFrame(){
        this.rotation = new Matrix3();
        this.translation = new Vector3();
    }

    public CFrame(Vector3 translation){
        this.rotation = Matrix3.identity();
        this.translation = translation;
    }

    public CFrame(CFrame other){
        this.rotation = other.rotation;
        this.translation = other.translation;
    }

    public CFrame(Matrix3 rotation, Vector3 translation){
        this.rotation = rotation;
        this.translation = translation;
    }

    public CFrame(Matrix3 rotation){
        this.rotation = rotation;
        this.translation = new Vector3();
    }

    public CFrame inverse(){
        CFrame out = new CFrame();
        out.rotation = rotation.transpose();
        out.translation = out.rotation.negate().multiply(translation);
        return out;
    }

    public float getHeading(){
        Vector3 look = new Vector3(rotation.m02, rotation.m12, rotation.m22);
        float angle = -(float)Math.atan2(-look.X, look.Z);

        return angle;
    }

    public Vector3 getTranslation(){
        return translation;
    }

    public Vector3 pointToWorldSpace(Vector3 v){
        return new Vector3(
                rotation.m00 * v.X + rotation.m01 * v.Y + rotation.m02 * v.Z + translation.X,
                rotation.m10 * v.X + rotation.m11 * v.Y + rotation.m12 * v.Z + translation.Y,
                rotation.m20 * v.X + rotation.m21 * v.Y + rotation.m22 * v.Z + translation.Z
        );
    }

    public CFrame mul(CFrame other){
        return new CFrame(rotation.multiply(other.rotation), pointToWorldSpace(other.translation));
    }

    public CFrame add(Vector3 other){
        return new CFrame(rotation, translation.add(other));
    }

    public CFrame subtract(Vector3 other){
        return new CFrame(rotation, translation.subtract(other));
    }


    public Vector3 lookVector(){
        return new Vector3(rotation.m01, rotation.m11, rotation.m21);
    }

    public Vector3 upVector(){
        return new Vector3(rotation.m02, rotation.m12, rotation.m22);
    }

    public Vector3 rightVector(){
        return new Vector3(rotation.m00, rotation.m10, rotation.m20);
    }

    public Matrix4 toMatrix4(){
        Matrix4 m = new Matrix4();
        m.m00 = rotation.m00; m.m01 = rotation.m01; m.m02 = rotation.m02; m.m03 = translation.X;
        m.m10 = rotation.m10; m.m11 = rotation.m11; m.m12 = rotation.m12; m.m13 = translation.Y;
        m.m20 = rotation.m20; m.m21 = rotation.m21; m.m22 = rotation.m22; m.m23 = translation.Z;
        m.m30 =            0; m.m31 =            0; m.m32 =            0; m.m33 =             1;

        return m;
    }

    public static CFrame fromMatrix4(Matrix4 m){
        CFrame c = new CFrame();
        c.rotation.m00 = m.m00; c.rotation.m01 = m.m01; c.rotation.m02 = m.m02;
        c.rotation.m10 = m.m10; c.rotation.m11 = m.m11; c.rotation.m12 = m.m12;
        c.rotation.m20 = m.m20; c.rotation.m21 = m.m21; c.rotation.m22 = m.m22;

        c.translation.X = m.m30; c.translation.Y = m.m31; c.translation.Y = m.m32;

        c.rotation.orthonormalize();

        return c;
    }

    public static CFrame Angles(Vector3 angles) {
        return new CFrame(Matrix3.fromEulerAnglesXYZ(angles.X, angles.Y, angles.Z), new Vector3());
    }

    public static CFrame Anglesd(Vector3 angles){
        return new CFrame(
                Matrix3.fromEulerAnglesXYZ(
                        (float)Math.toRadians(angles.X),
                        (float)Math.toRadians(angles.Y),
                        (float)Math.toRadians(angles.Z)
                ),
                new Vector3()
        );
    }

    public Matrix3 getRotation(){
        return this.rotation;
    }

    public Vector3 toEulerAnglesXYZ(){
        return this.rotation.toEulerAnglesXYZ();
    }
}
