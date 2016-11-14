package Navigateur;

import java.awt.Dimension;
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

		final JFrame frame = new JFrame();
		frame.getContentPane().add(canvas);
		frame.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e){
				new Thread() {
					public void run() {
						if (animator.isStarted()) animator.stop();
						System.exit(0);
					}
				}.start();
			}
		});
		frame.setTitle(TITLE);
		frame.pack();
		frame.setVisible(true);
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



