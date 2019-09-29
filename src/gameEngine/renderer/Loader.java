package gameEngine.renderer;

import gameEngine.math.Vector2;
import gameEngine.math.Vector3;
import gameEngine.renderer.models.RawModel;
import gameEngine.renderer.textures.Texture;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL46;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

public class Loader {

    private List<Integer> VAOs = new ArrayList<Integer>();
    private List<Integer> VBOs = new ArrayList<Integer>();
    private List<Integer> Textures = new ArrayList<Integer>();

    public RawModel loadToVAO(Vertex[] vertices, int[] indices){
        int vaoID = createVAO();
        bindVAO(vaoID);
        storeIndicesInVAO(indices);
        storeVerticesInVAO(0, vertices);
        storeUVCoordsInVAO(1, vertices);
        unbindVAO();

        return new RawModel(vaoID, indices.length);
    }

    public Texture loadTexture(String fileName, int interpolation){
        return new Texture(fileName, interpolation);
    }

    public void free(){
        for(int vao:VAOs){
            GL46.glDeleteVertexArrays(vao);
        }
        for(int vbo:VBOs){
            GL46.glDeleteBuffers(vbo);
        }
        for(int texture:Textures){
            GL46.glDeleteTextures(texture);
        }
    }

    private int createVAO(){
        int vaoid = GL46.glGenVertexArrays();
        VAOs.add(vaoid);
        return vaoid;
    }

    private void bindVAO(int vaoID){
        GL46.glBindVertexArray(vaoID);
    }

    private void storeVerticesInVAO(int attribNum, Vertex[] vertices){
        int vboID = GL46.glGenBuffers();
        VBOs.add(vboID);

        GL46.glBindBuffer(GL46.GL_ARRAY_BUFFER, vboID); //bind vbo

        FloatBuffer posBuffer = storeVerticesPositionInFloatBuffer(vertices);
        GL46.glBufferData(GL46.GL_ARRAY_BUFFER, posBuffer, GL46.GL_STATIC_DRAW);
        GL46.glVertexAttribPointer(attribNum, 3, GL46.GL_FLOAT, false, 0, 0);

        GL46.glBindBuffer(GL46.GL_ARRAY_BUFFER, 0); //unbind vbo
    }

    private void storeUVCoordsInVAO(int attribNum, Vertex[] vertices){
        int vboID = GL46.glGenBuffers();
        VBOs.add(vboID);

        GL46.glBindBuffer(GL46.GL_ARRAY_BUFFER, vboID); //bind vbo

        FloatBuffer uvBuffer = storeVerticesUVInFloatBuffer(vertices);
        GL46.glBufferData(GL46.GL_ARRAY_BUFFER, uvBuffer, GL46.GL_STATIC_DRAW);
        GL46.glVertexAttribPointer(attribNum, 2, GL46.GL_FLOAT, false, 0, 0);

        GL46.glBindBuffer(GL46.GL_ARRAY_BUFFER, 0); //unbind vbo
    }

    private void storeIndicesInVAO(int[] indices){
        int vboID = GL46.glGenBuffers();
        VBOs.add(vboID);

        GL46.glBindBuffer(GL46.GL_ELEMENT_ARRAY_BUFFER, vboID); //bind vbo

        IntBuffer indBuffer = storeIndiciesInIntBuffer(indices);
        GL46.glBufferData(GL46.GL_ELEMENT_ARRAY_BUFFER, indBuffer, GL46.GL_STATIC_DRAW);


        //GL46.glBindBuffer(GL46.GL_ELEMENT_ARRAY_BUFFER, 0); //unbind
    }

    private void unbindVAO(){
        GL46.glBindVertexArray(0);
    }

    private IntBuffer storeIndiciesInIntBuffer(int[] indices){
        IntBuffer buffer = BufferUtils.createIntBuffer(indices.length);

        buffer.put(indices);
        buffer.flip();

        return buffer;
    }

    private FloatBuffer storeVerticesPositionInFloatBuffer(Vertex[] vertices){
        FloatBuffer buffer = BufferUtils.createFloatBuffer(vertices.length * 3);
        Vector3 pos;
        for(int i=0; i<vertices.length; i++){
            pos = vertices[i].position;
            buffer.put(pos.X);
            buffer.put(pos.Y);
            buffer.put(pos.Z);
        }
        buffer.flip();

        return buffer;
    }

    private FloatBuffer storeVerticesUVInFloatBuffer(Vertex[] vertices){
        FloatBuffer buffer = BufferUtils.createFloatBuffer(vertices.length * 2);
        Vector2 pos;
        for(int i=0; i<vertices.length; i++){
            pos = vertices[i].uvCoordinate;
            buffer.put(pos.X);
            buffer.put(pos.Y);
        }
        buffer.flip();

        return buffer;
    }
}
