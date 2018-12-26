package shader;

import org.lwjgl.util.vector.Matrix4f;

import entities.Camera;
import entities.Light;
import toolbox.Maths;

/*
  * An implementation of the ShaderProgram
  */
public class StaticShader extends ShaderProgram{
	//Paths of the program code
    private static final String VERTEX_FILE = "GameEngine/src/shader/vertexShader.txt";
    private static final String FRAGMENT_FILE = "GameEngine/src/shader/fragmentShader.txt";
    
    /**
     * Locations of the uniform variables stored in memory.  If we want to connect
     * data to the uniform varaiblem, we must know the location
     */
    private int location_transformationMatrix;
    private int location_projectionMatrix;
    private int location_viewMatrix;
    private int location_lightPosition;
    private int location_lightColor;
    private int location_reflectivity;
    private int location_shineDamper; 
 
    /**
     * Calls bindAttributes, getUniformLocations
     */
    public StaticShader() {
        super(VERTEX_FILE, FRAGMENT_FILE);
    }
 
    /**
     * input for the GSLS code
     */
    @Override
    protected void bindAttributes() {
        super.bindAttribute(0, "position");
        super.bindAttribute(1, "textureCoords");
        super.bindAttribute(2, "normal");
    }

    /**
     * Gets the uniform variable id location
     */
	@Override
	protected void getAllUniformLocations() {
		location_transformationMatrix = super.getUniformLocation("transformationMatrix");
		location_projectionMatrix = super.getUniformLocation("projectionMatrix");
		location_viewMatrix = super.getUniformLocation("viewMatrix");
		location_lightPosition = super.getUniformLocation("lightPosition");
		location_lightColor = super.getUniformLocation("lightColor");
		location_reflectivity = super.getUniformLocation("reflectivity");
		location_shineDamper = super.getUniformLocation("shineDamper");
	}
	
	/**
	 * load the light source position into the uniform variable
	 * @param light
	 */
	public void loadLight(Light light) {
		super.loadVector(location_lightPosition, light.getPosition());
		super.loadVector(location_lightColor, light.getColor());
	}
	
	public void loadShineVariables(float shineDamper, float reflectivity) {
		super.loadFloat(location_shineDamper, shineDamper);
		super.loadFloat(location_reflectivity, reflectivity); 
	}
	
	/**
	 * Loads the transformation matrix into the uniform variables
	 * @param transformationMatrix
	 */
	public void loadTransformationMatrix(Matrix4f transformationMatrix) {
		super.loadMatrix(location_transformationMatrix, transformationMatrix);
	}
     
	/**
	 * loads the projection Matrix (movement about the z axis) into the 
	 * uniform variable
	 * @param projectionMatrix
	 */
	public void loadProjectionMatrix(Matrix4f projectionMatrix) {
		super.loadMatrix(location_projectionMatrix, projectionMatrix);
	}
	
	/**
	 * loads the view matrix (shifting of the objects in the world opposite
	 * of the camera movement)
	 * @param camera
	 */
	public void loadViewMatrix(Camera camera) {
		Matrix4f viewMatrix = Maths.createViewMatrix(camera);
		super.loadMatrix(location_viewMatrix, viewMatrix);
	}
	
}