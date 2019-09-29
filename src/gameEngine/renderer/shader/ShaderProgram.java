package gameEngine.renderer.shader;

import gameEngine.io.Debug;
import gameEngine.io.FileRead;
import gameEngine.math.Matrix4;
import gameEngine.math.Vector3;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL46;

import java.nio.FloatBuffer;

public abstract class ShaderProgram {

    private int programID;
    private int vertexShaderID;
    private int fragmentShaderID;

    private static FloatBuffer matrix4Buff = BufferUtils.createFloatBuffer(16);

    public ShaderProgram(String vsDir, String fsDir){
        programID = GL46.glCreateProgram();

        vertexShaderID = loadShader(FileRead.readFile(vsDir), GL46.GL_VERTEX_SHADER);
        fragmentShaderID = loadShader(FileRead.readFile(fsDir), GL46.GL_FRAGMENT_SHADER);

        GL46.glAttachShader(programID, vertexShaderID);
        GL46.glAttachShader(programID, fragmentShaderID);

        bindAttributes();

        // shaders are no longer needed after being attached
        GL46.glDeleteShader(vertexShaderID);
        GL46.glDeleteShader(fragmentShaderID);

        GL46.glLinkProgram(programID);
        GL46.glValidateProgram(programID);

        getVarLocations();
    }

    public int getVarLocation(String variableName){
        return GL46.glGetUniformLocation(programID, variableName);
    }

    public void start(){
        GL46.glUseProgram(programID);
    }

    protected abstract void bindAttributes();
    protected abstract void getVarLocations();

    protected void bindAttribute(int attribute, String variableName){
        GL46.glBindAttribLocation(programID, attribute, variableName);
    }

    public void free(){
        stop();

        GL46.glDetachShader(programID, vertexShaderID);
        GL46.glDetachShader(programID, fragmentShaderID);

        GL46.glDeleteProgram(programID);
    }

    public void stop(){
        GL46.glUseProgram(0);
    }

    protected void loadFloat(int location, float val) {
        GL46.glUniform1f(location, val);
    }

    protected void loadVector3(int location, Vector3 vector){
        GL46.glUniform3f(location, vector.X, vector.Y, vector.Z);
    }

    protected void loadMatrix4(int location, Matrix4 matrix){
        matrix4Buff.clear();
        matrix.toBuffer(matrix4Buff);
        GL46.glUniformMatrix4fv(location, false, matrix4Buff);
    }

    private static int loadShader(String shader, int type){
        int shaderID = GL46.glCreateShader(type);
        GL46.glShaderSource(shaderID, shader);
        GL46.glCompileShader(shaderID);

        if(GL46.glGetShaderi(shaderID, GL46.GL_COMPILE_STATUS) == GL46.GL_FALSE){
            Debug.fatalError("Shader compilation error: " + GL46.glGetShaderInfoLog(shaderID, 500));
        }

        return shaderID;
    }
}
