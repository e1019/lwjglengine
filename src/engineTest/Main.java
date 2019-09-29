package engineTest;

import gameEngine.io.Debug;
import gameEngine.io.Window;
import gameEngine.math.Color3;
import gameEngine.math.Vector3;
import gameEngine.renderer.Loader;
import gameEngine.renderer.models.RawModel;
import gameEngine.renderer.Renderer;
import gameEngine.renderer.Vertex;
import gameEngine.renderer.objects.Camera;
import gameEngine.renderer.objects.MeshPart;
import gameEngine.renderer.shader.StaticShader;
import gameEngine.renderer.textures.ModelTexture;
import javafx.scene.shape.Mesh;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL46;

public class Main {
    static Window window;

    public static void loop(MeshPart part, Camera camera){
        Vector3 rotation = new Vector3(
                -(window.input.getMousePos().Y - window.getHeight()/2f)/window.getHeight() * 360,
                -(window.input.getMousePos().X - window.getWidth()/2f)/window.getWidth() * 360 ,
                0
        );

        camera.setEulerRotation(rotation);

        float speed = 0.01f;

        Vector3 velocity = new Vector3();

        if(window.input.isKeyDown(GLFW.GLFW_KEY_W)){
            velocity.Z += speed;
        }
        if(window.input.isKeyDown(GLFW.GLFW_KEY_S)){
            velocity.Z -= speed;
        }
        if(window.input.isKeyDown(GLFW.GLFW_KEY_A)){
            velocity.X += speed;
        }
        if(window.input.isKeyDown(GLFW.GLFW_KEY_D)){
            velocity.X -= speed;
        }
        if(window.input.isKeyDown(GLFW.GLFW_KEY_Q)){
            velocity.Y += speed;
        }
        if(window.input.isKeyDown(GLFW.GLFW_KEY_E)){
            velocity.Y -= speed;
        }

        camera.translateLocally(velocity);

        if(window.input.processKey(GLFW.GLFW_KEY_ESCAPE, 1)){
            window.close();
        }
    }

    public static void main(String[] argv){
        window = new Window(800, 600, "windows 11");
        window.initialize();

        Vertex[] vertices = {
                new Vertex(-0.5f, 0.5f, 0f, 0.25f, 0.25f),
                new Vertex(-0.5f, -0.5f, 0f, 0.25f, 0.75f),
                new Vertex(0.5f, -0.5f, 0f, 0.75f, 0.75f),
                new Vertex(0.5f, 0.5f, 0f, 0.75f, 0.25f)
        };

        int[] indices = {
                0, 1, 3,
                3, 1, 2
        };

        Renderer renderer = new Renderer(Color3.fromRGB(128, 0, 32));
        Loader loader = new Loader();
        StaticShader shader = new StaticShader();

        RawModel rectangle = loader.loadToVAO(vertices, indices);
        ModelTexture texture = new ModelTexture(loader.loadTexture("res/beautiful.jpg", GL46.GL_LINEAR));

        MeshPart beautifulMesh = new MeshPart(rectangle, texture);
        MeshPart beautifulMesh2 = new MeshPart(rectangle, texture);
        Camera primaryCamera = new Camera(70f);
        primaryCamera.setWindow(window);

        beautifulMesh2.setPosition(new Vector3(-1f, 0f, -1f));

        while(!window.shouldClose()){
            //beautifulMesh2.translate(new Vector3(0.002f, 0, 0));
            //beautifulMesh.rotate(new Vector3(0.2f, 1f, 0.3f));
            //beautifulMesh2.rotate(new Vector3(0f, 1f, -0.08f));
            renderer.prepare();

            shader.start();
            renderer.render(beautifulMesh, shader, primaryCamera);
            renderer.render(beautifulMesh2, shader, primaryCamera);
            shader.stop();

            window.update();
            loop(beautifulMesh, primaryCamera);
        }

        loader.free();
        shader.free();
        window.destroy();
    }
}
