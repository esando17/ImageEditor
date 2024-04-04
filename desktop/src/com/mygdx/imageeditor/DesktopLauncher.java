package com.mygdx.imageeditor;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.mygdx.imageeditor.ImageEditor;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument (play-=> run config -> arguments -> paste "-XstartOnFirstThread" -> apply)
public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setForegroundFPS(60);
		config.setTitle("ImageEditor");
		System.out.println("Project made by: Emely & Benita" );
		config.setWindowedMode(584, 480);
		new Lwjgl3Application(new ImageEditor(), config);
		

	}
}
