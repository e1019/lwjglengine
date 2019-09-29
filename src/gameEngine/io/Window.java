package gameEngine.io;

import gameEngine.FPSCounter;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.glfw.GLFWWindowSizeCallback;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL46;

public class Window {
    private int width, height;
    private String title;
    private long window;

    public Input input;

    private boolean fullscreen, wasResized;
    private FPSCounter fpsCounter;

    public Window(int width, int height, String title){
        this.width = width;
        this.height = height;
        this.title = title;
    }

    public void initialize(){
        if(!GLFW.glfwInit()){
            Debug.fatalError("GLFW initialization failure");
            return;
        }

        long primaryMonitor = GLFW.glfwGetPrimaryMonitor();

        window = GLFW.glfwCreateWindow(width, height, title, 0, 0);

        if(window == 0){
            Debug.fatalError("window creation failure");
            return;
        }

        GLFWVidMode videoMode = GLFW.glfwGetVideoMode(primaryMonitor);
        GLFW.glfwSetWindowPos(window, (videoMode.width() - width)/2, (videoMode.height() - height)/2);

        GLFW.glfwMakeContextCurrent(window);
        GLFW.glfwShowWindow(window);
        GLFW.glfwSwapInterval(1);

        fpsCounter = new FPSCounter();

        this.input = new Input(window);
        makeCallbacks();

        GL.createCapabilities();
    }

    public void makeCallbacks(){
        GLFW.glfwSetKeyCallback(window, input.getKeyboard());
        GLFW.glfwSetMouseButtonCallback(window, input.getMouse());
        GLFW.glfwSetCursorPosCallback(window, input.getPos());
        GLFW.glfwSetScrollCallback(window, input.getScroll());

        GLFW.glfwSetWindowSizeCallback(window, new GLFWWindowSizeCallback() {
            @Override
            public void invoke(long window, int w, int h) {
                width = w;
                height = h;
                wasResized = true;
            }
        });
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void update(){
        GLFW.glfwPollEvents();
        GLFW.glfwSwapBuffers(window);
        float FPS = fpsCounter.getFps();
        GLFW.glfwSetWindowTitle(window, title + " | " + "FPS: " + FPS);

        if(input.processKey(GLFW.GLFW_KEY_F11, 1)){
            fullscreen = !fullscreen;
            long monitor = GLFW.glfwGetPrimaryMonitor();
            GLFWVidMode videoMode = GLFW.glfwGetVideoMode(monitor);
            if(fullscreen){
                GLFW.glfwSetWindowMonitor(window, monitor, 0, 0, videoMode.width(), videoMode.height(), videoMode.refreshRate());
            }else{
                width = 800;
                height = 600;
                GLFW.glfwSetWindowMonitor(window, 0, (videoMode.width() - width)/2, (videoMode.height() - height)/2, width, height, 0);
            }
            wasResized = true;
        }

        if(wasResized){
            GL46.glViewport(0, 0, width, height);
            wasResized = false;
        }
    }

    public void destroy(){
        GLFW.glfwDestroyWindow(window);
        input.destroy();
    }

    public boolean shouldClose(){
        return GLFW.glfwWindowShouldClose(window);
    }

    public void close(){
        GLFW.glfwSetWindowShouldClose(window, true);
    }
}
