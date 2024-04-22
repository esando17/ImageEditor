package Utility;

import java.io.FileOutputStream;
import java.io.IOException;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.imageeditor.EditWindow;
import com.mygdx.imageeditor.Util;

public class ImageInputOutput {
	public static ImageInputOutput Instance;
    private byte[] _fileHeader; // Private instance variable to hold the file header
    private Pixmap _pixels;
    public String ImageFolderLocation; 
    
	public ImageInputOutput() {
	Instance = this;
	}
	
	public Pixmap loadImage(String filePath) {
		System.out.print("loadimage");
		ImageFolderLocation = scrapeFolderLocation(filePath);

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
		
		_fileHeader = new byte[startPoint]; // Allocate _fileHeader with size startPoint
		for(int i = 0; i < startPoint; i++) {
		    _fileHeader[i] = fileIntData[i]; //filling out _fileHeader w/ first startPoint bites 
		}

		
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
	    _pixels = pixels; // Set _pixels equal to the local Pixmap variable pixels
		return pixels;
		}
	
	public void saveImage(String filePath) throws IOException {
		FileOutputStream output = new FileOutputStream(filePath);
		byte[] color;
		byte[] colorData = new byte[_pixels.getWidth() * _pixels.getHeight() * 3];
		//Pixmap doodle = EditWindow.Instance.DoodleMap;
		Pixmap doodle = Util.scalePixmap(EditWindow.Instance.DoodleMap, new Vector2(_pixels.getWidth(), _pixels.getHeight()));
		int colorIndex = 0;
		for(int y = doodle.getHeight() - 1; y >= 0; y--) {
			for(int x = 0; x < doodle.getWidth(); x++) {
				color = Util.intToSignedBytes(_pixels.getPixel(x, y));//switchinf to doodle saves the doodles 
				if(color[3] != -1) {colorIndex += 3; continue; }
				colorData[colorIndex] = color[2];
				colorData[colorIndex + 1] = color[1];
				colorData[colorIndex + 2] = color[0];
				colorIndex += 3;
				//System.out.println(_pixels.getHeight() + " " + _pixels.getWidth() + " \n"
						//+ doodle.getHeight() + " " + doodle.getWidth());
			}
		}
		
		output.write(_fileHeader);
		colorIndex = 0;

		for(int y = doodle.getHeight() - 1; y >= 0; y--) {
			for(int x = 0; x < doodle.getWidth(); x++) {
				color = Util.intToSignedBytes(doodle.getPixel(x, y));//switchinf to doodle saves the doodles 
				if(color[3] != -1) {colorIndex += 3; continue; }
				colorData[colorIndex] = color[2];
				colorData[colorIndex + 1] = color[1];
				colorData[colorIndex + 2] = color[0];
				colorIndex += 3;
//				System.out.println(_pixels.getHeight() + " " + _pixels.getWidth() + " \n"
//						+ doodle.getHeight() + " " + doodle.getWidth());
			}
		}
		output.write(colorData);
		output.close();
	}

	private String scrapeFolderLocation(String filePath) {
		StringBuilder builder = new StringBuilder(filePath);
		for(int i = filePath.length() - 1; i >= 0; i--) {
		if(filePath.charAt(i) != '/') continue;
		return builder.substring(0,i);
		}
		return null;		}

}
