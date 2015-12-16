package fi.jmak.utils;

public class Utils
{

	public static float lerpf(float v0, float v1, float t)
	{
		return v0 * (1 - t) + v1 * t;
	}
}
