import java.awt.*;
import javax.swing.*;
/*
 * The InfoPanel class represents an abstract class that
 * contains all the needed information linking to a 
 * fractal. This sets the basic layer of user-interactive
 * components that each fractal should link to.
 */
@SuppressWarnings("serial")
public abstract class InfoPanel extends JPanel{
	public  JTextField minReal,minImag,maxReal,maxImag,iterations,trapMin, trapMax;
	public JCheckBox trap;
	protected final Font TYPE = new Font("Times New Roman",Font.BOLD,16);	//Font used for labelling the components in a pleasant way
	public GridBagConstraints c;
	
	public InfoPanel(){
		iterations = new JTextField(""+Default.CYCLE);
		minReal =new  JTextField(""+Default.getMinReal(),4);
		minImag =new  JTextField(""+Default.getMinImag(),4);
		maxReal =new  JTextField(""+Default.getMaxReal(),4);
		maxImag =new  JTextField(""+Default.getMaxImag(),4);
		trapMin = new JTextField(""+Default.getTrapMin(),4);
		trapMax = new JTextField(""+Default.getTrapMax(),4);
		trap = new JCheckBox("Orbit Trap");
		c= new GridBagConstraints();
	}
	/*abstract init method for each fractal to implement
	  on its own how it should initialise*/
	public abstract void init();
	public JTextField getIterations(){
		return iterations;
	}
	public JTextField getMinReal() {
		return minReal;
	}
	public JTextField getMinImag() {
		return minImag;
	}
	public JTextField getMaxReal() {
		return maxReal;
	}
	public JTextField getMaxImag() {
		return maxImag;
	}
	public void setMinReal(double number) {
		minReal.setText(""+number);
	}
	public void setMinImag(double number) {
		minImag.setText(""+number);
	}
	public void setMaxReal(double number) {
		maxReal.setText(""+number);
	}
	public void setMaxImag(double number) {
		maxImag.setText(""+number);
	}
	public JCheckBox getTrap() {
		return trap;
	}
	public void setTrap(JCheckBox trap) {
		this.trap = trap;
	}
	public JTextField getTrapMin() {
		return trapMin;
	}
	public JTextField getTrapMax() {
		return trapMax;
	}
	public void setTrapMin(JTextField trapMin) {
		this.trapMin = trapMin;
	}
	public void setTrapMax(JTextField trapMax) {
		this.trapMax = trapMax;
	}
	
}
