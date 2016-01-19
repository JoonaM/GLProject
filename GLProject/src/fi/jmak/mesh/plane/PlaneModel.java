package fi.jmak.mesh.plane;

import java.nio.FloatBuffer;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;

import fi.jmak.transformation.Transform;
import fi.jmak.utils.flippedBuffer.FlippedBuffer;
import fi.jmak.utils.glBuffers.VertexArrayObject;
import fi.jmak.utils.glBuffers.VertexBufferObject;

public class PlaneModel
{
	private Transform transform;
	private VertexArrayObject vao;
	private VertexBufferObject vbo;
	private int vertices;

	public PlaneModel()
	{
		transform = new Transform();
		
		float size = 0.5f;
		float texx = 2f;
		float texy = 2f;
		
		float[] data = new float[] {
				-size, 0,  size, 0, 	0, 		0, 1, 0,
				-size, 0, -size, 0, 	texy, 	0, 1, 0,
				 size, 0, -size, texx, 	texy, 	0, 1, 0,
				
				 -size, 0,  size, 0, 	0, 		0, 1, 0,
				  size, 0, -size, texx, texy, 	0, 1, 0,
				  size, 0,  size, texx, 0, 		0, 1, 0
		};
		
		vertices = 6;
		
		vao = new VertexArrayObject();
		vbo = new VertexBufferObject();

		vao.bind();
		vbo.bind(GL15.GL_ARRAY_BUFFER);
		
		FloatBuffer buf = FlippedBuffer.fbuffer( data );
		
		GL15.glBufferData(vbo.target(), buf, GL15.GL_STATIC_DRAW);
		
		GL20.glVertexAttribPointer(0, 3, GL11.GL_FLOAT, false, (3 + 2 + 3) * Float.BYTES, 0);
		GL20.glVertexAttribPointer(1, 2, GL11.GL_FLOAT, false, (3 + 2 + 3) * Float.BYTES, 3 * Float.BYTES);
		GL20.glVertexAttribPointer(2, 3, GL11.GL_FLOAT, false, (3 + 2 + 3) * Float.BYTES, (3 + 2) * Float.BYTES);
		
		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);
		GL20.glEnableVertexAttribArray(2);
		
		vao.unbind();
	}
	
	public Transform getTransform()
	{
		return transform;
	}
	
	public void render()
	{
		vao.bind();
	
		GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, vertices );
		
		vao.unbind();
	}
	
	public void delete()
	{
		vao.delete();
		vbo.delete();
	}

}
