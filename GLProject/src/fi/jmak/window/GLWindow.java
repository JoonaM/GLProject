package fi.jmak.window;

import static org.lwjgl.system.MemoryUtil.NULL;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;

import fi.jmak.camera.Camera;
import fi.jmak.input.Keyboard;
import fi.jmak.input.Mouse;
import fi.jmak.transformation.Transform;
import fi.jmak.utils.gameUtils.camera.CameraControls;

public class GLWindow
{

	private GLRender renderer;
	private int width;
	private int height;
	private long window;
	private String title;
	private WindowHints hints;

	private static boolean escapeTerminates;
	private static Keyboard keys;
	private static Mouse mouse;
	private static CameraControls camControls;
	
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

	public void init(GLRender renderer, boolean initBasics)
	{
		init(renderer, null, initBasics);
	}
	
	public void init(GLRender renderer, WindowHints hints, boolean initBasics)
	{
		this.renderer = renderer;
		this.hints = hints;
		
		init(initBasics);
	}
	
	
	public int getWidth()
	{
		return width;
	}
	
	public int getHeight()
	{
		return height;
	}
	
	private void init(boolean initBasics)
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
		
		loop(initBasics);
	}

	
	private void initBasics()
	{
		setEscapeTerminates(true);
		
		keys = new Keyboard(window);
		mouse = new Mouse(window);
		
		Camera mainCamera = new Camera(0, 0, -3);
		Camera.setMain(mainCamera);
		mainCamera.setProjection(45, width / (float) height, 0.01f, 500.0f);
		
		Transform.setCamera(mainCamera);
		
		camControls = new CameraControls(mainCamera, 1.0f, width, height);
		
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glClearColor(0, 0, 0, 1);
	}
	
	public void cls()
	{
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
	}
	
	private void loop(boolean initBasics)
	{
		try
		{
			if (initBasics)
				initBasics();
			
			renderer.init();
		}
		catch (Exception e)
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
				
				if (keys != null)
					keys.tick();
				if (mouse != null)
					mouse.tick();
				
				if (camControls != null)
					camControls.tick(keys, mouse, delta);
				
				if (escapeTerminates)
					if (keys.hit(GLFW.GLFW_KEY_ESCAPE))
						terminate();
				
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
		
		if (keys != null)
			keys.delete();
		
		if (mouse != null)
			mouse.delete();
		
		renderer.destroy();
		
		GLFW.glfwDestroyWindow(window);
	}
	
	public static CameraControls getCameraControls()
	{
		return camControls;
	}
	
	public static Keyboard getKeyboard()
	{
		return keys;
	}
	
	public static Mouse getMouse()
	{
		return mouse;
	}

	public static void setEscapeTerminates(boolean f)
	{
		escapeTerminates = f;
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
