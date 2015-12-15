package fi.jmak.math.matrix;

import fi.jmak.math.quaternion.Quatf;
import fi.jmak.math.vector.Vec3f;

public class Mat4f
{
	private float[][] m;
	
	public Mat4f()
	{
		m = new float[4][4];
	}
	
	public Mat4f identity()
	{
		m[0][0] = 1;	m[0][1] = 0;	m[0][2] = 0;	m[0][3] = 0;
		m[1][0] = 0;	m[1][1] = 1;	m[1][2] = 0;	m[1][3] = 0;
		m[2][0] = 0;	m[2][1] = 0;	m[2][2] = 1;	m[2][3] = 0;
		m[3][0] = 0;	m[3][1] = 0;	m[3][2] = 0;	m[3][3] = 1;
		
		return this;
	}
	
	public Mat4f translation(Vec3f v)
	{
		return translation(v.getX(), v.getY(), v.getZ());
	}
	public Mat4f translation(float x, float y, float z)
	{
		m[0][0] = 1;	m[0][1] = 0;	m[0][2] = 0;	m[0][3] = x;
		m[1][0] = 0;	m[1][1] = 1;	m[1][2] = 0;	m[1][3] = y;
		m[2][0] = 0;	m[2][1] = 0;	m[2][2] = 1;	m[2][3] = z;
		m[3][0] = 0;	m[3][1] = 0;	m[3][2] = 0;	m[3][3] = 1;
		
		return this;
	}
	
	public Mat4f scale(Vec3f v)
	{
		return scale(v.getX(), v.getY(), v.getZ());
	}
	public Mat4f scale(float x, float y, float z)
	{
		m[0][0] = x;	m[0][1] = 0;	m[0][2] = 0;	m[0][3] = 0;
		m[1][0] = 0;	m[1][1] = y;	m[1][2] = 0;	m[1][3] = 0;
		m[2][0] = 0;	m[2][1] = 0;	m[2][2] = z;	m[2][3] = 0;
		m[3][0] = 0;	m[3][1] = 0;	m[3][2] = 0;	m[3][3] = 1;
		
		return this;
	}

	public Mat4f rotation(Quatf q)
	{
		float n = q.getW() * q.getW() + q.getX() * q.getX() + q.getY() * q.getY() + q.getZ() * q.getZ();
		
		if (n == 0)
			return identity();
		
		n = 2.0f / n;
		
		float wx = n * q.getW() * q.getX(); float wy = n * q.getW() * q.getY(); float wz = n * q.getW() * q.getZ();
		float xx = n * q.getX() * q.getX(); float xy = n * q.getX() * q.getY(); float xz = n * q.getX() * q.getZ();
		float yy = n * q.getY() * q.getY(); float yz = n * q.getY() * q.getZ(); float zz = n * q.getZ() * q.getZ();
		
		m[0][0] = 1 - (yy + zz);	m[0][1] = xy - wz; 			m[0][2] = xz + wy; 			m[0][3] = 0; 
		m[1][0] = xy + wz; 			m[1][1] = 1 - (xx + zz); 	m[1][2] = yz - wx; 			m[1][3] = 0;
		m[2][0] = xz - wy;			m[2][1] = yz + wx; 			m[2][2] = 1 - (xx + yy); 	m[2][3] = 0;
		m[3][0] = 0;				m[3][1] = 0;				m[3][2] = 0; 				m[3][3] = 1;   
		
		return this;
	}
	
	public Mat4f rotation(Vec3f f, Vec3f u)
	{
		Vec3f r = u.cross(f);
		
		f.normalize();
		r.normalize();
		u.normalize();
		
		return rotation(f, r, u);
	}
	
	public Mat4f rotation(Vec3f f, Vec3f r, Vec3f u)
	{
		m[0][0] = r.getX();	m[0][1] = r.getY();	m[0][2] = r.getZ();	m[0][3] = 0;
		m[1][0] = u.getX();	m[1][1] = u.getY();	m[1][2] = u.getZ();	m[1][3] = 0;
		m[2][0] = f.getX();	m[2][1] = f.getY();	m[2][2] = f.getZ();	m[2][3] = 0;
		m[3][0] = 0;		m[3][1] = 0;		m[3][2] = 0;		m[3][3] = 1;
		
		return this;
	}
	
	public Mat4f orthographic(float left, float right, float top, float bottom, float near, float far)
	{
		float width  = right - left;
		float height = top 	 - bottom;
		float depth  = far 	 - near;
		
		m[0][0] = 2 / width;m[0][1] = 0;			m[0][2] = 0;			m[0][3] = -(right + left) / width;
		m[1][0] = 0;		m[1][1] = 2 / height;	m[1][2] = 0;			m[1][3] = -(top + bottom) / height;
		m[2][0] = 0;		m[2][1] = 0;			m[2][2] = -2 / depth;	m[2][3] = -(far + near) / depth;
		m[3][0] = 0;		m[3][1] = 0;			m[3][2] = 0;			m[3][3] = 1;

		return this;
	}
	
	public Mat4f perspective(float fov, float aspect, float near, float far)
	{
		float tan = (float) Math.tan(Math.toRadians(fov / 2));
		float range = far - near;
		
		m[0][0] = 1 / (tan * aspect);	m[0][1] = 0;		m[0][2] = 0;						m[0][3] = 0;
		m[1][0] = 0;					m[1][1] = 1 / tan;	m[1][2] = 0;						m[1][3] = 0;
		m[2][0] = 0;					m[2][1] = 0;		m[2][2] = (far + near) / range;		m[2][3] = 2 * far * near / -range;
		m[3][0] = 0;					m[3][1] = 0;		m[3][2] = 1;						m[3][3] = 0;
		
		return this;
	}
	
	public void set(int x, int y, float val)
	{
		m[x][y] = val;
	}
	public float get(int x, int y)
	{
		return m[x][y];
	}

	public float[][] get()
	{
		return m;
	}

	public Mat4f mul(Mat4f r)
	{
		Mat4f ret = new Mat4f();
		
		for (int x = 0; x < 4; x++)
		{
			for (int y = 0; y < 4; y++)
			{
				ret.m[x][y] =
						m[x][0] * r.m[0][y] +
						m[x][1] * r.m[1][y] +
						m[x][2] * r.m[2][y] +
						m[x][3] * r.m[3][y];
			}
		}
		
		return ret;
	}

}
