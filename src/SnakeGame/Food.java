package SnakeGame;

import java.awt.Point;
import java.util.Random;

public class Food {
	public Point spawnFood() {
		Random r = new Random();
		int x,y;
		
		do {
			x = r.nextInt(MainFrame.SIZE - 1);
			y = r.nextInt(MainFrame.SIZE - 1);
		} while (checkFood(x,y));
		return new Point(x,y);
	}
	
	public boolean checkFood(int x, int y) {
		for (int i = 0; i < Snake.length; i++)
			if (x == Snake.x[i] && y == Snake.y[i]) return true;
		return false;
	}
	
}
