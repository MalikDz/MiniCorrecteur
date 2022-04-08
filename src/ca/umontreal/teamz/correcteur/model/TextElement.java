package ca.umontreal.teamz.correcteur.model;

/**
 * 
 * @author Jean-Charles Taza
 * @author Pirasana Ariyam
 * @author Malik Abada
 *
 */

public class TextElement {

	// Contient le contenu de l'element texte (peut etre d'un type seulement).
	private String content;

	// Une valeur booleene contenant si le l'element text est surligner ou pas.
	private boolean highlighted;

	// Le type du text element (voir l'enum TextElementType).
	private TextElementType type;

	// La position du premier caractere du contenu du text element.
	private int positionInDocText;

	/**
	 * 
	 * @param content String contenant le contenu de l'élément texte
	 * @param type    Element de type TextElementType contenant le type de cette
	 *                élément.
	 * 
	 */

	public TextElement(String content, TextElementType type) {
		this.content = content;
		this.type = type;
	}

	/**
	 * 
	 * @param content           String contenant le contenu de l'élément texte
	 * @param type              Element de type TextElementType contenant le type de
	 *                          cette élément.
	 * @param positionInDocText Entier contenant la position du premier caractere du
	 *                          contenu du text element.
	 * 
	 */

	public TextElement(String content, TextElementType type, int positionInDocText) {
		this(content, type);
		this.positionInDocText = positionInDocText;
	}

	/**
	 * 
	 * @return le string representant le contenu de ce text element.
	 * 
	 */

	public String getContent() {
		return content;
	}

	/**
	 * 
	 * Change le contenu de cette instance du text Element
	 * 
	 */

	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * 
	 * @return vrai si le mot est surligné.
	 * 
	 */

	public boolean isHighlighted() {
		return highlighted;
	}

	/**
	 * 
	 * @param highlighted valeur boolean indiquant si l'element est surligner
	 * 
	 */

	public void setHighlighted(boolean highlighted) {
		this.highlighted = highlighted;
	}

	/**
	 * 
	 * @return le type de texte qu'est cette text element.
	 * 
	 */

	public TextElementType getType() {
		return type;
	}

	/**
	 * 
	 * @return un entier representant la position du premier caractere du contenu du
	 *         text element.
	 * 
	 */

	public int getPositionInDocText() {
		return positionInDocText;
	}

	/**
	 * 
	 * @param posIndex une position dans le texte du document.
	 * @return vrai si un des caracteres de ce mot se trouve a la position posIndex
	 * 
	 */

	public boolean posWithinBoundaries(int posIndex) {
		return positionInDocText <= posIndex && posIndex < (positionInDocText + content.length());
	}

	/**
	 * 
	 * @return une representation texte de cette instance de TextElement
	 * 
	 */

	@Override
	public String toString() {
		return type + ":" + content;
	}
}
