package gameEngine.renderer;

import gameEngine.math.Color3;
import gameEngine.math.Vector2;
import gameEngine.math.Vector3;

public class Vertex {
    public Vector3 position;
    public Vector2 uvCoordinate;

    public Vertex(float x, float y, float z, float u, float v){
        this.position = new Vector3(x, y, z);
        this.uvCoordinate = new Vector2(u, v);
    }
    public Vertex(float x, float y, float z){
        this.position = new Vector3(x, y, z);
        this.uvCoordinate = new Vector2();
    }
}
