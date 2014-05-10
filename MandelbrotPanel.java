import java.awt.*;
/*
 * The MandelbrotPanel represents an instance of the
 * super() class Fractal. 
 */
@SuppressWarnings("serial")
public class MandelbrotPanel extends Fractal{
	public MandelbrotPanel(){
		super();
		setFormula(new MandelFormula(this));
	}

	public void paintComponent(Graphics g){
		super.paintComponents(g);
		width = this.getSize().getWidth();
		height = this.getSize().getHeight();
		/*Iteration cycle which walks through all the pixels in
		 *the panel.Depending on the chosen formula and if the
		 *panel has Orbit Traps enabled, the colour will be
		 *different*/
		for(double x=0; x<width;x++){
			for(double y=0;y<height;y++){
				current = new Complex(0,0);
				Complex c = getComplex(x,y);
				//calculating iterations based on the formula of the fractal
				double iterations = getFormula().calculate(c, current);
				paintFractal(g, isTrapped(), iterations, x, y);
			}

		}
	}
}
