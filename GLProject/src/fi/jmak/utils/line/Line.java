package fi.jmak.utils.line;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import fi.jmak.math.vector.Vec2f;
import fi.jmak.utils.glBuffers.Program;
import fi.jmak.utils.glBuffers.VertexArrayObject;
import fi.jmak.utils.glBuffers.VertexBufferObject;

public class Line
{
	
	@SuppressWarnings("unused")
	private static final Line instance = new Line();
	
	private static Program program;
	private static VertexArrayObject vao;
	private static VertexBufferObject vbo;

	private static FloatBuffer vertexBuffer;

	private int maxLineAmt;
	private static int lineAmt;

	private Line()
	{
		String root = Line.class.getProtectionDomain().getCodeSource().getLocation().getPath();
		Program.setGeneralPath(root + "/fi/jmak/utils/line/");
		program = new Program("line.vs", "line.fs");
		
		vao = new VertexArrayObject();
		vbo = new VertexBufferObject();
		
		vao.bind();
		{
			vbo.bind(GL15.GL_ARRAY_BUFFER);
			
			maxLineAmt  = 1024;
			lineAmt		= 0;
			vertexBuffer = BufferUtils.createFloatBuffer(2 * maxLineAmt);
			
			GL15.glBufferData(vbo.target(), vertexBuffer.capacity() * Float.BYTES, GL15.GL_DYNAMIC_DRAW);
			
			GL20.glVertexAttribPointer(0, 2, GL11.GL_FLOAT, false, 0, 0);
			GL20.glEnableVertexAttribArray(0);
			
		}
		vao.unbind();
	}
	
	public static void draw(Vec2f v0, Vec2f v1)
	{
		draw(v0.getX(), v0.getY(), v1.getX(), v1.getY());
	}
	
	public static void draw(float x0, float y0, float x1, float y1)
	{
		int prevVao = GL11.glGetInteger(GL30.GL_VERTEX_ARRAY_BINDING);
		
		vao.bind();
//		vbo.bind(GL15.GL_ARRAY_BUFFER);

		vertexBuffer.put(x0).put(y0);
		vertexBuffer.put(x1).put(y1);
		lineAmt += 2;
		
		GL30.glBindVertexArray(prevVao);
	}
	
	
	public static void render()
	{
		vao.bind();
		vbo.bind(GL15.GL_ARRAY_BUFFER);
		program.bind();
		
		vertexBuffer.flip();
		GL15.glBufferSubData(vbo.target(), 0, vertexBuffer);
		
		GL11.glDrawArrays(GL11.GL_LINES, 0, lineAmt);
		
		vertexBuffer.clear();
		lineAmt = 0;
		
		vao.unbind();
	}
	
	public static void delete()
	{
		program.delete();
		vao.delete();
		vbo.delete();
	}
}
