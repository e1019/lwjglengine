package gameEngine.io;

import gameEngine.math.Vector2;
import org.lwjgl.glfw.*;

class EngineKeyInput extends GLFWKeyCallback {
    Input input;
    public EngineKeyInput(Input input){
        this.input = input;
    }

    @Override
    public void invoke(long window, int key, int scancode, int action, int mods) {
        if(window != this.input.tgtWindow) return;
        if((action != GLFW.GLFW_RELEASE) && (action != GLFW.GLFW_PRESS)) return;
        this.input.setKey(key, action != GLFW.GLFW_RELEASE ? Integer.MAX_VALUE : Integer.MIN_VALUE);
    }
}

class EngineMouseButtonInput extends GLFWMouseButtonCallback {
    Input input;
    public EngineMouseButtonInput(Input input){
        this.input = input;
    }

    @Override
    public void invoke(long window, int button, int action, int mods) {
        if(window != this.input.tgtWindow) return;
        if((action != GLFW.GLFW_RELEASE) && (action != GLFW.GLFW_PRESS)) return;
        if(!input.mouseLocked){
            input.LockMouse();
            return;
        }
        this.input.setMouseButton(button, action == GLFW.GLFW_PRESS ? Integer.MAX_VALUE : Integer.MIN_VALUE);
    }
}

class EngineMousePosInput extends GLFWCursorPosCallback {
    Input input;
    public EngineMousePosInput(Input input){
        this.input = input;
    }

    @Override
    public void invoke(long window, double xpos, double ypos) {
        if(window != this.input.tgtWindow) return;
        if(input.mouseLocked){
            GLFW.glfwSetCursorPos(input.tgtWindow, 0, 0);
            this.input.mouseDelta.set((float)xpos, (float)ypos);
        }else{
            //this.input.mouseDelta.set((float)xpos - input.mousePos.X, (float)ypos - input.mousePos.Y);
            this.input.mouseDelta.set(0, 0);
        }
        this.input.mousePos.set((float)xpos, (float)ypos);

    }
}



class EngineScrollInput extends GLFWScrollCallback {
    Input input;
    public EngineScrollInput(Input input){
        this.input = input;
    }

    @Override
    public void invoke(long window, double xoffset, double yoffset) {
        if(window != this.input.tgtWindow) return;

        this.input.scrollPos.addFrom(new Vector2((float)xoffset, (float)yoffset));
    }
}

public class Input {
    private int[] keys = new int[GLFW.GLFW_KEY_LAST+1];
    private int[] mouseButtons = new int[GLFW.GLFW_MOUSE_BUTTON_LAST+1];


    private GLFWKeyCallback keyboard;
    private GLFWMouseButtonCallback mouse;
    private GLFWCursorPosCallback pos;
    private GLFWScrollCallback scroll;

    boolean mouseLocked;

    Vector2 mousePos;
    Vector2 mouseDelta;
    Vector2 scrollPos;

    long tgtWindow;

    public Input(long window){
        this.tgtWindow = window;

        keyboard = new EngineKeyInput(this);
        mouse = new EngineMouseButtonInput(this);
        pos = new EngineMousePosInput(this);
        scroll = new EngineScrollInput(this);

        mousePos = new Vector2();
        scrollPos = new Vector2();
        mouseDelta = new Vector2();
    }

    public Vector2 getMousePos(){
        return mousePos;
    }

    public GLFWKeyCallback getKeyboard() {
        return keyboard;
    }

    public GLFWMouseButtonCallback getMouse() {
        return mouse;
    }

    public GLFWCursorPosCallback getPos() {
        return pos;
    }

    public GLFWScrollCallback getScroll() {
        return scroll;
    }

    public void setMouseButton(int button, int state){
        this.mouseButtons[button] = state;
    }

    public void setKey(int key, int state){
        if(key < 0){
            Debug.warn("Key " + key + " out of range"); //TODO: just remove its unnecessary
            return;
        }
        this.keys[key] = state;
    }

    public boolean processMouseButton(int button, int priority){
        if(this.mouseButtons[button] > priority){
            this.mouseButtons[button] = priority;
            return true;
        }
        return false;
    }

    public boolean processKey(int key, int priority){
        if(this.keys[key] > priority){
            this.keys[key] = priority;
            return true;
        }
        return false;
    }

    public boolean isKeyDown(int key){
        return this.keys[key] > 0;
    }

    public boolean isMouseButtonDown(int button){
        return this.mouseButtons[button] > 0;
    }

    public void destroy(){
        keyboard.free();
        scroll.free();
        mouse.free();
        pos.free();

        keys = null;
        mouseButtons = null;
        mousePos = null;
        scrollPos = null;
    }

    public void LockMouse(){
        mouseLocked = true;
        GLFW.glfwSetInputMode(tgtWindow, GLFW.GLFW_CURSOR, GLFW.GLFW_CURSOR_DISABLED);
    }

    public void UnlockMouse(){
        mouseLocked = false;
        GLFW.glfwSetInputMode(tgtWindow, GLFW.GLFW_CURSOR, GLFW.GLFW_CURSOR_NORMAL);
    }

    public boolean IsMouseLocked(){
        return mouseLocked;
    }

    public Vector2 getMouseDelta(boolean destroy){
        Vector2 dt = new Vector2(mouseDelta.X, mouseDelta.Y);
        if(destroy){
            mouseDelta.set(0, 0);
        }
        return dt;
    }
}
