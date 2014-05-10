import java.awt.*;

import javax.swing.*;
import javax.swing.border.LineBorder;
/*
 * This is an abstract fractal class which represents all fractals.
 * It contains all the necessary things that a fractal needs or has
 * to implement via an abstract method.
 */
@SuppressWarnings("serial")
public abstract class Fractal extends JPanel{
	public double minImag,minReal,maxImag,maxReal,trapMin,trapMax;
	protected double CYCLE ,width,height;
	protected Formula formula;
	protected Complex current;
	public boolean trapped = false;
	
	public Fractal(){
		minImag = Default.getMinImag();
		maxImag = Default.getMaxImag();
		minReal = Default.getMinReal();
		maxReal = Default.getMaxReal();
		trapMin = Default.getTrapMin();
		trapMax = Default.getTrapMax();
		CYCLE= Default.getCYCLE();
		init();
	}
	/*
	 * Initialisation of the panel.
	 */
	public void init(){
		setPreferredSize(new Dimension(400,320));
		setLayout(null);
		setBorder(new LineBorder(Color.GRAY));
		
	}

	/*
	 * This method returns a complex number based on the
	 * (x,y) positioned pixel. Commonly used for further graphical
	 * calculations in listeners
	 */
	public Complex getComplex(double x, double y){
		double real= x*(maxReal-minReal)/getWidth() +minReal;
		double imag= y*(maxImag-minImag)/getHeight() + minImag;
		Complex number = new Complex(real,imag);
		return number;
	}
	/*
	 * Abstract method for each class to implement on its own
	 */
	public abstract void paintComponent(Graphics g);
	/*Setting different colour if the fractal
	 *has been set to trapped or not. This is
	 *done because the produced image needs a
	 *greater visualisation , through different
	 *colours, because the Orbit Trap iterations
	 *produce numbers which get coloured in a
	 *way, which doesn't allow the user to
	 *distinguish the trapped regions*/
	public void paintFractal(Graphics g, boolean trapped,double iterations,double x, double y){
		if(!trapped){
			if(iterations < CYCLE)
				g.setColor(Color.getHSBColor(Default.H - (float)(iterations/CYCLE*Math.log(CYCLE)), Default.S, Default.B));
			else
				g.setColor(new Color(0,0,0));
			g.fillRect((int)x,(int) y, 1, 1);
		}
		else{
			if(iterations < CYCLE)
				g.setColor(Color.getHSBColor(Default.H - (float)Math.log(iterations/CYCLE), Default.S, Default.B));
			else
				g.setColor(new Color(0,0,0));
			g.fillRect((int)x,(int) y, 1, 1);
		}
	}
	public void setIterations(double iter){
		CYCLE = iter;
	}
	public double getMinImag() {
		return minImag;
	}
	public double getMinReal() {
		return minReal;
	}
	public double getMaxImag() {
		return maxImag;
	}
	public double getMaxReal() {
		return maxReal;
	}
	public void setMinImag(double minImag) {
		this.minImag = minImag;
	}
	public void setMinReal(double minReal) {
		this.minReal = minReal;
	}
	public void setMaxImag(double maxImag) {
		this.maxImag = maxImag;
	}
	public void setMaxReal(double maxReal) {
		this.maxReal = maxReal;
	}
	public Formula getFormula() {
		return formula;
	}
	public void setFormula(Formula formula) {
		this.formula = formula;
	}
	public boolean isTrapped() {
		return trapped;
	}
	public void setTrapped(boolean trapped) {
		this.trapped = trapped;
	}
	public double getTrapMin() {
		return trapMin;
	}
	public double getTrapMax() {
		return trapMax;
	}
	public void setTrapMin(double trapMin) {
		this.trapMin = trapMin;
	}
	public void setTrapMax(double trapMax) {
		this.trapMax = trapMax;
	}
	public double getCYCLE() {
		return CYCLE;
	}
}
