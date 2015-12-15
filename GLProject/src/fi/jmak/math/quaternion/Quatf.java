package fi.jmak.math.quaternion;

import fi.jmak.math.matrix.Mat4f;
import fi.jmak.math.vector.Vec3f;

public class Quatf
{
	private float x, y, z, w;
	
	public Quatf() { };
	public Quatf(float x, float y, float z, float w)
	{
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;
	}
	
	public Quatf toIdentity()
	{
		x = 0;
		y = 0;
		z = 0;
		w = 1;
		
		return this;
	}
	
	
	public Quatf conjugate()
	{
		return new Quatf(-x, -y, -z, w);
	}
	
	
	public Quatf rotate(float xax, float yax, float zax, float rad)
	{
		return rotate(new Vec3f(xax, yax, zax), rad);
	}
	
	public Quatf rotate(Vec3f axis, float rad)
	{
		axis.normalize();
		
		float sin = (float) Math.sin(rad / 2);
		float cos = (float) Math.cos(rad / 2);
		
		float x = axis.getX() * sin;
		float y = axis.getY() * sin;
		float z = axis.getZ() * sin;
		float w = cos;
		
		return new Quatf(x, y, z, w);
	}

	public Mat4f rotationMatrix()
	{
		float n = w * w + x * x + y * y + z * z;
		
		Mat4f ret = new Mat4f();
		if (n == 0)
			return ret.identity();
		
		n = 2.0f / n;
		
		float wx = n * w * x; float wy = n * w * y; float wz = n * w * z;
		float xx = n * x * x; float xy = n * x * y; float xz = n * x * z;
		float yy = n * y * y; float yz = n * y * z; float zz = n * z * z;
		
		float[][] m = ret.get();

		m[0][0] = 1 - (yy + zz);	m[0][1] = xy - wz; 			m[0][2] = xz + wy; 			m[0][3] = 0; 
		m[1][0] = xy + wz; 			m[1][1] = 1 - (xx + zz); 	m[1][2] = yz - wx; 			m[1][3] = 0;
		m[2][0] = xz - wy;			m[2][1] = yz + wx; 			m[2][2] = 1 - (xx + yy); 	m[2][3] = 0;
		m[3][0] = 0;				m[3][1] = 0;				m[3][2] = 0; 				m[3][3] = 1;   
		
		return ret;
	}
	
	public Quatf mul(Quatf q)
	{
		float _w = w * q.w - x * q.x - y * q.y - z * q.z;
		float _x = w * q.x + x * q.w + y * q.z - z * q.y;
		float _y = w * q.y + y * q.w + z * q.x - x * q.z;
		float _z = w * q.z + z * q.w + x * q.y - y * q.x;
	
		return new Quatf(_x, _y, _z, _w);
	}
	
	public Quatf mul(Vec3f v)
	{
		float _w = -x * v.getX() - y * v.getY() - z * v.getZ();
		float _x =  w * v.getX() + y * v.getZ() - z * v.getY();
		float _y =  w * v.getY() + z * v.getX() - x * v.getZ();
		float _z =  w * v.getZ() + x * v.getY() - y * v.getX();
		
		return new Quatf(_x, _y, _z, _w);		
	}
	
	public Vec3f rotate(Vec3f v)
	{
		Quatf ret = this.mul(v).mul(conjugate());	
		return new Vec3f(ret.x, ret.y, ret.z);
	}
	
	public Vec3f forward() { return rotate(new Vec3f(0, 0, 1)); }
	public Vec3f back() { return rotate(new Vec3f(0, 0, -1)); }
	
	public Vec3f left() { return rotate(new Vec3f(-1, 0, 0)); }
	public Vec3f right() { return rotate(new Vec3f(1, 0, 0)); }
	
	public Vec3f up() { return rotate(new Vec3f(0, 1, 0)); }
	public Vec3f down() { return rotate(new Vec3f(0, -1, 0)); }
	
	public float getX() { return x; }
	public float getY() { return y; }
	public float getZ() { return z; }
	public float getW() { return w; }
	
	public void set(Quatf q)
	{
		this.x = q.x;
		this.y = q.y;
		this.z = q.z;
		this.w = q.w;
	}
	public Quatf normalized()
	{
		float l = length();
		
		return new Quatf(x / l, y / l, z / l, w / l);
	}
	
	private float length()
	{
		return (float) Math.sqrt(x * x + y * y + z * z + w * w);
	}
}
