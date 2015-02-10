
public class Ship extends AstBase{

	int[] shipX = {0,-10,0,10,0};
	int[] shipY = {13,-6,-2,-6,12};
	
	int[][] LASER = {{-1, 1, 1, -1, -1}, {11, 11, 19, 19, 11}};
	int[][] BOMB = {{-2, 2, 2, -2, -2}, {10, 10, 16, 16, 10}};
	
	public int currentWeapon = 0;
	public static int TotalNum = 3;

	
	
	public Ship(int x, int y, int xSpeed, int ySpeed) {
		super(x, y, xSpeed, ySpeed, AstBase.SHIP);
		// TODO Auto-generated constructor stub
	}
	public void weaponchange () {
		currentWeapon++;
		if (currentWeapon >= TotalNum) {
			currentWeapon = currentWeapon % TotalNum;
		}
		return;
	}	
}
