package alphaMc;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.ResourceBundle;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.border.Border;

import danfulea.utils.FrameUtilities;

/**
 * PleaseWait frame showing busy status!
 * 
 * @author Dan Fulea, 04 DEC. 2003
 */
public class PleaseWait extends JFrame implements Runnable {

	private static final long serialVersionUID = 1L;
	public static final Border STANDARD_BORDER = BorderFactory
			.createEmptyBorder(5, 5, 5, 5);
	private static final Dimension prefferedSize = new Dimension(250, 90);
	private JLabel l;
	// --------------------
	int delay = 100;
	private Thread pleaseWaitTh;
	int frameNumber = -1;
	// -----------------------
	private ResourceBundle resources = ResourceBundle
			.getBundle("alphaMc.resources.MessageResources");

	/**
	 * Constructor 1... setting up the application GUI!
	 */
	public PleaseWait() {
		setTitle(resources.getString("plswait.title"));

		setDefaultLookAndFeelDecorated(true);
		FrameUtilities.createImageIcon(
				this.resources.getString("form.icon.url"), this);

		JPanel content = new JPanel(new BorderLayout());
		content.setBorder(STANDARD_BORDER);

		l = new JLabel(resources.getString("plswait.label"));
		JProgressBar pb = new JProgressBar(0, 100);
		pb.setIndeterminate(true);

		content.add(l, BorderLayout.CENTER);
		content.add(pb, BorderLayout.SOUTH);
		setContentPane(content);

		pack();
		setLocationRelativeTo(null);
		setSize(prefferedSize);
		setVisible(true);

	}

	/**
	 * Constructor 2... setting up the application GUI!
	 * @param label text to be displayed
	 */
	public PleaseWait(String label) {

		setTitle(resources.getString("plswait.title"));

		setDefaultLookAndFeelDecorated(true);
		FrameUtilities.createImageIcon(
				this.resources.getString("form.icon.url"), this);

		JPanel content = new JPanel(new BorderLayout());
		content.setBorder(STANDARD_BORDER);

		l = new JLabel(label);
		JProgressBar pb = new JProgressBar(0, 100);
		pb.setIndeterminate(true);

		content.add(l, BorderLayout.CENTER);
		content.add(pb, BorderLayout.SOUTH);
		setContentPane(content);

		pack();
		setLocationRelativeTo(null);
		setSize(prefferedSize);
		setVisible(true);

	}

	/**
	 * Setting up the window image icon. Requires the URL of image file.
	 * 
	 * @param URLstr
	 */
	/*
	 * private void createImageIcon(String URLstr) { byte[] tmp = new
	 * byte[102400]; int size = 0; try { InputStream is =
	 * getClass().getResourceAsStream(URLstr);
	 * 
	 * while (is.available() > 0) { is.read(tmp, size, 1); size++; } is.close();
	 * byte[] data = new byte[size]; System.arraycopy(tmp, 0, data, 0, size);
	 * ImageIcon icon = new ImageIcon(data); setIconImage(icon.getImage()); }
	 * catch (Exception exc) {
	 * 
	 * } }
	 */

	/**
	 * Start animation.
	 */
	public void startAnimation() {
		if (pleaseWaitTh == null) {
			pleaseWaitTh = new Thread(this);
		}
		pleaseWaitTh.start();

	}

	/**
	 * Stop animation.
	 */
	public void stopAnimation() {
		// Stop animating thread.+ dispose JFrame
		pleaseWaitTh = null;
		dispose();
	}

	/**
	 * Runnable method. Initiated on thread start() method.
	 */
	public void run() {
		Thread.currentThread().setPriority(Thread.MAX_PRIORITY);
		// Starting time
		long startTime = System.currentTimeMillis();
		// Remember la care fir sunt
		Thread currentThread = Thread.currentThread();
		// Label animation;default ProgressBar
		while (currentThread == pleaseWaitTh) {
			frameNumber++;
			if (frameNumber % 2 == 0)
				l.setVisible(false);
			else
				l.setVisible(true);
			// Animate!!!
			repaint();
			// Delay
			try {
				startTime += delay;
				Thread.sleep(Math.max(0, startTime - System.currentTimeMillis()));
			} catch (InterruptedException e) {
				break;
			}
		}

	}

}