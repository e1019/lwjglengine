package gameEngine.math;

public class Vector2 {
    public float X;
    public float Y;
    public Vector2(float X, float Y){
        this.X = X;
        this.Y = Y;
    }

    public Vector2(){
        this.X = 0;
        this.Y = 0;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (!(o instanceof Vector2)) {
            return false;
        }

        Vector2 c = (Vector2) o;

        return Float.compare(X, c.X) == 0
                && Float.compare(Y, c.Y) == 0;
    }

    @Override
    public String toString(){
        return (X + ", " + Y);
    }

    public Vector2 add(Vector2 o){
        return new Vector2(X + o.X, Y + o.Y);
    }

    public Vector2 subtract(Vector2 o){
        return new Vector2(X - o.X, Y - o.Y);
    }

    public void addFrom(Vector2 o){
        X += o.X;
        Y += o.Y;
    }

    public void subtractFrom(Vector2 o){
        X -= o.X;
        Y -= o.Y;
    }

    public void set(float X, float Y){
        this.X = X;
        this.Y = Y;
    }
}
