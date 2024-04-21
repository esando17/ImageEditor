package com.mygdx.imageeditor;

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

	

}
