package gameEngine.math;/*
 * The MIT License (MIT)
 *
 * Copyright © 2015-2017, Heiko Brumme
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */


import gameEngine.math.Vector3;

import java.nio.FloatBuffer;

/**
 * This class represents a 3x3-Matrix. GLSL equivalent to mat3.
 *
 * @author Heiko Brumme
 */
public class Matrix3 {

    float m00, m01, m02;
    float m10, m11, m12;
    float m20, m21, m22;

    /**
     * Creates a 3x3 identity matrix.
     */
    public Matrix3() {
        setIdentity();
    }

    /**
     * Creates a 3x3 matrix with specified columns.
     *
     * @param col1 Vector with values of the first column
     * @param col2 Vector with values of the second column
     * @param col3 Vector with values of the third column
     */
    public Matrix3(Vector3 col1, Vector3 col2, Vector3 col3) {
        m00 = col1.X;
        m10 = col1.Y;
        m20 = col1.Z;

        m01 = col2.X;
        m11 = col2.Y;
        m21 = col2.Z;

        m02 = col3.X;
        m12 = col3.Y;
        m22 = col3.Z;
    }
    public Matrix3(float M00, float M01, float M02, float M10, float M11, float M12, float M20, float M21, float M22) {
        m00 = M00;
        m10 = M10;
        m20 = M20;

        m01 = M01;
        m11 = M11;
        m21 = M21;

        m02 = M02;
        m12 = M12;
        m22 = M22;
    }

    /**
     * Sets this matrix to the identity matrix.
     */
    public final void setIdentity() {
        m00 = 1f;
        m11 = 1f;
        m22 = 1f;

        m01 = 0f;
        m02 = 0f;
        m10 = 0f;
        m12 = 0f;
        m20 = 0f;
        m21 = 0f;
    }

    /**
     * Adds this matrix to another matrix.
     *
     * @param other The other matrix
     *
     * @return Sum of this + other
     */
    public Matrix3 add(Matrix3 other) {
        Matrix3 result = new Matrix3();

        result.m00 = this.m00 + other.m00;
        result.m10 = this.m10 + other.m10;
        result.m20 = this.m20 + other.m20;

        result.m01 = this.m01 + other.m01;
        result.m11 = this.m11 + other.m11;
        result.m21 = this.m21 + other.m21;

        result.m02 = this.m02 + other.m02;
        result.m12 = this.m12 + other.m12;
        result.m22 = this.m22 + other.m22;

        return result;
    }

    /**
     * Negates this matrix.
     *
     * @return Negated matrix
     */
    public Matrix3 negate() {
        return multiply(-1f);
    }

    /**
     * Subtracts this matrix from another matrix.
     *
     * @param other The other matrix
     *
     * @return Difference of this - other
     */
    public Matrix3 subtract(Matrix3 other) {
        return this.add(other.negate());
    }

    /**
     * Multiplies this matrix with a scalar.
     *
     * @param scalar The scalar
     *
     * @return Scalar product of this * scalar
     */
    public Matrix3 multiply(float scalar) {
        Matrix3 result = new Matrix3();

        result.m00 = this.m00 * scalar;
        result.m10 = this.m10 * scalar;
        result.m20 = this.m20 * scalar;

        result.m01 = this.m01 * scalar;
        result.m11 = this.m11 * scalar;
        result.m21 = this.m21 * scalar;

        result.m02 = this.m02 * scalar;
        result.m12 = this.m12 * scalar;
        result.m22 = this.m22 * scalar;

        return result;
    }

    /**
     * Multiplies this matrix to a vector.
     *
     * @param vector The vector
     *
     * @return Vector product of this * other
     */
    public Vector3 multiply(Vector3 vector) {
        float x = this.m00 * vector.X + this.m01 * vector.Y + this.m02 * vector.Z;
        float y = this.m10 * vector.X + this.m11 * vector.Y + this.m12 * vector.Z;
        float z = this.m20 * vector.X + this.m21 * vector.Y + this.m22 * vector.Z;
        return new Vector3(x, y, z);
    }

    /**
     * Multiplies this matrix to another matrix.
     *
     * @param other The other matrix
     *
     * @return Matrix product of this * other
     */
    public Matrix3 multiply(Matrix3 other) {
        Matrix3 result = new Matrix3();

        result.m00 = this.m00 * other.m00 + this.m01 * other.m10 + this.m02 * other.m20;
        result.m10 = this.m10 * other.m00 + this.m11 * other.m10 + this.m12 * other.m20;
        result.m20 = this.m20 * other.m00 + this.m21 * other.m10 + this.m22 * other.m20;

        result.m01 = this.m00 * other.m01 + this.m01 * other.m11 + this.m02 * other.m21;
        result.m11 = this.m10 * other.m01 + this.m11 * other.m11 + this.m12 * other.m21;
        result.m21 = this.m20 * other.m01 + this.m21 * other.m11 + this.m22 * other.m21;

        result.m02 = this.m00 * other.m02 + this.m01 * other.m12 + this.m02 * other.m22;
        result.m12 = this.m10 * other.m02 + this.m11 * other.m12 + this.m12 * other.m22;
        result.m22 = this.m20 * other.m02 + this.m21 * other.m12 + this.m22 * other.m22;

        return result;
    }

    /**
     * Transposes this matrix.
     *
     * @return Transposed matrix
     */
    public Matrix3 transpose() {
        Matrix3 result = new Matrix3();

        result.m00 = this.m00;
        result.m10 = this.m01;
        result.m20 = this.m02;

        result.m01 = this.m10;
        result.m11 = this.m11;
        result.m21 = this.m12;

        result.m02 = this.m20;
        result.m12 = this.m21;
        result.m22 = this.m22;

        return result;
    }

    /**
     * Stores the matrix in a given Buffer.
     *
     * @param buffer The buffer to store the matrix data
     */
    public void toBuffer(FloatBuffer buffer) {
        buffer.put(m00).put(m10).put(m20);
        buffer.put(m01).put(m11).put(m21);
        buffer.put(m02).put(m12).put(m22);
        buffer.flip();
    }


    public static Matrix3 identity(){
        return new Matrix3();
    }

    public void orthonormalize(){
        float fInvLength = 1f / (float)Math.sqrt(m00 * m00
                + m10 * m10 +
                m20 * m20);

        m00 *= fInvLength;
        m10 *= fInvLength;
        m20 *= fInvLength;

        // compute q1
        float fDot0 =
                m00 * m01 +
                        m10 * m11 +
                        m20 * m21;

        m01 -= fDot0 * m00;
        m11 -= fDot0 * m10;
        m21 -= fDot0 * m20;

        fInvLength = 1f / (float)Math.sqrt(m01 * m01 +
                m11 * m11 +
                m21 * m21);

        m01 *= fInvLength;
        m11 *= fInvLength;
        m21 *= fInvLength;

        // compute q2
        float fDot1 =
                m01 * m02 +
                        m11 * m12 +
                        m21 * m22;

        fDot0 =
                m00 * m02 +
                        m10 * m12 +
                        m20 * m22;

        m02 -= fDot0 * m00 + fDot1 * m01;
        m12 -= fDot0 * m10 + fDot1 * m11;
        m22 -= fDot0 * m20 + fDot1 * m21;

        fInvLength = 1f / (float)Math.sqrt(m02 * m02 +
                m12 * m12 +
                m22 * m22);

        m02 *= fInvLength;
        m12 *= fInvLength;
        m22 *= fInvLength;
    }

    public static Matrix3 fromEulerAnglesXYZ(float fYAngle, float fPAngle, float fRAngle){
        float fCos, fSin;

        fCos = (float)Math.cos(fYAngle);
        fSin = (float)Math.sin(fYAngle);
        Matrix3 kXMat = new Matrix3(1.0f, 0.0f, 0.0f, 0.0f, fCos, -fSin, 0.0f, fSin, fCos);

        fCos = (float)Math.cos(fPAngle);
        fSin = (float)Math.sin(fPAngle);
        Matrix3 kYMat = new Matrix3(fCos, 0.0f, fSin, 0.0f, 1.0f, 0.0f, -fSin, 0.0f, fCos);

        fCos = (float)Math.cos(fRAngle);
        fSin = (float)Math.sin(fRAngle);
        Matrix3 kZMat = new Matrix3(fCos, -fSin, 0.0f, fSin, fCos, 0.0f, 0.0f, 0.0f, 1.0f);

        return kXMat.multiply(kYMat.multiply(kZMat));
    }
}
