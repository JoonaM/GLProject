package fi.jmak.utils.gameUtils.camera;

import org.lwjgl.glfw.GLFW;

import fi.jmak.camera.Camera;
import fi.jmak.input.Keyboard;
import fi.jmak.input.Mouse;
import fi.jmak.math.vector.Vec3f;

public class CameraControls
{
	private Camera camera;
	
	private int keyUp;
	private int keyDown;
	private int keyRight;
	private int keyLeft;
	
	
	private boolean mouseInUse;
	private int width;
	private int height;
	private float sensitivity;
	private float moveSpeed;

	private int keyForward;

	private int keyBackward;
	
	public CameraControls(Camera camera, float sensitivity, int width, int height)
	{
		this.sensitivity = sensitivity;
		this.width = width;
		this.height = height;
		
		this.camera = camera;
		mouseInUse = false;
		
		moveSpeed = 10;
		
		keyForward	= GLFW.GLFW_KEY_W;
		keyBackward	= GLFW.GLFW_KEY_S;
		keyUp 		= GLFW.GLFW_KEY_SPACE;
		keyDown 	= GLFW.GLFW_KEY_LEFT_CONTROL;
		keyLeft 	= GLFW.GLFW_KEY_A;
		keyRight 	= GLFW.GLFW_KEY_D;
	}

	public float getSensitivity()
	{
		return sensitivity;
	}
	
	public void setSensitivity(float sens)
	{
		this.sensitivity = sens;
	}
	
	public void setMoveSpeed(float moveSpeed)
	{
		this.moveSpeed = moveSpeed;
	}
	
	public float getMoveSpeed()
	{
		return moveSpeed;
	}
	
	public void tick(Keyboard keys, Mouse mouse, float delta)
	{
		
		float ox = width / 2.0f;
		float oy = height / 2.0f;
		if (mouse.hit(GLFW.GLFW_MOUSE_BUTTON_LEFT))
		{
			mouseInUse = !mouseInUse ;
			
			if (mouseInUse)
				mouse.setCursor(GLFW.GLFW_CURSOR_DISABLED);
			else
				mouse.setCursor(GLFW.GLFW_CURSOR_NORMAL);
				
			mouse.position(ox, oy);
		}
		
		Vec3f mouseMove = new Vec3f();
		if (mouseInUse)
		{
			mouseMove.set(mouse.getPos().getX() - ox, mouse.getPos().getY() - oy, 0);
			
			mouse.position(ox, oy);
		}
		
		float speed = delta * moveSpeed;
		if (keys.down(GLFW.GLFW_KEY_LEFT_SHIFT))
		{
			speed = delta;
		}
		if (keys.down(keyLeft))
		{
			camera.translate(camera.getOrientation().left().mul(speed));
		}
		if (keys.down(keyRight))
		{
			camera.translate(camera.getOrientation().right().mul(speed));
		}
		if (keys.down(keyForward))
		{
			camera.translate(camera.getOrientation().forward().mul(speed));
		}
		if (keys.down(keyBackward))
		{
			camera.translate(camera.getOrientation().back().mul(speed));
		}
		if (keys.down(keyDown))
		{
			camera.translate(camera.getOrientation().down().mul(speed));
		}
		if (keys.down(keyUp))
		{
			camera.translate(camera.getOrientation().up().mul(speed));
		}
		
		float rotSpeed = (float) Math.toRadians(0.1f * sensitivity);
		if (mouseMove.getX() != 0)
		{
			camera.rotateGlobal(new Vec3f(0, 1, 0), mouseMove.getX() * rotSpeed);
		}
		if (mouseMove.getY() != 0)
		{			
			camera.rotateLocal(new Vec3f(1, 0, 0), mouseMove.getY() * rotSpeed);
		}
		
		camera.transform();
	}
	
	
	public Camera getCamera()
	{
		return camera;
	}

	public void setLeft(int key) 	{ keyLeft = key; }
	public void setRight(int key) 	{ keyRight = key; }
	public void setForward(int key) { keyForward = key; }
	public void setBackward(int key){ keyBackward = key; }	
	public void setUp(int key) 		{ keyUp = key; }
	public void setDown(int key) 	{ keyDown=  key; }
}
