package ca.umontreal.teamz.correcteur.view;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;

import ca.umontreal.teamz.correcteur.controller.FileController;
import ca.umontreal.teamz.correcteur.controller.TextCorrecteurController;

/**
 * 
 * @author Jean-Charles Taza
 * @author Pirasana Ariyam
 * @author Malik Abada
 *
 */

public class WindowView extends JFrame {

	private static final long serialVersionUID = 1L;

	// instance du champs de texte de notre applicaton
	private TextAreaView textArea;

	// instance du menu bar de la fenetre de notre application
	private ToolBarMenuView menuBar;

	// Titre de la fenetre de notre application
	public static final String APP_NAME = "MiniCorrecteur";

	public WindowView() {
		super(APP_NAME);
		textArea = new TextAreaView();
		menuBar = new ToolBarMenuView();
		setLayout(new BorderLayout(0, -3));
		Dimension scaledDim = textArea.getSize();
		setSize((int) (scaledDim.getWidth() + 15), (int) (scaledDim.getHeight() + 30));

		TextCorrecteurController correcteurController = new TextCorrecteurController(textArea);
		FileController fileController = new FileController(this, textArea, correcteurController);

		for (Component menuItem : menuBar.getOptionsMenu().getMenuComponents())
			((JMenuItem) menuItem).addActionListener(fileController);
		for (Component menuItem : menuBar.getFileMenu().getMenuComponents())
			((JMenuItem) menuItem).addActionListener(fileController);
		textArea.addMouseListener(correcteurController);

		add(new JScrollPane(textArea), BorderLayout.CENTER);
		add(menuBar, BorderLayout.NORTH);

		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);
	}
}
