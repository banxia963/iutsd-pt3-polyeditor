/**
 * PT3 Poly Editor
 * DUT Informatique 2016/2017 
 * Auteur : HUANG Qijia
 * 			LU Yi
 * Tuteur : P. Even
 * */

package Modeleur;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

/** 
 * class MoeleurView 
 * celui-ci construit l'interface de modeleur. 
 * */
public class ModeleurView extends JFrame {
	/** ModeleurModel qui fournit tous les variables et instances */
	private ModeleurModel mm;
	/** ModeleurController qui gere les fonctions de souris */
	private ModeleurController mc;
	
	// les variables de tests
	// Diriger et claser a la fin pour les mettre dans la class ModeleurModel
	private JPanel bg, toolbar, options, save;
	private Menu menu;
	private Image demo;
	
	
	
	
	public ModeleurView(String titre){
		super(titre);
		mm = new ModeleurModel();
		mc = new ModeleurController(mm);
		
		// bg contient le toolbar et le graphe
		bg = new JPanel();
		bg.setLayout(new BorderLayout(10,10));
		bg.setBackground(ModeleurModel.GREY);
		bg.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		
		// toolbar contient menu et save
		toolbar = new JPanel();
		toolbar.setLayout(new BorderLayout(10,10));
		toolbar.setBackground(ModeleurModel.DARKGREY2);
		
		// menu contient options
		menu = new Menu();
		menu.setPreferredSize(new Dimension(130*2,420*2));
		menu.addMouseListener(menu);
		
		// options contient les buttons
		options = new JPanel();
		options.setBackground(ModeleurModel.DARKGREY4);
		options.setPreferredSize(new Dimension(120*2,50*2));
		
		// save contient bsave
		save = new JPanel();
		save.setBackground(ModeleurModel.DARKGREY2);
		save.setPreferredSize(new Dimension(130*2,45*2));
		
		// le bouton d'enregistrement
		Font font = new Font("Arial", Font.BOLD, 20);
		mm.bSave = new JButton("ENREGISTRER");
		mm.bSave.setFont(font);
		mm.bSave.setForeground(ModeleurModel.BLACK);
		mm.bSave.setBackground(ModeleurModel.DARKGREY4);
		mm.bSave.setPreferredSize(new Dimension(110*2,70));
		mm.bSave.setFocusPainted(false);
		mm.bSave.addActionListener(mc);
		save.add(mm.bSave);
		
		mm.graph.setPreferredSize(new Dimension(550*2,340*2));
		mm.graph.addMouseListener(mc);
		mm.graph.addMouseMotionListener(mc);
		
		toolbar.add(menu, BorderLayout.CENTER);
		toolbar.add(save, BorderLayout.SOUTH);
		
		bg.add(mm.graph, BorderLayout.CENTER);
		bg.add(toolbar, BorderLayout.WEST);
		
		this.add(bg);
		
	}
	
	
	/**
	 * class interne Menu
	 * celui-ci dessine le Menu qui continet les options de tratement de graphe
	 * */
	class Menu extends JPanel implements MouseListener{
		Menu(){
			try{
				demo = ImageIO.read(new File("demo.png"));
			} catch (Exception e){};
		}
		
		public void paintComponent (Graphics g) {
			super.paintComponent(g);
			this.setBackground(ModeleurModel.DARKGREY2);
			g.setColor(ModeleurModel.DARKGREY4);
			g.fillRoundRect(20, 20, 110*2,410*2, 50, 50);
			
			g.drawImage(demo, 20, 20, this);
			g.drawImage(demo, 20, 110, this);
			g.drawImage(demo, 20, 200, this);
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			int x = e.getX();
			int y = e.getY();

			if ( 20 < x && x < 20+110*2){
				if (y > 20 && y < 110){
					mm.room.addVertex();
				}
				else if (y>110 && y<200){
					mm.room.addDoor("Door");
				}
				else if (y>200 && y<290){
					mm.room.addWindow("Window");
				}
			}
			
			mm.graph.validate();
			mm.graph.repaint();
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
	}

}