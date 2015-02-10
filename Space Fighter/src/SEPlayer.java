
import javax.media.ControllerListener;
import java.io.*;

import javax.media.CannotRealizeException;   
import javax.media.ControllerEvent;
import javax.media.EndOfMediaEvent;
import javax.media.Manager;   
import javax.media.NoPlayerException;   
import javax.media.Player;
import javax.media.Time;


public class SEPlayer implements ControllerListener{
String fn;
Player player;
String times;

	
	public void SePlayer(String fn,String times) {
		//System.out.println("in seplayer"+fn+times);
		final File f1 = new File(fn); 
		this.times=times;
		
	      new Thread(){
	          public void run(){
	              
	              try {   
	                  player = Manager.createRealizedPlayer(f1.toURI().toURL());   
	                  //player.addControllerListener(this);
	                  player.prefetch();
	                  player.setMediaTime(new Time(0));
	                  player.start();   
	                  
	              } catch (CannotRealizeException ex) {   
	              } catch (NoPlayerException ex) {   
	              } catch (IOException ex) {   
	              }   
	          }
	        }.start();


	}
	public void changSE(String bn,String times){
		File f1 = new File(bn);  
		this.times=times;
		player.stop();
        try {   
            player = Manager.createRealizedPlayer(f1.toURI().toURL());   
            player.addControllerListener(this);
            player.prefetch();
            player.setMediaTime(new Time(0));
            player.start();   
            
        } catch (CannotRealizeException ex) {   
        } catch (NoPlayerException ex) {   
        } catch (IOException ex) {   
        }   
		
	}
	public void controllerUpdate(ControllerEvent e)
	{if(e instanceof EndOfMediaEvent){
		System.out.println(times);
		if(times.equals("one")){
		player.stop();//System.out.println(times+"lala");
		}
		else{
			//System.out.println(times+"else");
			player.setMediaTime(new Time(0));
			player.start();
		}
	
	}
		}

}
