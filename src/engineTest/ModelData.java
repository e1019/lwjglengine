package engineTest;

import gameEngine.renderer.Vertex;

public class ModelData {

    public static Vertex[] getVertices(float[] vertices, float[] textureCoords){
        Vertex[] Vertices = new Vertex[vertices.length/3];
        for(int i=0; i<vertices.length/3; i++){
            Vertices[i] = new Vertex(vertices[i*3], vertices[i*3+1], vertices[i*3+2], textureCoords[i*2], textureCoords[i*2+1]);
        }

        return Vertices;
    }


}
