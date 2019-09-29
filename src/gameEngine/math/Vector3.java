package gameEngine.math;

public class Vector3 {
    public float X;
    public float Y;
    public float Z;
    public Vector3(float X, float Y, float Z){
        this.X = X;
        this.Y = Y;
        this.Z = Z;
    }

    public Vector3(){
        this.X = 0;
        this.Y = 0;
        this.Z = 0;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (!(o instanceof Vector3)) {
            return false;
        }

        Vector3 c = (Vector3) o;

        return Float.compare(X, c.X) == 0
                && Float.compare(Y, c.Y) == 0
                && Float.compare(Z, c.Z) == 0;
    }

    @Override
    public String toString(){
        return (X + ", " + Y + ", " + Z);
    }

    public Vector3 add(Vector3 o){
        return new Vector3(X + o.X, Y + o.Y, Z + o.Z);
    }

    public Vector3 subtract(Vector3 o){
        return new Vector3(X - o.X, Y - o.Y, Z - o.Z);
    }

    public void addFrom(Vector3 o){
        X += o.X;
        Y += o.Y;
        Z += o.Z;
    }

    public void subtractFrom(Vector3 o){
        X -= o.X;
        Y -= o.Y;
        Z -= o.Z;
    }

    public void set(float X, float Y, float Z){
        this.X = X;
        this.Y = Y;
        this.Z = Z;
    }

    public float length(){
        return (float)Math.sqrt(X * X + Y * Y + Z * Z);
    }

    public void normalize(){
        float length = length();
        this.X /= length;
        this.Y /= length;
        this.Z /= length;
    }
}
