package fi.jmak.utils.line;

import java.awt.Color;
import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import fi.jmak.camera.Camera;
import fi.jmak.math.matrix.Mat4f;
import fi.jmak.math.vector.Vec3f;
import fi.jmak.shader.Program;
import fi.jmak.transformation.Transform;
import fi.jmak.utils.flippedBuffer.FlippedBuffer;
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

	private static Transform transform;

	private static float color;

	private Line3f()
	{		
		String root = Line3f.class.getProtectionDomain().getCodeSource().getLocation().getPath();
		Program.setPath(root + "/fi/jmak/utils/line/");
		program = new Program("line3f.vs", "line3f.fs");
		translationLoc = GL20.glGetUniformLocation(program.get(), "translation");
		
		vao = new VertexArrayObject();
		vbo = new VertexBufferObject();
		
		vao.bind();
		{
			vbo.bind(GL15.GL_ARRAY_BUFFER);
			
			maxLineAmt  = 1024;
			lineAmt		= 0;
			vertexBuffer = BufferUtils.createFloatBuffer((3 + 1) * maxLineAmt);
			
			GL15.glBufferData(vbo.target(), vertexBuffer.capacity() * Float.BYTES, GL15.GL_DYNAMIC_DRAW);
			
			GL20.glVertexAttribPointer(0, 3, GL11.GL_FLOAT, false, (3 + 1) * Float.BYTES, 0);
			GL20.glVertexAttribPointer(1, 1, GL11.GL_FLOAT, false, (3 + 1) * Float.BYTES, 3 * Float.BYTES);
			GL20.glEnableVertexAttribArray(0);
			GL20.glEnableVertexAttribArray(1);
			
		}
		vao.unbind();
		
		Line3f.setTransform(new Transform());
		
	}
	
	
	public static Transform getTransform()
	{
		return transform;
	}
	
	public static void setTransform(Transform transform)
	{
		Line3f.transform = transform;
	}

	public static void setColor(Color col)
	{
		Line3f.color = packColor(col);
	}

	private static float packColor(Color color) {
	    return color.getRed() + color.getGreen() * 256.0f + color.getBlue() * 256.0f * 256.0f;
	}
	
	public static void draw(Vec3f v0, Vec3f v1)
	{
		draw(v0.getX(), v0.getY(), v0.getZ(), v1.getX(), v1.getY(), v1.getZ());
	}
	
	public static void draw(float x0, float y0, float z0, float x1, float y1, float z1)
	{
		if (vertexBuffer.remaining() < 2 * (3 + 1))
		{
			flush();
		}
		
//		vao.bind();
//		vbo.bind(GL15.GL_ARRAY_BUFFER);

		vertexBuffer.put(x0).put(y0).put(z0);
		vertexBuffer.put(color);
		vertexBuffer.put(x1).put(y1).put(z1);
		vertexBuffer.put(color);
		
		lineAmt += 2;
		

	}
	
	
	static Vec3f origin 	= new Vec3f(0, 0, 0);
	static Vec3f xAx 		= new Vec3f(1, 0, 0);
	static Vec3f yAx 		= new Vec3f(0, 1, 0);
	static Vec3f zAx 		= new Vec3f(0, 0, 1);

	private static boolean drawOrigin;
	
	public static void drawOrigin(boolean f)
	{
		drawOrigin = f;
	}
	
	private static void flush()
	{
		
		int prevVao = GL11.glGetInteger(GL30.GL_VERTEX_ARRAY_BINDING);
		int prevProg = GL11.glGetInteger(GL20.GL_CURRENT_PROGRAM);
		vao.bind();
		vbo.bind(GL15.GL_ARRAY_BUFFER);
		program.bind();
		
		Mat4f trans = 
				Camera.getMain().getProjection() 
				.mul (Camera.getMain().getTransform() .mul(transform.transformModel()));
				
		GL20.glUniformMatrix4fv(translationLoc, true, FlippedBuffer.mat4fBuffer(trans));
		vertexBuffer.flip();
		GL15.glBufferSubData(vbo.target(), 0, vertexBuffer);
		
		GL11.glDrawArrays(GL11.GL_LINES, 0, lineAmt);
		
		vertexBuffer.clear();
		lineAmt = 0;
		
		GL20.glUseProgram(prevProg);
		GL30.glBindVertexArray(prevVao);
	}
	
	public static void render()
	{	
		if (drawOrigin)
		{
			Line3f.setColor(Color.red);
			Line3f.draw(origin, xAx);
			Line3f.setColor(Color.green);
			Line3f.draw(origin, yAx);
			Line3f.setColor(Color.blue);
			Line3f.draw(origin, zAx);
		}
		
		flush();
	}
	
	public static void delete()
	{
		program.delete();
		vao.delete();
		vbo.delete();
	}
}
