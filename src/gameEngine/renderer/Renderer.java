package gameEngine.renderer;

import gameEngine.math.Color3;
import gameEngine.math.Matrix4;
import gameEngine.renderer.models.RawModel;
import gameEngine.renderer.objects.MeshPart;
import gameEngine.renderer.shader.StaticShader;
import gameEngine.renderer.textures.ModelTexture;
import org.lwjgl.opengl.GL46;

public class Renderer {
    Color3 bg;
    public Renderer(Color3 background){
        bg = background;
    }

    public void prepare(){
        GL46.glClear(GL46.GL_COLOR_BUFFER_BIT);
        GL46.glClearColor(bg.R, bg.G, bg.B, 1.0f);
    }

    public void render(RawModel model){

        GL46.glBindVertexArray(model.vaoID);
        GL46.glEnableVertexAttribArray(0);

        GL46.glDrawElements(GL46.GL_TRIANGLES, model.vertexCount, GL46.GL_UNSIGNED_INT, 0);

        GL46.glDisableVertexAttribArray(0);
        GL46.glBindVertexArray(0);

    }

    public void render(MeshPart mesh, StaticShader shader){

        RawModel model = mesh.getModel();
        ModelTexture texture = mesh.getTexture();

        Matrix4 transformationMatrix = mesh.getTransformationMatrix();

        GL46.glBindVertexArray(model.vaoID);
        GL46.glEnableVertexAttribArray(0);
        GL46.glEnableVertexAttribArray(1);

        shader.loadTransformation(transformationMatrix);

        GL46.glActiveTexture(GL46.GL_TEXTURE0);

        texture.bind();

        GL46.glDrawElements(GL46.GL_TRIANGLES, model.vertexCount, GL46.GL_UNSIGNED_INT, 0);

        texture.unbind();

        GL46.glDisableVertexAttribArray(0);
        GL46.glDisableVertexAttribArray(1);
        GL46.glBindVertexArray(0);

    }

}
