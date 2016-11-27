/**
 * PT3 Poly Editor
 * DUT Informatique 2016/2017 
 * Auteur : HUANG Qijia
 * 			LU Yi
 * Tuteur : P. Even
 * */

package Modeleur;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JButton;
import javax.swing.JPanel;

import Blueprint.Room;

/** 
 * class ModeleurModel 
 * celui-ci contient tous les variables et constantes prevues, ainsi que les methodes de fonctionnalite. 
 * */
public class ModeleurModel {
	// Les constances de couleur  
	public static final Color BLACK = new Color(0 ,0 ,0);
	public static final Color DARKGREY1 = new Color(183, 183, 183); 
	public static final Color DARKGREY2 = new Color(153, 153, 153);
	public static final Color DARKGREY3 = new Color(102, 102, 102); 
	public static final Color DARKGREY4 = new Color(67, 67, 67); 
	public static final Color GREY = new Color(204, 204, 204); 
	public static final Color LIGHTGREY1 = new Color(217, 217, 217); 
	public static final Color LIGHTGREY2 = new Color(239, 239, 239);
	public static final Color LIGHTGREY3 = new Color(243, 243, 243);
	public static final Color WHITE = new Color(255, 255, 255); 
	
	/** Le Graphe qui dessine le Blueprint */
	protected Graph graph = new Graph();
	
	/** Le bouton d'enregistrement */
	protected JButton bSave;
	
	/** Une entiere qui signifie le mode de dessiner: 1 - Chambre(Room) ; 2 - Couloir(Corridor) */
	protected int mode=1;
	
	// les variables qu'on definit pour le test
	protected JPanel p1,p2;
	protected JButton b1,b2,b3,b4;
	protected Room room = new Room(4,"Rectangle");
	
	/**
	 * class interne Graph
	 * On dessine une chambres ou un couloirs dans cette class 
	 * */
	class Graph extends JPanel {
		public void paintComponent (Graphics g) {
			super.paintComponent(g);
			this.setBackground(ModeleurModel.DARKGREY3);
			if(mode==1) // mode pour dessiner le chambre
				room.draw(g);
		}
	}
}