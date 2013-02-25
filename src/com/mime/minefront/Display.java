package com.mime.minefront;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import javax.swing.JFrame;

import com.mime.minefront.graphics.Render;
import com.mime.minefront.graphics.Screen;
import com.mime.minefront.input.InputHandler;

public class Display extends Canvas implements Runnable {

	public static final int WIDTH = 400;
	public static final int HEIGHT = 300;
	public static final String TITLE = "MonCraft Pre-Alpha 0.0.1";

	private Thread thread;
	private Screen screen;
	private Game game;
	private Render render;
	private BufferedImage img;
	private boolean running = false;
	private int pixels[];
	private InputHandler input;
	

	public Display() {
		Dimension size = new Dimension(WIDTH, HEIGHT);
		setPreferredSize(size);
		setMaximumSize(size);
		setMinimumSize(size);
				
		game = new Game();
		screen = new Screen(WIDTH, HEIGHT);
		img = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		pixels = ((DataBufferInt) img.getRaster().getDataBuffer()).getData();
		
		input = new InputHandler();
		addKeyListener(input);
		addFocusListener(input);
		addMouseListener(input);
		addMouseMotionListener(input);
	}

	private void start() {
		if (running)
			return;

		running = true;
		thread = new Thread(this);
		thread.start();

		System.out.println("Working");
	}

	private void stop() {
		if (!running)
			return;
		running = false;
		try {
			thread.join();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(0);
		}
	}

	public void run() {
		int frames = 0;
		double unProccessedSeconds = 0;
		long previousTime = System.nanoTime();
		double secondsPerTick = 1 / 60.0;
		int tickCount = 0;
		boolean ticked = false;

		while (running) {
			// calculate FPS
			long currentTime = System.nanoTime();
			long passedTime = currentTime - previousTime;
			previousTime = currentTime;
			unProccessedSeconds += passedTime / 1000000000.0;

			while (unProccessedSeconds > secondsPerTick) {
				tick();
				unProccessedSeconds -= secondsPerTick;
				ticked = true;
				tickCount++;
				if (tickCount % 60 == 0) {
					//System.out.println(frames + " FPS ");
					previousTime += 1000;
					frames = 0;
				}
			}
			if (ticked) {
				render();
				frames++;
			}
			render();
			frames++;
		}

	}

	private void tick() {
		game.tick(input.key);
	}

	private void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			return;

		}
		screen.render(game);

		for (int i = 0; i < WIDTH * HEIGHT; i++) {
			pixels[i] = screen.pixels[i];
		}
		Graphics g = bs.getDrawGraphics();
		g.drawImage(img, 0, 0, WIDTH, HEIGHT, null);
		g.dispose();
		bs.show();
	}

	public static void main(String[] args) {
		Display game = new Display();
		JFrame frame = new JFrame();

		frame.add(game);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		frame.setTitle(TITLE);
		
		frame.setResizable(false);
		frame.setVisible(true);

		game.start();
		System.out.println("Running...");

	}

}
