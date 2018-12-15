package shader;
 /*
  * An implementation of the ShaderProgram
  */
public class StaticShader extends ShaderProgram{
     
	//Paths of the program code
    private static final String VERTEX_FILE = "GameEngine/src/shader/vertexShader.txt";
    private static final String FRAGMENT_FILE = "GameEngine/src/shader/fragmentShader.txt";
 
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
     
     
 
}