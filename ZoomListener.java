import java.awt.Color;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;


import javax.swing.*;
import javax.swing.border.LineBorder;

import java.util.Stack;
import java.util.Timer;
import java.util.TimerTask;
/*
 * The ZoomListener class allows the fractal panel.
 * which we are attaching it to, to be zoomed.
 * It is extended to have a zooming animation
 * based on the area chosen by the user according
 * to the duration and steps which we have set.
 */
public class ZoomListener extends MouseAdapter {
	private Timer time;
	private Point pressed,moved;
	private Fractal panel ;
	private double deltaX,deltaY,constant,topX,topY,botX,botY;
	private Stack<Double> traceBack;
	private int duration,steps ;
	private InfoPanel info;
	private JPanel zoomSelection;
	private Complex top,bot;
	/*
	 * The ZoomListener takes in the constructor the Fractal
	 * panel and the InfoPanel , which it uses to correlate
	 * the data with. 
	 * Zooming out is featured as well. It is modelled with a stack
	 * where we keep all the previously zoomed coordinates.
	 */
	public ZoomListener(Fractal panel,InfoPanel info){
		this.panel = panel;
		this.info = info;
		time = null;
		traceBack = new Stack<Double>();
		duration = 1000;
		steps = 50;
		zoomSelection = new JPanel();
		zoomSelection.setOpaque(false);
		zoomSelection.setBorder(new LineBorder(Color.BLUE));
		zoomSelection.setBounds(0, 0, 0, 0);
		panel.add(zoomSelection);
	}
	/*
	 * The mousePressed event registers a when the mouse is pressed
	 * with a point.
	 */
	public void mousePressed(MouseEvent e) {
		pressed = e.getPoint();
	}
	/*
	 * The mouseDragged represents the userDefined plane to zoom
	 * by using a transparent JPanel with a coloured line border
	 * to indicate the zooming plane.
	 */
	public void mouseDragged(MouseEvent e){
		if(time != null)
			return;
		moved = e.getPoint();
		deltaX = Math.abs(moved.getX() - pressed.getX());
		deltaY = Math.abs(moved.getY() - pressed.getY());

		/*
		 * The following statements align the JPanel ,which indicates
		 * the zooming area, by checking the start point and setting 
		 * the bounds based on the quadrant the dragged point is 
		 * placed in.
		 */
		if(pressed.getX() < moved.getX() && pressed.getY()<moved.getY()){
			setBounds(pressed.getX(), pressed.getY(), deltaX, deltaY);

		}
		else if(pressed.getX()>moved.getX() && pressed.getY()>moved.getY()){
			setBounds(moved.getX(),moved.getY(), deltaX, deltaY);
		}
		else if(pressed.getX()>moved.getX() && pressed.getY()< moved.getY()){
			setBounds(moved.getX(),pressed.getY(), deltaX, deltaY);

		}
		else{
			setBounds(pressed.getX(), moved.getY(), deltaX, deltaY);
		}
		panel.repaint();
	}
	/*
	 * The mouseReleased method produces the required zooming effect
	 * by zooming in based on the pressed and released points.
	 * A timer is used with a timer task to produce a zooming 
	 * animation.
	 * 
	 * This event is extended, so that it produces a zoomed fractal
	 * relative to the dimensions of the chosen Fractal panel. This
	 * makes a much more pleasant representation of the Fractal.
	 * The displayed zoom is always relative to the pressed point.
	 */
	public void mouseReleased(MouseEvent e) {
		if(time != null )
			return;		
		time = new Timer();
		constant= (double)panel.getHeight()/(double)panel.getWidth();	//gradient of the panel
		if(pressed.equals(e.getPoint()) || moved == null){
			return;
		}
		/*
		 * The following calculations check the chosen points for
		 * zooming and sets the new complex plane which the zoom
		 * should result in. The conditionals are based on the
		 * quadrant generated by the mousePressed/Released clicks. 
		 */
		Complex c ;
		Point carry;
		Complex top =panel.getComplex(pressed.getX(), pressed.getY());
		if(pressed.getX() < moved.getX() && pressed.getY()<moved.getY()){
			carry=calculateDownwardRight();
			c=panel.getComplex(carry.getX(), carry.getY());
			setPlane(top.getImag(),c.getImag(),top.getReal(),c.getReal());

		}
		else if(pressed.getX()>moved.getX() && pressed.getY()>moved.getY()){
			carry=calculateUpwardLeft();
			c=panel.getComplex(carry.getX(), carry.getY());
			setPlane(c.getImag(),top.getImag(),c.getReal(),top.getReal());

		}
		else if(pressed.getX()>moved.getX() && pressed.getY()< moved.getY()){
			carry=calculateDownwardLeft();
			c=panel.getComplex(carry.getX(), carry.getY());
			setPlane(top.getImag(),c.getImag(),c.getReal(),top.getReal());

		}
		else{
			carry=calculateUpwardRight();
			c=panel.getComplex(carry.getX(), carry.getY());
			setPlane(c.getImag(),top.getImag(),top.getReal(),c.getReal());
		}
		MyTask task = new MyTask(panel) ;
		zoomSelection.setBounds(0, 0, 0, 0);
		time.schedule(task, 0, duration/steps);
		panel.repaint();
	}
	/*
	 * This method sets the bounds of the zooming JPanel which we use
	 * to show the user where he is pointing currently at.
	 */
	public void setBounds(double x, double y, double width, double height){
		zoomSelection.setBounds((int)x, (int)y, (int)width, (int)height);
	}
	/*
	 * This method sets the plane of the new rectangle to be zoomed.
	 */
	public void setPlane(double topY,double botY,double topX,double botX){
		this.topX = topX;
		this.topY = topY;
		this.botX = botX;
		this.botY = botY;
	}
	/*
	 * This method sets the Dimension of the new Fractal and the information,
	 * which we display in the information panel.
	 */
	public void setDimension(double minImag, double maxImag, double minReal, double maxReal){
		panel.setMinImag(minImag);
		panel.setMinReal(minReal);
		panel.setMaxImag(maxImag);
		panel.setMaxReal(maxReal);
		info.setMinImag(Default.round(minImag));
		info.setMinReal(Default.round(minReal));
		info.setMaxImag(Default.round(maxImag));
		info.setMaxReal(Default.round(maxReal));
	}
	/*
	 * Calculating the point based on the 4th Quadrant.
	 * Type casting needed, as the Point constructor accepts
	 * only integers.
	 */
	public Point calculateDownwardRight(){
		if((deltaX) < (deltaY))
			return new Point((int)(pressed.getX()+deltaX), (int)(pressed.getY()+deltaX*constant));
		else
			return new Point((int)(pressed.getX()+deltaY/constant),(int)(pressed.getY()+deltaY));
	}
	/*
	 * Calculating the point based on the 3rd Quadrant.
	 */
	public Point calculateDownwardLeft(){
		if((deltaX) < (deltaY))
			return new Point((int)(moved.getX()),(int)( pressed.getY()+deltaX*constant));
		else
			return new Point((int)(pressed.getX() -deltaY/constant),(int)moved.getY());
	}
	/*
	 * Calculating the point based on the 2nd Quadrant.
	 */
	public Point calculateUpwardLeft(){
		if((deltaX) < (deltaY))
			return new Point((int)(pressed.getX()-deltaX), (int)(pressed.getY()-deltaX*constant));
		else
			return new Point((int)(pressed.getX()-deltaY/constant),(int)(pressed.getY()-deltaY));
	}
	/*
	 * Calculating the point based on the 1st Quadrant.
	 */
	public Point calculateUpwardRight(){
		if((deltaX) < (deltaY))
			return new Point((int)(moved.getX()),(int)( pressed.getY()-deltaX*constant));
		else
			return new Point((int)(pressed.getX() +deltaY/constant),(int)(moved.getY()));
	}
	/*
	 * Mutators used for setting the top and bot
	 * rectangle edges.
	 */
	public void setTop(double real, double imag){
		top.setReal(real);
		top.setImag(imag);
	}
	public void setBot(double real, double imag){
		bot.setReal(real);
		bot.setImag(imag);
	}
	/*
	 * Inner class which represents the
	 * zooming task we are modelling.
	 */
	class MyTask extends TimerTask{
		private Fractal panel;
		private int i = 0;
		private double deltaTopX,deltaTopY,deltaBotX,deltaBotY;
		private Complex zoomTop,zoomBot;
		public MyTask(Fractal panel){
			zoomTop = new Complex(topX,topY);
			zoomBot = new Complex(botX,botY);
			top = panel.getComplex(0, 0);
			bot = panel.getComplex(panel.getWidth(), panel.getHeight());
			this.panel = panel;	
			calculateSteps();
		}
		/*
		 * This method calculates the distance which 
		 * needs to be zoomed each time.
		 */
		public void calculateSteps(){
			deltaTopX= Math.abs(top.getReal() - zoomTop.getReal())/steps;
			deltaTopY= Math.abs(top.getImag() - zoomTop.getImag())/steps;
			deltaBotX= Math.abs(bot.getReal() - zoomBot.getReal())/steps;
			deltaBotY= Math.abs(bot.getImag() - zoomBot.getImag())/steps;
		}

		@Override
		public void run() {
			if(i==steps){
				time.cancel();
				time = null;
			}

			setDimension(top.getImag()+deltaTopY,bot.getImag()-deltaBotY , top.getReal()+deltaTopX,bot.getReal()-deltaBotX);
			//adding the zooming coordinates to the stack
			traceBack.push(bot.getReal()-deltaBotX);
			traceBack.push(top.getReal()+deltaTopX);
			traceBack.push(bot.getImag()-deltaBotY );
			traceBack.push(top.getImag()+deltaTopY);
			setTop(top.getReal()+deltaTopX,top.getImag()+deltaTopY);
			setBot(bot.getReal()-deltaBotX,bot.getImag()-deltaBotY);
			panel.repaint();
			i++;
		}
	}
	@Override
	/*
	 * This method implements a zoom out feature
	 * by popping all the previously placed coordinates
	 * and tracing back to the original fractal .
	 */
	public void mouseWheelMoved(MouseWheelEvent e) {
		if(traceBack.isEmpty())
			return;
		setDimension((double)traceBack.pop(),(double)traceBack.pop(),(double)traceBack.pop(),(double)traceBack.pop());
		panel.repaint();
	}
}