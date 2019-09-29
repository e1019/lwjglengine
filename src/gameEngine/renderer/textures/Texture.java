package gameEngine.renderer.textures;

import gameEngine.io.Debug;
import org.lwjgl.opengl.GL46;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;


public class Texture {
    private int width, height;
    private int texture;

    public Texture(String path, int interpolation){
        texture = load(path, interpolation);
    }

    private int load(String path, int interpolation){
        int[] pixels;
        try {
            BufferedImage image = ImageIO.read(new FileInputStream(path));
            width = image.getWidth();
            height = image.getHeight();
            pixels = new int[width * height];
            image.getRGB(0, 0, width, height, pixels, 0, width);
        } catch (IOException e) {
            Debug.fatalError("Failed to load image " + path);
            return -1;
        }

        int[] data = new int[width * height];
        for(int i=0; i < width*height; i++){
            int a = (pixels[i] >> 24) & 0xFF;
            int r = (pixels[i] >> 16) & 0xFF;
            int g = (pixels[i] >> 8) & 0xFF;
            int b = (pixels[i]) & 0xFF;

            data[i] = a << 24 | b << 16 | g << 8 | r;
        }

        int result = GL46.glGenTextures();
        GL46.glBindTexture(GL46.GL_TEXTURE_2D, result);
        GL46.glTexParameteri(GL46.GL_TEXTURE_2D, GL46.GL_TEXTURE_MIN_FILTER, interpolation);
        GL46.glTexParameteri(GL46.GL_TEXTURE_2D, GL46.GL_TEXTURE_MAG_FILTER, interpolation);

        IntBuffer buffer = ByteBuffer.allocateDirect(data.length << 2).order(ByteOrder.nativeOrder()).asIntBuffer();
        buffer.put(data).flip();

        GL46.glTexImage2D(GL46.GL_TEXTURE_2D, 0, GL46.GL_RGBA, width, height, 0, GL46.GL_RGBA, GL46.GL_UNSIGNED_BYTE, buffer);

        return result;
    }

    public void bind(){
        GL46.glBindTexture(GL46.GL_TEXTURE_2D, texture);
    }

    public void unbind(){
        GL46.glBindTexture(GL46.GL_TEXTURE_2D, 0);
    }

    public int getTextureID(){
        return texture;
    }
}
