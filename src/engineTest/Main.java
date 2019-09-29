package engineTest;

import gameEngine.io.Debug;
import gameEngine.io.Window;
import gameEngine.math.Color3;
import gameEngine.math.Vector2;
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
        Vector2 mouseDelta = window.input.getMouseDelta(true);

        Vector3 rotation = new Vector3(
                -mouseDelta.Y/4f,
                -mouseDelta.X/4f,
                0
        );

        //rotation.clampBoth();

        camera.rotate(rotation);

        Vector3 eRotation = camera.getEulerRotation();
        eRotation.clampBoth(new Vector3(90f, Float.MAX_VALUE-1f, Float.MAX_VALUE-1f));
        camera.setEulerRotation(eRotation);

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

        /*if(window.input.processKey(GLFW.GLFW_KEY_ESCAPE, 1)){
            window.close();
        }*/
    }

    public static void main(String[] argv){
        window = new Window(800, 600, "windows 11");
        window.initialize();

        Vertex[] cubeVertices = ModelData.getVertices(Cube.vertices, Cube.textureCoords);
        int[] cubeIndices = Cube.indices;

        Renderer renderer = new Renderer(Color3.fromRGB(125, 192, 216));
        Loader loader = new Loader();
        StaticShader shader = new StaticShader();

        RawModel cubeModel = loader.loadToVAO(cubeVertices, cubeIndices);
        ModelTexture texture = new ModelTexture(loader.loadTexture("res/beautiful.jpg", GL46.GL_LINEAR));
        ModelTexture percy = new ModelTexture(loader.loadTexture("res/percy.jpg", GL46.GL_NEAREST));

        MeshPart beautifulMesh = new MeshPart(cubeModel, texture);
        MeshPart beautifulMesh2 = new MeshPart(cubeModel, percy);
        MeshPart floor = new MeshPart(cubeModel, percy);
        Camera primaryCamera = new Camera(70f);
        primaryCamera.setWindow(window);

        beautifulMesh.setPosition(new Vector3(5f, 5f, 5f));

        beautifulMesh2.setPosition(new Vector3(-1f, 0f, -1f));
        beautifulMesh2.setEulerRotation(new Vector3(0f, 36f, 0f));
        beautifulMesh2.setScale(new Vector3(4.5f, 2.54f, 1f));

        floor.setScale(new Vector3(100f, 100f, 1f));
        floor.setEulerRotation(new Vector3(90f, 0f, 0f));
        floor.setPosition(new Vector3(0f, -10f, 0f));

        while(!window.shouldClose()){
            //beautifulMesh2.translate(new Vector3(0.002f, 0, 0));
            beautifulMesh2.rotate(new Vector3(1f, 1f, 0f));
            //beautifulMesh2.rotate(new Vector3(0f, 0.05f, 0f));
            renderer.prepare();

            shader.start();
            shader.loadPerspective(primaryCamera.getPerspective());
            shader.loadCamera(primaryCamera.getViewMatrix());
            renderer.render(beautifulMesh, shader);
            renderer.render(beautifulMesh2, shader);
            //renderer.render(floor, shader);
            shader.stop();

            window.update();
            loop(beautifulMesh, primaryCamera);
        }

        loader.free();
        shader.free();
        window.destroy();
    }
}
