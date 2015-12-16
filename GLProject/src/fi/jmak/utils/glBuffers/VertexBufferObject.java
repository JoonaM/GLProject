package fi.jmak.utils.glBuffers;

import org.lwjgl.opengl.GL15;

public class VertexBufferObject
{
	
	private int vbo;
	private int target;

	public VertexBufferObject()
	{
		vbo = GL15.glGenBuffers();
	}

	public void bind(int target)
	{
		this.target = target;
		GL15.glBindBuffer(target, vbo);
	}
	
	public void bind()
	{
		GL15.glBindBuffer(target, vbo);
	}
	
	public void unbind()
	{
		GL15.glBindBuffer(target, 0);
	}
	
	public int get()
	{
		return vbo;
	}
	
	public void delete()
	{
		GL15.glDeleteBuffers(vbo);
	}

	public int target()
	{
		return target;	
	}

}
