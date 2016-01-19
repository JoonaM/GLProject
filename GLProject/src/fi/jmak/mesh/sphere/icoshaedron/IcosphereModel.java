package fi.jmak.mesh.sphere.icoshaedron;

import java.lang.reflect.Array;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;

import fi.jmak.transformation.Transform;
import fi.jmak.utils.flippedBuffer.FlippedBuffer;
import fi.jmak.utils.glBuffers.VertexArrayObject;
import fi.jmak.utils.glBuffers.VertexBufferObject;

public class IcosphereModel
{
	
	private VertexArrayObject vao;
	private VertexBufferObject vbo;
	private VertexBufferObject ibo;
	private Transform transform;
	private int indices;
	private int vertices;
	private VertexBufferObject nbo;

	public IcosphereModel(int recursionLevel)
	{
		transform = new Transform();
		IcoData ico = new IcoData();
		ico.create(recursionLevel);
		
		vao = new VertexArrayObject();
		vbo = new VertexBufferObject();
		ibo = new VertexBufferObject();
		
		vao.bind();
		vbo.bind(GL15.GL_ARRAY_BUFFER);
		ibo.bind(GL15.GL_ELEMENT_ARRAY_BUFFER);
		
		float[] vertArray = ico.getVertArray();
		float[] normArray = ico.getNormArray();
		vertices = vertArray.length;
		
//		float[] vertexData = new float[vertArray.length * 2];
//		int offset = vertArray.length;
//		
//		for (int i = 0; i < vertArray.length; i++)
//		{
//			vertexData[i] = vertArray[i];
//			vertexData[i + offset] = normArray[i];
//		}
		
//		System.out.println(vertArray.length);
		
		int[] indArray = ico.getIndexArray();
		indices = indArray.length;
		
		GL15.glBufferData(vbo.target(), FlippedBuffer.fbuffer(vertArray), GL15.GL_STATIC_DRAW);
		GL15.glBufferData(ibo.target(), FlippedBuffer.ibuffer(indArray), GL15.GL_STATIC_DRAW);
		
		GL20.glVertexAttribPointer(0, 3, GL11.GL_FLOAT, false, 0, 0);
		
		GL20.glEnableVertexAttribArray(0);
		
		nbo = new VertexBufferObject();
		nbo.bind(GL15.GL_ARRAY_BUFFER);
		
		GL15.glBufferData(vbo.target(), FlippedBuffer.fbuffer(normArray), GL15.GL_STATIC_DRAW);
		GL20.glVertexAttribPointer(2, 3, GL11.GL_FLOAT, false, 0, 0);
		
		GL20.glEnableVertexAttribArray(2);
		
		vao.unbind();
	}
	
	public void render()
	{
		vao.bind();
		
//		GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, vertices);
		GL11.glDrawElements(GL11.GL_TRIANGLES, indices, GL11.GL_UNSIGNED_INT, 0);
		
		vao.unbind();
	}
	
	public Transform getTransform()
	{	
		return transform;
	}
	
	public void delete()
	{
		vao.delete();
		vbo.delete();
		ibo.delete();
	}

}
