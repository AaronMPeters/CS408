public class Asteroid extends AstBase{
    int[] xs = {-20,-20,20,20};
    int[] ys = {-20,20,20,-20};
    public Asteroid(int x, int y, int speed, int ySpeed) {
        super(x, y, 0, 0, AstBase.ASTERIOD);
        // TODO Auto-generated constructor stub
        int[] xst = {-20,-25,(int)(-9+Math.random()*10),0,(int)(-3+Math.random()*10),(int)(8+Math.random()*10),(int)(7+Math.random()*10),(int)(-6+Math.random()*10),-15,-20};
                        //20,(int)(Math.random()*20), (int)(20-Math.random()*5), (int)(20-Math.random()*5), (int)(20-Math.random()*5), (int)(20-Math.random()*5), (int)(20-Math.random()*5), (int)(20-Math.random()*5),20};
        xs = xst;
        int[] yst = {-8,0,(int)(7+Math.random()*10),4,(int)(-3+Math.random()*10),(int)(-2+Math.random()*10),(int)(-9+Math.random()*10),(int)(-15+Math.random()*10),-8,-18};
                        //(int)(Math.random()*20), (int)(20-Math.random()*5), (int)(20-Math.random()*5), (int)(20-Math.random()*5), (int)(20-Math.random()*5), (int)(20-Math.random()*5), (int)(20-Math.random()*5),20};
        
        ys = yst;
        this.speed = speed;
        heading = (int)(Math.random() * 360);
    }
 
}
