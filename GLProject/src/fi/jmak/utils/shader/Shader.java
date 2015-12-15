package fi.jmak.utils.shader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL32;
import org.lwjgl.opengl.GL40;

public class Shader
{
	
	public static int loadImmediate(String vertexPath, String tcsPath, String tesPath, String geometryPath, String fragmentPath)
	{
		int program = GL20.glCreateProgram();
		
		loadAttachDelete(program, vertexPath, GL20.GL_VERTEX_SHADER);
		loadAttachDelete(program, tcsPath, GL40.GL_TESS_CONTROL_SHADER);
		loadAttachDelete(program, tesPath, GL40.GL_TESS_EVALUATION_SHADER);
		loadAttachDelete(program, geometryPath, GL32.GL_GEOMETRY_SHADER);
		loadAttachDelete(program, fragmentPath, GL20.GL_FRAGMENT_SHADER);

		GL20.glLinkProgram(program);
		if (GL20.glGetProgrami(program, GL20.GL_LINK_STATUS) != GL11.GL_TRUE)
		{
			System.err.println(GL20.glGetProgramInfoLog(program, 4096));
			throw new RuntimeException();
		}
		
		GL20.glValidateProgram(program);
		if (GL20.glGetProgrami(program, GL20.GL_VALIDATE_STATUS) != GL11.GL_TRUE)
		{
			System.err.println(GL20.glGetProgramInfoLog(program, 4096));
			throw new RuntimeException();
		}
		
		return program;
	}
	
	private static void loadAttachDelete(int program, String path, int type)
	{
		if (path == null || path == "")
			return;
		
		int shader = loadShader(path, type);

		GL20.glAttachShader(program, shader);
		GL20.glDeleteShader(shader);		
	}
	
	public static int loadShader(String path, int type)
	{
		int shader = GL20.glCreateShader(type);
		
		GL20.glShaderSource(shader, loadSrc(path));
		
		GL20.glCompileShader(shader);
		
		if (GL20.glGetShaderi(shader, GL20.GL_COMPILE_STATUS) != GL11.GL_TRUE)
		{
			System.err.println(GL20.glGetShaderInfoLog(shader, 4096));
			throw new RuntimeException();
		}
		
		return shader;
	}
	
	private static String loadSrc(String path)
	{
		
		try (FileReader freader = new FileReader(new File(path)) ;
			BufferedReader reader = new BufferedReader(freader))
		{
			StringBuilder builder = new StringBuilder();
			
			String line = "";
			while ((line = reader.readLine()) != null)
			{
				builder.append(line).append("\n");
			}
			
			builder.append("\n");
			
			return builder.toString();
			
		} catch (FileNotFoundException e)
		{
			e.printStackTrace();
			return null;
		} catch (IOException e)
		{
			e.printStackTrace();
			return null;
		}	
	}
}
