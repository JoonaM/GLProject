package fi.jmak.shader;

import java.nio.FloatBuffer;
import java.util.HashMap;
import java.util.Map;

import org.lwjgl.opengl.GL20;

import fi.jmak.math.matrix.Mat4f;
import fi.jmak.math.vector.Vec3f;
import fi.jmak.utils.flippedBuffer.FlippedBuffer;

public class Shader
{
	private static int ID = -1;
	private final String name;
	
	private Map<String, Integer> uniforms;
	private Program program;
	
	public Shader(Program program)
	{
		this("Shader " + (++ ID), program);
	}
	
	public Shader(String name, Program program)
	{
		uniforms = new HashMap<String, Integer>();
		
		this.name = name;
		this.program = program;
	}
	
	public int get()
	{
		return program.get();
	}
	
	public Program getUnderlyingProgram()
	{
		return program;
	}
	
	public void bind()
	{
		program.bind();
	}
	public void unbind()
	{
		program.unbind();
	}
	
	public void delete()
	{
		program.delete();
	}

	public void setUniforms(String ... unis)
	{
		for (int i = 0; i < unis.length; i++)
		{
			
			int loc = GL20.glGetUniformLocation(get(), unis[i]);
			if (loc == -1)
			{
				System.err.println("Warning: couldn't find uniform " + unis[i] + " from shader \"" + name + "\"");
			}
			else
			{
				uniforms.put(unis[i], loc);
			}
		}
	}

	public void mat4fv(String uniform, boolean transpose, Mat4f matrix)
	{
		mat4fv(uniform, transpose, FlippedBuffer.mat4fBuffer(matrix));
	}
	
	public void mat4fv(String uniform, boolean transpose, FloatBuffer matrix)
	{
		GL20.glUniformMatrix4fv(uniforms.get(uniform), transpose, matrix);
	}

	public void vec3f(String uniform, Vec3f v)
	{
		vec3f(uniform, v.getX(), v.getY(), v.getZ());
	}

	public void vec3f(String uniform, float x, float y, float z)
	{
		GL20.glUniform3f(uniforms.get(uniform), x, y, z);
	}
	public void int1(String uniform, int val)
	{
		GL20.glUniform1i(uniforms.get(uniform), val);
	}
}
