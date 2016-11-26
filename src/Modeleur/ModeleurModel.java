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
import Blueprint.Vertex;
import Blueprint.Wall;

/** 
 * class ModeleurModel 
 * celui-ci contient tous les variables et constantes prevues, ainsi que les methodes de fonctionnalite. 
 * */
public class ModeleurModel {
	// Color 
	public static Color BLACK = new Color(0 ,0 ,0),
				DARKGREY1 = new Color(183, 183, 183), 
				DARKGREY2 = new Color(153, 153, 153), 
				DARKGREY3 = new Color(102, 102, 102), 
				DARKGREY4 = new Color(67, 67, 67), 
				GREY = new Color(204, 204, 204), 
				LIGHTGREY1 = new Color(217, 217, 217), 
				LIGHTGREY2 = new Color(239, 239, 239),
				LIGHTGREY3 = new Color(243, 243, 243),
				WHITE = new Color(255, 255, 255); 
				
	protected Room room = new Room(4,"Rectangle");
	protected Graph graph = new Graph();
	protected JButton bSave;
	
	/**
	 * 
	 * */
	class Graph extends JPanel {
		public void paintComponent (Graphics g) {
			super.paintComponent(g);
			this.setBackground(ModeleurModel.DARKGREY3);	
			room.draw(g);
			
			
		}
	}
}
