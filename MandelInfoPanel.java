import javax.swing.*;
import java.awt.*;
/*
 * The MandelInfoPanel class is an extension of the InfoPanel.
 * It represents the information which corresponds/navigates
 * the MandelbrotPanel 
 */
@SuppressWarnings("serial")
public class MandelInfoPanel extends InfoPanel{
	public JTextField selected;
	public JComboBox<String> formulaBox;
	public MandelInfoPanel(){
		super();
		selected = new JTextField("",8);
		formulaBox = new JComboBox<String>();
		init();
	}
	/*
	 * This method adds formula names to the JComboBox
	 * relating to the formulas.
	 */
	public void addFormulas(String form){
		formulaBox.addItem(form);
	}
	/*
	 * This method sets the selected complex points
	 * which correspond to the picked JuliaPanel state.
	 */
	public void setSelected(double x, double y){
		selected.setText(Default.round(x)+","+Default.round(y));
	}
	/*
	 * This method initialises the panel and sets
	 * the position of the components in the panel
	 * through GridBagConstraints
	 */
	public void init(){
		selected.setEditable(false);
		setLayout(new GridBagLayout());
		setPreferredSize(new Dimension(400,300));
		c.anchor = GridBagConstraints.PAGE_START;

		
		add(formulaBox,c);
		c.weightx=0.0;
		c.weighty=0.0;
		
		c.gridy=1;
		c.anchor= GridBagConstraints.WEST;
		JLabel real = new JLabel("R:");
		real.setFont(TYPE);
		add(real,c);
		c.anchor=GridBagConstraints.EAST;
		add(minReal,c);
		add(new JLabel("to"),c);
		add(maxReal,c);
		
		c.gridy=2;
		c.anchor= GridBagConstraints.WEST;
		JLabel imag = new JLabel("I:");
		imag.setFont(TYPE);
		add(imag,c);
		c.anchor=GridBagConstraints.EAST;
		add(minImag,c);
		add(new JLabel("to"),c);
		add(maxImag,c);
		
		c.gridy=3;
		add(selected,c);
		
		c.gridy=4;

		add(new JLabel("Iterations:"),c);
		c.gridx = GridBagConstraints.LINE_END;
		add(iterations,c);
		
		c.anchor = GridBagConstraints.CENTER;
		c.gridx = GridBagConstraints.NONE;
		c.gridy= 5;
		add(trap,c);
		c.gridy=6;

		add(new JLabel("Global Trap range:"),c);
		c.gridx=1;
		add(trapMin,c);
		c.gridx=2;
		add(new JLabel("to"),c);
		c.gridx=3;
		add(trapMax,c);
		
	}
	public JComboBox<String> getFormulaBox() {
		return formulaBox;
	}
}
