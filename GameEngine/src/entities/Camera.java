package entities;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;

public class Camera {
	private Vector3f position = new Vector3f(0, 0, 0);
	
	// how high or low the camera is aimed
	private float pitch;
	
	//How much left or right
	private float yaw;
	
	//How much tiltled
	private float roll;
	
	private float speed = 0.25f;
	private float degree = 1f;
	
	public Camera() {}
	
	/**
	 * Changes the position of the camera depending on actions
	 */
	public void move() {
		if(Keyboard.isKeyDown(Keyboard.KEY_COMMA)) {
			speed = 2f;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_PERIOD)) {
			speed = 0.5f;
		}
		
		if(Keyboard.isKeyDown(Keyboard.KEY_W)) {
			position.z -= speed * Math.cos(Math.toRadians(yaw));
			position.x += speed * Math.sin(Math.toRadians(yaw));
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_S)) {
			position.z += speed * Math.cos(Math.toRadians(yaw));
			position.x -= speed * Math.sin(Math.toRadians(yaw));
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_D)) {
			yaw += degree;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_A)) {
			yaw -= degree;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_Q)) {
			position.z += speed * 1.5f * Math.sin(Math.toRadians(-yaw));
			position.x -= speed * 1.5f * Math.cos(Math.toRadians(-yaw));
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_E)) {
			position.z -= speed * 1.5f * Math.sin(Math.toRadians(-yaw));
			position.x += speed * 1.5f *Math.cos(Math.toRadians(-yaw));
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_Z)) {
			position.y += speed;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_X)) {
			position.y -= speed;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_R)) {
			this.pitch -= this.degree;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_F)) {
			this.pitch += this.degree;
		}
		
		if(Keyboard.isKeyDown(Keyboard.KEY_P)) {
			System.exit(1);
		}
		
	}

	public Vector3f getPosition() {
		return position;
	}

	public float getPitch() {
		return pitch;
	}

	public float getYaw() {
		return yaw;
	}

	public float getRoll() {
		return roll;
	}

}
