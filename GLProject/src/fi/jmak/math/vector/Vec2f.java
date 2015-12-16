package fi.jmak.math.vector;

public class Vec2f
{
	private float x, y;
	
	public Vec2f(float x, float y)
	{
		this.x = x;
		this.y = y;
	}
	
	public float getX()
	{
		return x;
	}
	
	public float getY()
	{
		return y;
	}
	
	public float dot(Vec2f v)
	{
		return x * v.x + y * v.y;
	}

	public Vec2f sub(Vec2f v)
	{
		return new Vec2f(x - v.x, y - v.y);
	}
	
	public String toString()
	{
		return "[" + x + "] [" + y + "]";
	}
}
