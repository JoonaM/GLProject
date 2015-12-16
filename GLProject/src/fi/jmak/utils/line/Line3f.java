package fi.jmak.utils.line;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import fi.jmak.math.matrix.Mat4f;
import fi.jmak.math.vector.Vec2f;
import fi.jmak.math.vector.Vec3f;
import fi.jmak.utils.flippedBuffer.FlippedBuffer;
import fi.jmak.utils.gameUtils.transformation.Transform;
import fi.jmak.utils.glBuffers.Program;
import fi.jmak.utils.glBuffers.VertexArrayObject;
import fi.jmak.utils.glBuffers.VertexBufferObject;

public class Line3f
{
	
	@SuppressWarnings("unused")
	private static final Line3f instance = new Line3f();
	
	private static Program program;
	private static VertexArrayObject vao;
	private static VertexBufferObject vbo;

	private static FloatBuffer vertexBuffer;

	private int maxLineAmt;

	private static int translationLoc;
	private static int lineAmt;

	private static Mat4f transform;

	private Line3f()
	{
		String root = Line3f.class.getProtectionDomain().getCodeSource().getLocation().getPath();
		Program.setGeneralPath(root + "/fi/jmak/utils/line/");
		program = new Program("line3f.vs", "line3f.fs");
		translationLoc = GL20.glGetUniformLocation(program.get(), "translation");
		
		vao = new VertexArrayObject();
		vbo = new VertexBufferObject();
		
		vao.bind();
		{
			vbo.bind(GL15.GL_ARRAY_BUFFER);
			
			maxLineAmt  = 1024;
			lineAmt		= 0;
			vertexBuffer = BufferUtils.createFloatBuffer(3 * maxLineAmt);
			
			GL15.glBufferData(vbo.target(), vertexBuffer.capacity() * Float.BYTES, GL15.GL_DYNAMIC_DRAW);
			
			GL20.glVertexAttribPointer(0, 3, GL11.GL_FLOAT, false, 0, 0);
			GL20.glEnableVertexAttribArray(0);
			
		}
		vao.unbind();
	}
	
	public static void setTransform(Mat4f transform)
	{
		Line3f.transform = transform;
	}
	
	public static void draw(Vec3f v0, Vec3f v1)
	{
		draw(v0.getX(), v0.getY(), v0.getZ(), v1.getX(), v1.getY(), v1.getZ());
	}
	
	public static void draw(float x0, float y0, float z0, float x1, float y1, float z1)
	{
		int prevVao = GL11.glGetInteger(GL30.GL_VERTEX_ARRAY_BINDING);
		
		vao.bind();
//		vbo.bind(GL15.GL_ARRAY_BUFFER);

		vertexBuffer.put(x0).put(y0).put(z0);
		vertexBuffer.put(x1).put(y1).put(z1);
		lineAmt += 2;
		
		GL30.glBindVertexArray(prevVao);
	}
	
	
	public static void render()
	{
		vao.bind();
		vbo.bind(GL15.GL_ARRAY_BUFFER);
		program.bind();
		
		GL20.glUniformMatrix4fv(translationLoc, true, FlippedBuffer.mat4fBuffer(transform));
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
