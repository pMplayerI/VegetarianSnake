package SnakeGame;

public class Controller {
	public static int GO_UP = 1;
	public static int GO_DOWN = -1;
	public static int GO_LEFT = 2;
	public static int GO_RIGHT = -2;
	
	Food food = new Food();
	long time = 0;
	static int vector = Controller.GO_RIGHT;
	boolean limitChangeVector = true;
	
	int maxScore = 0;
	int score = 0;
	int currentLevel = 1;
	int speed = 200, temp1 = speed;
	int maxLength = 3, temp2 = maxLength;
	
	public void setVector(int v) {
		if (vector != -v && limitChangeVector)
			vector = v;
		
//		Giới hạn một lần mỗi di chuyển
			limitChangeVector = false;
	}
	
	public int getCurrentSpeed() {
		int s = temp1;
		for (int i = 1; i < currentLevel; i++)
			s -= 20;
		return s;
	}
	
	public void snakeMove() {
		if (System.currentTimeMillis() - time > speed) {
			
//			Làm mới level
			if (Snake.length == maxLength) {
				MainFrame.isPlaying = false;
				currentLevel++;
				speed = getCurrentSpeed();
				maxLength++;
				
				Snake.newSnake();
			}
			
//			Điều kiện thắng
			if (currentLevel == 20) {
				MainFrame.isPlaying = false;
				Snake.newSnake();
				System.out.println("WIN");
				if (score > maxScore)
					maxScore = score;
				score = 0;
				currentLevel = 1;
				speed = temp1;
				maxLength = temp2;
			}
			
//			Kiểm tra đầu đụng thân
			for (int i = 1; i < Snake.length; i++) {
				if (Snake.x[i] == Snake.x[0] && Snake.y[i] == Snake.y[0]) {
					MainFrame.isPlaying = !MainFrame.isPlaying;
					MainFrame.isGameOver = !MainFrame.isGameOver;
					if (score > maxScore)
						maxScore = score;
					score = 0;
					currentLevel = 1;
					speed = temp1;
					maxLength = temp2;
				}
			}
			
//			Kiểm tra rắn ăn thức ăn sau đó sinh thức ăn mới
			if (MainFrame.bg[Snake.x[0]][Snake.y[0]] == 1) {
				Snake.length++;
				score++;
				MainFrame.bg[Snake.x[0]][Snake.y[0]] = 0;
				MainFrame.bg[food.spawnFood().x][food.spawnFood().y] = 1;
			}
			
//			Cập nhật phần thân theo phần đầu
			for (int i = Snake.length - 1; i > 0; i--) {
				Snake.x[i] = Snake.x[i-1];
				Snake.y[i] = Snake.y[i-1];
			}
			
//			Điều khiển con rắn
			limitChangeVector = true;			
			if (vector == Controller.GO_UP) Snake.y[0]--;
			if (vector == Controller.GO_DOWN) Snake.y[0]++;
			if (vector == Controller.GO_LEFT) Snake.x[0]--;
			if (vector == Controller.GO_RIGHT) Snake.x[0]++;
			
//			Khi con rắn đi vào rìa sẽ qua rìa đối diện
			if (Snake.x[0] < 0) Snake.x[0] = MainFrame.SIZE-1;
			if (Snake.x[0] > MainFrame.SIZE-1) Snake.x[0] = 0;
			if (Snake.y[0] < 0) Snake.y[0] = MainFrame.SIZE-1;
			if (Snake.y[0] > MainFrame.SIZE-1) Snake.y[0] = 0;
			
			time = System.currentTimeMillis();
		}
	}
	
}
