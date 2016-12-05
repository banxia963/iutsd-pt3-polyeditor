/**
 * PT3 Poly Editor
 * DUT Informatique 2016/2017 
 * Auteur : HUANG Qijia
 * 			LU Yi
 * Tuteur : P. Even
 * */

package Modeleur;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
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
	protected Graph graph;
	
	/** Le bouton d'enregistrement */
	protected JButton bSave;
	
	/** Une entiere qui signifie le mode de dessiner: 1 - Chambre(Room) ; 2 - Couloir(Corridor) */
	protected int mode=0;
	
	protected JPanel bg, toolbar, optsMode, optsRoom, optsWall, save;
	
	protected JButton bRoom, bCorridor, bRectangle, bHexagon, bOctogon, bVertx, bDoor, bWindow;
	
	protected Room room = new Room(4,"Rectangle");
	
	/** rayon de Vertex */
	protected float r=(float)25/2;
	
	/** Constructeur par default */
	public ModeleurModel(){
		Font font = new Font("Arial", Font.BOLD, 20);
		GridBagConstraints g = new GridBagConstraints();
		
		optsMode = new JPanel(new GridBagLayout());
		optsMode.setBackground(ModeleurModel.DARKGREY2);
		optsMode.setPreferredSize(new Dimension(130*2,420*2));
		
		bRoom= new JButton("CHAMBRE");
		bRoom.setFont(font);
		bRoom.setForeground(ModeleurModel.BLACK);
		bRoom.setBackground(ModeleurModel.DARKGREY4);
		bRoom.setPreferredSize(new Dimension(110*2,70));
		bRoom.setFocusPainted(false);
		
		bCorridor = new JButton("COULOIR");
		bCorridor.setFont(font);
		bCorridor.setForeground(ModeleurModel.BLACK);
		bCorridor.setBackground(ModeleurModel.DARKGREY4);
		bCorridor.setPreferredSize(new Dimension(110*2,70));
		bCorridor.setFocusPainted(false);
		
		g.gridx = 0;
		g.gridy = 1;
		g.insets= new Insets(10,0,10,0);
		optsMode.add(bRoom,g);
		g.gridy = 2;
		optsMode.add(bCorridor, g);
		
		optsRoom = new JPanel(new GridBagLayout());
		optsRoom.setBackground(ModeleurModel.DARKGREY2);
		optsRoom.setPreferredSize(new Dimension(130*2,420*2));
		
		bRectangle= new JButton("RECTANGLE");
		bRectangle.setFont(font);
		bRectangle.setForeground(ModeleurModel.BLACK);
		bRectangle.setBackground(ModeleurModel.DARKGREY4);
		bRectangle.setPreferredSize(new Dimension(110*2,70));
		bRectangle.setFocusPainted(false);
		
		bHexagon= new JButton("HEXAGONE");
		bHexagon.setFont(font);
		bHexagon.setForeground(ModeleurModel.BLACK);
		bHexagon.setBackground(ModeleurModel.DARKGREY4);
		bHexagon.setPreferredSize(new Dimension(110*2,70));
		bHexagon.setFocusPainted(false);
		
		bOctogon= new JButton("OCTOGONE");
		bOctogon.setFont(font);
		bOctogon.setForeground(ModeleurModel.BLACK);
		bOctogon.setBackground(ModeleurModel.DARKGREY4);
		bOctogon.setPreferredSize(new Dimension(110*2,70));
		bOctogon.setFocusPainted(false);
		
		g.gridx = 0;
		g.gridy = 1;
		g.insets= new Insets(10,0,10,0);
		optsRoom.add(bRectangle,g);
		g.gridy = 2;
		optsRoom.add(bHexagon,g);
		g.gridy = 3;
		optsRoom.add(bOctogon,g);
		
		optsWall = new JPanel(new GridBagLayout());
		optsWall.setBackground(ModeleurModel.DARKGREY2);
		optsWall.setPreferredSize(new Dimension(130*2,420*2));
		
		bVertx= new JButton("+ POINT");
		bVertx.setFont(font);
		bVertx.setForeground(ModeleurModel.BLACK);
		bVertx.setBackground(ModeleurModel.DARKGREY4);
		bVertx.setPreferredSize(new Dimension(110*2,70));
		bVertx.setFocusPainted(false);
		
		bDoor= new JButton("+ PORTE");
		bDoor.setFont(font);
		bDoor.setForeground(ModeleurModel.BLACK);
		bDoor.setBackground(ModeleurModel.DARKGREY4);
		bDoor.setPreferredSize(new Dimension(110*2,70));
		bDoor.setFocusPainted(false);
		
		bWindow= new JButton("+ FENETRE");
		bWindow.setFont(font);
		bWindow.setForeground(ModeleurModel.BLACK);
		bWindow.setBackground(ModeleurModel.DARKGREY4);
		bWindow.setPreferredSize(new Dimension(110*2,70));
		bWindow.setFocusPainted(false);
		
		g.gridx = 0;
		g.gridy = 1;
		g.insets= new Insets(10,0,10,0);
		optsWall.add(bVertx,g);
		g.gridy = 2;
		optsWall.add(bDoor,g);
		g.gridy = 3;
		optsWall.add(bWindow,g);
		
		bSave = new JButton("ENREGISTRER");
		bSave.setFont(font);
		bSave.setForeground(ModeleurModel.BLACK);
		bSave.setBackground(ModeleurModel.DARKGREY4);
		bSave.setPreferredSize(new Dimension(110*2,70));
		bSave.setFocusPainted(false);
		
		// save contient bsave
		save = new JPanel();
		save.setBackground(ModeleurModel.DARKGREY2);
		save.setPreferredSize(new Dimension(130*2,45*2));
		save.add(bSave);
		
		// bg contient le toolbar et le graphe
		bg = new JPanel();
		bg.setLayout(new BorderLayout(10,10));
		bg.setBackground(ModeleurModel.GREY);
		bg.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		
		// toolbar contient menu et save
		toolbar = new JPanel();
		toolbar.setLayout(new BorderLayout(10,10));
		toolbar.setBackground(ModeleurModel.DARKGREY2);
		
		graph = new Graph();
		graph.setPreferredSize(new Dimension(535*2,340*2));
	
		//toolbar.add(menu, BorderLayout.CENTER);
		toolbar.add(optsMode, BorderLayout.CENTER);
		toolbar.add(save, BorderLayout.SOUTH);
				
		bg.add(graph, BorderLayout.CENTER);
		bg.add(toolbar, BorderLayout.WEST);
		
	}
	
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