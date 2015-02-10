
public class Bullet extends AstBase{
	public static int SHIP1 = 1;
	public static int SHIP2 = 2;
	public static int ENEMY = 3;
	public int[] LASERX = {-1, 1, 1, -1, -1};
	public int[] LASERY = {-5, -5, 0, 0, -5};
	public int[] BOMBX = {-1, 0, 1, 5, 1, 0, -1, -5, -1};
	public int[] BOMBY = {1, 5, 1, 0, -1, -5, -1, 0, 1};
	public int weapon = 0;
	int dis;
	int owner;
	public Bullet(int x, int y, int heading, int owner, int weapon) {
		super(x, y, 0, 0, AstBase.BULLET);
		this.weapon = weapon;
		this.heading = heading;
		dis = 0;
		this.owner = owner;
		adjustweapon();
		// TODO Auto-generated constructor stub
	}
	private void adjustweapon() {
		switch (weapon) {
		case 0:
			speed = 10;
			break;
		case 1:
			speed = 20;
			break;
		case 2:
			speed = 5;
		}
		return;
	}

}
