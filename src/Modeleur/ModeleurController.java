/**
 * PT3 Poly Editor
 * DUT Informatique 2016/2017 
 * Auteur : HUANG Qijia
 * 			LU Yi
 * Tuteur : P. Even
 * */

package Modeleur;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

import Blueprint.Wall;

/**
 * class ModeleurController 
 * celui-ci dirige tous les instructions de fonctionnalite de modeleur.
 * */
public class ModeleurController implements MouseListener, MouseMotionListener {
	/** ModeleurModel qui fournit tous les variables et instances, ainsi que les methodes de fonctionnalite */
	ModeleurModel mm;
	
	public ModeleurController(ModeleurModel mm) {
		this.mm = mm;
	}

	
	
	@Override
	public void mouseClicked(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
		
		mm.w.getV1().select(x,y);
		mm.w.getV2().select(x, y);
		mm.w.select(x, y);
		
		for (Wall w : mm.room.getWalls()){
			w.getV1().select(x, y);
			w.getV2().select(x, y);
			w.select(x, y);
			mm.graph.repaint();
		}
		
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

	@Override
	public void mouseDragged(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();

		for (Wall w : mm.room.getWalls()){
		if (w.getV1().isSelected()){
			w.getV1().move(x-25/2, y-25/2);
			mm.graph.repaint();
		}
		if (w.getV2().isSelected()){
			w.getV2().move(x-25/2, y-25/2);
			mm.graph.repaint();
		}
		}
		
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
