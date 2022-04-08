package ca.umontreal.teamz.correcteur.view;

import java.util.Arrays;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

/**
 * 
 * @author Jean-Charles Taza
 * @author Pirasana Ariyam
 * @author Malik Abada
 *
 */

public class ToolBarMenuView extends JMenuBar {

	/**
	 * 
	 * settingMenu -> component contenant les items "corriger" et "dictionnaire"
	 * optionsMenu -> component contenant les items du "ouvrir" ,"sauvegarder/-sous"
	 * 
	 */

	private JMenu optionsMenu, fileMenu;
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 * initilialisation et ajout de tout les components de notre menu bar
	 * 
	 */

	public ToolBarMenuView() {
		optionsMenu = new JMenu(OptionMenu.OPTIONS.toString());
		fileMenu = new JMenu(OptionMenu.FICHIER.toString());

		optionsMenu.add(new JMenuItem(OptionMenu.CORRIGER.toString()));
		optionsMenu.add(new JMenuItem(OptionMenu.DICTIONNAIRE.toString()));

		fileMenu.add(new JMenuItem(OptionMenu.OUVRIR.toString()));
		fileMenu.add(new JMenuItem(OptionMenu.SAUVEGARDER.toString()));
		fileMenu.add(new JMenuItem(OptionMenu.SAUVEGARDER_SOUS.toString()));

		add(fileMenu);
		add(optionsMenu);
	}

	/**
	 * 
	 * @return le menu d'options
	 * 
	 */

	public JMenu getOptionsMenu() {
		return optionsMenu;
	}

	/**
	 * 
	 * @return le menu de fichier
	 * 
	 */

	public JMenu getFileMenu() {
		return fileMenu;
	}

	/**
	 * 
	 * Enum utilisé juste pour faciliter la maintenance.
	 * 
	 */

	public enum OptionMenu {

		OPTIONS, CORRIGER, DICTIONNAIRE, FICHIER, OUVRIR, SAUVEGARDER, SAUVEGARDER_SOUS;

		/**
		 * 
		 * @param menuTxt String contenant le text d'un menu item
		 * @return le Option qui match avec le String passe en parametre
		 * 
		 */
		public static OptionMenu getMatchingMenu(String menuTxt) {
			return Arrays.stream(OptionMenu.values()).filter(e -> e.toString().equals(menuTxt)).findFirst().get();
		}

		/**
		 * 
		 * transforme le string representant lenum en String utilise dans notre
		 * interface graphique (Ex : "SAUVEGARDER_SOUS" devient "Sauvegarder sous")
		 * 
		 */

		@Override
		public String toString() {
			final String[] tempo = super.toString().split("_");
			return tempo[0].substring(0, 1) + tempo[0].substring(1).toLowerCase()
					+ (tempo.length > 1 ? " " + tempo[1].toLowerCase() : "");
		}
	}
}
