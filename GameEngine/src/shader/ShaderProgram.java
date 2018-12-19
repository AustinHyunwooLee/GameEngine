package shader;
 
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.FloatBuffer;
 
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;
 
public abstract class ShaderProgram {
     
    private int programID;
    private int vertexShaderID;
    private int fragmentShaderID;
     
    private static FloatBuffer matrixBuffer = BufferUtils.createFloatBuffer(16);
     
    /**
     * Loads the vertex and fragment file code in GSLS so that it can be used.
     * Attaches the vertex and fragment shader to a program ID
     * @param vertexFile
     * @param fragmentFile
     */
    public ShaderProgram(String vertexFile,String fragmentFile){
        vertexShaderID = loadShader(vertexFile,GL20.GL_VERTEX_SHADER);
        fragmentShaderID = loadShader(fragmentFile,GL20.GL_FRAGMENT_SHADER);
        programID = GL20.glCreateProgram();
        GL20.glAttachShader(programID, vertexShaderID);
        GL20.glAttachShader(programID, fragmentShaderID);
        bindAttributes();
        GL20.glLinkProgram(programID);
        GL20.glValidateProgram(programID);
        getAllUniformLocations();
    }
     
    protected abstract void getAllUniformLocations();
     
    /**
     * returns the uniform location id of a variable
     * @param uniformName
     * @return
     */
    protected int getUniformLocation(String uniformName){
        return GL20.glGetUniformLocation(programID,uniformName);
    }
     
    /**
     * in order to use the GPU rendering, a program ID must be supplied.
     * start anytime something needs to be rendered.
     */
    public void start(){
        GL20.glUseProgram(programID);
    }
     
    /**
     * after rendering is finished for a model, stop the program
     */
    public void stop(){
        GL20.glUseProgram(0);
    }
     
    /** 
     * when the window is closed, delete the vertex, fragment and program ID
     */
    public void cleanUp(){
        stop();
        GL20.glDetachShader(programID, vertexShaderID);
        GL20.glDetachShader(programID, fragmentShaderID);
        GL20.glDeleteShader(vertexShaderID);
        GL20.glDeleteShader(fragmentShaderID);
        GL20.glDeleteProgram(programID);
    }
     
    protected abstract void bindAttributes();
     
    /**
     * Binds an attribute to a variable in the vertexShader class 
     * so that the GPU will be able to recieve information.
     * @param attribute
     * @param variableName
     */
    protected void bindAttribute(int attribute, String variableName){
        GL20.glBindAttribLocation(programID, attribute, variableName);
    }
     
    /**
     * Loads a float into a uniform variable location
     * @param location
     * @param value
     */
    protected void loadFloat(int location, float value){
        GL20.glUniform1f(location, value);
    }
    
    /**
     * Loads a vector into a uniform variable location
     * @param location
     * @param vector
     */
    protected void loadVector(int location, Vector3f vector){
        GL20.glUniform3f(location,vector.x,vector.y,vector.z);
    }
     
    /**
     * Loads a boolean into a uniform variable location
     * @param location
     * @param value
     */
    protected void loadBoolean(int location, boolean value){
        float toLoad = 0;
        if(value){
            toLoad = 1;
        }
        GL20.glUniform1f(location, toLoad);
    }
     
    /**
     * Loads a matrix into a uniform variable location
     * @param location
     * @param matrix
     */
    protected void loadMatrix(int location, Matrix4f matrix){
        matrix.store(matrixBuffer);
        matrixBuffer.flip();
        GL20.glUniformMatrix4(location, false, matrixBuffer);
    }
     
    /**
     * loads the GPU code in GSLS so that OpenGL can use it
     * @param file
     * @param type
     * @return
     */
    private static int loadShader(String file, int type){
        StringBuilder shaderSource = new StringBuilder();
        try{
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            while((line = reader.readLine())!=null){
                shaderSource.append(line).append("//\n");
            }
            reader.close();
        }catch(IOException e){
            e.printStackTrace();
            System.exit(-1);
        }
        int shaderID = GL20.glCreateShader(type);
        GL20.glShaderSource(shaderID, shaderSource);
        GL20.glCompileShader(shaderID);
        if(GL20.glGetShaderi(shaderID, GL20.GL_COMPILE_STATUS )== GL11.GL_FALSE){
            System.out.println(GL20.glGetShaderInfoLog(shaderID, 500));
            System.err.println("Could not compile shader!");
            System.exit(-1);
        }
        return shaderID;
    }
 
}