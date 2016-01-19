package fi.jmak.utils.flippedBuffer;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;

import fi.jmak.math.matrix.Mat3f;
import fi.jmak.math.matrix.Mat4f;
import fi.jmak.math.vector.Vec3f;

public class FlippedBuffer
{

	public static FloatBuffer fBuffer(float[] ... data)
	{
		int length = 0;
		for (int i = 0; i < data.length; i++)
			length += data[i].length;
		
		
		FloatBuffer buffer = BufferUtils.createFloatBuffer(length);
		for (int i = 0; i < data.length; i++)
		{
			for (int j = 0; j < data[i].length; j++)
			{
				buffer.put(data[i][j]);
			}
		}
		buffer.flip();
		
		return buffer;
	}
	
	
	public static FloatBuffer fbuffer(float[] vals)
	{
		FloatBuffer buf = BufferUtils.createFloatBuffer(vals.length);
		buf.put(vals);
		buf.flip();
		
		return buf;
	}

	public static IntBuffer ibuffer(int[] vals)
	{
		IntBuffer buf = BufferUtils.createIntBuffer(vals.length);
		buf.put(vals);
		buf.flip();
		
		return buf;
	}
	
	public static FloatBuffer vec3fBuffer(Vec3f vec)
	{
		FloatBuffer buf = BufferUtils.createFloatBuffer(3);
		buf.put(vec.getX()).put(vec.getY()).put(vec.getZ());
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

	public static FloatBuffer mat3fBuffer(Mat3f matrix, boolean transpose)
	{
		if (transpose)
			return mat3fBufferTransp(matrix);
		
		return mat3fBuffer(matrix);
	}
	
	public static FloatBuffer mat3fBuffer(Mat3f matrix)
	{
		FloatBuffer ret = BufferUtils.createFloatBuffer(3 * 3);
		
		for (int x = 0; x < 3; x++)
		{
			for (int y = 0; y < 3; y++)
			{
				ret.put(matrix.get(x, y));
			}
		}	
		ret.flip();
		
		return ret;
	}
	private static FloatBuffer mat3fBufferTransp(Mat3f matrix)
	{
		FloatBuffer ret = BufferUtils.createFloatBuffer(3 * 3);
		
		for (int x = 0; x < 3; x++)
		{
			for (int y = 0; y < 3; y++)
			{
				ret.put(matrix.get(y, x));
			}
		}	
		ret.flip();
		
		return ret;
	}

}
