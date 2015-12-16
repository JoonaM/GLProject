package fi.jmak.utils.texture;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;

import javax.imageio.ImageIO;

import org.lwjgl.BufferUtils;

public class TextureFile
{

	public static ByteBuffer loadARGB(String path, boolean flipx, boolean flipy) throws IOException
	{
		BufferedImage img = ImageIO.read(new File(path));
		int width = img.getWidth();
		int height = img.getHeight();
		int[] rgb = img.getRGB(0, 0, width, height, null, 0, width);
		
		ByteBuffer buffer = BufferUtils.createByteBuffer(width * height * 4);
		boolean hasAlpha = img.getColorModel().hasAlpha();
		byte a = (byte) 0xff;
		
		int startX 	= 0;
		int endX	= width - 1;
		int dirX	= 1;
		
		int startY 	= 0;
		int endY	= height - 1; 
		int dirY	= 1;
		
		if (flipx)
		{
			startX 	= width - 1;
			endX	= 0;
			dirX	= -1;
		}
		if (flipy)
		{			
			startY 	= height - 1;
			endY 	= 0;
			dirY	= -1;
		}
		
		for (int x = startX; x != endX; x += dirX)
		{
			for (int y = startY; y != endY; y += dirY)
			{
				int index = x + y * width;
				
				byte r = (byte) (rgb[index >> 16] & 0xff);
				byte g = (byte) (rgb[index >>  8] & 0xff);
				byte b = (byte) (rgb[index		] & 0xff);
				
				if (hasAlpha)
					a = (byte) 	(rgb[index >> 24] & 0xff);
				
				buffer.put(r).put(g).put(b).put(a);
			}
		}
		buffer.flip();
		
		img = null;
		
		return buffer;
	}
}
