package fi.jmak.utils;

public class SimplePath
{

	public static String current(Class<?> c)
	{
		return c.getProtectionDomain().getCodeSource().getLocation().getPath();
	}
}
