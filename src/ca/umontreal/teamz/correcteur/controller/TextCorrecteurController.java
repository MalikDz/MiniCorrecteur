package ca.umontreal.teamz.correcteur.controller;

import java.util.Arrays;
import javax.swing.JMenuItem;
import javax.swing.JTextArea;
import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;
import javax.swing.text.Highlighter;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.BadLocationException;
import javax.swing.text.Highlighter.HighlightPainter;

import ca.umontreal.teamz.correcteur.model.Document;
import ca.umontreal.teamz.correcteur.model.Dictionary;
import ca.umontreal.teamz.correcteur.model.TextElement;
import ca.umontreal.teamz.correcteur.view.TextAreaView;

import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;

/**
 * 
 * @author Jean-Charles Taza
 * @author Pirasana Ariyam
 * @author Malik Abada
 *
 */

public class TextCorrecteurController extends MouseAdapter implements ActionListener {

	// Instance de Document , structure de donn� repr�sentant un texte.
	private Document document;

	// Instance du champs de texte de l'application.
	private JTextArea textArea;

	// Instance de Dictionary, structure de donn� repr�sentant un dictonnaire.
	private Dictionary dictionary;

	// Instance de l'�l�ment text right cliqu� (lors d'une correction)
	private TextElement clickedElement;

	// Instance du JPopupMenu, menu qui pop lorsqu'on right un mot �rron�
	private JPopupMenu currentPopupMenu;

	/**
	 * 
	 * @param textArea L'instance du champs de texte de notre application
	 * 
	 */

	public TextCorrecteurController(JTextArea textArea) {
		this.document = new Document();
		this.textArea = textArea;
	}

	/**
	 * 
	 * @return L'instance du document
	 *
	 */

	public Document getDocument() {
		return document;
	}

	/**
	 * 
	 * @param fileContent  Un String contenant le contenu texte du document.
	 * @param absolutePath Un String contenant le path du fichier ou le contenu
	 *                     texte sera sauvegarder.
	 */

	public void buildDocument(String fileContent, String absolutePath) {
		this.document.buildElements(fileContent);
		this.document.setAbsolutePath(absolutePath);
	}

	/**
	 * Cette fonction cr�e une liste contenant tout les mots d'un dictionnaire en
	 * s�parant chaque element delimit� par un saut de ligne(ces �l�ments sont dans
	 * le string fileContent) pour ensuite cette derniere dans un object de type
	 * Dictionary.
	 * 
	 * @param fileContent Un String contenant le contenu texte d'un fichier
	 *                    dictionnaire.
	 * 
	 */

	public void buildDictionnary(String fileContent) {
		dictionary = new Dictionary(Arrays.asList(fileContent.split("\n")));
	}

	/**
	 * 
	 * Class Highlighter custom
	 * 
	 */

	public class MyHighlightPainter extends DefaultHighlighter.DefaultHighlightPainter {
		public MyHighlightPainter(Color color) {
			super(color);
		}
	}

	/**
	 * 
	 * reconstruit l'objet document a partir du texte de le JTextArea. Enleve tout
	 * les highlights mots �ron�s (utilis� pour enl�ve le highlight des mots qui ont
	 * �t� corrig�s). Puis finalement, on boucle a travers tous les �l�ments du
	 * document recontruit contenant un mot �ron� pour ensuite les surligner.
	 * 
	 */

	public void buildNSpellCheckDocument() throws BadLocationException {
		document.buildElements(textArea.getText());
		removeHighlights();
		for (TextElement wordElement : document.getMispelledWords(dictionary))
			highlightWordElement(wordElement);
	}

	/**
	 * 
	 * Fonction responsable d'enlever tout les texts highlighter.
	 * 
	 */

	public void removeHighlights() {
		Highlighter hilite = textArea.getHighlighter();
		Highlighter.Highlight[] hilites = hilite.getHighlights();
		document.elements().stream().forEach(e -> e.setHighlighted(false));
		Arrays.stream(hilites).filter(h -> h.getPainter() instanceof MyHighlightPainter)
				.forEach(x -> hilite.removeHighlight(x));
	}

	/**
	 * 
	 * Fonction reponsable de highlighter un textElement en particulier.
	 * 
	 */

	private void highlightWordElement(TextElement wordElement) throws BadLocationException {
		wordElement.setHighlighted(true);
		final Highlighter highlighter = textArea.getHighlighter();
		final HighlightPainter painter = new MyHighlightPainter(Color.pink);
		final int posInText = wordElement.getPositionInDocText();
		highlighter.addHighlight(posInText, posInText + wordElement.getContent().length(), painter);
	}

	/**
	 * Methode qui se charge d'afficher un menu de correction lorsque l'utilisateur
	 * right click sur un mot �ron�.
	 * 
	 * @param e Instance de MouseEvent representant le clique de l'utilisateur
	 * 
	 */

	private void handlePotentialWordCorrection(MouseEvent e) throws Exception {
		if (SwingUtilities.isRightMouseButton(e) && dictionary != null && document.elements() != null) {
			final long initialTime = System.currentTimeMillis();
			final int indexPosition = textArea.viewToModel2D(e.getPoint());
			final TextElement clickedElement = document.getClickedElement(indexPosition);
			final Rectangle furthestCharPosModel = textArea.modelToView2D(document.totalChars()).getBounds();
			if (clickIsInTextBoundaries(furthestCharPosModel, e.getPoint())) {
				if (clickedElement != null && clickedElement.isHighlighted() && !dictionary.contains(clickedElement)) {
					currentPopupMenu = new JPopupMenu();
					String clickedWord = clickedElement.getContent();
					System.out.println(clickedWord + " correct options :");
					for (String wordChoice : dictionary.findSimilarWords(clickedWord, 5)) {
						JMenuItem optionItem = new JMenuItem(wordChoice);
						optionItem.setPreferredSize(new Dimension(140, 22));
						optionItem.addActionListener(TextCorrecteurController.this);
						currentPopupMenu.add(optionItem);
					}
					this.clickedElement = clickedElement;
					currentPopupMenu.show(textArea, e.getX(), e.getY());
					System.out.printf("Click + algo process time : %d%n%n", (System.currentTimeMillis() - initialTime));
				}
			}
		}
	}

	/**
	 * Pour verrifier si le clique est dans le texte.On forme un rectangle a droite
	 * du dernier charactere(sur la meme ligne a droite)et un en dessous(toutes les
	 * lignes en bas du dernier char).C'est deux rectangles sont les zones du text
	 * area ou il n'ya pas de texte. Puis on check si la position du clique se situe
	 * a l'ext�rieur de ces 2 rectangles. Les 3 dernieres instructions avant le
	 * return sont juste pour debogger les rectangles.
	 *
	 * @param baseRectMode Instance de type Rectangle repr�sentant le model
	 *                     (position et tailles)du dernier charact�re du texte.
	 * @param clickPoint   Instance de type Point repr�sentant la position du clique
	 *                     de l'utilisateur.
	 * @return vrai si le clique de l'utilisateur est dans une Zone texte . C'est a
	 *         dire un espace se si situant avant le dernier charact�re.
	 */

	public boolean clickIsInTextBoundaries(Rectangle baseRectModel, Point clickPoint) {
		final Rectangle rightEmptyRect = new Rectangle(baseRectModel.x, baseRectModel.y, 5000, baseRectModel.height);
		final Rectangle bottowEmptyRect = new Rectangle(0, (baseRectModel.y + baseRectModel.height),
				textArea.getSize().width, 4000);
		TextAreaView.rightRect = rightEmptyRect;
		TextAreaView.bottomRect = bottowEmptyRect;
		textArea.repaint();
		return !rightEmptyRect.contains(clickPoint) && !bottowEmptyRect.contains(clickPoint);
	}

	/**
	 * Dans cette fonction , on prend le texte du menu cliqu� (ce dernier contient
	 * la correction du textElement cliqu�) puis le met dans le text �l�ment cliqu�.
	 * 
	 * @param clickedWordItem L'instance du menu item cliqu� du menu de correction.
	 * 
	 */

	public void correctWord(JMenuItem clickedWordItem) throws BadLocationException {
		if (clickedElement == null)
			return;
		System.out.println("Selected text :" + (clickedWordItem.getText()));
		clickedElement.setContent(clickedWordItem.getText());
		textArea.setText(document.toString());
		clickedElement.setHighlighted(false);
		currentPopupMenu = null;
		clickedElement = null;
		buildNSpellCheckDocument();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			correctWord(((JMenuItem) e.getSource()));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		try {
			handlePotentialWordCorrection(e);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
