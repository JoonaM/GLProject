package fi.jmak.utils;

public class Utils
{

	public static float lerpf(float v0, float v1, float t)
	{
		return v0 * (1 - t) + v1 * t;
	}
	
	public static float range021(float min, float max, float t)
	{
		return (t - min ) / (max - min);
	}

	public static float clamp(float min, float max, float val)
	{
		if (val < min)
			val = min;
		else if (val > max)
			val = max;
		
		return val;
	}

	public static float cos(double d)
	{
		return (float) Math.cos(System.currentTimeMillis() / d);
	}
	public static float sin(double d)
	{
		return (float) Math.sin(System.currentTimeMillis() / d);
	}
	
}
