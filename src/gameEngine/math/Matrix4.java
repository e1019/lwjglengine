package gameEngine.math;

import java.nio.FloatBuffer;

public class Matrix4 {
    public float m00, m01, m02, m03;
    public float m10, m11, m12, m13;
    public float m20, m21, m22, m23;
    public float m30, m31, m32, m33;

    public Matrix4(){
        toIdentity();
    }

    public Matrix4(Vector4 c1, Vector4 c2, Vector4 c3, Vector4 c4){
        m00 = c1.X; m10 = c1.Y; m20 = c1.Z; m30 = c1.W;
        m01 = c2.X; m11 = c2.Y; m21 = c2.Z; m31 = c2.W;
        m02 = c3.X; m12 = c3.Y; m22 = c3.Z; m32 = c3.W;
        m03 = c4.X; m13 = c4.Y; m23 = c4.Z; m33 = c4.W;
    }

    public Matrix4(
            float m00, float m01, float m02, float m03,
            float m10, float m11, float m12, float m13,
            float m20, float m21, float m22, float m23,
            float m30, float m31, float m32, float m33
    ) {
        this.m00 = m00; this.m01 = m01; this.m02 = m02; this.m03 = m03;
        this.m10 = m10; this.m11 = m11; this.m12 = m12; this.m13 = m13;
        this.m20 = m20; this.m21 = m21; this.m22 = m22; this.m23 = m23;
        this.m30 = m30; this.m31 = m31; this.m32 = m32; this.m33 = m33;
    }

    public void toIdentity(){
        m00 = 1; m01 = 0; m02 = 0; m03 = 0;
        m10 = 0; m11 = 1; m12 = 0; m13 = 0;
        m20 = 0; m21 = 0; m22 = 1; m23 = 0;
        m30 = 0; m31 = 0; m32 = 0; m33 = 1;

    }

    public Matrix4 add(Matrix4 o){
        return new Matrix4(
                this.m00 + o.m00,   this.m01 + o.m01,   this.m02 + o.m02,   this.m03 + o.m03,
                this.m10 + o.m10,   this.m11 + o.m11,   this.m12 + o.m12,   this.m13 + o.m13,
                this.m20 + o.m20,   this.m21 + o.m21,   this.m22 + o.m22,   this.m23 + o.m23,
                this.m30 + o.m30,   this.m31 + o.m31,   this.m32 + o.m32,   this.m33 + o.m33
        );
    }

    public Matrix4 negate(){
        return multiply(-1f);
    }

    public Matrix4 subtract(Matrix4 o){
        return this.add(o.negate());
    }

    public Matrix4 multiply(Matrix4 o){
        return new Matrix4(
                this.m00 * o.m00 + this.m01 * o.m10 + this.m02 * o.m20 + this.m03 * o.m30,
                this.m00 * o.m01 + this.m01 * o.m11 + this.m02 * o.m21 + this.m03 * o.m31,
                this.m00 * o.m02 + this.m01 * o.m12 + this.m02 * o.m22 + this.m03 * o.m32,
                this.m00 * o.m03 + this.m01 * o.m13 + this.m02 * o.m23 + this.m03 * o.m33,

                this.m10 * o.m00 + this.m11 * o.m10 + this.m12 * o.m20 + this.m13 * o.m30,
                this.m10 * o.m01 + this.m11 * o.m11 + this.m12 * o.m21 + this.m13 * o.m31,
                this.m10 * o.m02 + this.m11 * o.m12 + this.m12 * o.m22 + this.m13 * o.m32,
                this.m10 * o.m03 + this.m11 * o.m13 + this.m12 * o.m23 + this.m13 * o.m33,

                this.m20 * o.m00 + this.m21 * o.m10 + this.m22 * o.m20 + this.m23 * o.m30,
                this.m20 * o.m01 + this.m21 * o.m11 + this.m22 * o.m21 + this.m23 * o.m31,
                this.m20 * o.m02 + this.m21 * o.m12 + this.m22 * o.m22 + this.m23 * o.m32,
                this.m20 * o.m03 + this.m21 * o.m13 + this.m22 * o.m23 + this.m23 * o.m33,

                this.m30 * o.m00 + this.m31 * o.m10 + this.m32 * o.m20 + this.m33 * o.m30,
                this.m30 * o.m01 + this.m31 * o.m11 + this.m32 * o.m21 + this.m33 * o.m31,
                this.m30 * o.m02 + this.m31 * o.m12 + this.m32 * o.m22 + this.m33 * o.m32,
                this.m30 * o.m03 + this.m31 * o.m13 + this.m32 * o.m23 + this.m33 * o.m33
        );
    }

    public void multiplyWith(Matrix4 o){
        Matrix4 result = multiply(o);

        set(result);
    }

    public void set(Matrix4 o){
        this.m00 = o.m00; this.m01 = o.m01; this.m02 = o.m02; this.m03 = o.m03;
        this.m10 = o.m10; this.m11 = o.m11; this.m12 = o.m12; this.m13 = o.m13;
        this.m20 = o.m20; this.m21 = o.m21; this.m22 = o.m22; this.m23 = o.m23;
        this.m30 = o.m30; this.m31 = o.m31; this.m32 = o.m32; this.m33 = o.m33;
    }

    public Matrix4 multiply(float s){
        return new Matrix4(
                this.m00 * s,   this.m01 * s,   this.m02 * s,   this.m03 * s,
                this.m10 * s,   this.m11 * s,   this.m12 * s,   this.m13 * s,
                this.m20 * s,   this.m21 * s,   this.m22 * s,   this.m23 * s,
                this.m30 * s,   this.m31 * s,   this.m32 * s,   this.m33 * s
        );
    }

    public Vector4 multiply(Vector4 o){
        return new Vector4(
                this.m00 * o.X + this.m01 * o.Y + this.m02 * o.Z + this.m03 * o.W,
                this.m10 * o.X + this.m11 * o.Y + this.m12 * o.Z + this.m13 * o.W,
                this.m20 * o.X + this.m21 * o.Y + this.m22 * o.Z + this.m23 * o.W,
                this.m30 * o.X + this.m31 * o.Y + this.m32 * o.Z + this.m33 * o.W
        );
    }

    public Matrix4 transpose(){
        return new Matrix4(
                this.m00, this.m10, this.m20, this.m30,
                this.m01, this.m11, this.m21, this.m31,
                this.m02, this.m12, this.m22, this.m32,
                this.m03, this.m13, this.m23, this.m33
        );
    }

    public void toBuffer(FloatBuffer buffer){
        buffer.put(m00).put(m10).put(m20).put(m30);
        buffer.put(m01).put(m11).put(m21).put(m31);
        buffer.put(m02).put(m12).put(m22).put(m32);
        buffer.put(m03).put(m13).put(m23).put(m33);
        buffer.flip();
    }

    public static Matrix4 fromPerspective(float FOV, float aspect, float min, float max){
        Matrix4 perspective = new Matrix4();

        float f = (float) (1f / Math.tan(Math.toRadians(FOV) / 2f));

        perspective.m00 = f / aspect;
        perspective.m11 = f;
        perspective.m22 = (max + min) / (min - max);
        perspective.m32 = -1f;
        perspective.m23 = (2f * max * min) / (min - max);
        perspective.m33 = 0f;

        return perspective;
    }

    public static Matrix4 fromTranslation(float x, float y, float z){
        Matrix4 translation = new Matrix4();
        translation.m03 = x;
        translation.m13 = y;
        translation.m23 = z;
        return translation;
    }

    public static Matrix4 fromTranslation(Vector3 translation){
        return fromTranslation(translation.X, translation.Y, translation.Z);
    }

    public static Matrix4 fromRotation(float degrees, Vector3 axis){
        Matrix4 rotation = new Matrix4();

        float rad = (float)Math.toRadians(degrees);

        float c = (float)Math.cos(rad);
        float s = (float)Math.sin(rad);

        if(axis.length() != 1f){
            axis.normalize();
        }

        float x = axis.X; float y = axis.Y; float z = axis.Z;

        rotation.m00 = x * x * (1f - c) + c;
        rotation.m10 = y * x * (1f - c) + z * s;
        rotation.m20 = x * z * (1f - c) - y * s;
        rotation.m01 = x * y * (1f - c) - z * s;
        rotation.m11 = y * y * (1f - c) + c;
        rotation.m21 = y * z * (1f - c) + x * s;
        rotation.m02 = x * z * (1f - c) + y * s;
        rotation.m12 = y * z * (1f - c) - x * s;
        rotation.m22 = z * z * (1f - c) + c;

        return rotation;
    }

    public static Matrix4 fromRotation(float degrees, float x, float y, float z){
        return fromRotation(degrees, new Vector3(x, y, z));
    }

    public static Matrix4 fromScale(float x, float y, float z){
        Matrix4 scaling = new Matrix4();
        scaling.m00 = x;
        scaling.m11 = y;
        scaling.m22 = z;
        return scaling;
    }

    public static Matrix4 fromScale(Vector3 scale){
        return fromScale(scale.X, scale.Y, scale.Z);
    }

    public void translate(Vector3 translation){ //TODO: somethings fucked
        Matrix4 tmp = new Matrix4();
        tmp.m00 = 1;
        tmp.m01 = 0;
        tmp.m02 = 0;
        tmp.m03 = translation.X;
        tmp.m10 = 0;
        tmp.m11 = 1;
        tmp.m12 = 0;
        tmp.m13 = translation.Y;
        tmp.m20 = 0;
        tmp.m21 = 0;
        tmp.m22 = 1;
        tmp.m23 = translation.Z;
        tmp.m30 = 0;
        tmp.m31 = 0;
        tmp.m32 = 0;
        tmp.m33 = 1;

        multiplyWith(tmp);
    }

    public void rotate(float angle, Vector3 axis){ //TODO: somethings fucked here too
        float c = (float) Math.cos(angle);
        float s = (float) Math.sin(angle);
        float oneminusc = 1.0f - c;
        float xy = axis.X*axis.Y;
        float yz = axis.Y*axis.Z;
        float xz = axis.X*axis.Z;
        float xs = axis.X*s;
        float ys = axis.Y*s;
        float zs = axis.Z*s;

        float f00 = axis.X*axis.X*oneminusc+c;
        float f01 = xy*oneminusc+zs;
        float f02 = xz*oneminusc-ys;
        // n[3] not used
        float f10 = xy*oneminusc-zs;
        float f11 = axis.Y*axis.Y*oneminusc+c;
        float f12 = yz*oneminusc+xs;
        // n[7] not used
        float f20 = xz*oneminusc+ys;
        float f21 = yz*oneminusc-xs;
        float f22 = axis.Z*axis.Z*oneminusc+c;

        float t00 = this.m00 * f00 + this.m10 * f01 + this.m20 * f02;
        float t01 = this.m01 * f00 + this.m11 * f01 + this.m21 * f02;
        float t02 = this.m02 * f00 + this.m12 * f01 + this.m22 * f02;
        float t03 = this.m03 * f00 + this.m13 * f01 + this.m23 * f02;
        float t10 = this.m00 * f10 + this.m10 * f11 + this.m20 * f12;
        float t11 = this.m01 * f10 + this.m11 * f11 + this.m21 * f12;
        float t12 = this.m02 * f10 + this.m12 * f11 + this.m22 * f12;
        float t13 = this.m03 * f10 + this.m13 * f11 + this.m23 * f12;
        this.m20 = this.m00 * f20 + this.m10 * f21 + this.m20 * f22;
        this.m21 = this.m01 * f20 + this.m11 * f21 + this.m21 * f22;
        this.m22 = this.m02 * f20 + this.m12 * f21 + this.m22 * f22;
        this.m23 = this.m03 * f20 + this.m13 * f21 + this.m23 * f22;
        this.m00 = t00;
        this.m01 = t01;
        this.m02 = t02;
        this.m03 = t03;
        this.m10 = t10;
        this.m11 = t11;
        this.m12 = t12;
        this.m13 = t13;
    }

    public void scale(Vector3 scale){
        this.m00 *= scale.X;
        this.m01 *= scale.X;
        this.m02 *= scale.X;
        this.m03 *= scale.X;
        this.m10 *= scale.Y;
        this.m11 *= scale.Y;
        this.m12 *= scale.Y;
        this.m13 *= scale.Y;
        this.m20 *= scale.Z;
        this.m21 *= scale.Z;
        this.m22 *= scale.Z;
        this.m23 *= scale.Z;
    }

    // https://github.com/libgdx/libgdx/blob/master/gdx/src/com/badlogic/gdx/math/Matrix4.java#L168
    public static Matrix4 createTransformation(float xt, float yt, float zt, float qx, float qy, float qz, float qw, float sx, float sy, float sz){
        Matrix4 result = new Matrix4();

        float xs = qx * 2f, ys = qy * 2f, zs = qz * 2f;
        float wx = qw * xs, wy = qw * ys, wz = qw * zs;
        float xx = qx * xs, xy = qx * ys, xz = qx * zs;
        float yy = qy * ys, yz = qy * zs, zz = qz * zs;

        result.m00 = (1.0f - (yy + zz));
        result.m01 = (xy - wz);
        result.m02 = (xz + wy);
        result.m03 = xt;

        result.m10 = (xy + wz);
        result.m11 = (1.0f - (xx + zz));
        result.m12 = (yz - wx);
        result.m13 = yt;

        result.m20 = (xz - wy);
        result.m21 = (yz + wx);
        result.m22 = (1.0f - (xx + yy));
        result.m23 = zt;

        result.m30 = 0.f;
        result.m31 = 0.f;
        result.m32 = 0.f;
        result.m33 = 1.0f;

        result.scale(new Vector3(sx, sy, sz));

        return result;
    }

    public static Matrix4 createTransformation(Vector3 translation, Vector3 r, Vector3 scale){
        float yaw   = (float)Math.toRadians(r.Y);
        float pitch = (float)Math.toRadians(r.X);
        float roll  = (float)Math.toRadians(r.Z);

        float hr = roll * 0.5f;
        float shr = (float)Math.sin(hr);
        float chr = (float)Math.cos(hr);
        float hp = pitch * 0.5f;
        float shp = (float)Math.sin(hp);
        float chp = (float)Math.cos(hp);
        float hy = yaw * 0.5f;
        float shy = (float)Math.sin(hy);
        float chy = (float)Math.cos(hy);
        float chy_shp = chy * shp;
        float shy_chp = shy * chp;
        float chy_chp = chy * chp;
        float shy_shp = shy * shp;

        float x = (chy_shp * chr) + (shy_chp * shr);
        float y = (shy_chp * chr) - (chy_shp * shr);
        float z = (chy_chp * shr) - (shy_shp * chr);
        float w = (chy_chp * chr) + (shy_shp * shr);

        return createTransformation(translation.X, translation.Y, translation.Z, x, y, z, w, scale.X,
                scale.Y, scale.Z);
    }
}
