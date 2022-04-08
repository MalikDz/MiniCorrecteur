package ca.umontreal.teamz.correcteur.model;

import java.io.File;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * 
 * @author Jean-Charles Taza
 * @author Pirasana Ariyam
 * @author Malik Abada
 *
 */

public class Document {

	// String contenant le nom du fichier ou le document est sauvergadé
	private String fileName;

	// String contenant le chemin absolu du fichier ou le document est sauvergadé
	private String absolutePath;

	// entier contenant le nombre de charactere du document
	private int totalCharAmount;

	// Liste de contenant tout les elements texte (voir TextElementType)
	private List<TextElement> elements;

	public Document() {
		this(null);
	}

	/**
	 * 
	 * @param absolutePath String contenant le chemin absolu du fichier ou le
	 *                     document est sauvegardé.
	 * 
	 */

	public Document(String absolutePath) {
		this.absolutePath = absolutePath;
		this.fileName = absolutePathToFileName(absolutePath);
	}

	/**
	 * 
	 * @return Le nombre total de caracteres.
	 * 
	 */

	public int totalChars() {
		return totalCharAmount;
	}

	/**
	 * 
	 * @return Le chemin absolu du fichier ou est sauvegardé cette instance de
	 *         document.
	 * 
	 */
	public String getAbsolutePath() {
		return absolutePath;
	}

	/**
	 * 
	 * Cette fonction permet de stocker le chemin dans absolutePath.
	 * 
	 */

	public void setAbsolutePath(String absolutePath) {
		this.absolutePath = absolutePath;
	}

	/**
	 * 
	 * @return le nom du document/fichier.
	 * 
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * 
	 * @return une liste des elements texte de cette instance de document (un
	 *         element textElement peut etre un mot , saut de ligne/espace ou un
	 *         symbole).
	 * 
	 */

	public List<TextElement> elements() {
		return elements;
	}

	/**
	 * 
	 * @param absolutePath String contenant le chemin absolu du fichier ou le
	 *                     document est sauvergadé
	 * @return le nom du fichier pris du chemin absolu.
	 *
	 */

	private String absolutePathToFileName(String absolutePath) {
		return absolutePath == null ? null : new File(absolutePath).getName();
	}

	/**
	 * Note :Chaque caractère dans un texte prend une position. Cette fonction nous
	 * retourne l'élément texte à la position passé en paramètre.
	 * 
	 * @param indexPosition Entier contenant une position/index dans un texte.
	 * @return le text element à cette position.
	 * 
	 */
	public TextElement getClickedElement(int indexPosition) {
		return elements.stream().filter(e -> e.posWithinBoundaries(indexPosition)).findAny().orElse(null);
	}

	/**
	 * 
	 * @return la concatenation du contenu tout les elements texte
	 * 
	 */

	@Override
	public String toString() {
		return elements == null || elements.isEmpty() ? ""
				: String.join("", elements.stream().map(e -> e.getContent()).collect(Collectors.toList()));
	}

	/**
	 * 
	 * @param dictionary Instance de Dictionary contenant qui contient la liste des
	 *                   mots d'un dictionnaire.
	 * @return La liste de tous les TextElements qui ont comme contenu (variable
	 *         content dans TextElement) un mot qui n'est pas dans le dictionnaire
	 *         passé en paramétre.
	 */
	public List<TextElement> getMispelledWords(Dictionary dictionary) {
		return (dictionary == null) ? new ArrayList<TextElement>()
				: elements.stream().filter(e -> e.getType() == TextElementType.WORD && !dictionary.contains(e))
						.collect(Collectors.toList());
	}

	/**
	 * Sépare tous les éléments(mot, symbole,saut de lignes/espaces) de fileContent
	 * pour les mettre dans une liste.
	 * 
	 * @param fileContent String contenant le contenu du du JTextarea ou fichier
	 * 
	 */

	public void buildElements(String fileContent) {
		totalCharAmount = 0;
		elements = new ArrayList<TextElement>();
		for (String textElement : fileContent.split("\\b")) {
			final TextElementType type = TextElementType.getMatchingType(textElement);
			elements.add(new TextElement(textElement, type, totalCharAmount));
			totalCharAmount += textElement.length();
		}
	}
}
