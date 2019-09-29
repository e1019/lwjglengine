package gameEngine.math;

public class Vector4 {
    public float X;
    public float Y;
    public float Z;
    public float W;
    public Vector4(float X, float Y, float Z, float W){
        this.X = X;
        this.Y = Y;
        this.Z = Z;
        this.W = W;
    }

    public Vector4(){
        this.X = 0;
        this.Y = 0;
        this.Z = 0;
        this.W = 0;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (!(o instanceof Vector4)) {
            return false;
        }

        Vector4 c = (Vector4) o;

        return Float.compare(X, c.X) == 0
                && Float.compare(Y, c.Y) == 0
                && Float.compare(Z, c.Z) == 0
                && Float.compare(W, c.W) == 0;
    }

    @Override
    public String toString(){
        return (X + ", " + Y + ", " + Z);
    }

    public Vector4 add(Vector4 o){
        return new Vector4(X + o.X, Y + o.Y, Z + o.Z, W + o.W);
    }

    public Vector4 subtract(Vector4 o){
        return new Vector4(X - o.X, Y - o.Y, Z - o.Z, W + o.W);
    }

    public void addFrom(Vector4 o){
        X += o.X;
        Y += o.Y;
        Z += o.Z;
        W += o.W;
    }

    public void subtractFrom(Vector4 o){
        X -= o.X;
        Y -= o.Y;
        Z -= o.Z;
        W -= o.W;
    }

    public void set(float X, float Y, float Z, float W){
        this.X = X;
        this.Y = Y;
        this.Z = Z;
        this.W = W;
    }

}
