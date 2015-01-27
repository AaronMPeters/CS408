
public class Bullet extends AstBase{
	public static int SHIP1 = 1;
	public static int SHIP2 = 2;
	public static int ENEMY = 3;
	int dis;
	int owner;
	public Bullet(int x, int y, int heading, int owner) {
		super(x, y, 0, 0, AstBase.BULLET);
		speed = 10;
		this.heading = heading;
		dis = 0;
		this.owner = owner;
		// TODO Auto-generated constructor stub
	}

}
