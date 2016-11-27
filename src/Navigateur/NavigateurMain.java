/**
 * PT3 Poly Editor
 * DUT Informatique 2016/2017 
 * Auteur : HUANG Qijia
 * 			LU Yi
 * Tuteur : P. Even
 * */

package Navigateur;

import java.awt.Dimension;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.util.FPSAnimator;

@SuppressWarnings("serial")
public class NavigateurMain extends JFrame {
	// Definir les constants
	/** nom de la fenetre */
	private static String TITLE = "Escape Demo";
	/** largeur de canvas */
	private static final int CANVAS_WIDTH = 800;
	/** longeur de canvas */
	private static final int CANVAS_HEIGHT = 600;
	/** frames par second de l'animateur */
	private static final int FPS = 60;
	
	/** Constructeur pour creer le container et l'animator*/
	public NavigateurMain() {
		// Creer le OpenGL rendering canvas
		GLCanvas canvas = new GLCanvas(); // heavy-weight GLCanvas
		canvas.setPreferredSize(new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT));
		NavigateurModel model = new NavigateurModel();
		NavigateurView renderer = new NavigateurView(model);
		canvas.addGLEventListener(renderer);

		// Pour Handling KeyEvents
		NavigateurController controller = new NavigateurController(model);
		canvas.addKeyListener(controller);
		canvas.setFocusable(true);
		canvas.requestFocus();

		final FPSAnimator animator = new FPSAnimator(canvas, FPS, true);

		model.frame.getContentPane().add(canvas);
		model.frame.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e){
				new Thread() {
					public void run() {
						if (animator.isStarted()) animator.stop();
						System.exit(0);
					}
				}.start();
			}
		});

		MenuBar bar;  
		Menu fileMenu; 
		bar = new MenuBar();  
		fileMenu = new Menu("Fichier"); 	
		fileMenu.add(model.openItem);     
		fileMenu.add(model.closeItem);  
		fileMenu.add(model.menutexture);
		model.closeItem.addActionListener(controller);
		model.openItem.addActionListener(controller);
		model.menutexture.addActionListener(controller);
		bar.add(fileMenu);  

		model.frame.setMenuBar(bar);    
		model.frame.addWindowListener(new WindowAdapter()  
		{  
			public void windowClosing(WindowEvent e)  
			{  
				System.exit(0);  
			}  
		});  
	  
		model.frame.setTitle(TITLE);
		model.frame.pack();
		model.frame.setVisible(true);
		animator.start();
		
}	


public static void main(String[] args) {
	SwingUtilities.invokeLater(new Runnable() {
		public void run() {
			new NavigateurMain();
		}
	});

}

}