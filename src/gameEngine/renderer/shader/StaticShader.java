package gameEngine.renderer.shader;

import gameEngine.math.Matrix4;

public class StaticShader extends ShaderProgram {

    private static final String OFF = "src/gameEngine/renderer/shader/";

    private static final String VERTEX_SHADER = OFF + "vertexShader.fs";
    private static final String FRAGMENT_SHADER = OFF + "fragmentShader.fs";

    private int transformationVariable;
    private int perspectiveVariable;
    private int cameraVariable;

    public StaticShader(){
        super(VERTEX_SHADER, FRAGMENT_SHADER);

    }

    @Override
    protected void bindAttributes() {
        super.bindAttribute(0, "position");
        super.bindAttribute(1, "uvCoords");

    }

    @Override
    protected void getVarLocations() {
        transformationVariable = super.getVarLocation("transformation");
        perspectiveVariable = super.getVarLocation("perspective");
        cameraVariable = super.getVarLocation("camera");
    }

    public void loadTransformation(Matrix4 matrix){
        super.loadMatrix4(transformationVariable, matrix);
    }
    public void loadPerspective(Matrix4 perspective){
        super.loadMatrix4(perspectiveVariable, perspective);
    }
    public void loadCamera(Matrix4 camera){
        super.loadMatrix4(cameraVariable, camera);
    }
}
