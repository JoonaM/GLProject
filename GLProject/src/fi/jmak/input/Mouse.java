package fi.jmak.input;

import java.awt.MouseInfo;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWCursorPosCallback;
import org.lwjgl.glfw.GLFWMouseButtonCallback;

import fi.jmak.math.vector.Vec3f;

public class Mouse
{
	private static final int SIZE = MouseInfo.getNumberOfButtons();
	
	private static final int NONE 	= 0;
	private static final int DOWN 	= 1;
	private static final int Q_HIT 	= 2;
	private static final int Q_UP 	= 3;
	private static final int HIT 	= 4;
	private static final int UP 	= 5;
	
	
	private Vec3f pos;
	private int[] buffer;

	private GLFWMouseButtonCallback mouseBtnCallBack;
	private GLFWCursorPosCallback cursorPosCallBack;

	private long window;
	
	public Mouse(long window)
	{
		pos = new Vec3f();
		buffer = new int[SIZE];
		this.window = window;
		
		GLFW.glfwSetMouseButtonCallback(window, mouseBtnCallBack = new GLFWMouseButtonCallback()
		{
			@Override
			public void invoke(long window, int btn, int action, int mods)
			{
				if (btn >= SIZE)
				return;
			
				if (action == GLFW.GLFW_PRESS)
				{
					if (buffer[btn] != NONE)
						return;
					
					buffer[btn] = Q_HIT;
					return;
				}
				
				if (action == GLFW.GLFW_RELEASE)
				{
					buffer[btn] = Q_UP;
					return;
				}
			}
		});
		
		GLFW.glfwSetCursorPosCallback(window, cursorPosCallBack = new GLFWCursorPosCallback()
		{
			@Override
			public void invoke(long window, double xpos, double ypos)
			{
				pos.set((float) xpos, (float) ypos, 0);
			}
		});
	}
	
	public Vec3f getPos()
	{
		return pos;
	}
	
	public void position(float x, float y)
	{	
		GLFW.glfwSetCursorPos(window, x, y);
		pos.set(x, y, 0);
	}
	
	public void position(Vec3f pos)
	{
		position(pos.getX(), pos.getY());
	}

	public void setCursor(int cursor)
	{
		GLFW.glfwSetInputMode(window, GLFW.GLFW_CURSOR, cursor);
	}
	
	public boolean hit(int btn)
	{
		if (btn < 0 || btn >= SIZE)
			return false;
		
		return buffer[btn] == HIT;
	}
	public boolean up(int btn)
	{
		if (btn < 0 || btn >= SIZE)
			return false;
		
		return buffer[btn] == UP;
	}
	public boolean down(int btn)
	{
		if (btn < 0 || btn >= SIZE)
			return false;
		
		return buffer[btn] == DOWN;
	}
	public void tick()
	{
		for (int i = 0; i < SIZE; i++)
		{
			switch (buffer[i])
			{
			case NONE:
			case DOWN:
				continue;
			case Q_HIT:
				buffer[i] = HIT;
				continue;
			case Q_UP:
				buffer[i] = UP;
				continue;
			case HIT:
				buffer[i] = DOWN;
				continue;
			case UP:
				buffer[i] = NONE;
				continue;
			}
		}
	}
	public void delete()
	{
		mouseBtnCallBack.release();
		cursorPosCallBack.release();
	}

}
