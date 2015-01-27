
public abstract class AstBase {
	
	public static int SHIP = 1;
	public static int BULLET = 2;
	public static int ASTERIOD = 3;
	public static int ASTERIODSMALL = 4;
	public static int ENEMY = 5;
	public static int BOSS = 6;
	
	
	int x;
	int y;
	int speed;
	int xSpeed;
	int ySpeed;
	int type;
	int heading;
	boolean isAlive;
	
	public AstBase(int x, int y, int xSpeed, int ySpeed, int type){
		this.x = x;
		this.y = y;
		this.xSpeed = xSpeed;
		this.ySpeed = ySpeed;
		this.type = type;
		isAlive = true;
	}
	
	public String toString(){
		return type + " " + isAlive + " "+ x + " " + y + " " + speed + " " + heading;
	}
}
