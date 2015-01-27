public class AlienShip extends AstBase{
    int hp;
    long lastFire;
    int firerate;
    int[] xs = {-20,-20,-12,12,20,20,25,20,18,12,-12,-18,-20,-25,-20};
    int[] ys = { 0,6,12,12,6,0,-4,-4,-8,-4,-4,-8,-4,-4,0};
    public AlienShip(int x, int y, int speed, int heading, int firerate) {
        super(x, y, 0, 0, AstBase.ENEMY);
        hp = 3;
        this.firerate = firerate;
        this.heading=heading;
        this.speed=speed;
    }

}
