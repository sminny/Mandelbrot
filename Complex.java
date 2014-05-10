/*
 * This class represents a Complex number consisting
 * of a real and imaginary part.
 */
public class Complex {
	double real;
	double imag;
	
	public Complex(double real, double imag){
		this.real = real;
		this.imag = imag;
	}
	/*
	 * The method square() is used to update the
	 * value of the complex number whenever it is
	 * squared
	 */
	public void square(){
		double tmpReal = real*real - imag*imag;
		double tmpImag = 2*real*imag;
		real= tmpReal;
		imag = tmpImag;
	}
	/*
	 * This method is called in the fractals in order
	 * to check the bounds for drawing in the Fractal
	 * panels.
	 */
	public double modulusSquare(){
		double square= real*real + imag*imag;
		return square;
	}
	/*
	 * This method is used when we want to add two Complex
	 * numbers . It adds the real and the imaginary parts
	 * of the two Complex numbers.
	 */
	public void add(Complex d){
		real= real+ d.getReal();
		imag= imag + d.getImag();
	}

	public void setReal(double real) {this.real = real;}
	public void setImag(double imag) {this.imag = imag;}
	public double getReal(){return real;}
	public double getImag(){return imag;}
}

