package com.mime.minefront.graphics;

import java.util.Random;

import com.mime.minefront.Game;

public class Screen extends Render {

	Render test;
	private Render3D render;
	public Screen(int width, int height) {
		super(width, height);

		Random random = new Random();
		render = new Render3D(width, height);
		test = new Render(256, 256);
		for (int i = 0; i < 256 * 256; i++) {
			test.pixels[i] = (int) (random.nextInt() * (random.nextInt(5)/4.5));
		}
	}
	
	
	
	public void render(Game game) {
		for(int i = 0; i <width*height; i++){
			//sets pixels to 0 every frame
			pixels[i] = 0;
		}
		//random math animation
		
		
		render.floor(game);
		draw(render,0,0);
		
	}
}
