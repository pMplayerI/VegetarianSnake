package SnakeGame;

import java.awt.Point;
import java.util.Random;

public class Food {
	
//	Sinh ngẫu nhiên thức ăn
	public static Point spawnFood() {
		int x,y;
		
		Random r = new Random();
		
		do {
			x = r.nextInt(MainFrame.SIZE - 1);
			y = r.nextInt(MainFrame.SIZE - 1);
		} while (checkFood(x,y));
		
		return new Point(x,y);
	}
	
//	Không cho vị trí thức ăn trùng vị trí con rắn
	public static boolean checkFood(int x, int y) {
		for (int i = 0; i < Snake.length; i++)
			if (x == Snake.x[i] && y == Snake.y[i]) return true;
		return false;
	}
}
