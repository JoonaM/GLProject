package fi.jmak.math.vector;

import java.util.Random;

import fi.jmak.utils.Utils;

public class Vec3f
{
	private float x, y, z;
	
	public Vec3f() { x = 0; y = 0; z = 0; }
	
	public Vec3f(Vec3f copy)
	{
		this.x = copy.x;
		this.y = copy.y;
		this.z = copy.z;
	}
	
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
	
	public float lengthSquared()
	{
		return x * x + y * y + z * z;		
	}
	
	public Vec3f length(float f)
	{
		normalize();
		return mul(f);
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

	public Vec3f opposite()
	{
		return new Vec3f(-x, -y, -z);
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
	
	public void setX(float x) { this.x = x; }
	public void setY(float y) { this.y = y; }
	public void setZ(float z) { this.z = z; }
	
	public Vec3f lerpf(Vec3f to, float t)
	{
		Vec3f ret = new Vec3f();
		
		ret.x = Utils.lerpf(getX(), to.getX(), t);
		ret.y = Utils.lerpf(getY(), to.getY(), t);
		ret.z = Utils.lerpf(getZ(), to.getZ(), t);
		
		return ret;
	}
	
	public boolean isNull()
	{
		
		return x == 0 && y == 0 && z == 0;
	}
	
	public static void lerpf(Vec3f to, Vec3f v0, Vec3f v1, float t)
	{
		to.x = Utils.lerpf(v0.getX(), v1.getX(), t);
		to.y = Utils.lerpf(v0.getY(), v1.getY(), t);
		to.z = Utils.lerpf(v0.getZ(), v1.getZ(), t);
	}
	
	public static Vec3f getMaxComponents(Vec3f f, Vec3f s)
	{
		return new Vec3f(
				Math.max(f.getX(), s.getX()),
				Math.max(f.getY(), s.getY()),
				Math.max(f.getZ(), s.getZ())
				);
	}
	public static Vec3f getMinComponents(Vec3f f, Vec3f s)
	{
		return new Vec3f(
				Math.min(f.getX(), s.getX()),
				Math.min(f.getY(), s.getY()),
				Math.min(f.getZ(), s.getZ())
				);
	}

	public Vec3f rand(Random r)
	{
		this.x = r.nextFloat();
		this.y = r.nextFloat();
		this.z = r.nextFloat();
		
		return this;		
	}
	
	public Vec3f rand()
	{
		this.x = (float) Math.random();
		this.y = (float) Math.random();
		this.z = (float) Math.random();
		
		return this;
	}

	public boolean same(Vec3f pos)
	{
		return x == pos.getX() && y == pos.getY() && z == pos.getZ();
	}
	
	public static Vec3f projOnPlane(Vec3f v, Vec3f planeNormal)
	{
		return v .sub( 
						planeNormal.mul ( 
											((v .dot (planeNormal)) / planeNormal.lengthSquared())
										) 
					 );				
	}

	public String toString()
	{
		return "[" + x + "] [" + y + "] [" + z + "]";
	}
}
