package SnakeGame;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

public class MainFrame extends JPanel implements Runnable {
	Thread thread;
	Snake snake;
	Controller controller;
	
//	size: kích cỡ mảng
//	zoom: độ to nhỏ của vật thể
	static int SIZE = 25, ZOOM = 20;
	static int[][] bg = new int [SIZE][SIZE];
	int WIDTH = 500, HEIGHT = 500;
	
//	Bắt đâu game sẽ dừng
	static boolean isPlaying = false, isGameOver = false, isWinning = false, isNextLevel = false;

	public MainFrame() {
		
//		Khởi tạo con rắn lúc bắt đầu
		snake = new Snake();
		
		controller = new Controller();
		
//		Sinh thức ăn lúc bắt đầu
		MainFrame.bg[Food.spawnFood().x][Food.spawnFood().y] = 1;
		
		thread = new Thread(this);
		
//		Phương thức này tự gọi đến run()
		thread.start(); 
	}

//	Vẽ tất cả :)
	public void paint(Graphics g) {
		paintBG(g);
		paintBorder(g);
		snake.drawSnake(g);
		
		if (!isPlaying) {
			g.setColor(Color.blue);
			g.setFont(g.getFont().deriveFont(18.0f));
			g.drawString("PRESS SPACE TO PLAY GAME!", 100, 200);
		}
		
		if (isGameOver) {
			g.setColor(Color.blue);
			g.setFont(g.getFont().deriveFont(18.0f));
			g.drawString("GAME OVER!", 180, 230);
		}
		
		if (isWinning) {
			g.setColor(Color.blue);
			g.setFont(g.getFont().deriveFont(18.0f));
			g.drawString("YOU ARE THE BEST!", 150, 230);
		}
		
		if (isNextLevel && !isWinning) {
			g.setColor(Color.blue);
			g.setFont(g.getFont().deriveFont(18.0f));
			g.drawString("NEXT LEVEL!", 170, 230);
		}
		
		g.setColor(Color.black);
		g.setFont(g.getFont().deriveFont(28.0f));
		g.drawString("LEVEL: "+controller.currentLevel, 600, 100);
		
		g.setColor(Color.black);
		g.setFont(g.getFont().deriveFont(18.0f));
		g.drawString("HIGHEST SCORE: "+controller.maxScore, 600, 200);
		
		g.setColor(Color.black);
		g.setFont(g.getFont().deriveFont(18.0f));
		g.drawString("SCORE: "+controller.score, 600, 250);
		
		
		g.setColor(Color.black);
		g.setFont(g.getFont().deriveFont(18.0f));
		g.drawString("MAX LENGTH: "+controller.maxLength, 600, 300);
		
		g.setColor(Color.black);
		g.setFont(g.getFont().deriveFont(18.0f));
		g.drawString("SPEED: "+controller.speed, 600, 350);	
	}

//	Vẽ khung nền
	public void paintBG(Graphics g) {
		g.setColor(Color.white);
		
//		Xóa màn hình
		g.fillRect(0, 0, WIDTH+300, HEIGHT);
		
		for (int i = 0; i < SIZE; i++)
			for (int j = 0; j < SIZE; j++) {
				
//				Vẽ ô vuông
//				g.fillRect(i*ZOOM, j*ZOOM, ZOOM-2, ZOOM-2);		
				
//				Vẽ thức ăn
				if (bg[i][j] == 1) {
					g.setColor(Color.red);
					g.fillRoundRect(i*ZOOM, j*ZOOM, ZOOM-2, ZOOM-2, ZOOM-2, ZOOM-2);
					
//					Xóa màu thừa của thức ăn trên các ô vuông
//					g.setColor(Color.white);
				}
			}
	}
	
//	Vẽ đường viền
	public void paintBorder(Graphics g) {
		g.setColor(Color.black);
		g.drawRect(0, 0, WIDTH, HEIGHT);
		g.drawRect(0, 0, WIDTH+300, HEIGHT);
	}
	
	@Override
	public void run() {
		while (true) {
			if (isPlaying) controller.snakeMove();			
			repaint();
			try {
				
//				Giới hạn 20 khung hình trên giây
				Thread.sleep(20);
				
			} catch (InterruptedException e) {}
		}
	}
}
