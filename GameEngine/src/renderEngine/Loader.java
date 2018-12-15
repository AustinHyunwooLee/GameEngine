package renderEngine;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

public class Loader {
	
	//Keep track of the VAO and VBOs created.  They are referenced by their ID number in OpenGL
    private List<Integer> vaos = new ArrayList<>();
    private List<Integer> vbos = new ArrayList<>();
	
	/**
	 * Loads the position into a VAO and return a RawModel that contains the vertex count and the vao ID 
	 * @param position
	 * @return
	 */
    public RawModel loadToVAO(float[] positions,int[] indices){
        int vaoID = createVAO();
        bindIndicesBuffer(indices);
        storeDataInAttributeList(0,positions);
        unbindVAO();
        return new RawModel(vaoID,indices.length);
    }

    /**
     * Creates a VAO and returns the ID
     * @return
     */
    private int createVAO(){
        int vaoID = GL30.glGenVertexArrays();
        vaos.add(vaoID);
        GL30.glBindVertexArray(vaoID);
        return vaoID;
    }
	
    /**
     * Stores the float data into an attribute slot in the form of a VBO
     * @param attributeNumber
     * @param data
     */
    private void storeDataInAttributeList(int attributeNumber, float[] data){
        int vboID = GL15.glGenBuffers();
        vbos.add(vboID);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboID);
        FloatBuffer buffer = storeDataInFloatBuffer(data);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
        GL20.glVertexAttribPointer(attributeNumber,3,GL11.GL_FLOAT,false,0,0);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
    }

    /**
     * After finished with inserting VAO, unbind it because there is nothing else to do
     */
	private void unbindVAO() {
		GL30.glBindVertexArray(0);
	}
	
	/**
	 * Store the integer indices array into an integer buffer and bind it to a vbo.  
	 * No need to store it in an attribute slot because the position is already stored.  Use the indices
	 * to choose the vertex
	 * @param data
	 */
	private void bindIndicesBuffer(int[] data) {
		int vboID = GL15.glGenBuffers();
		vbos.add(vboID);
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, vboID);
		IntBuffer buffer = storeDataInIntBuffer(data);
		GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
	}

	/**
	 * Convert an integer array into an IntBuffer
	 * @param data
	 * @return
	 */
	private IntBuffer storeDataInIntBuffer(int[] data) {
		IntBuffer buffer = BufferUtils.createIntBuffer(data.length);
		buffer.put(data);
		buffer.flip();
		return buffer;
	}
	
	/**
	 * Store a float array into a FloatBuffer
	 * @param data
	 * @return
	 */
	private FloatBuffer storeDataInFloatBuffer(float[] data) {
		FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
		buffer.put(data);
		buffer.flip();
		return buffer;
	}
	
	/**
	 * After the display is closed, delete the VAO and VBOs that were created
	 */
	public void cleanUp() {
		for(int vao : vaos) {
			GL30.glDeleteVertexArrays(vao);
		}
		for(int vbo : vbos) {
			GL15.glDeleteBuffers(vbo);
		}
	}
	
}
