package fi.jmak.window;

import static org.lwjgl.system.MemoryUtil.*;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;

public class GLWindow
{

	private GLRender renderer;
	private int width;
	private int height;
	private long window;
	private String title;
	private WindowHints hints;

	public GLWindow(int width, int height, String title)
	{
		this.width = width;
		this.height = height;
		this.title = title;
	}
	public GLWindow(int width, int height)
	{
		this(width, height, "");
	}

	public void init(GLRender renderer)
	{
		init(renderer, null);
	}
	
	public void init(GLRender renderer, WindowHints hints)
	{
		this.renderer = renderer;
		this.hints = hints;
		
		init();
	}
	
	
	public int getWidth()
	{
		return width;
	}
	
	public int getHeight()
	{
		return height;
	}
	
	private void init()
	{
		
		if (GLFW.glfwInit() != GLFW.GLFW_TRUE)
		{
			throw new IllegalStateException("Couldn't init GLFW");
		}
		
		GLFW.glfwDefaultWindowHints();
		GLFW.glfwWindowHint(GLFW.GLFW_VISIBLE, GLFW.GLFW_FALSE);
		GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, GLFW.GLFW_FALSE);
		
		if (hints != null)
		{
			hints.set();
		}
		
		window = GLFW.glfwCreateWindow(width, height, title, NULL, NULL);
		if (window == NULL)
			throw new RuntimeException("Could not create window!");
		
		GLFW.glfwMakeContextCurrent(window);
		
		GLFW.glfwShowWindow(window);
		
		GL.createCapabilities();
		loop();
		
	}
	
	private void loop()
	{
		try
		{
			renderer.init();
		} catch (Exception e)
		{
			terminate();
			e.printStackTrace();
		}
		
		float delta = 0;
		long stime = System.nanoTime();
		while (GLFW.glfwWindowShouldClose(window) == GL11.GL_FALSE)
		{
			
			try
			{
				delta = (float) ((System.nanoTime() - stime) * 1e-9);
				stime = System.nanoTime();
				
				renderer.tick(delta);
				renderer.render();
			
			} catch (Exception e)
			{
				terminate();
				e.printStackTrace();
				break;
			}
			
			GLFW.glfwPollEvents();
			GLFW.glfwSwapBuffers(window);
		}
		
		renderer.destroy();
		
		GLFW.glfwDestroyWindow(window);
		
	}
	
	public void terminate()
	{
		GLFW.glfwSetWindowShouldClose(window, GLFW.GLFW_TRUE);
	}
	public long getHandle()
	{
		return window;
	}
}
