/**
 * PT3 Poly Editor
 * DUT Informatique 2016/2017 
 * Auteur : HUANG Qijia
 * 			LU Yi
 * Tuteur : P. Even
 * */

package Modeleur;

import javax.swing.JFrame;

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
	
	public ModeleurView(String titre){
		super(titre);
		mm = new ModeleurModel();
		mc = new ModeleurController(mm);
		
		// options contient les buttons
		
		mm.bRoom.addActionListener(mc);
		mm.bCorridor.addActionListener(mc);
		
		// le bouton d'enregistrement
		mm.bSave.addActionListener(mc);
		
		mm.bDelVertex.addActionListener(mc);
		
		mm.graph.addMouseListener(mc);
		mm.graph.addMouseMotionListener(mc);
		
		this.add(mm.bg);
		
	}

}