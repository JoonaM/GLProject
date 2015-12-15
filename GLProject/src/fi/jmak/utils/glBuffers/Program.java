package fi.jmak.utils.glBuffers;

import org.lwjgl.opengl.GL20;

import fi.jmak.utils.shader.Shader;

public class Program
{
	private static String generalPath = "";
	private int program;
	
	public Program() { }
	
	public Program(String vertexPath, String tcsPath, String tesPath, String geometryPath, String fragmentPath)
	{		
		vertexPath 		= vertexPath 	!= null ? generalPath + vertexPath	:null;
		tcsPath 		= tcsPath 		!= null ? generalPath + tcsPath		:null;
		tesPath 		= tesPath 		!= null ? generalPath + tesPath		:null;
		geometryPath 	= geometryPath	!= null ? generalPath + geometryPath:null;
		fragmentPath 	= fragmentPath	!= null ? generalPath + fragmentPath:null;
		
		program = Shader.loadImmediate(vertexPath, tcsPath, tesPath, geometryPath, fragmentPath);
	}
	
	public Program(String vertexPath, String fragmentPath)
	{
		this(vertexPath, null, null, null, fragmentPath);
	}
	
	public static void setGeneralPath(String generalPath)
	{
		Program.generalPath = generalPath;
	}
	
	public int get()
	{
		return program;
	}
	
	public void bind()
	{
		GL20.glUseProgram(program);
	}
	
	public void unbind()
	{
		GL20.glUseProgram(0);
	}
	
	public void delete()
	{
		GL20.glDeleteProgram(program);
	}
}
