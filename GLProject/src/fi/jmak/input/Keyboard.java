package fi.jmak.input;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWKeyCallback;

public class Keyboard
{
	private static final int SIZE = 512;
	
	private static final int NONE 	= 0;
	private static final int DOWN 	= 1;
	private static final int Q_HIT 	= 2;
	private static final int Q_UP 	= 3;
	private static final int HIT 	= 4;
	private static final int UP 	= 5;
	
	private int[] buffer;
	private GLFWKeyCallback keyCallback;
	
	public Keyboard(long window)
	{
		buffer = new int[SIZE];
		
		GLFW.glfwSetKeyCallback(window, keyCallback = new GLFWKeyCallback()
		{
			@Override
			public void invoke(long window, int key, int scancode, int action, int mods)
			{
				if (key >= SIZE)
					return;
				
				if (action == GLFW.GLFW_PRESS)
				{
					if (buffer[key] != NONE)
						return;
					
					buffer[key] = Q_HIT;
					return;
				}
				
				if (action == GLFW.GLFW_RELEASE)
				{
					buffer[key] = Q_UP;
					return;
				}
				
			}
		});
	}
	
	public boolean hit(int key)
	{
		if (key < 0 || key >= SIZE)
			return false;
		
		return buffer[key] == HIT;
	}
	public boolean up(int key)
	{
		if (key < 0 || key >= SIZE)
			return false;
		
		return buffer[key] == UP;
	}
	public boolean down(int key)
	{
		if (key < 0 || key >= SIZE)
			return false;
		
		return buffer[key] == DOWN;
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
		keyCallback.release();
	}
}
