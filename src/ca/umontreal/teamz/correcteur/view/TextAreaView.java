package ca.umontreal.teamz.correcteur.view;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Dimension;

//import java.awt.Color;
//import java.awt.Graphics2D;
//import java.awt.BasicStroke;

import javax.swing.JTextArea;

/**
 * 
 * @author Jean-Charles Taza
 * @author Pirasana Ariyam
 * @author Malik Abada
 *
 */

public class TextAreaView extends JTextArea {

	/**
	 * 
	 * les variables rect sont tempo juste pour debogger
	 *
	 */

	public static Rectangle rightRect, bottomRect;
	private static final long serialVersionUID = 1L;
	private static final Dimension TEXT_AREA_DIMENSION = new Dimension(800, 500);

	/***
	 * 
	 * ajouter des proprietes plus tards
	 * 
	 */

	public TextAreaView() {
		this.setSize(TEXT_AREA_DIMENSION);
		this.setLineWrap(true);
	}

	/**
	 * 
	 * override paintComponentJuste pour debogger
	 *
	 */

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		// Graphics2D g2 = (Graphics2D) g;
		// g2.setStroke(new BasicStroke(2));
		// g2.setColor(Color.RED);
		// if (rightRect != null) g2.draw(rightRect);
		// if (bottomRect != null)g2.draw(bottomRect);
	}
}