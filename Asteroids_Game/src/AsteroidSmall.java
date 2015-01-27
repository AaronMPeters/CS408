public class AsteroidSmall extends AstBase{
    int[] xs = {-20,-20,20,20};
    int[] ys = {-20,20,20,-20};
    public AsteroidSmall(int x, int y, int speed, int ySpeed) {
        super(x, y, 0, ySpeed, AstBase.ASTERIODSMALL);
        // TODO Auto-generated constructor stub
        int[] xst = {-10,-12,(int)(-9+Math.random()*5),0,(int)(-1+Math.random()*5),(int)(4+Math.random()*5),(int)(3+Math.random()*5),(int)(-3+Math.random()*5),-7,-10};
                        //20,(int)(Math.random()*20), (int)(20-Math.random()*5), (int)(20-Math.random()*5), (int)(20-Math.random()*5), (int)(20-Math.random()*5), (int)(20-Math.random()*5), (int)(20-Math.random()*5),20};
        xs = xst;
        int[] yst = {-4,0,(int)(3+Math.random()*5),2,(int)(-1+Math.random()*5),(int)(-1+Math.random()*5),(int)(-4+Math.random()*5),(int)(-7+Math.random()*5),-4,-9};
                        //(int)(Math.random()*20), (int)(20-Math.random()*5), (int)(20-Math.random()*5), (int)(20-Math.random()*5), (int)(20-Math.random()*5), (int)(20-Math.random()*5), (int)(20-Math.random()*5),20};
        
        ys = yst;
        this.speed = speed;
        heading = (int)(Math.random() * 360);
    }
 
}
