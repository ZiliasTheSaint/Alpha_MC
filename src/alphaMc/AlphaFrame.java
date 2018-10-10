package alphaMc;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToolBar;

import org.apache.pdfbox.pdmodel.PDDocument;

import danfulea.utils.ExampleFileFilter;
import danfulea.utils.ScanDiskLFGui;
import danfulea.math.Convertor;
import danfulea.utils.MessageRetriever;
import danfulea.utils.PDFRenderer;
import danfulea.phys.Alpha_MC;
import danfulea.utils.FrameUtilities;

/**
 * The graphical user interface (GUI) for Alpha_MC class (see phys package in danfulea.jar). <br>
 * 
 * @author Dan Fulea, 05 APR. 2011
 * 
 */
public class AlphaFrame extends JFrame implements Runnable, ActionListener,
		MessageRetriever {

	private static final long serialVersionUID = 1L;
	private final Dimension PREFERRED_SIZE = new Dimension(950, 500);
	private final Dimension sizeCb = new Dimension(100, 20);
	//protected Color bkgColor = new Color(112, 178, 136, 255);
	private Window parent = null;
	private boolean standalone = true;
	public static Color bkgColor = new Color(230, 255, 210, 255);// Linux mint
	// green
	// alike
	public static Color foreColor = Color.black;// Color.white;
	public static Color textAreaBkgColor = Color.white;// Color.black;
	public static Color textAreaForeColor = Color.black;// Color.yellow;
	public static boolean showLAF = true;

	private JLabel statusL = new JLabel("Waiting...");
	private static final String BASE_RESOURCE_CLASS = "alphaMc.resources.AlphaResources";
	protected ResourceBundle resources;
	private static final String EXIT_COMMAND = "EXIT";
	private static final String ABOUT_COMMAND = "ABOUT";
	private static final String RUN_COMMAND = "RUN";
	private static final String KILL_COMMAND = "KILL";
	private static final String PRINT_COMMAND = "PRINT";
	private static final String LOOKANDFEEL_COMMAND = "LOOKANDFEEL";

	private JButton runB, killB, printB;
	protected JTextArea textArea = new JTextArea();
	private JTextField sourceToDetectorDistanceTf = new JTextField(5);
	private JTextField sourceDiameterTf = new JTextField(5);
	private JTextField detectorDiameterTf = new JTextField(5);
	@SuppressWarnings("rawtypes")
	private JComboBox numberOfHystoriesCb;
	private double sourceToDetectorDistance = 7.0;// cm
	private double sourceDiameter = 1.0;// cm
	private double detectorDiameter = 4.4;// cm
	private int numberOfHystories = 1000;

	private PleaseWait pw;
	private volatile Thread simTh;
	private boolean stopAppend = false;
	protected String outFilename = null;

	/**
	 * Constructor... setting up the application GUI!
	 */
	public AlphaFrame() {
		// TODO Auto-generated constructor stub
		// super("Alpha_MC");

		resources = ResourceBundle.getBundle(BASE_RESOURCE_CLASS);
		this.setTitle(this.resources.getString("Application.NAME"));//("Application.name"));

		// the key to force attemptExit() method on close!!
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				attemptExit();
			}
		});

		JMenuBar menuBar = createMenuBar(resources);
		setJMenuBar(menuBar);

		createGUI();
		setDefaultLookAndFeelDecorated(true);
		FrameUtilities.createImageIcon(
				this.resources.getString("form.icon.url"), this);

		FrameUtilities.centerFrameOnScreen(this);
		setVisible(true);
	}
	
	public AlphaFrame(Window frame) {
		this();
		this.parent = frame;
		standalone = false;
	}

	/**
	 * Setting up the frame size.
	 */
	public Dimension getPreferredSize() {
		return PREFERRED_SIZE;
	}

	/**
	 * GUI creation.
	 */
	private void createGUI() {
		JPanel content = new JPanel(new BorderLayout());
		JPanel mainPanel = createMainPanel();
		content.add(mainPanel);
		// Create the statusbar.
		JToolBar statusBar = new JToolBar();
		statusBar.setFloatable(false);
		initStatusBar(statusBar);
		content.add(statusBar, BorderLayout.PAGE_END);

		setContentPane(new JScrollPane(content));
		content.setOpaque(true); // content panes must be opaque
		pack();
	}

	/**
	 * Creates the frame main panel.
	 * 
	 * @return the main Panel
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private JPanel createMainPanel() {
		String[] sarray = (String[]) resources.getObject("numberOfHystoriesCb");
		numberOfHystoriesCb = new JComboBox(sarray);
		String s = sarray[1];
		numberOfHystoriesCb.setSelectedItem((Object) s);
		numberOfHystoriesCb.setMaximumRowCount(5);
		numberOfHystoriesCb.setPreferredSize(sizeCb);

		String buttonName = "";
		String buttonToolTip = "";
		String buttonIconName = "";
		
		buttonName = resources.getString("runB");
		buttonToolTip = resources.getString("runB.toolTip");
		buttonIconName = resources.getString("img.set");
		runB = FrameUtilities.makeButton(buttonIconName, RUN_COMMAND,
				buttonToolTip, buttonName, this, this);
		//runB = new JButton(resources.getString("runB"));
		Character mnemonic = (Character) resources.getObject("runB.mnemonic");
		runB.setMnemonic(mnemonic.charValue());
		//runB.setActionCommand(RUN_COMMAND);
		//runB.addActionListener(this);
		//runB.setToolTipText(resources.getString("runB.toolTip"));

		buttonName = resources.getString("killB");
		buttonToolTip = resources.getString("killB.toolTip");
		buttonIconName = resources.getString("img.close");
		killB = FrameUtilities.makeButton(buttonIconName, KILL_COMMAND,
				buttonToolTip, buttonName, this, this);
		//killB = new JButton(resources.getString("killB"));
		mnemonic = (Character) resources.getObject("killB.mnemonic");
		killB.setMnemonic(mnemonic.charValue());
		//killB.setActionCommand(KILL_COMMAND);
		//killB.addActionListener(this);
		//killB.setToolTipText(resources.getString("killB.toolTip"));

		buttonName = resources.getString("printB");
		buttonToolTip = resources.getString("printB.toolTip");
		buttonIconName = resources.getString("img.report");
		printB = FrameUtilities.makeButton(buttonIconName, PRINT_COMMAND,
				buttonToolTip, buttonName, this, this);
		//printB = new JButton(resources.getString("printB"));
		mnemonic = (Character) resources.getObject("printB.mnemonic");
		printB.setMnemonic(mnemonic.charValue());
		//printB.setActionCommand(PRINT_COMMAND);
		//printB.addActionListener(this);
		//printB.setToolTipText(resources.getString("printB.toolTip"));

		textArea.setCaretPosition(0);
		textArea.setEditable(false);
		textArea.setText("");
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);

		JPanel resultP = new JPanel(new BorderLayout());
		resultP.add(new JScrollPane(textArea), BorderLayout.CENTER);
		resultP.setBackground(bkgColor);

		JPanel p1P = new JPanel();
		p1P.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 2));
		JLabel label = new JLabel();
		label.setText(resources.getString("numberOfHystoriesLb"));
		p1P.add(label);
		p1P.add(numberOfHystoriesCb);
		p1P.setBackground(bkgColor);

		JPanel p2P = new JPanel();
		p2P.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 2));
		label = new JLabel();
		label.setText(resources.getString("sourceToDetectorDistanceLb"));
		p2P.add(label);
		p2P.add(sourceToDetectorDistanceTf);
		sourceToDetectorDistanceTf.setText("" + sourceToDetectorDistance);
		p2P.setBackground(bkgColor);

		JPanel p3P = new JPanel();
		p3P.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 2));
		label = new JLabel();
		label.setText(resources.getString("sourceDiameterLb"));
		p3P.add(label);
		p3P.add(sourceDiameterTf);
		sourceDiameterTf.setText("" + sourceDiameter);
		label = new JLabel();
		label.setText(resources.getString("detectorDiameterLb"));
		p3P.add(label);
		p3P.add(detectorDiameterTf);
		detectorDiameterTf.setText("" + detectorDiameter);
		p3P.setBackground(bkgColor);

		JPanel p4P = new JPanel();
		p4P.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 2));
		p4P.add(runB);
		p4P.add(killB);
		p4P.add(printB);
		p4P.setBackground(bkgColor);

		JPanel p5 = new JPanel();
		BoxLayout bld = new BoxLayout(p5, BoxLayout.Y_AXIS);
		p5.setLayout(bld);
		p5.add(p1P, null);
		p5.add(p2P, null);
		p5.add(p3P, null);
		p5.add(p4P, null);
		p5.setBackground(bkgColor);

		JPanel mainP = new JPanel(new BorderLayout());
		mainP.add(p5, BorderLayout.NORTH);
		mainP.add(resultP, BorderLayout.CENTER);
		mainP.setBackground(bkgColor);
		return mainP;
	}

	/**
	 * Setting up the menu bar.
	 * 
	 * @param resources resources
	 * @return the menu bar
	 */
	private JMenuBar createMenuBar(ResourceBundle resources) {
		ImageIcon img;
		String imageName = "";
		// create the menus
		JMenuBar menuBar = new JMenuBar();

		String label;
		Character mnemonic;

		// first the file menu
		label = resources.getString("menu.file");
		mnemonic = (Character) resources.getObject("menu.file.mnemonic");
		JMenu fileMenu = new JMenu(label, true);
		fileMenu.setMnemonic(mnemonic.charValue());

		imageName = resources.getString("img.set");
		img = FrameUtilities.getImageIcon(imageName, this);
		label = resources.getString("runB");
		mnemonic = (Character) resources.getObject("runB.mnemonic");
		JMenuItem runItem = new JMenuItem(label, mnemonic.charValue());
		runItem.setActionCommand(RUN_COMMAND);
		runItem.setIcon(img);
		runItem.addActionListener(this);
		fileMenu.add(runItem);

		imageName = resources.getString("img.close");
		img = FrameUtilities.getImageIcon(imageName, this);
		label = resources.getString("killB");
		mnemonic = (Character) resources.getObject("killB.mnemonic");
		JMenuItem killItem = new JMenuItem(label, mnemonic.charValue());
		killItem.setActionCommand(KILL_COMMAND);
		killItem.setIcon(img);
		killItem.addActionListener(this);
		fileMenu.add(killItem);

		imageName = resources.getString("img.report");
		img = FrameUtilities.getImageIcon(imageName, this);
		label = resources.getString("printB");
		mnemonic = (Character) resources.getObject("printB.mnemonic");
		JMenuItem printItem = new JMenuItem(label, mnemonic.charValue());
		printItem.setActionCommand(PRINT_COMMAND);
		printItem.setIcon(img);
		printItem.addActionListener(this);
		fileMenu.add(printItem);
		fileMenu.addSeparator();

		imageName = resources.getString("img.close");
		img = FrameUtilities.getImageIcon(imageName, this);
		label = resources.getString("menu.file.exit");
		mnemonic = (Character) resources.getObject("menu.file.exit.mnemonic");
		JMenuItem exitItem = new JMenuItem(label, mnemonic.charValue());
		exitItem.setActionCommand(EXIT_COMMAND);
		exitItem.setIcon(img);
		exitItem.addActionListener(this);
		fileMenu.add(exitItem);

		// then the help menu
		label = resources.getString("menu.help");
		mnemonic = (Character) resources.getObject("menu.help.mnemonic");
		JMenu helpMenu = new JMenu(label);
		helpMenu.setMnemonic(mnemonic.charValue());

		label = resources.getString("menu.help.LF");
		mnemonic = (Character) resources.getObject("menu.help.LF.mnemonic");
		JMenuItem lfItem = new JMenuItem(label, mnemonic.charValue());
		lfItem.setActionCommand(LOOKANDFEEL_COMMAND);
		lfItem.addActionListener(this);
		lfItem.setToolTipText(resources.getString("menu.help.LF.toolTip"));

		if (showLAF) {
			helpMenu.add(lfItem);
			helpMenu.addSeparator();
		}
		
		imageName = resources.getString("img.about");
		img = FrameUtilities.getImageIcon(imageName, this);
		label = resources.getString("menu.help.about");
		mnemonic = (Character) resources.getObject("menu.help.about.mnemonic");
		JMenuItem aboutItem = new JMenuItem(label, mnemonic.charValue());
		aboutItem.setActionCommand(ABOUT_COMMAND);
		aboutItem.setIcon(img);
		aboutItem.addActionListener(this);
		helpMenu.add(aboutItem);

		// finally, glue together the menu and return it
		menuBar.add(fileMenu);
		menuBar.add(helpMenu);

		return menuBar;
	}

	/**
	 * Setting up the status bar.
	 * 
	 * @param toolBar toolBar
	 */
	private void initStatusBar(JToolBar toolBar) {
		JPanel toolP = new JPanel();
		toolP.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 1));

		toolP.add(statusL);
		toolBar.add(toolP);
		statusL.setText(resources.getString("status.wait"));
	}

	/**
	 * Setting up actions!
	 * @param arg0 the commands
	 */
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		String command = arg0.getActionCommand();
		if (command.equals(ABOUT_COMMAND)) {
			about();
		} else if (command.equals(EXIT_COMMAND)) {
			attemptExit();
		} else if (command.equals(RUN_COMMAND)) {
			startSimulation();
		} else if (command.equals(KILL_COMMAND)) {
			stopAppend = true;
			stopSimulation();
		} else if (command.equals(PRINT_COMMAND)) {
			printReport();
		} else if (command.equals(LOOKANDFEEL_COMMAND)) {
			lookAndFeel();
		} 
	}

	/**
	 * Change look and feel can be done done here. Also display some gadgets.
	 */
	private void lookAndFeel(){
		setVisible(false);// setEnabled(false);
		new ScanDiskLFGui(this);
	}
	/**
	 * Shows the about window!
	 */
	private void about() {
		new AboutFrame(this);
	}

	/**
	 * Application close!
	 */
	private void attemptExit() {
		//String title = resources.getString("dialog.exit.title");
		//String message = resources.getString("dialog.exit.message");

		//Object[] options = (Object[]) resources
		//		.getObject("dialog.exit.buttons");
		//int result = JOptionPane.showOptionDialog(this, message, title,
		//		JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,
		//		options, options[0]);
		//if (result == JOptionPane.YES_OPTION) {
		//	dispose();
		//	System.exit(0);
		//}
		if (standalone) {
			dispose();
			System.exit(0);
		} else {
			parent.setVisible(true);
			dispose();
		}
	}

	/**
	 * Start Monte-Carlo simulation.
	 */
	private void startSimulation() {
		if (simTh == null) {
			simTh = new Thread(this);
			simTh.start();// Allow one simulation at time!
		}
		// Do nothing if simulation is in progress and run button is hit again!
	}

	/**
	 * Performing Monte-Carlo calculation.
	 */
	private void performEfficiencyCalculation() {
		textArea.selectAll();
		textArea.replaceSelection("");
		boolean neg = false;

		numberOfHystories = Convertor.stringToInt((String) numberOfHystoriesCb
				.getSelectedItem());
		try {
			sourceToDetectorDistance = Convertor
					.stringToDouble(sourceToDetectorDistanceTf.getText());
			if (sourceToDetectorDistance < 0)
				neg = true;

			sourceDiameter = Convertor.stringToDouble(sourceDiameterTf
					.getText());
			if (sourceDiameter < 0)
				neg = true;

			detectorDiameter = Convertor.stringToDouble(detectorDiameterTf
					.getText());
			if (detectorDiameter < 0)
				neg = true;
		} catch (Exception exc) {
			String s = resources.getString("number.error");
			textArea.append(s);
			return;
		}
		if (neg) {
			String s = resources.getString("number.error");
			textArea.append(s);
			return;
		}

		String label = resources.getString("pleaseWait.label");
		pw = new PleaseWait(label);
		pw.startAnimation();

		label = resources.getString("status.computing");
		statusL.setText(label);

		Alpha_MC amc = new Alpha_MC(sourceToDetectorDistance, sourceDiameter,
				detectorDiameter, numberOfHystories);
		if (Alpha_MC.STOPSIMULATION) {
			return;// force exit!
		}
		double eff = amc.getEffp();// %
		double unc = amc.getErreff();
		double solidAngle = amc.getSolidAngle();

		NumberFormat nf = NumberFormat.getInstance(Locale.US);
		nf.setMinimumFractionDigits(5);
		nf.setMaximumFractionDigits(5);
		nf.setGroupingUsed(false);

		// String pattern = "0.###E0";
		// DecimalFormatSymbols dfs = new DecimalFormatSymbols(Locale.US);
		// DecimalFormat nff = new DecimalFormat(pattern, dfs);
		String str = "";
		str = str + resources.getString("alpha.settings") + "\n";
		str = str + resources.getString("numberOfHystoriesLb")
				+ numberOfHystories + "\n";
		str = str + resources.getString("sourceToDetectorDistanceLb")
				+ sourceToDetectorDistance + "\n";
		str = str + resources.getString("sourceDiameterLb") + sourceDiameter
				+ "\n";
		str = str + resources.getString("detectorDiameterLb")
				+ detectorDiameter + "\n";

		str = str + "\n";// line empty

		str = str + resources.getString("alpha.results") + "\n";

		str = str + resources.getString("text.alphaEff") + nf.format(eff)
				+ resources.getString("text.alphaEff.err") + nf.format(unc)
				+ "\n";
		str = str + resources.getString("text.solidAngle")
				+ nf.format(solidAngle)
				+ resources.getString("text.solidAngle.err") + nf.format(unc)
				+ "\n";
		textArea.append(str);

		stopSimulation();
		label = resources.getString("status.done");
		statusL.setText(label);

		amc = null;// just in case marked all amc variables and those used from
					// amc as disposable!
		// this is not really necesary since this method returns!!
	}

	/**
	 * run method ..current thread is initiated!
	 */
	public void run() {
		Thread.currentThread().setPriority(Thread.NORM_PRIORITY);
		simTh.setPriority(Thread.NORM_PRIORITY);
		performEfficiencyCalculation();
	}

	/**
	 * Stop Monte-Carlo simulation.
	 */
	private void stopSimulation() {
		/*
		 * // TESTING // String fromDir = //
		 * "/home/dane/Downloads";///Logice";//System.getProperty("user.dir");
		 * String fromDir = "d:\\inscriptionat\\test";//
		 * /Logice";//System.getProperty("user.dir"); // String
		 * toDir="/home/dane/temp"; String toDir = "d:\\temp"; //String fromFile
		 * = fromDir + System.getProperty("file.separator") // +
		 * "test_alphaEff.java"; //String toFile = toDir +
		 * System.getProperty("file.separator") // + "test_alphaEff.java";
		 * 
		 * // String[] s=FileOperation.getDirs(new File(currentDir)); for (int
		 * // i=0;i<s.length;i++){ System.out.println(s[i]); } //
		 * s=FileOperation.getFiles(new File(currentDir)); for (int //
		 * i=0;i<s.length;i++){ System.out.println(s[i]); }
		 * 
		 * try { FileOperation.mr = this; // FileOperation.copyFile(fromFile,
		 * toFile); // FileOperation.copyDir(fromDir, toDir);
		 * FileOperation.copyWholeDir(fromDir, toDir); } catch (Exception ex) {
		 * ex.printStackTrace(); } // END TESTING=>WORKS!
		 */
		if (simTh == null) {
			stopAppend = false;// press kill button but simulation never
								// started!
			return;
		}
		simTh = null;
		if (stopAppend) {// kill button was pressed!
			Alpha_MC.STOPSIMULATION = true;// tell to stop simulation loop
											// immediatly!
			textArea.append(resources.getString("text.simulation.stop") + "\n");
			stopAppend = false;
			String label = resources.getString("status.done");
			statusL.setText(label);
		}
		if (pw != null) {
			pw.stopAnimation();
			pw = null;
		}
	}

	/**
	 * Printing messages via interface!
	 */
	public void printSequence(String s) {
		textArea.append(s + "\n");
	}

	/**
	 * Printing report
	 */
	private void printReport() {
		String FILESEPARATOR = System.getProperty("file.separator");
		String currentDir = System.getProperty("user.dir");
		File infile = null;

		String ext = resources.getString("file.extension");
		String pct = ".";
		String description = resources.getString("file.description");
		ExampleFileFilter eff = new ExampleFileFilter(ext, description);

		String myDir = currentDir + FILESEPARATOR;//
		// File select
		JFileChooser chooser = new JFileChooser(myDir);
		chooser.addChoosableFileFilter(eff);
		chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

		int returnVal = chooser.showSaveDialog(this);// parent=this frame
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			infile = chooser.getSelectedFile();
			outFilename = chooser.getSelectedFile().toString();

			int fl = outFilename.length();
			String test = outFilename.substring(fl - 4);// exstension lookup!!
			String ctest = pct + ext;
			if (test.compareTo(ctest) != 0)
				outFilename = chooser.getSelectedFile().toString() + pct + ext;

			if (infile.exists()) {
				String title = resources.getString("dialog.overwrite.title");
				String message = resources
						.getString("dialog.overwrite.message");

				Object[] options = (Object[]) resources
						.getObject("dialog.overwrite.buttons");
				int result = JOptionPane
						.showOptionDialog(this, message, title,
								JOptionPane.YES_NO_OPTION,
								JOptionPane.QUESTION_MESSAGE, null, options,
								options[0]);
				if (result != JOptionPane.YES_OPTION) {
					return;
				}

			}

			//new AlphaReport(this);
			performPrintReport();
			statusL.setText(resources.getString("status.save") + outFilename);
		} else {
			return;
		}
		// end File select
		// new AlphaReport(this);//can be called here!
	}
	
	/**
	 * Actual pdf renderer is called here. Called by printReport.
	 */
	public void performPrintReport(){
		PDDocument doc = new PDDocument();
		PDFRenderer renderer = new PDFRenderer(doc);
		try{
			renderer.setTitle(resources.getString("pdf.content.title"));
			renderer.setSubTitle(
					resources.getString("pdf.content.subtitle")+
					resources.getString("pdf.metadata.author")+ ", "+
							new Date());
						
			String str = " \n" + textArea.getText();
		
			//renderer.renderTextHavingNewLine(str);//works!!!!!!!!!!!!!!!!
			renderer.renderTextEnhanced(str);
			
			renderer.addPageNumber();
			renderer.close();		
			doc.save(new File(outFilename));
			doc.close();
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
