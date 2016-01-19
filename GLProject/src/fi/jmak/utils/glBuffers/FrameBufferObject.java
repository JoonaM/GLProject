package fi.jmak.utils.glBuffers;

import org.lwjgl.opengl.GL30;

public class FrameBufferObject
{
	private int fbo;
	private int target;
	
	public FrameBufferObject()
	{
		fbo = GL30.glGenFramebuffers();
	}
	
	public void bind()
	{
		GL30.glBindFramebuffer(target, fbo);
	}
	public void bind(int target)
	{
		this.target = target;
		GL30.glBindFramebuffer(target, fbo);
	}
	
	public int get()
	{
		return fbo;
	}
	
	public boolean statusComplete(int target)
	{
		return (GL30.glCheckFramebufferStatus(target) == GL30.GL_FRAMEBUFFER_COMPLETE);
	}
	
	public int target()
	{
		return target;
	}
	public void unbind()
	{
		GL30.glBindFramebuffer(target, 0);
	}
	
	public void delete()
	{
		GL30.glDeleteFramebuffers(fbo);
	}
}
