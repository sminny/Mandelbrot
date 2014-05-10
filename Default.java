
/*
 * This class represents the Default values which the
 * Fractals will have when the program initialises first.
 */
public class Default {
	public final static double minReal = -2;
	public final static double maxReal = 2;
	public final static double minImag = -1.6;
	public final static double maxImag = 1.6;
	
	public final static double trapMin =-0.075;
	public final static double trapMax = 0.075;

	public final static float H = 1.0f;
	public final static float S = 1.0f;
	public final static float B = 0.5f;
	public static double CYCLE = 100;
	/*
	 * This method is accessed externally by other classes
	 * to round the displayed calculations for a much nicer
	 * representation of the used variables.
	 */
	public static double round(double num){
		num = (double)Math.round(num*100)/100;
		return num; 
	}

	public static double getMinReal() {
		return minReal;
	}
	public static double getMaxReal() {
		return maxReal;
	}
	public static double getMinImag() {
		return minImag;
	}
	public static double getMaxImag() {
		return maxImag;
	}
	public static double getTrapMin() {
		return trapMin;
	}
	public static double getTrapMax() {
		return trapMax;
	}
	public static float getH() {
		return H;
	}
	public static float getS() {
		return S;
	}
	public static float getB() {
		return B;
	}
	public static double getCYCLE() {
		return CYCLE;
	}
	public static void setCYCLE(double cYCLE) {
		CYCLE = cYCLE;
	}
}	
