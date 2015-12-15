package fi.jmak.utils.glBuffers;

import org.lwjgl.opengl.GL30;

public class VertexArrayObject
{
	private int vao;

	public VertexArrayObject()
	{
		vao = GL30.glGenVertexArrays();
	}
	
	public void bind()
	{
		GL30.glBindVertexArray(vao);
	}
	
	public int get()
	{
		return vao;
	}
	public void unbind()
	{
		GL30.glBindVertexArray(0);
	}
	
	public void delete()
	{
		GL30.glDeleteVertexArrays(vao);
	}
}
