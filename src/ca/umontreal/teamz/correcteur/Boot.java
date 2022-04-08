package ca.umontreal.teamz.correcteur;

import javax.swing.UIManager;
import javax.swing.SwingUtilities;
import javax.swing.UnsupportedLookAndFeelException;

import ca.umontreal.teamz.correcteur.view.WindowView;

/**
 * 
 * @author Jean-Charles Taza (202003170)
 * @author Pirasana Ariyam (20199437)
 * @author Malik Abada (20173066)
 *
 */

public class Boot {
	public static void main(String[] args) throws ClassNotFoundException, InstantiationException,
			IllegalAccessException, UnsupportedLookAndFeelException {
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		SwingUtilities.invokeLater(() -> new WindowView());
	}
}
