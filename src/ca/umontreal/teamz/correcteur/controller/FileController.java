package ca.umontreal.teamz.correcteur.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.charset.StandardCharsets;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;
import javax.swing.JTextArea;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import ca.umontreal.teamz.correcteur.view.WindowView;
import ca.umontreal.teamz.correcteur.view.TextAreaView;
import ca.umontreal.teamz.correcteur.view.ToolBarMenuView;

/**
 * 
 * @author Jean-Charles Taza
 * @author Pirasana Ariyam
 * @author Malik Abada
 *
 */

public class FileController implements ActionListener {

	// Instance de la fenetre de l'application.
	private WindowView window;

	// Instance du champs de texte de l'application.
	private JTextArea textArea;

	// Instance du controlleur responsable de gérer la correction du texte.
	private TextCorrecteurController correcteurController;

	// L'extension du type de fichier que l'application interagit avec.
	private static final String APP_FILE_EXT = ".txt";

	// Format du titre de la JFrame d'application(%s = absolute path du saved file).
	private static final String WINDOW_TITLE_FORMAT = WindowView.APP_NAME + " - %s";

	/**
	 * 
	 * @param textArea             L'instance de type JTextarea de notre correcteur
	 * @param correcteurController L'instance de controleur qui la gere la
	 *                             correction du texte
	 */

	public FileController(JTextArea textArea, TextCorrecteurController correcteurController) {
		this.correcteurController = correcteurController;
		this.textArea = textArea;
	}

	/**
	 * 
	 * @param window               L'instance de la Jframe de notre application.
	 * @param textArea             L'instance de type JTextarea de notre correcteur.
	 * @param correcteurController L'instance de controlleur qui la gere la
	 *                             correction du texte.
	 */

	public FileController(WindowView window, TextAreaView textArea, TextCorrecteurController correcteurController) {
		this(textArea, correcteurController);
		this.window = window;
	}

	/**
	 *
	 * @param absolutePath Le chemin absolu du fichier ou l'on veut ecrire.
	 * @param content      Un string contenant le texte qu'on veut ecrire dans le
	 *                     fichier.
	 * 
	 */

	private void writeTextToFile(String absolutePath, String content) throws IOException {
		Files.write(Paths.get(absolutePath), content.getBytes(StandardCharsets.UTF_8));
	}

	/**
	 * 
	 * @param file Une instance de Type File représentant le fichier à lire.
	 * @return Un string contenant le contenu du fichier lu.
	 * 
	 */

	private String readFile(File file) throws IOException {
		return file == null || !file.getName().endsWith(APP_FILE_EXT) ? null : Files.readString(file.toPath());
	}

	/**
	 * 
	 * Cette fonction fait les actions nécéssaires répondant au clique du menu item
	 * "Sauvegarder". Si le champs absolutePath du document n'est pas null, (c'est a
	 * dire qu'on a deja enregistré le fichier mais qu'on veut juste sauvergarder
	 * les changements),on récrit les le texte du champs de texte dans le fichier.
	 * Sinon on appelle la fonction saveTextToFile qui s'occupe de sauvegarder le
	 * texte du JTextArea dans un fichier sélectionné par l'utilisateur.
	 * 
	 */

	private void updateTextFile() throws IOException {
		if (correcteurController.getDocument().getAbsolutePath() != null)
			writeTextToFile(correcteurController.getDocument().getAbsolutePath(), textArea.getText());
		else
			saveTextToFile();
	}

	/**
	 * 
	 * @return une instance de JFileChooser ou les elements fichier sont restraint a
	 *         ceux qui ont l'extension ".txt".
	 * 
	 */

	private JFileChooser createTxtFilesRestrictedFileChooser() {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileFilter(new FileNameExtensionFilter("*.txt", "txt"));
		return fileChooser;
	}

	/**
	 * 
	 * Créer l'instance d'un dictionnaire (dictionary) de notre correcteur en
	 * fonction d'un fichier dictionnaire selectionné par l'utilisateur.
	 * 
	 */

	private void setUpDictionnary() throws IOException {
		String fileContent = "";
		JFileChooser fileChooser = createTxtFilesRestrictedFileChooser();
		if (fileChooser.showOpenDialog(textArea) == JFileChooser.APPROVE_OPTION)
			if ((fileContent = readFile(fileChooser.getSelectedFile())) != null)
				correcteurController.buildDictionnary(fileContent);
	}

	/**
	 * 
	 * Fonction qui permet a l'utilisateur d'ouvrir un fichier dans le
	 * MiniCorrecteur. Cette fonction procéde aussi a la constrution d'un objet
	 * Document (structure de donnée contenant les elements de texte du fichier)
	 * créer a partir du texte de ce fichier.
	 * 
	 */

	private void openTextFile() throws IOException {
		JFileChooser fileChooser = createTxtFilesRestrictedFileChooser();
		if (fileChooser.showOpenDialog(textArea) != JFileChooser.APPROVE_OPTION)
			return;
		File file;
		String fileContent;
		if ((fileContent = readFile((file = fileChooser.getSelectedFile()))) != null) {
			window.setTitle(String.format(WINDOW_TITLE_FORMAT, file.getAbsolutePath()));
			correcteurController.buildDocument(fileContent, file.getAbsolutePath());
			textArea.setText(fileContent);
		}
	}

	/**
	 * 
	 * Fonction qui permet a l'utilisateur d'enregister le contenu du JTextArea de
	 * l'application dans un fichier sélectionné. Cette fonction procéde aussi a la
	 * constrution d'un objet Document (structure de donnée contenant les elements
	 * de texte du fichier) créer a partir du texte de ce fichier.
	 * 
	 */

	private void saveTextToFile() throws IOException {
		JFileChooser fileChooser = createTxtFilesRestrictedFileChooser();
		if (fileChooser.showSaveDialog(textArea) != JFileChooser.APPROVE_OPTION)
			return;
		String fileAbsolutePath = fileChooser.getSelectedFile().getAbsolutePath();
		fileAbsolutePath += fileAbsolutePath.endsWith(APP_FILE_EXT) ? "" : APP_FILE_EXT;
		correcteurController.buildDocument(textArea.getText(), fileAbsolutePath);
		window.setTitle(String.format(WINDOW_TITLE_FORMAT, fileAbsolutePath));
		writeTextToFile(fileAbsolutePath, textArea.getText());
	}

	/**
	 * 
	 * Cette methode est le coeur du controlleur de fichier. Elle ecoute les cliques
	 * sur les menu items et appelle les fonctions appropriés pour faire les actions
	 * correspondants au texte du menu item.
	 * 
	 */

	@Override
	public void actionPerformed(ActionEvent e) {
		if (!(e.getSource() instanceof JMenuItem))
			return;
		try {
			final String itemTxt = ((JMenuItem) e.getSource()).getText();
			switch (ToolBarMenuView.OptionMenu.getMatchingMenu((itemTxt))) {
			case CORRIGER:
				correcteurController.buildNSpellCheckDocument();
				break;
			case SAUVEGARDER:
				updateTextFile();
				break;
			case SAUVEGARDER_SOUS:
				saveTextToFile();
				break;
			case DICTIONNAIRE:
				setUpDictionnary();
				break;
			case OUVRIR:
				openTextFile();
				break;
			default:
				break;
			}
		} catch (Exception e1) {e1.printStackTrace();}
	}
}
