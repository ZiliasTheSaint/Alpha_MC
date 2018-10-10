package alphaMc;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ResourceBundle;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import danfulea.utils.FrameUtilities;

/**
 * The About window displays some information about the application. Created on
 * 
 * @author Dan Fulea, 06 APR. 2011
 */

public class AboutFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	protected AlphaFrame mf;
	private static final String BASE_RESOURCE_CLASS = "alphaMc.resources.AlphaResources";
	protected ResourceBundle resources;

	private JLabel jLabel1 = new JLabel();
	private JLabel jLabel2 = new JLabel();
	private JLabel lbAuthor = new JLabel();
	private JLabel lbVersion = new JLabel();
	private JLabel jLabel7 = new JLabel();
	private JPanel jPanel1 = new JPanel();
	private JPanel jPanel2 = new JPanel();
	private JPanel jPanel3 = new JPanel();
	private JScrollPane jScrollPane1 = new JScrollPane();
	private JTextArea textLicense = new JTextArea();

	/**
	 * Constructor. About window is connected to Alpha window.
	 * @param mf mf, parent AlphaFrame object
	 */
	public AboutFrame(AlphaFrame mf) {
		super("About");
		this.mf = mf;
		resources = ResourceBundle.getBundle(BASE_RESOURCE_CLASS);
		this.setResizable(false);
		
		jLabel1.setForeground(AlphaFrame.foreColor);
		jLabel2.setForeground(AlphaFrame.foreColor);
		lbAuthor.setForeground(AlphaFrame.foreColor);
		lbVersion.setForeground(AlphaFrame.foreColor);
		jLabel7.setForeground(AlphaFrame.foreColor);
		textLicense.setBackground(AlphaFrame.textAreaBkgColor);
		textLicense.setForeground(AlphaFrame.textAreaForeColor);

		createGUI();

		setDefaultLookAndFeelDecorated(true);
		FrameUtilities.createImageIcon(
				this.resources.getString("form.icon.url"), this);

		FrameUtilities.centerFrameOnScreen(this);

		setVisible(true);

		mf.setVisible(false);
		final AlphaFrame mff = mf;
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				mff.setVisible(true);
				dispose();
			}
		});

	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 */
	private void createGUI() {

		jPanel1.setLayout(new java.awt.BorderLayout());

		jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
		jLabel1.setIcon(FrameUtilities.getImageIcon(
				this.resources.getString("icon.url"), this));
		jLabel1.setText(this.resources.getString("Application.NAME"));
		jLabel1.setVerticalAlignment(javax.swing.SwingConstants.TOP);
		jPanel1.add(jLabel1, java.awt.BorderLayout.NORTH);
		jPanel1.setBackground(AlphaFrame.bkgColor);

		jPanel3.setLayout(new java.awt.GridLayout(3, 2, 0, 4));

		jLabel2.setText(this.resources.getString("Author"));
		jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);

		jPanel3.add(jLabel2);

		lbAuthor.setText(this.resources.getString("Author.name"));

		jPanel3.add(lbAuthor);

		jLabel7.setText(this.resources.getString("Version"));
		jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);

		jPanel3.add(jLabel7);

		lbVersion.setText(this.resources.getString("Version.name"));

		jPanel3.add(lbVersion);
		jPanel3.setBackground(AlphaFrame.bkgColor);

		jPanel1.add(jPanel3, java.awt.BorderLayout.SOUTH);

		getContentPane().add(jPanel1, java.awt.BorderLayout.NORTH);

		jPanel2.setLayout(new java.awt.BorderLayout());

		jScrollPane1.setBorder(new javax.swing.border.TitledBorder(
				new javax.swing.border.LineBorder(
						new java.awt.Color(0, 51, 255), 1, true),
				"BSD Licence",
				javax.swing.border.TitledBorder.CENTER,
				javax.swing.border.TitledBorder.TOP));
		jScrollPane1
				.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		jScrollPane1
				.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		jScrollPane1.setAutoscrolls(true);
		textLicense.setColumns(1);
		textLicense.setEditable(false);

		textLicense.setLineWrap(true);
		textLicense.setRows(10);
		textLicense.setText(this.resources.getString("License"));
		textLicense.setWrapStyleWord(true);
		jScrollPane1.setViewportView(textLicense);

		jPanel2.add(jScrollPane1, java.awt.BorderLayout.CENTER);
		jPanel2.setBackground(AlphaFrame.bkgColor);

		getContentPane().add(jPanel2, java.awt.BorderLayout.CENTER);
		pack();
	}
}
