package fi.jmak.utils.texture;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.DataBufferInt;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageFile
{
	private BufferedImage img;
	
	public void load(String path) throws IOException
	{
		img = ImageIO.read(new File(path));
	}
	
	public void colorToAlpha(Color ... colors)
	{
		int[] rgb = img.getRGB(0, 0, img.getWidth(), img.getHeight(), null, 0, img.getWidth());
		
		for (int x = 0; x < img.getWidth(); x++)
		{
			for (int y = 0; y < img.getHeight(); y++)
			{
				int index = x + y * img.getWidth();
				for (int i = 0; i < colors.length; i++)
				{
					if (colors[i].getRGB() == rgb[index])
					{
						int curRGB = (colors[i].getRGB() << 8);
						int newRGB = (0x00 << 24) | (curRGB << 16);
						
						img.setRGB(x, y, newRGB);
						
					}
				}
			}
		}
	}
	
	public BufferedImage get()
	{
		return img;
	}
}
