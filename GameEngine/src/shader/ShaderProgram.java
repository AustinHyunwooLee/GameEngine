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
	
	//ID numbers
	private int programID;
	private int vertexShaderID;
	private int fragmentShaderID;
	private static FloatBuffer matrixBuffer = BufferUtils.createFloatBuffer(16);
	 
	/**
	 * Links the vertex and fragment code to the programID and loads the program so that the color of each
	 * pixel is rendered
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
	    getAllUniformLocation();
	}
	
	protected abstract void getAllUniformLocation();
	
	/**
	 * Returns the id location of the uniform variable
	 * @param variableName
	 * @return
	 */
	protected int getUniformLocation(String variableName) {
		return GL20.glGetUniformLocation(programID, variableName);
	}
	
	/**
	 * Start the program.  GPU will render the pixel's color
	 */
	public void start(){
	    GL20.glUseProgram(programID);
	}
 
	/**
	 * Stops the program (color rendering by the GPU)
	 */
	public void stop(){
	    GL20.glUseProgram(0);
	}
	
	/**
	 * When the display is closed, the program must be closed and the IDs must be deleted
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
	 * Input of the variable in the GSLS code for the vertexShader
	 * @param attribute
	 * @param variableName
	 */
	protected void bindAttribute(int attribute, String variableName){
	    GL20.glBindAttribLocation(programID, attribute, variableName);
	}
	
	/**
	 * Load a float value into a uniform matrix
	 * @param location
	 * @param value
	 */
	protected void loadFloat(int location, float value) {
		GL20.glUniform1f(location, value);
	}
	
	/** 
	 * Loads a 3d vector into a uniform variable
	 * @param location
	 * @param vector
	 */
	protected void loadVector(int location, Vector3f vector) {
		GL20.glUniform3f(location, vector.x, vector.y, vector.z);
	}
	
	/**
	 * Converts a boolean to a float of 1 or 0 into a uniform variable
	 * @param location
	 * @param value
	 */
	protected void loadBoolean(int location, boolean value) {
		Float toLoad = 0f;
		if(value) {
			toLoad = 1f;
		}
		GL20.glUniform1f(location, toLoad);
	}
	
	/**
	 * Loads a matrix into a matrix buffer and loads it into a uniform variable
	 * @param location
	 * @param matrix
	 */
	protected void loadMatrix(int location, Matrix4f matrix) {
		matrix.store(matrixBuffer);
		matrixBuffer.flip();
		GL20.glUniformMatrix4(location, false, matrixBuffer);
	}
	
	/**
	 * Loads the program (vertex and fragment shader)
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