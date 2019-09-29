package gameEngine.renderer.textures;

public class ModelTexture {
    private Texture texture;

    public ModelTexture(Texture texture){
        this.texture = texture;
    }

    public void bind(){
        texture.bind();
    }

    public void unbind(){
        texture.unbind();
    }
}
