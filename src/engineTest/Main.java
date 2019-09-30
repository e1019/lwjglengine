package engineTest;

import gameEngine.io.Debug;
import gameEngine.io.Window;
import gameEngine.math.CFrame;
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

final class ImprovedNoise {
    static public double noise(double x, double y, double z) {
        int X = (int)Math.floor(x) & 255,                  // FIND UNIT CUBE THAT
                Y = (int)Math.floor(y) & 255,                  // CONTAINS POINT.
                Z = (int)Math.floor(z) & 255;
        x -= Math.floor(x);                                // FIND RELATIVE X,Y,Z
        y -= Math.floor(y);                                // OF POINT IN CUBE.
        z -= Math.floor(z);
        double u = fade(x),                                // COMPUTE FADE CURVES
                v = fade(y),                                // FOR EACH OF X,Y,Z.
                w = fade(z);
        int A = p[X  ]+Y, AA = p[A]+Z, AB = p[A+1]+Z,      // HASH COORDINATES OF
                B = p[X+1]+Y, BA = p[B]+Z, BB = p[B+1]+Z;      // THE 8 CUBE CORNERS,

        return lerp(w, lerp(v, lerp(u, grad(p[AA  ], x  , y  , z   ),  // AND ADD
                grad(p[BA  ], x-1, y  , z   )), // BLENDED
                lerp(u, grad(p[AB  ], x  , y-1, z   ),  // RESULTS
                        grad(p[BB  ], x-1, y-1, z   ))),// FROM  8
                lerp(v, lerp(u, grad(p[AA+1], x  , y  , z-1 ),  // CORNERS
                        grad(p[BA+1], x-1, y  , z-1 )), // OF CUBE
                        lerp(u, grad(p[AB+1], x  , y-1, z-1 ),
                                grad(p[BB+1], x-1, y-1, z-1 ))));
    }
    static double fade(double t) { return t * t * t * (t * (t * 6 - 15) + 10); }
    static double lerp(double t, double a, double b) { return a + t * (b - a); }
    static double grad(int hash, double x, double y, double z) {
        int h = hash & 15;                      // CONVERT LO 4 BITS OF HASH CODE
        double u = h<8 ? x : y,                 // INTO 12 GRADIENT DIRECTIONS.
                v = h<4 ? y : h==12||h==14 ? x : z;
        return ((h&1) == 0 ? u : -u) + ((h&2) == 0 ? v : -v);
    }
    static final int p[] = new int[512], permutation[] = { 151,160,137,91,90,15,
            131,13,201,95,96,53,194,233,7,225,140,36,103,30,69,142,8,99,37,240,21,10,23,
            190, 6,148,247,120,234,75,0,26,197,62,94,252,219,203,117,35,11,32,57,177,33,
            88,237,149,56,87,174,20,125,136,171,168, 68,175,74,165,71,134,139,48,27,166,
            77,146,158,231,83,111,229,122,60,211,133,230,220,105,92,41,55,46,245,40,244,
            102,143,54, 65,25,63,161, 1,216,80,73,209,76,132,187,208, 89,18,169,200,196,
            135,130,116,188,159,86,164,100,109,198,173,186, 3,64,52,217,226,250,124,123,
            5,202,38,147,118,126,255,82,85,212,207,206,59,227,47,16,58,17,182,189,28,42,
            223,183,170,213,119,248,152, 2,44,154,163, 70,221,153,101,155,167, 43,172,9,
            129,22,39,253, 19,98,108,110,79,113,224,232,178,185, 112,104,218,246,97,228,
            251,34,242,193,238,210,144,12,191,179,162,241, 81,51,145,235,249,14,239,107,
            49,192,214, 31,181,199,106,157,184, 84,204,176,115,121,50,45,127, 4,150,254,
            138,236,205,93,222,114,67,29,24,72,243,141,128,195,78,66,215,61,156,180
    };
    static { for (int i=0; i < 256 ; i++) p[256+i] = p[i] = permutation[i]; }
}

public class Main {
    static Window window;

    static Vector3 camRot = new Vector3();


    public static void loop(Camera camera){
        Vector2 mouseDelta = window.input.getMouseDelta(true);

        Vector3 rotation = new Vector3(
                -mouseDelta.Y/4f,
                -mouseDelta.X/4f,
                0
        );

        camRot.addFrom(rotation);
        camRot.clampBoth(new Vector3(90f, Float.MAX_VALUE-1f, Float.MAX_VALUE-1f));

        camera.setCFrame(
                new CFrame(camera.getPosition())
                    .mul(CFrame.Anglesd(new Vector3(0, camRot.Y, 0)))
                    .mul(CFrame.Anglesd(new Vector3(camRot.X, 0, 0)))
        );



        float speed = 0.01f;

        Vector3 velocity = new Vector3();

        if(window.input.isKeyDown(GLFW.GLFW_KEY_W)){
            velocity.Z -= speed;
        }
        if(window.input.isKeyDown(GLFW.GLFW_KEY_S)){
            velocity.Z += speed;
        }
        if(window.input.isKeyDown(GLFW.GLFW_KEY_A)){
            velocity.X -= speed;
        }
        if(window.input.isKeyDown(GLFW.GLFW_KEY_D)){
            velocity.X += speed;
        }
        if(window.input.isKeyDown(GLFW.GLFW_KEY_Q)){
            velocity.Y -= speed;
        }
        if(window.input.isKeyDown(GLFW.GLFW_KEY_E)){
            velocity.Y += speed;
        }

        camera.translateLocally(velocity);

        /*if(window.input.processKey(GLFW.GLFW_KEY_ESCAPE, 1)){
            window.close();
        }*/
    }

    public static int pack(int x, int z, int size){
        return x + z*size;
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
        ModelTexture texture = new ModelTexture(loader.loadTexture("res/beautiful.jpg", GL46.GL_NEAREST));


        Camera primaryCamera = new Camera(70f);
        primaryCamera.setWindow(window);

        final int worldsize = 32;

        MeshPart[] meshes = new MeshPart[worldsize*worldsize];



        for(int x=0; x<worldsize; x++){
            for(int z=0; z<worldsize; z++){
                int idx = pack(x, z, worldsize);

                meshes[idx] = new MeshPart(cubeModel, texture);

                meshes[idx].translate(new Vector3(x, (float)ImprovedNoise.noise(x*0.5f + Math.random(), z*0.5f + Math.random(), 0) * 1f, z));


            }
        }

        while(!window.shouldClose()){
            renderer.prepare();

            shader.start();
            shader.loadPerspective(primaryCamera.getPerspective());
            shader.loadCamera(primaryCamera.getViewMatrix());

            for(int i=0; i<meshes.length; i++){
                renderer.render(meshes[i], shader);
            }

            shader.stop();

            window.update();
            loop(primaryCamera);
        }

        loader.free();
        shader.free();
        window.destroy();
    }
}
