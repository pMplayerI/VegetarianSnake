package SnakeGame;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;

public class Main extends JFrame {	
	MainFrame game;
	
	public Main() {
		setSize(850,600);
		setTitle("Con Rắn Và Quả Táo");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(true);
		
		game = new MainFrame();
		add(game);
		
		this.addKeyListener(new handler());
		
		setVisible(true);
	}
	
	public static void main(String[] args) {
		Main main = new Main();
	}
	
	private class handler implements KeyListener {

		@Override
		public void keyTyped(KeyEvent e) {}

		@Override
		public void keyPressed(KeyEvent e) {
			
//			Điều khiển bằng các nút mũi tên
			if (e.getKeyCode() == KeyEvent.VK_UP)
				game.controller.setVector(Controller.GO_UP);
			if (e.getKeyCode() == KeyEvent.VK_DOWN)
				game.controller.setVector(Controller.GO_DOWN);
			if (e.getKeyCode() == KeyEvent.VK_LEFT)
				game.controller.setVector(Controller.GO_LEFT);
			if (e.getKeyCode() == KeyEvent.VK_RIGHT)
				game.controller.setVector(Controller.GO_RIGHT);
			if (e.getKeyCode() == KeyEvent.VK_SPACE)
				
//				Game dừng khi ấn space
				MainFrame.isPlaying = !MainFrame.isPlaying;
			
//				Chơi ván khác khi đã thua bằng space
				if (MainFrame.isGameOver) {
					MainFrame.isGameOver = !MainFrame.isGameOver;
				}
				
//				Chơi ván khác khi đã thắng bằng space
				if (MainFrame.isWinning) {
					MainFrame.isWinning = !MainFrame.isWinning;
				}
				
//				Qua màn tiếp theo bằng space
				if (MainFrame.isNextLevel) {
					MainFrame.isNextLevel = !MainFrame.isNextLevel;
				}
		}

		@Override
		public void keyReleased(KeyEvent e) {}	
	}
}
