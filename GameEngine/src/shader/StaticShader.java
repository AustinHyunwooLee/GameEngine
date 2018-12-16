package shader;

import org.lwjgl.util.vector.Matrix4f;

/*
  * An implementation of the ShaderProgram
  */
public class StaticShader extends ShaderProgram{
     
	//Paths of the program code
    private static final String VERTEX_FILE = "GameEngine/src/shader/vertexShader.txt";
    private static final String FRAGMENT_FILE = "GameEngine/src/shader/fragmentShader.txt";
    
    private int location_transformationMatrix;
 
    public StaticShader() {
        super(VERTEX_FILE, FRAGMENT_FILE);
    }
 
    /**
     * input for the GSLS code
     */
    @Override
    protected void bindAttributes() {
        super.bindAttribute(0, "position");
    }

	@Override
	protected void getAllUniformLocation() {
		location_transformationMatrix = super.getUniformLocation("transformationMatrix");
	}
	
	public void loadTransformationMatrix(Matrix4f matrix) {
		
	}
     
 
}