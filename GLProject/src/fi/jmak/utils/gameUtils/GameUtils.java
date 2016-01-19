package fi.jmak.utils.gameUtils;

public class GameUtils
{

	private static long fps = 0;
	private static long fpsLoopCounter = 0;
	private static long fpsTimer = System.currentTimeMillis();
	
	public static boolean tickFPS()
	{
		fpsLoopCounter ++;
		
		if (System.currentTimeMillis() - fpsTimer >= 1000)
		{
			fps = fpsLoopCounter;
			fpsTimer = System.currentTimeMillis();
			fpsLoopCounter = 0;
			
			return true;
		}
		
		return false;
	}
	
	public static long getFPS()
	{
		return fps;
	}
}
