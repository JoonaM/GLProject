package fi.jmak.shader;

import org.lwjgl.opengl.GL20;

public class Program 
{
	private static String generalPath = "";
	private int program;
	private String vertexSrc;
	
	public Program()
	{ 
		
	}
	
	public Program(String vertexPath, String tcsPath, String tesPath, String geometryPath, String fragmentPath)
	{	
		this();
		
		vertexPath 		= vertexPath 	!= null ? generalPath + vertexPath	:null;
		tcsPath 		= tcsPath 		!= null ? generalPath + tcsPath		:null;
		tesPath 		= tesPath 		!= null ? generalPath + tesPath		:null;
		geometryPath 	= geometryPath	!= null ? generalPath + geometryPath:null;
		fragmentPath 	= fragmentPath	!= null ? generalPath + fragmentPath:null;
		
		program = ShaderLoader.loadImmediate(vertexPath, tcsPath, tesPath, geometryPath, fragmentPath);
		
	}
	
	public Program(String vertexPath, String fragmentPath)
	{
		this(vertexPath, null, null, null, fragmentPath);
	}
	
	public String getVertexSrc()
	{
		return vertexSrc;
	}
	
	public static void setPath(Class<?> root, String generalPath)
	{
		String rootPath = root.getProtectionDomain().getCodeSource().getLocation().getPath();	
		
		setPath(rootPath + generalPath);
	}
	
	public static void setPath(String generalPath)
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

	public Program defaultShader()
	{
		String genPath = Program.generalPath;
		
		Program.setPath(Program.class, "fi/jmak/shader/");
		
		Program ret = new Program("defaultShader.vs", "defaultShader.fs");
		
		Program.setPath(genPath);
		
		return ret;
	}

}
