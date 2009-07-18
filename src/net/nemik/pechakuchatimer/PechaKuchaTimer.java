package net.nemik.pechakuchatimer;

import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import javax.microedition.midlet.MIDlet;
import javax.microedition.midlet.MIDletStateChangeException;

public class PechaKuchaTimer 
	extends MIDlet
	implements CommandListener
{	
	private Display display;
	
	static final Command CMD_EXIT = new Command("Exit", Command.EXIT, 2);
	static final Command CMD_OK = new Command("OK", Command.OK, 2);
	
	PC pc;
	
     protected void startApp()
     	throws MIDletStateChangeException
     {
    	 thread_illuminazione.start();
    	 this.display = Display.getDisplay(this);
    	 pc = new PC(this.display);
    	 pc.addCommand(CMD_EXIT);
    	 pc.addCommand(CMD_OK);
     }
     
     Thread thread_illuminazione = new Thread() 
     {
         public void run() 
         {
             while (true) 
             {
            	 display.flashBacklight(1000);
                 try 
                 {
                     Thread.sleep(20);
                 } 
                 catch (Exception e) 
                 {}
             }
         }
     };

     protected void pauseApp(){
          // ignore
     }

     protected void destroyApp(boolean unconditional) throws MIDletStateChangeException{
          // nothing to clean up
     }

	public void commandAction(Command command, Displayable displayable) 
	{
		if (command == CMD_EXIT) 
		{
			try
			{
				destroyApp(true);
			} 
			catch (MIDletStateChangeException e)
			{}
		    notifyDestroyed();
		}
		else if (command == CMD_OK) 
		{
			pc.reset();
		}
	}
	
	public void commandAction(Command command)
	{
		if (command == CMD_EXIT) 
		{
			try
			{
				destroyApp(true);
			} 
			catch (MIDletStateChangeException e)
			{}
		    notifyDestroyed();
		}
		else if (command == CMD_OK) 
		{
			pc.reset();
		}
	}
}

class PC extends Canvas
{
	Image iii;
	int i;
	
	public PC(Display d)
	{	
		d.setCurrent(this);
		setFullScreenMode(true);
		try
  		{
  			this.iii  = Image.createImage("/1.PNG");;
  		}
  		catch(Exception e)
  		{}
  		i = 20;
  		while(true)
  		{
  			if(i == 0)
  			{
  				i = 20;
  			}
  			try
  			{
  				this.iii = Image.createImage("/"+i+".PNG");
  				repaint();
  			}
  			catch(Exception e)
  			{};
  			try 
  			{
  				Thread.sleep(1000); 
  			} 
  			catch (Exception e) 
  			{}
  			i--;
  		}
	}
	
	public void reset()
	{
		i = 1;
	}
	
	protected void paint(Graphics g)
	{
		g.setColor(255, 255, 255);
		g.fillRect(0, 0, getWidth(), getHeight());
		if(null != this.iii)
		{
			g.drawImage(this.iii, getWidth()/2, getHeight()/2, Graphics.HCENTER | Graphics.VCENTER);
		}
	}
	
	public void keyPressed(int code)
	{
		if(code == -6 || code == -5)
		{
			this.reset();
		}
		System.out.println(code);
	}
}