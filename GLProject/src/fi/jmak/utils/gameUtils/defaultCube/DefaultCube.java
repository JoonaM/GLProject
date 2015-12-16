package fi.jmak.utils.gameUtils.defaultCube;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;

import fi.jmak.utils.flippedBuffer.FlippedBuffer;
import fi.jmak.utils.gameUtils.camera.GameCam;
import fi.jmak.utils.gameUtils.transformation.Transform;
import fi.jmak.utils.glBuffers.Program;
import fi.jmak.utils.glBuffers.VertexArrayObject;
import fi.jmak.utils.glBuffers.VertexBufferObject;
import fi.jmak.window.GLWindow;

public class DefaultCube
{
	
	private Transform transform;
	
	private VertexArrayObject vao;
	private VertexBufferObject vbo;
	private Program program;

	private int transformLoc;

	public DefaultCube()
	{
		transform = new Transform();
		Transform.setCamera(GLWindow.getGameCam().getCamera());
		Program.setGeneralPath(
				DefaultCube.class.getProtectionDomain().getCodeSource().getLocation().getPath() 
				+ "fi/jmak/utils/gameUtils/defaultCube/");
		
		program = new Program("shader.vs", "shader.fs");
		transformLoc = GL20.glGetUniformLocation(program.get(), "transform");
		
		vao = new VertexArrayObject();
		vbo = new VertexBufferObject();
		vao.bind();
		vbo.bind(GL15.GL_ARRAY_BUFFER);
		
		float size = 0.5f;
		float[] data = new float[] {
			-size,  size, 0,
			-size, -size, 0,
			 size, -size, 0,
			 
			 -size,  size, 0,
			  size, -size, 0, 
			  size,  size, 0
		};

		GL15.glBufferData(vbo.target(), FlippedBuffer.fbuffer(data), GL15.GL_STATIC_DRAW);

		GL20.glVertexAttribPointer(0, 3, GL11.GL_FLOAT, false, 0, 0);
		GL20.glEnableVertexAttribArray(0);
		
		vao.unbind();
	}
	
	public void tick()
	{
		
	}
	
	public void render()
	{
		vao.bind();
		program.bind();
		GL20.glUniformMatrix4fv(transformLoc, true, FlippedBuffer.mat4fBuffer(transform.doTransform()));
		
		GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, 6);
		
		vao.unbind();
		program.unbind();
	}
	
	public void delete()
	{
		program.delete();
		vao.delete();
		vbo.delete();
	}
}
