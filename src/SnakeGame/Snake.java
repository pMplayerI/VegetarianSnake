package SnakeGame;

import java.awt.Color;
import java.awt.Graphics;

public class Snake {
	static int length = 2, temp = length;
	static int[] x,y;
	
	public Snake() {
		x = new int[MainFrame.SIZE];
		y = new int[MainFrame.SIZE];
		
		x[0] = 1;
		y[0] = 0;
		
		x[1] = 0;
		y[1] = 0;	
	}
	
	public static void newSnake() {
		length = temp;		
		Controller.vector = Controller.GO_RIGHT;
		
		x = new int[MainFrame.SIZE];
		y = new int[MainFrame.SIZE];
		
		x[0] = 0;
		y[0] = 0;
		
		x[1] = 1;
		y[1] = 0;
	}
	
	public void drawSnake(Graphics g) {
		for (int i = 0; i < length; i++) {
			if (i==0) g.setColor(Color.gray);
			else g.setColor(Color.black);
			g.fillRect(x[i]*MainFrame.ZOOM, y[i]*MainFrame.ZOOM, MainFrame.ZOOM-2, MainFrame.ZOOM-2);
		}
	}
	
}
