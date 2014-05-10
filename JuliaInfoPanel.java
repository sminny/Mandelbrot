import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

/*
 * The JuliaInfoPanel class is an extension of the InfoPanel.
 * It represents the information which corresponds/navigates
 * the JuliaPanel .
 */
import javax.swing.*;
@SuppressWarnings("serial")
public class JuliaInfoPanel extends InfoPanel{
	private JTextField name;
	private JButton favourite;
	private JComboBox<String> favouriteBox;
	public JuliaInfoPanel(){
		super();
		name = new JTextField(10);
		favourite= new JButton("Add to Favourites");
		favouriteBox = new JComboBox<String>();
		init();
	}
	/* 
	 * This method adds the Favourite picture 
	 * names to the JComboBox .
	 */
	public void addFavourite(String name){
		favouriteBox.addItem(name);
	}
	/*
	 * This method initialises the panel and sets
	 * the position of the components in the panel
	 * through GridBagConstraints
	 */
	
	public void init(){
		addFavourite("--Favourite fractals--");
		setLayout(new GridBagLayout());
		setPreferredSize(new Dimension(400,300));
		c.anchor = GridBagConstraints.PAGE_START;
		JLabel mandelbrot = new JLabel("Julia:");
		mandelbrot.setFont(TYPE);
		add(mandelbrot,c);
		
		c.weightx=0.0;
		c.weighty=0.0;
		c.gridy=1;
		c.anchor=GridBagConstraints.WEST;
		JLabel real = new JLabel("R:");
		real.setFont(TYPE);
		add(real,c);
		c.anchor= GridBagConstraints.EAST;
		add(minReal,c);
		add(new JLabel("to"),c);
		add(maxReal,c);
		
		c.gridy=2;
		c.anchor=GridBagConstraints.WEST;
		JLabel imag = new JLabel("I:");
		imag.setFont(TYPE);
		add(imag,c);
		c.anchor= GridBagConstraints.EAST;
		add(minImag,c);
		add(new JLabel("to"),c);
		add(maxImag,c);
		
		c.gridy=3;
		add(name,c);
		add(favourite,c);
		c.gridy=4;
		add(favouriteBox,c);
		
		c.gridy=5;
		c.anchor = GridBagConstraints.EAST;
		add(new JLabel("Iterations:"),c);
		add(iterations,c);
		
		c.gridy=6;
		add(trap,c);
	}
	public JButton getFavourite() {
		return favourite;
	}
	public JComboBox<String> getFavouriteBox() {
		return favouriteBox;
	}
	public void setFavourite(JButton favourite) {
		this.favourite = favourite;
	}
	public String getTextFieldName(){
		return name.getText();
	}
	public void setFavouriteBox(JComboBox<String> favouriteBox) {
		this.favouriteBox = favouriteBox;
	}
	
}