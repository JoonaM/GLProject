package fi.jmak.window;

import org.lwjgl.glfw.GLFW;

public interface WindowHints
{

	public static WindowHints coreProfile(int major, int minor)
	{
		WindowHints hints = new WindowHints()
		{
			@Override
			public void set()
			{
				GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MAJOR, major);
				GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MINOR, minor);
				GLFW.glfwWindowHint(GLFW.GLFW_OPENGL_PROFILE, GLFW.GLFW_OPENGL_CORE_PROFILE);
				GLFW.glfwWindowHint(GLFW.GLFW_OPENGL_COMPAT_PROFILE, GLFW.GLFW_OPENGL_FORWARD_COMPAT);
			}
		};
		
		return hints;
	}
	public void set();
	
}
