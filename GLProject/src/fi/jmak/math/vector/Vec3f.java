package fi.jmak.math.vector;

import fi.jmak.utils.Utils;

public class Vec3f
{
	private float x, y, z;
	
	public Vec3f() { x = 0; y = 0; z = 0; }
	public Vec3f(float x, float y, float z)
	{
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public void set(float x, float y, float z)
	{
		this.x = x;
		this.y = y;
		this.z = z;
	}
	public void set(Vec3f v)
	{
		this.x = v.x;
		this.y = v.y;
		this.z = v.z;
	}
	
	public float length()
	{
		return (float) Math.sqrt(x * x + y * y + z * z);
	}
	
	public Vec3f normalized()
	{
		float l = length();
		
		return new Vec3f(x / l, y / l, z / l);
	}
	
	public void normalize()
	{
		float l = length();
		x /= l;
		y /= l;
		z /= l;
	}
	
	public float dot(Vec3f v)
	{
		return x * v.x + y * v.y + z * v.z;
	}
	
	public Vec3f cross(Vec3f v)
	{
		float _x = y * v.z - z * v.y;
		float _y = z * v.x - x * v.z;
		float _z = x * v.y - y * v.x;
		
		return new Vec3f(_x, _y, _z);
	}
	
	public Vec3f perspectiveDivide()
	{
		return new Vec3f(x / z, y / z, 1);
	}
	
	public Vec3f add(Vec3f v) { return new Vec3f(x + v.x, y + v.y, z + v.z); }
	public Vec3f add(float x, float y, float z) { return new Vec3f(this.x + x, this.y + y, this.z + z); }
	public Vec3f add(float v) { return new Vec3f(x + v, y + v, z + v); }
	
	public Vec3f sub(Vec3f v) { return new Vec3f(x - v.x, y - v.y, z - v.z); }
	public Vec3f sub(float x, float y, float z) { return new Vec3f(this.x - x, this.y - y, this.z - z); }
	public Vec3f sub(float v) { return new Vec3f(x - v, y - v, z - v); }
	
	public Vec3f div(Vec3f v) { return new Vec3f(x / v.x, y / v.y, z / v.z); }
	public Vec3f div(float x, float y, float z) { return new Vec3f(this.x / x, this.y / y, this.z / z); }
	public Vec3f div(float v) { return new Vec3f(x / v, y / v, z / v); }
	
	public Vec3f mul(Vec3f v) { return new Vec3f(x * v.x, y * v.y, z * v.z); }
	public Vec3f mul(float x, float y, float z) { return new Vec3f(this.x * x, this.y * y, this.z * z); }
	public Vec3f mul(float v) { return new Vec3f(x * v, y * v, z * v); }
	
	public float getX() { return x; }
	public float getY() { return y; }
	public float getZ() { return z; }
	
	public Vec2f getXY() { return new Vec2f(x, y); }
	public Vec2f getYX() { return new Vec2f(y, x); }
	
	public Vec2f getYZ() { return new Vec2f(y, z); }
	public Vec2f getZY() { return new Vec2f(z, y); }
	
	public Vec2f getXZ() { return new Vec2f(x, z); }
	public Vec2f getZX() { return new Vec2f(z, x); }
	
	public void setX(int x) { this.x = x; }
	public void setY(int y) { this.y = y; }
	public void setZ(int z) { this.z = z; }
	
	public static void lerpf(Vec3f to, Vec3f v0, Vec3f v1, float t)
	{
		to.x = Utils.lerpf(v0.getX(), v1.getX(), t);
		to.y = Utils.lerpf(v0.getY(), v1.getY(), t);
		to.z = Utils.lerpf(v0.getZ(), v1.getZ(), t);
	}
	public boolean isNull()
	{
		return (x + y + z) == 0;
	}
	
	public String toString()
	{
		return "[" + x + "] [" + y + "] [" + z + "]";
	}
	
}
