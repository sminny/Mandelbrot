import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;
/*
 * The JuliaPanel represents an instance of the
 * super() class Fractal. 
 */
@SuppressWarnings("serial")
public class JuliaPanel extends Fractal{

	private BufferedImage bi;
	private Complex selected;
	public JuliaPanel(){
		super();
		selected = new Complex(Default.getMinReal(),Default.getMinImag());	//default value for the JuliaPanel to start with
		setFormula(new JuliaFormula(this));						
	}
	/*
	 * Constructor of a JuliaPanel, which has a designated 
	 * complex number which we want to display from.
	 */
	public JuliaPanel(Complex selected){
		this();
		this.selected = selected;
	}
	/*
	 * This method allows the JuliaPanel to be altered
	 * with the selected Complex number being changed.
	 */
	public void setSelected(Complex c){
		selected=c;
	}

	public void paintComponent(Graphics g){
		width = this.getSize().getWidth();
		height = this.getSize().getHeight();

		Complex c = selected;
		for(double x=0;x<width;x++){
			for(double y=0;y<height;y++){
				current = getComplex(x,y);
				//calculating iterations based on the formula of the fractal
				double iterations= getFormula().calculate(c,current);
				paintFractal(g, isTrapped(), iterations, x, y);
			}
		}
	}
	/*
	 * This method creates an image, with the designated name,
	 * which was added as a favourite. Stores the image in the
	 * source directory.
	 */
	public void createImage(String name){
		bi = new BufferedImage((int)width,(int)height, BufferedImage.TYPE_INT_ARGB);
		paint(bi.getGraphics());
		try {
			if(name.equals(""))
				throw new Exception();
			ImageIO.write(bi,"png",new File(name+".png"));
		} catch (IOException e) {
			JOptionPane.showMessageDialog(getParent(), "Something went wrong while writing the data");
		} catch (Exception e){
			JOptionPane.showMessageDialog(getParent(), "Please choose a name for the picture\nbefore you save it");
		}
	}

	public Complex getSelected() {
		return this.selected;
	}

}
