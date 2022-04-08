package ca.umontreal.teamz.correcteur.model;

/**
 * 
 * @author Jean-Charles Taza
 * @author Pirasana Ariyam
 * @author Malik Abada
 *
 */

public enum TextElementType {

	/**
	 * 
	 * Voici les types d'elements et le regex qui permet de les identifier:
	 * Word : 			un mot.
	 * Separator : 		des saut de lignes ou des espaces.
	 * Ponctuations :	les symboles de ponctuation.
	 * Default : 		tout ce qui ne rentre pas dans les types definies juste en haut.
	 * 
	 */

	WORD("[0-9A-Za-zÀ-ÿ]+"), 
	SEPARATOR("\\s+|(\\n+)"), 
	PONCTUATION(".*\\p{Punct}.*"), 
	DEFAULT("");

	private String regexPattern;

	TextElementType(String regexPattern) {
		this.regexPattern = regexPattern;
	}

	public boolean matches(String element) {
		return element.matches(regexPattern);
	}

	public static TextElementType getMatchingType(String element) {
		for (TextElementType d : TextElementType.values())
			if (d.matches(element))
				return d;
		return DEFAULT;
	}
}
