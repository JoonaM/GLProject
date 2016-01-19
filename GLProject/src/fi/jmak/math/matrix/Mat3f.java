package fi.jmak.math.matrix;

import java.nio.FloatBuffer;

public class Mat3f
{
	private float[][] m;
	
	public Mat3f()
	{
		m = new float[3][3];
	}
	
	public Mat3f identity()
	{
		m[0][0] = 1;	m[0][1] = 0;	m[0][2] = 0;
		m[1][0] = 0;	m[1][1] = 1;	m[1][2] = 0;
		m[2][0] = 0;	m[2][1] = 0;	m[2][2] = 1;
		
		return this;
	}
	
	public Mat3f ortho(float left, float right, float top, float bottom)
	{
		float width = (right - left);
		float height = (top - bottom);
		
		m[0][0] = 2.0f / width;	m[0][1] = 0;				m[0][2] = -(left + right) / width;
		m[1][0] = 0;			m[1][1] = 2.0f / height;	m[1][2] = -(top + bottom) / height;
		m[2][0] = 0;			m[2][1] = 0;				m[2][2] = 1;
		
		return this;		
	}

	public float get(int x, int y)
	{
		return m[x][y];
	}
}
