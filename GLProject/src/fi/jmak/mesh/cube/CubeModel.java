package fi.jmak.mesh.cube;

import java.io.IOException;
import java.nio.FloatBuffer;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;

import fi.jmak.math.vector.Vec3f;
import fi.jmak.transformation.Transform;
import fi.jmak.utils.flippedBuffer.FlippedBuffer;
import fi.jmak.utils.glBuffers.VertexArrayObject;
import fi.jmak.utils.glBuffers.VertexBufferObject;

public class CubeModel
{
	private Transform transform;
	private VertexArrayObject vao;
	private VertexBufferObject vbo;

	public CubeModel() throws IOException
	{
		transform = new Transform();
		vao = new VertexArrayObject();
		vbo = new VertexBufferObject();
		
		vao.bind();
		vbo.bind(GL15.GL_ARRAY_BUFFER);
		
		FloatBuffer buf = FlippedBuffer.fbuffer( CubeData.get() );
		
		GL15.glBufferData(vbo.target(), buf, GL15.GL_STATIC_DRAW);
		
		GL20.glVertexAttribPointer(0, 3, GL11.GL_FLOAT, false, (3 + 2 + 3) * Float.BYTES,  0);
		GL20.glVertexAttribPointer(1, 2, GL11.GL_FLOAT, false, (3 + 2 + 3) * Float.BYTES,  3 * Float.BYTES);
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
		
		GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, CubeData.vertices());
		
		vao.unbind();
	}

	public void delete()
	{
		
		vao.delete();
		vbo.delete();
//		System.out.println("deleted");
//		vao.delete();
//		vbo.delete();
	}

}
