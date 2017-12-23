package GUI;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextField;

public class helpWin {

	private JFrame frame;
	private JTextField txtHelpWind;

	/**
	 * Launch the application.
	 */
	public static void helpScreen() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					helpWin window = new helpWin();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public helpWin() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 594, 377);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		txtHelpWind = new JTextField();
		txtHelpWind.setText("Help wind");
		txtHelpWind.setBounds(118, 76, 86, 20);
		frame.getContentPane().add(txtHelpWind);
		txtHelpWind.setColumns(10);
	}
}
