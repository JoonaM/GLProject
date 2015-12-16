package fi.jmak.utils.glBuffers;

import java.nio.ByteBuffer;

import org.lwjgl.opengl.GL11;

public class Texture
{
	private int texture;
	private int target;
	
	public Texture()
	{
		texture = GL11.glGenTextures();
	}
	
	public void use(int target, ByteBuffer buffer, int width, int height)
	{
		bind(target);
		
		GL11.glTexParameteri(target, GL11.GL_TEXTURE_WRAP_S, GL11.GL_REPEAT);
		GL11.glTexParameteri(target, GL11.GL_TEXTURE_WRAP_T, GL11.GL_REPEAT);
		
		GL11.glTexParameterf(target, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
		GL11.glTexParameterf(target, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
		
		GL11.glTexImage2D(target, 0, GL11.GL_RGBA, width, height, 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, buffer);
		
		unbind();
	}
	
	public int get()
	{
		return texture;
	}
	
	public void bind(int target)
	{
		this.target = target;
		bind();
	}
	public void bind()
	{
		GL11.glBindTexture(target, texture);
	}
	public void unbind()
	{
		GL11.glBindTexture(target, 0);
	}
	
	public void delete()
	{
		GL11.glDeleteTextures(texture);
	}
}
