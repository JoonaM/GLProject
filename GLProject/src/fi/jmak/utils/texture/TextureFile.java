package fi.jmak.utils.texture;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;

import javax.imageio.ImageIO;

import org.lwjgl.BufferUtils;

public class TextureFile
{
	
	private static int width, height;
	
	public static ByteBuffer loadRGBA(BufferedImage img, boolean flipx, boolean flipy) throws IOException
	{
		width = img.getWidth();
		height = img.getHeight();
		int[] rgb = img.getRGB(0, 0, width, height, null, 0, width);
		
		ByteBuffer buffer = BufferUtils.createByteBuffer(width * height * 4);
		boolean hasAlpha = img.getColorModel().hasAlpha();
		
		int startX 	= 0;
		int endX	= width;
		int dirX	= 1;
		
		int startY 	= 0;
		int endY	= height; 
		int dirY	= 1;
		
		if (flipx)
		{
			startX 	= width - 1;
			endX	= -1;
			dirX	= -1;
		}
		if (flipy)
		{			
			startY 	= height - 1;
			endY 	= -1;
			dirY	= -1;
		}
		
		byte a = (byte) 0xff;
	
		for (int y = startY; y != endY; y += dirY)
		{
			for (int x = startX; x != endX; x += dirX)
			{
				int index = x + y * width;
				
				byte r = (byte) ((rgb[index] >> 16) & 0xff);
				byte g = (byte) ((rgb[index] >>  8) & 0xff);
				byte b = (byte) ((rgb[index]	  ) & 0xff);
				
				if (hasAlpha)	
					a = (byte) 	((rgb[index] >> 24) & 0xff);

				buffer.put(r).put(g).put(b).put(a);
			}
		}
		buffer.flip();
		
		return buffer;	
	}
	
	public static ByteBuffer loadRGBA(String path, boolean flipx, boolean flipy) throws IOException
	{
		BufferedImage img = ImageIO.read(new File(path));
		
		ByteBuffer ret = loadRGBA(img, flipx, flipy);
		img = null;
		
		return ret;
	}
	
	public static int getWidth()
	{
		return width;
	}
	public static int getHeight()
	{
		return height;
	}
}
