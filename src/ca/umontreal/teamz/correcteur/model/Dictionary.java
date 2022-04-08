package ca.umontreal.teamz.correcteur.model;

import java.util.List;
import java.util.Collections;
import java.util.stream.Collectors;

/**
 * 
 * @author Jean-Charles Taza
 * @author Pirasana Ariyam
 * @author Malik Abada
 *
 */

public class Dictionary {

	private List<String> words;

	/**
	 * 
	 * @param words Liste de string contenant les mots du dictionnaire francais.
	 * 
	 */

	public Dictionary(List<String> words) {
		Collections.sort(this.words = words);
	}

	/**
	 * 
	 * @return Une liste de string contenant les mots du dictionnaire francais.
	 * 
	 */

	public List<String> getWords() {
		return words;
	}

	/**
	 * 
	 * @param textElement Instance de l'élément text qu'on cherche a savoir si il
	 *                    est dans le dictionnaire.
	 * @return vrai si le dictionnaire a un mot qui match le contenu du text
	 *         élément.
	 * 
	 */

	public boolean contains(TextElement textElement) {
		return contains(textElement.getContent());
	}

	/**
	 * 
	 * @param word Un string qu'on cherche dont on veut savoir si il se trouve dans
	 *             les mots du dictionnaire
	 * @return vrai si le dictionnaire contient le meme mot que le string passé en
	 *         paramètre.
	 * 
	 */

	public boolean contains(String word) {
		return Collections.binarySearch(this.words, word.toLowerCase()) >= 0;
	}

	/**
	 * 
	 * @param word Un string dont un cherche 5 autres mots similaires a ce dernier.
	 * @return une liste de 5 string (mots) qui ressemble au passé en paramètre.
	 * 
	 */

	public List<String> findTopFiveSimilarWords(String word) {
		return findSimilarWords(word, 5);
	}

	/**
	 * 
	 * @param word    Un string dont un cherche 5 autres mots similaires a ce
	 *                dernier.
	 * @param wordLim Un entier represenantant le nombre de mots similaires que l'on
	 *                doit trouver.
	 * @return une liste de string contenant "wordLim" nombre de mots similaire a
	 *         celui passé en paramètre
	 * 
	 */

	public List<String> findSimilarWords(String word, int wordLim) {
		long timeIni = System.currentTimeMillis();
		List<StringDistance> stringDistWrappers = words.stream().map(mainWord -> new StringDistance(mainWord, word))
				.collect(Collectors.toList());
		Collections.sort(stringDistWrappers);
		stringDistWrappers.subList(0, wordLim).stream().forEach(w -> System.out.println(w.getMainString()));
		System.out.println("Similar words algo processing time : " + (System.currentTimeMillis() - timeIni) + "ms");
		return stringDistWrappers.subList(0, wordLim).stream().map(s -> s.getMainString()).collect(Collectors.toList());
	}
}