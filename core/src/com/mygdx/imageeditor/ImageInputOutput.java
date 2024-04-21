package com.mygdx.imageeditor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;

public class ImageInputOutput {
	public static ImageInputOutput Instance;
	public ImageInputOutput() {
	Instance = this;
	}
	
	public Pixmap loadImage(String filePath) {
		
		byte[] fileIntData = Gdx.files.internal(filePath).readBytes();
		// Create a new int array called ints by calling unsignBytes on fileBytes
		int[] fileBytes = Util.unsignBytes(fileIntData);
		if(fileIntData[0] != 'B' || fileIntData[1] != 'M') {
			System.out.println(filePath + " is NOT a bitmap image");
			return null;
		}
		//System.out.println("Loading file of size " + bytes.length);
		byte[] fileSize = {fileIntData[2], fileIntData[3], fileIntData[4], fileIntData[5]};
		byte[] start = {fileIntData[10], fileIntData[11], fileIntData[12], fileIntData[13]};
		byte[] widthBytes = {fileIntData[18], fileIntData[19], fileIntData[20], fileIntData[21]};//;;/
		byte[] heightBytes = {fileIntData[22], fileIntData[23], fileIntData[24], fileIntData[25]};//;;/
		byte[] bitsPerPixel = {fileIntData[28], fileIntData[29]};
		int startPoint = Util.bytesToInt(start);//;;/
		int width = Util.bytesToInt(widthBytes);//;;/
		int height = Util.bytesToInt(heightBytes);//;;/
		int bytesPerPixel = Util.bytesToInt(bitsPerPixel) / 8;
		
		if(bytesPerPixel != 3) {
			System.out.println("Unsupported image pixel format. Incorrect bits per pixel"); 
			return null;
		}
		
		Pixmap pixels = new Pixmap(width, height, Format.RGBA8888);
		int r,g,b;
		int x = 0, y = height - 1;//;
		for(int i = startPoint; i < fileBytes.length - 3; i += 3) {//Loop for color reading 
			//read color components and saving them 
			b = fileBytes[i];
			g = fileBytes[i + 1];
			r = fileBytes[i + 2];
			//normalize color values from 0-256 to 0-1
			float normalizedR = (r & 0xFF) / 256f;
			float normalizedG = (g & 0xFF) / 256f;
			float normalizedB = (b & 0xFF) / 256f;
			//set active color 
			int color = ((int) (normalizedR * 255) << 24) |  // Blue
		            	((int) (normalizedG * 255) << 16) |  // Green
		            	((int) (normalizedB * 255) << 8)  |  // Red
		            	0xFF; // Alpha channel

			pixels.setColor(color);

            pixels.drawPixel(x, y);// Draw pixel at x, y
            x++;// Increment x            
            if (x >= width) {// Check if x exceeds width, then reset x and increment y
                x = 0;
                y--;
            }
		}
		return pixels;
		}
	


}
