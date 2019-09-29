package gameEngine.math;

public class Color3 {
    public float R;
    public float G;
    public float B;
    public Color3(float R, float G, float B){
        this.R = R;
        this.G = G;
        this.B = B;
    }

    public static Color3 fromRGB(int R, int G, int B){
        return new Color3(R/255f, G/255f, B/255f);
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (!(o instanceof Color3)) {
            return false;
        }

        Color3 c = (Color3) o;

        return Float.compare(R, c.R) == 0
                && Float.compare(G, c.G) == 0
                && Float.compare(B, c.B) == 0;
    }

    @Override
    public String toString(){
        return (R + ", " + G + ", " + B);
    }
}
