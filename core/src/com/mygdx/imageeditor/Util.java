package com.mygdx.imageeditor;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.math.Vector2;

//mark all methods as static (pg9)
public class Util {
	public static int bytesToInt(byte[] bytes) {
		int[] unsignedInts = unsignBytes(bytes);
		
		int result = 0;
		for(int i = 0; i < unsignedInts.length; i++) {
		result += (int) bytes[i] << (8 * i);
		}
		return result;
		}
	public static int[] unsignBytes(byte[] bytes) {
	    int[] ints = new int[bytes.length];
	    for(int i = 0; i < bytes.length; i++) {
	        ints[i] = bytes[i] & 0xFF;
	    }
	    return ints;
	}
	public static byte[] intToSignedBytes(int value) {
		byte[] result = new byte[4];
		result[0] = (byte) (value >> 24);       // Leftmost byte (freebee from pg 5) 
        result[1] = (byte) (value >> 16);       // 2nd leftmost byte
        result[2] = (byte) (value >> 8);        // 3rd leftmost byte
        result[3] = (byte) (value);             // 4th leftmost byte
		return result;
	}
	
	public static void testIntToSignedBytes() {//grabs test results and compares them to the expected results
		byte[] testResults = intToSignedBytes(543152314);
		int[] expectedResults = {32, 95, -40, -70};
		for(int i = 0; i < testResults.length; i++) {
			if((int) testResults[i] != expectedResults[i])
				System.out.println("TEST FAILED! INDEX " + i + " IS "
						+ testResults[i] + " EXPECTED: " + expectedResults[i]);
		}
	}

	public static Pixmap scalePixmap(Pixmap source, Vector2 desiredSize) {
	    int targetWidth = (int) desiredSize.x;
	    int targetHeight = (int) desiredSize.y;

	    int sourceWidth = source.getWidth();
	    int sourceHeight = source.getHeight();

	    Pixmap target = new Pixmap(targetWidth, targetHeight, Pixmap.Format.RGBA8888);

	    for (int targetY = 0; targetY < targetHeight; targetY++) {
	        for (int targetX = 0; targetX < targetWidth; targetX++) {
	            int sourceX = Math.round((float) targetX / targetWidth * sourceWidth);
	            int sourceY = Math.round((float) targetY / targetHeight * sourceHeight);
	        

	            int color = source.getPixel(sourceX, sourceY);
	            target.drawPixel(targetX, targetY, color);
	        }
	    }

	    return target;
	}



}
