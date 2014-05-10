import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class Main {
	public static void main(String[] args){
		MainFrame gui = new MainFrame("Coursework frame");
		gui.init();
	}
}
/*
 * The MainFrame is the application frame
 * of the GUI where we assemble all the components.
 * Its content pane is the MainPanel.
 */
@SuppressWarnings("serial")
class MainFrame extends JFrame{
	private MainPanel panel;
	public MainFrame(String name){

		super(name);
		panel = new MainPanel();
	}
	public void init(){
		setContentPane(panel);
		pack();
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
	}
}
/*
 * The MainPanel class is where we 
 * assemble all the components and
 * link them up to form the GUI
 */
@SuppressWarnings("serial")
class MainPanel extends JPanel{
	private JuliaInfoPanel iJul;
	private MandelInfoPanel iMan;
	private JuliaPanel julia;
	private MandelbrotPanel mandel;
	private GridBagConstraints c;
	private Map<String,Complex> fav;
	private Map<String,Formula> form;
	/*
	 * The MainPanel consists of 4 JPanels:
	 * JuliaPanel,MandelbrotPanel, JuliaInfoPanel
	 * and MandelInfoPanel. Each one of them is
	 * position in the GridBagLayout with 
	 * gridBag Constraints.
	 * The MainPanel contains two HashMaps
	 * which correspond to: the user Favourite
	 * Julia Fractals and the Formula picked
	 */
	public MainPanel(){
		fav = new HashMap<String,Complex>();
		form = new HashMap<String,Formula>();
		iJul = new JuliaInfoPanel();
		iMan = new MandelInfoPanel();
		julia = new JuliaPanel();
		mandel = new MandelbrotPanel();
		c= new GridBagConstraints();
		init();
	}
	/*
	 * Initialisation of the MainPanel
	 * and assembling of the layout.
	 */
	public void init(){
		addTextActionListener(iMan,mandel);
		addTextActionListener(iJul,julia);
		addIterationListener(iMan, mandel);
		addIterationListener(iJul, julia);
		addTrapListener(iJul,julia);
		addTrapListener(iMan,mandel);
		addFavouriteListener();
		addFavouriteBoxListener(iJul);
		populateFormulaBox();
		addFormulaBoxListener();
		addGlobalTrapActionListener();
		setLayout(new GridBagLayout());
		c.weightx= 0.0;
		c.weighty= 0.0;
		add(iMan,c);
		
		/*
		 * MouseAdapter which animates the 
		 * chosen(Mandelbrot) point in the JuliaPanel
		 */
		final MouseAdapter m = new MouseAdapter(){
			public void mouseMoved(MouseEvent e) {
				Complex c = julia.getComplex(e.getX(), e.getY());
				julia.setSelected(c);
				julia.repaint();

			}
		};
		mandel.addMouseMotionListener(m);
		mandel.addMouseListener(new MouseAdapter(){
			/*
			 * Anonymous MouseAdapter which removes
			 * the 'mouseover' animation in the MandelbrotPanel
			 * and sets the JuliaPoint to a value which could
			 * be changed with another moseClicked event on
			 * the MandelbrotPanel
			 */
			public void mouseClicked(MouseEvent e){
				mandel.removeMouseMotionListener(m);
				Complex c= julia.getComplex(e.getX(),e.getY());
				iMan.setSelected(c.getReal(),c.getImag());
				julia.setSelected(c);
				julia.repaint();
			}
		});
		ZoomListener mandelZoom = new ZoomListener(mandel,iMan);
		mandel.addMouseListener(mandelZoom);
		mandel.addMouseMotionListener(mandelZoom);
		mandel.addMouseWheelListener(mandelZoom);
		c.weightx = 1.0;
		c.weighty = 1.0;
		c.fill = GridBagConstraints.BOTH;
		add(mandel,c);

		c.gridy=1;
		c.fill = GridBagConstraints.NONE;
		c.weightx = 0.0;
		c.weighty = 0.0;
		add(iJul,c);
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 1.0;
		c.weighty = 1.0;
		ZoomListener juliaZoom = new ZoomListener(julia,iJul);
		julia.addMouseListener(juliaZoom);
		julia.addMouseMotionListener(juliaZoom);
		julia.addMouseWheelListener(juliaZoom);
		add(julia,c);
	}
	/*
	 * This method adds the formulas in the formula HashMap
	 */
	public void populateFormulaBox(){
		iMan.addFormulas("MandelFormula");
		form.put("MandelFormula", new MandelFormula(mandel));
		iMan.addFormulas("BurningShipFormula");
		form.put("BurningShipFormula", new BurningShipFormula(mandel));
		iMan.addFormulas("MyMandelFormula");
		form.put("MyMandelFormula", new MyMandelFormula(mandel));
	}
	/*
	 * This method adds an ActionListener to the favourite box
	 * to display the selected Favourite Julia Fractal.
	 */
	public void addFavouriteBoxListener(final JuliaInfoPanel info){
		info.getFavouriteBox().addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				if(e.getID() ==ActionEvent.ACTION_LAST){

					final String name  = info.getFavouriteBox().getSelectedItem().toString();
					if(name.equals("--Favourite fractals--"))
						return;
					JFrame pic = new JFrame(name);
					JuliaPanel jul = new JuliaPanel();
					jul.setSelected(fav.get(name));
					pic.setContentPane(jul);
					pic.pack();
					pic.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
					pic.setVisible(true);
				}
			}
		});
	}
	/*
	 * This method adds an ActionListener to the 
	 * formulaBox in the MandelInfoPanel to display
	 * the newly chosen formula from the JComboBox
	 */
	public void addFormulaBoxListener(){
		iMan.getFormulaBox().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				mandel.setFormula(form.get(iMan.getFormulaBox().getSelectedItem()));
				mandel.repaint();
			}
		});
	}
	/*
	 * This method adds a new Favourite to the JComboBox
	 * when the 'favourite' JButton has been pressed 
	 * with the name in the JTextField for favourites.
	 */
	public void addFavouriteListener(){
		iJul.getFavourite().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String name =iJul.getTextFieldName();
				if(name.equals("--Favourite fractals--"))
					return;
				Complex c = julia.getSelected();
				fav.put(name,c);
				julia.createImage(name);
				iJul.addFavourite(name);
			}
		});
	}
	/*
	 * This method adds an ActionListener for the iteration
	 * JTextField. When the JTextfield is changed, the fractal
	 * corresponds by repainting the panel with the new CYCLE
	 * being set.
	 */
	public void addIterationListener(final InfoPanel info, final Fractal panel){
		info.getIterations().addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				if(e.getID() == ActionEvent.ACTION_PERFORMED){
					try{
						panel.setIterations(Double.parseDouble(info.getIterations().getText()));
						panel.repaint();
					}catch(NumberFormatException e1){
						JOptionPane.showMessageDialog(getParent(), "Wrong input for iterations\n"+e1.getMessage());
					}
				}
			}
		});
	}
	/*
	 * This method adds an actionListener to each JTextField
	 * in the designated InfoPanel and links them to the 
	 * chosen Fractal panel . This allows the user to alter
	 * the range of the real and imaginary plane for the
	 * Fractal panel.
	 */
	public void addTextActionListener(final InfoPanel info,final Fractal panel){

		info.getMinImag().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(e.getID()==ActionEvent.ACTION_PERFORMED){
					try{
						panel.setMinImag(Double.parseDouble(info.getMinImag().getText()));
						panel.repaint();
					}catch(NumberFormatException e1){
						JOptionPane.showMessageDialog(getParent(), "The number you input was wrong,\n try again.");
					}
				}

			}
		});
		info.getMaxImag().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(e.getID()==ActionEvent.ACTION_PERFORMED){
					try{
						panel.setMaxImag(Double.parseDouble(info.getMaxImag().getText()));
						panel.repaint();
					}catch(NumberFormatException e1){
						JOptionPane.showMessageDialog(getParent(), "The number you input was wrong,\n try again.");
					}
				}

			}
		});
		info.getMinReal().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(e.getID()==ActionEvent.ACTION_PERFORMED){
					try{
						panel.setMinReal(Double.parseDouble(info.getMinReal().getText()));
						panel.repaint();
					}catch(NumberFormatException e1){
						JOptionPane.showMessageDialog(getParent(), "The number you input was wrong,\n try again.");
					}
				}

			}
		});
		info.getMaxReal().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(e.getID()==ActionEvent.ACTION_PERFORMED){
					try{
						panel.setMaxReal(Double.parseDouble(info.getMaxReal().getText()));
						panel.repaint();
					}catch(NumberFormatException e1){
						JOptionPane.showMessageDialog(getParent(), "The number you input was wrong,\n try again.");
					}
				}
			}
		});

	}
	/*
	 * This method adds an ActionListener to the JTextFields
	 * in the MandelInfoPanel which allows the user to change
	 * the Orbit Trap range.
	 */
	public void addGlobalTrapActionListener(){
		iMan.getTrapMin().addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(e.getID() == ActionEvent.ACTION_PERFORMED){
					try{
						mandel.setTrapMin(Double.parseDouble(iMan.getTrapMin().getText()));
						julia.setTrapMin(Double.parseDouble(iMan.getTrapMin().getText()));
						mandel.repaint();
						julia.repaint();
					}catch(NumberFormatException e1){
						JOptionPane.showMessageDialog(getParent(), "Error: Detected "+e1.getMessage());
					}
				}
				
			}
		});
		iMan.getTrapMax().addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(e.getID() == ActionEvent.ACTION_PERFORMED){
					try{
						mandel.setTrapMax(Double.parseDouble(iMan.getTrapMax().getText()));
						julia.setTrapMax(Double.parseDouble(iMan.getTrapMax().getText()));
						mandel.repaint();
						julia.repaint();
					}catch(NumberFormatException e1){
						JOptionPane.showMessageDialog(getParent(),  "Error: Detected "+e1.getMessage());
					}
				}
			}
		});
	}
	/*
	 * This method adds an ItemListener to the JCheckBoxes in each InfoPanel.
	 * By linking the Fractal panel with the InfoPanel JCheckBox this allows
	 * the user to select if he wants the Fractal panel to have Orbit Trap
	 * enabled or not.
	 */
	public void addTrapListener(final InfoPanel info, final Fractal panel){
		info.getTrap().addItemListener(new ItemListener(){
			@Override
			public void itemStateChanged(ItemEvent e) {

				panel.setTrapped(info.getTrap().isSelected());
				panel.repaint();
			}		
		});
	}
}

