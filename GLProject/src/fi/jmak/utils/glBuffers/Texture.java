package fi.jmak.utils.glBuffers;

import java.io.IOException;
import java.nio.ByteBuffer;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;

import fi.jmak.utils.texture.TextureFile;

public class Texture
{
	private int texture;
	private int target;
	
	public Texture()
	{
		texture = GL11.glGenTextures();
	}
	public Texture(String path) throws IOException
	{
		this();
		try
		{
			use(
				GL11.GL_TEXTURE_2D,
				TextureFile.loadRGBA(path, false, false),
				TextureFile.getWidth(), TextureFile.getHeight());
		} catch (IOException e)
		{
			delete();
			throw new IOException();
		}
	
	}

	public void use(int target, ByteBuffer buffer, int width, int height, boolean customParams)
	{
		bind(target);
		
		if (!customParams)
		{
			GL11.glTexParameteri(target, GL11.GL_TEXTURE_WRAP_S, GL11.GL_REPEAT);
			GL11.glTexParameteri(target, GL11.GL_TEXTURE_WRAP_T, GL11.GL_REPEAT);
			
			GL11.glTexParameteri(target, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR_MIPMAP_LINEAR);
			GL11.glTexParameteri(target, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
		}
		
		GL11.glTexImage2D(target, 0, GL11.GL_RGBA, width, height, 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, buffer);
		
		if (!customParams)
		{
			GL30.glGenerateMipmap(target);
		}
		
		unbind();
	}
	
	public void genMipmap()
	{
		bind(target);
		GL30.glGenerateMipmap(target);		
		unbind();
	}
	
	public void use(int target, ByteBuffer buffer, int width, int height)
	{
		use(target, buffer, width, height, false);
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
		if (target == 0)
			System.err.println("Warning: Texture bound to 0");
		
		GL11.glBindTexture(target, texture);
	}
	
	public void unbind()
	{
		GL11.glBindTexture(target, 0);
	}

	public int target()
	{
		return target;
	}

	public void set(int texture)
	{
		this.texture = texture;
	}
	public void delete()
	{
		GL11.glDeleteTextures(texture);
	}

}
