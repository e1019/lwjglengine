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

    public Vector3() {
        this.X = 0;
        this.Y = 0;
        this.Z = 0;
    }

    public Vector3(float l){
        this.X = l;
        this.Y = l;
        this.Z = l;
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

    public Vector3 div(Vector3 o){
        return new Vector3(X / o.X, Y / o.Y, Z / o.Z);
    }
    public Vector3 mul(Vector3 o){
        return new Vector3(X * o.X, Y * o.Y, Z * o.Z);
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

    public Vector3 normalize(){
        float length = length();
        return new Vector3(X/length, Y/length, Z/length);
    }

    public void clampBoth(Vector3 min_max){
        float xlim = Math.abs(min_max.X);
        float ylim = Math.abs(min_max.Y);
        float zlim = Math.abs(min_max.Z);
        X = Math.max(-xlim, X);
        X = Math.min( xlim, X);
        Y = Math.max(-ylim, Y);
        Y = Math.min( ylim, Y);
        Z = Math.max(-zlim, Z);
        Z = Math.min( zlim, Z);
    }

    public Vector3 negate(){
        return new Vector3(-X, -Y, -Z);
    }


    public void toRad(){
        this.X = (float)Math.toRadians(X);
        this.Y = (float)Math.toRadians(Y);
        this.Z = (float)Math.toRadians(Z);
    }

    public void toDeg(){
        this.X = (float)Math.toDegrees(X);
        this.Y = (float)Math.toDegrees(Y);
        this.Z = (float)Math.toDegrees(Z);
    }
}
