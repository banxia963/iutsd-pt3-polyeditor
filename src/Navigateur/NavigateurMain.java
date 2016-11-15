package Navigateur;

import java.awt.Dimension;
import java.awt.FileDialog;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.util.FPSAnimator;

import Blueprint.Room;

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

		MenuBar bar;  
		Menu fileMenu;  
		MenuItem closeItem; 
		MenuItem openItem;  
		FileDialog openDia;		     

		bar = new MenuBar();  
		fileMenu = new Menu("Fichier"); 

		openItem = new MenuItem("Ouvrir");     
		closeItem = new MenuItem("Exit"); 

		fileMenu.add(openItem);     
		fileMenu.add(closeItem);  
		bar.add(fileMenu);  

		frame.setMenuBar(bar);    
		openDia = new FileDialog(frame,"Ouvrir",FileDialog.LOAD);

		openItem.addActionListener(new ActionListener()  
		{  
			public void actionPerformed(ActionEvent e)  
			{  
				openDia.setVisible(true);  
				String dirPath = openDia.getDirectory();  
				String fileName = openDia.getFile(); 
				File file = new File(dirPath,fileName);  	
				Room r= new Room(4, "Rectangle");	
				try {
					r.read(fileName);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}	
			}	
		});  

		closeItem.addActionListener(new ActionListener()  
		{  
			//设置退出功能  
			public void actionPerformed(ActionEvent e)  
			{  
				System.exit(0);  
			}  
		});  

		frame.addWindowListener(new WindowAdapter()  
		{  
			public void windowClosing(WindowEvent e)  
			{  
				System.exit(0);  
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



