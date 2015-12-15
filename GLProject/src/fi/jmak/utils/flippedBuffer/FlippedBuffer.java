package fi.jmak.utils.flippedBuffer;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;

import fi.jmak.math.matrix.Mat4f;

public class FlippedBuffer
{

	public static FloatBuffer fbuffer(float[] vals)
	{
		FloatBuffer buf = BufferUtils.createFloatBuffer(vals.length);
		buf.put(vals);
		buf.flip();
		
		return buf;
	}

	public static FloatBuffer mat4fBuffer(Mat4f matrix, boolean transpose)
	{
		if (transpose)
			return mat4fBufferTransp(matrix);
		
		return mat4fBuffer(matrix);	
	}
	
	public static FloatBuffer mat4fBuffer(Mat4f matrix)
	{
		FloatBuffer ret = BufferUtils.createFloatBuffer(4 * 4);
		
		for (int x = 0; x < 4; x++)
		{
			for (int y = 0; y < 4; y++)
			{
				ret.put(matrix.get(x, y));
			}
		}
		
		ret.flip();
		
		return ret;
	}
	
	private static FloatBuffer mat4fBufferTransp(Mat4f matrix)
	{
		FloatBuffer ret = BufferUtils.createFloatBuffer(4 * 4);
		
		for (int x = 0; x < 4; x++)
		{
			for (int y = 0; y < 4; y++)
			{
				ret.put(matrix.get(y, x));
			}
		}
		
		ret.flip();
		
		return ret;		
	}
}
