package gameEngine.renderer.objects;

import gameEngine.renderer.models.RawModel;
import gameEngine.renderer.textures.ModelTexture;

public class MeshPart extends Object {
    private RawModel model;
    private ModelTexture texture;

    public MeshPart(RawModel model, ModelTexture texture){
        super();

        this.model = model;
        this.texture = texture;
    }

    public RawModel getModel() {
        return model;
    }

    public void setModel(RawModel model) {
        this.model = model;
    }

    public ModelTexture getTexture() {
        return texture;
    }

    public void setTexture(ModelTexture texture) {
        this.texture = texture;
    }
}
