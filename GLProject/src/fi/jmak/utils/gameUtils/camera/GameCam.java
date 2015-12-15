package fi.jmak.utils.gameUtils.camera;

import org.lwjgl.glfw.GLFW;

import fi.jmak.input.Keyboard;
import fi.jmak.input.Mouse;
import fi.jmak.math.vector.Vec3f;

public class GameCam
{
	private Camera camera;
	private boolean mouseInUse;
	private int width;
	private int height;
	private float sensitivity;
	
	public GameCam(Camera camera, float sensitivity, int width, int height)
	{
		this.sensitivity = sensitivity;
		this.width = width;
		this.height = height;
		
		this.camera = camera;
		mouseInUse = false;
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
		
		float speed = delta * 10;	
		if (keys.down(GLFW.GLFW_KEY_A))
		{
			camera.translate(camera.getOrientation().left().mul(speed));
		}
		if (keys.down(GLFW.GLFW_KEY_D))
		{
			camera.translate(camera.getOrientation().right().mul(speed));
		}
		if (keys.down(GLFW.GLFW_KEY_W))
		{
			camera.translate(camera.getOrientation().forward().mul(speed));
		}
		if (keys.down(GLFW.GLFW_KEY_S))
		{
			camera.translate(camera.getOrientation().back().mul(speed));
		}
		if (keys.down(GLFW.GLFW_KEY_LEFT_CONTROL))
		{
			camera.translate(camera.getOrientation().down().mul(speed));
		}
		if (keys.down(GLFW.GLFW_KEY_SPACE))
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
		
//		if (keys.down(GLFW.GLFW_KEY_UP))
//		{
//		}
//		if (keys.down(GLFW.GLFW_KEY_DOWN))
//		{
//			camera.rotateLocal(new Vec3f(1, 0, 0), rotSpeed);
//		}

	}
	
	public Camera getCamera()
	{
		return camera;
	}
}
