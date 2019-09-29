package gameEngine.math;

import java.nio.FloatBuffer;

public class Matrix4 {
    Vector4 Row0;
    Vector4 Row1;
    Vector4 Row2;
    Vector4 Row3;

    public void toIdentity(){
        if(Row0 == null){
            Row0 = new Vector4();
            Row1 = new Vector4();
            Row2 = new Vector4();
            Row3 = new Vector4();
        }

        this.Row0.X = 1; this.Row0.Y = 0; this.Row0.Z = 0; this.Row0.W = 0;
        this.Row1.X = 0; this.Row1.Y = 1; this.Row1.Z = 0; this.Row1.W = 0;
        this.Row2.X = 0; this.Row2.Y = 0; this.Row2.Z = 1; this.Row2.W = 0;
        this.Row3.X = 0; this.Row3.Y = 0; this.Row3.Z = 0; this.Row3.W = 1;

    }

    public static void RotateX(Matrix4 result, float angle){
        float cosA = (float)Math.cos(angle);
        float sinA = (float)Math.sin(angle);

        result.toIdentity();

        result.Row1.Y = cosA; result.Row1.Z = sinA;
        result.Row2.Y = -sinA; result.Row2.Z = cosA;
    }

    public static void RotateY(Matrix4 result, float angle){
        float cosA = (float)Math.cos(angle);
        float sinA = (float)Math.sin(angle);

        result.toIdentity();

        result.Row0.X = cosA; result.Row0.Z = -sinA;
        result.Row2.X = sinA; result.Row2.Z = cosA;
    }

    public static void RotateZ(Matrix4 result, float angle){
        float cosA = (float)Math.cos(angle);
        float sinA = (float)Math.sin(angle);

        result.toIdentity();

        result.Row0.X = cosA; result.Row0.Y = sinA;
        result.Row1.X = -sinA; result.Row1.Y = cosA;
    }


    public static void Translate(Matrix4 result, float x, float y, float z){
        result.toIdentity();
        result.Row3.X = x; result.Row3.Y = y; result.Row3.Z = z;
    }

    public static void Scale(Matrix4 result, float x, float y, float z){
        result.toIdentity();
        result.Row0.X = x; result.Row1.Y = y; result.Row2.Z = z;
    }


    public static void Mul(Matrix4 result, Matrix4 left, Matrix4 right){
        /* Originally from http://www.edais.co.uk/blog/?p=27 */
        float
                lM11 = left.Row0.X, lM12 = left.Row0.Y, lM13 = left.Row0.Z, lM14 = left.Row0.W,
                lM21 = left.Row1.X, lM22 = left.Row1.Y, lM23 = left.Row1.Z, lM24 = left.Row1.W,
                lM31 = left.Row2.X, lM32 = left.Row2.Y, lM33 = left.Row2.Z, lM34 = left.Row2.W,
                lM41 = left.Row3.X, lM42 = left.Row3.Y, lM43 = left.Row3.Z, lM44 = left.Row3.W,

                rM11 = right.Row0.X, rM12 = right.Row0.Y, rM13 = right.Row0.Z, rM14 = right.Row0.W,
                rM21 = right.Row1.X, rM22 = right.Row1.Y, rM23 = right.Row1.Z, rM24 = right.Row1.W,
                rM31 = right.Row2.X, rM32 = right.Row2.Y, rM33 = right.Row2.Z, rM34 = right.Row2.W,
                rM41 = right.Row3.X, rM42 = right.Row3.Y, rM43 = right.Row3.Z, rM44 = right.Row3.W;

        result.Row0.X = (((lM11 * rM11) + (lM12 * rM21)) + (lM13 * rM31)) + (lM14 * rM41);
        result.Row0.Y = (((lM11 * rM12) + (lM12 * rM22)) + (lM13 * rM32)) + (lM14 * rM42);
        result.Row0.Z = (((lM11 * rM13) + (lM12 * rM23)) + (lM13 * rM33)) + (lM14 * rM43);
        result.Row0.W = (((lM11 * rM14) + (lM12 * rM24)) + (lM13 * rM34)) + (lM14 * rM44);

        result.Row1.X = (((lM21 * rM11) + (lM22 * rM21)) + (lM23 * rM31)) + (lM24 * rM41);
        result.Row1.Y = (((lM21 * rM12) + (lM22 * rM22)) + (lM23 * rM32)) + (lM24 * rM42);
        result.Row1.Z = (((lM21 * rM13) + (lM22 * rM23)) + (lM23 * rM33)) + (lM24 * rM43);
        result.Row1.W = (((lM21 * rM14) + (lM22 * rM24)) + (lM23 * rM34)) + (lM24 * rM44);

        result.Row2.X = (((lM31 * rM11) + (lM32 * rM21)) + (lM33 * rM31)) + (lM34 * rM41);
        result.Row2.Y = (((lM31 * rM12) + (lM32 * rM22)) + (lM33 * rM32)) + (lM34 * rM42);
        result.Row2.Z = (((lM31 * rM13) + (lM32 * rM23)) + (lM33 * rM33)) + (lM34 * rM43);
        result.Row2.W = (((lM31 * rM14) + (lM32 * rM24)) + (lM33 * rM34)) + (lM34 * rM44);

        result.Row3.X = (((lM41 * rM11) + (lM42 * rM21)) + (lM43 * rM31)) + (lM44 * rM41);
        result.Row3.Y = (((lM41 * rM12) + (lM42 * rM22)) + (lM43 * rM32)) + (lM44 * rM42);
        result.Row3.Z = (((lM41 * rM13) + (lM42 * rM23)) + (lM43 * rM33)) + (lM44 * rM43);
        result.Row3.W = (((lM41 * rM14) + (lM42 * rM24)) + (lM43 * rM34)) + (lM44 * rM44);

    }


    public void toBuffer(FloatBuffer buffer){
        buffer.put(Row0.X).put(Row1.X).put(Row2.X).put(Row3.X);
        buffer.put(Row0.Y).put(Row1.Y).put(Row2.Y).put(Row3.Y);
        buffer.put(Row0.Z).put(Row1.Z).put(Row2.Z).put(Row3.Z);
        buffer.put(Row0.W).put(Row1.W).put(Row2.W).put(Row3.W);
        buffer.flip();
    }

    public static Matrix4 fromPerspective(float FOV, float aspect, float min, float max){
        Matrix4 perspective = new Matrix4();

        perspective.toIdentity();

        float f = (float) (1f / Math.tan(Math.toRadians(FOV) / 2f));

        perspective.Row0.X = f / aspect;
        perspective.Row1.Y = f;
        perspective.Row2.Z = (max + min) / (min - max);
        perspective.Row3.Z = -1f;
        perspective.Row2.W = (2f * max * min) / (min - max);
        perspective.Row3.W = 0f;

        return perspective;
    }

}
