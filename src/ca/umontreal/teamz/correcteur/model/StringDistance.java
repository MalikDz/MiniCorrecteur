package ca.umontreal.teamz.correcteur.model;

/**
 * 
 * @author Jean-Charles Taza
 * @author Pirasana Ariyam
 * @author Malik Abada
 *
 */

public class StringDistance implements Comparable<StringDistance> {

	// Distance de levenshtein entre notre mot principal et le mot a comparer.
	private int distance;

	// Ce sont tous les deux des string contenant des mots qu'on compare.
	private String mainString, comparedString;

	/**
	 * 
	 * @param mainString     String contenant le mot principal , c'est a dire le mot
	 *                       dont on cherche le nombre de differences (distance)
	 *                       avec un autre mot.
	 * @param comparedString String contenant le mot qu'on compare avec le mot
	 *                       principal.
	 * 
	 */

	public StringDistance(String mainString, String comparedString) {
		this.mainString = mainString.toLowerCase();
		this.comparedString = comparedString.toLowerCase();
		this.distance = this.distance(this.mainString, this.comparedString);
	}

	/**
	 * 
	 * @return la distance de levenshtein des 2 mots de cette instance.
	 * 
	 */

	public int getDistance() {
		return distance;
	}

	/**
	 * 
	 * @return le String contenant le mot principal , c'est a dire le mot dont on
	 *         cherche le nombre de differences (distance) avec un autre mot.
	 * 
	 */

	public String getMainString() {
		return mainString;
	}

	/**
	 * 
	 * @return le String contenant le mot qu'on compare avec le mot principal.
	 * 
	 */

	public String getComparedString() {
		return comparedString;
	}

	/**
	 * 
	 * Methode nécéssaire pour faire le tri d'éléments de type StringDistance.
	 *
	 * @return un int positive si la distance de cette paire de mot (de this
	 *         instance) est plus grande que la distance ins, zero si la distance de
	 *         cette paire de mot (de this instance) est plus égal que la distance
	 *         ins et un nombre négatif si la distance de cette paire de mot (de
	 *         this instance) est plus petite que la distance ins.
	 * 
	 */

	@Override
	public int compareTo(StringDistance ins) {
		return distance - ins.getDistance();
	}

	/**
	 * 
	 * @param s1 un mot en String
	 * @param s2 un autre mot en String
	 * @return on retourne la distannce qui correspond au nombre de changements
	 *         qu'on doit faire pour changer un mot en un autre mot
	 * 
	 */

	private int distance(String s1, String s2) {
		int edits[][] = new int[s1.length() + 1][s2.length() + 1];
		for (int i = 0; i <= s1.length(); i++)
			edits[i][0] = i;
		for (int j = 1; j <= s2.length(); j++)
			edits[0][j] = j;
		for (int i = 1; i <= s1.length(); i++)
			for (int j = 1; j <= s2.length(); j++)
				edits[i][j] = Math.min(edits[i - 1][j] + 1, Math.min(edits[i][j - 1] + 1,
						edits[i - 1][j - 1] + ((s1.charAt(i - 1) == s2.charAt(j - 1) ? 0 : 1))));
		return edits[s1.length()][s2.length()];
	}
}