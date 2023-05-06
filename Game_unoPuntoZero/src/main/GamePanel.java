package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;


import javax.swing.JPanel;
import entities.Player;
import tile.TileManager;


public class GamePanel extends JPanel implements Runnable{
	//SCREEN SETTINGS
	final int originalTileSize = 16; // 16x16 tile, dimensione base dei pg e delle tessere della mappa
	final int scale = 3;
	
	public final int tileSize = originalTileSize * scale; // le tile diventano 48x48
	public final int maxScreenCol = 16;
	public final int maxScreenRow = 12;
	public final int screenWidth = tileSize * maxScreenCol; // 768 px
	public final int screenHeight = tileSize * maxScreenRow; // 576 px
	//otteniamo così un ratio 4:3
	
	//FPS
	int FPS = 60;
	
	

	TileManager tileM = new TileManager(this);
	
	KeyHandler keyH = new KeyHandler();
	Thread gameThread;//il thread è uno switch che possiamo accendere e spegnere, il programma gira finché non va off; 
	//dalla libreria java Runnable
	
	Player player = new Player(this, keyH);
	
	
	
	public GamePanel() {
		
		this.setPreferredSize(new Dimension(screenWidth, screenHeight));
		this.setBackground(Color.gray);
		this.setDoubleBuffered(true);// questo aumenta le performance in rendering
		this.addKeyListener(keyH);
		this.setFocusable(true);//con questo il gamepanel prende il "focus" per ricevere il key input
	}
	
	public void startGameThread() {
		
		gameThread = new Thread(this);
		gameThread.start();
		
	}
	
	@Override
	public void run() {
		
		double drawInterval = 1000000000/FPS;
		double delta = 0;
		long lastTime = System.nanoTime();
		long currentTime;
		long timer = 0;
		int drawCount = 0;
		
		while(gameThread != null) {
			
			currentTime = System.nanoTime();
			
			delta += (currentTime - lastTime) / drawInterval;
			timer += (currentTime - lastTime);
			lastTime = currentTime;
			
			if(delta >= 1) {
				update();
				repaint();
				delta--;
				drawCount++;
			}
			if(timer >= 1000000000) {
				System.out.println("FPS: " + drawCount);
				drawCount = 0;
				timer = 0;
			}
			
			
		}
	}
	
	public void update() {
		
		player.update();
		
	}
	public void paintComponent(Graphics g) {
		
		super.paintComponent(g);
		
		Graphics2D g2 = (Graphics2D)g;
		

		tileM.draw(g2);
		
		player.draw(g2);
		
		g2.dispose();
	}
	
}









