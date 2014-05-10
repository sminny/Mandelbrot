/*
 * Abstract class which represents Formulas
 * for different fractal generating based
 * on iterations.
 */
public abstract class Formula {

	protected Fractal panel;
	public Formula(Fractal panel){

		this.panel = panel;
	}
	/*
	 * The calculate method calculates the iterations of each
	 * complex number in the plane which we pass by taking the
	 * current point and applying the needed calculations to
	 * process it. The method checks if the OrbitTrap is enabled
	 * or not to generate the fractal.
	 */
	public abstract double calculate(Complex c,Complex current);
}
/*
 * MandelFormula is an extension of the abstract class
 * Formula. This class represents the calculations needed
 * to derive at the required fractal.
 */
class MandelFormula extends Formula{

	public MandelFormula(Fractal panel){
		super(panel);
	}
	/*
	 * The calculation corresponds to Z(i+1) =Z(i)*Z(i) + c , Z(0) = c
	 */
	public double calculate(Complex c , Complex current) {
		double iterations=0;
		current.add(c);
		if ( panel.isTrapped() == false){
			while(iterations <panel.getCYCLE()   && current.modulusSquare() < 4){
				current.square();
				current.add(c);
				iterations++;
			}
			return iterations;
		}
		while(iterations <panel.getCYCLE()   && current.modulusSquare() < 4){
			current.square();
			current.add(c);
			if(current.getReal()<panel.getTrapMax() && current.getReal() > panel.getTrapMin())
				break;
			if(current.getImag()<panel.getTrapMax() && current.getImag() > panel.getTrapMin())
				break;
			iterations++;
		}
		
		return iterations;
		
	}
}
/*
 * JuliaFormula is an extension of the abstract class
 * Formula. This class represents the calculations needed
 * to derive at the required fractal.
 */
class JuliaFormula extends Formula{
	public JuliaFormula(Fractal panel){
		super(panel);
	}
	/*
	 * The calculation corresponds to Z(i+1) =Z(i)*Z(i) + c , Z(0) = d
	 */
	public double calculate(Complex c, Complex current) {
		double iterations=0;
		if(panel.isTrapped() == false){
			while(iterations <panel.CYCLE   && current.modulusSquare() < 4){
				current.square();
				current.add(c);
				iterations++;
			}
			return iterations;
		}
		while(iterations <panel.CYCLE   && current.modulusSquare() < 4){

			current.square();
			current.add(c);
			if(current.getReal()<panel.getTrapMax() && current.getReal() > panel.getTrapMin())
				break;
			if(current.getImag()<panel.getTrapMax() && current.getImag() > panel.getTrapMin())
				break;
			iterations++;
		}
		return iterations;
	}
}
/*
 * BurningShipFormula is an extension of the abstract class
 * Formula. This class represents the calculations needed
 * to derive at the required fractal.
 */
class BurningShipFormula extends Formula{

	public BurningShipFormula(Fractal panel){
		super(panel);
	}
	/*
	 * The calculation corresponds to Z(i+1) =(|Re Z(i)|*i|Im Z(i)|)^2 + c , Z(0) = c
	 */
	public double calculate(Complex c, Complex current) {
		double iterations=0;
		current.add(c);
		if(panel.isTrapped() == false){

			while(iterations <panel.CYCLE   && current.modulusSquare() < 4){
				current.setReal(Math.abs(current.getReal()));
				current.setImag(Math.abs(current.getImag()));
				current.square();
				current.add(c);
				iterations++;
			}
			return iterations;
		}
		while(iterations <panel.CYCLE   && current.modulusSquare() < 4){
			current.setReal(Math.abs(current.getReal()));
			current.setImag(Math.abs(current.getImag()));
			current.square();
			current.add(c);
			if(current.getReal()<panel.getTrapMax() && current.getReal() > panel.getTrapMin())
				break;
			if(current.getImag()<panel.getTrapMax() && current.getImag() > panel.getTrapMin())
				break;
			iterations++;
		}
		return iterations;
	}
}
/*
 * MyMandelFormula is an extension of the abstract class
 * Formula. This class represents the calculations needed
 * to derive at the required fractal.
 */
class MyMandelFormula extends Formula{
	public MyMandelFormula(Fractal panel){
		super(panel);
	}
	/*
	 * The calculation corresponds to Z(i+1) =(Z(i)*Z(i))^2 + c , Z(0) = c
	 */
	public double calculate(Complex c, Complex current) {
		double iterations=0;
		current.add(c);
		if(panel.isTrapped() == false){
			while(iterations <panel.CYCLE   && current.modulusSquare() < 4){
				current.square();
				current.square();
				current.add(c);
				iterations++;
			}
			return iterations;
		}
		while(iterations <panel.CYCLE   && current.modulusSquare() < 4){

			current.square();
			current.square();
			current.add(c);
			if(current.getReal()<panel.getTrapMax() && current.getReal() > panel.getTrapMin())
				break;
			if(current.getImag()<panel.getTrapMax() && current.getImag() > panel.getTrapMin())
				break;
			iterations++;
		}
		return iterations;

	}

}
