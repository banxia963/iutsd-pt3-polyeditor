/**
 * PT3 Poly Editor
 * DUT Informatique 2016/2017 
 * Auteur : HUANG Qijia
 * 			LU Yi
 * Tuteur : P. Even
 * */

package Modeleur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.IOException;
import Blueprint.Room;
import Blueprint.Wall;

/**
 * class ModeleurController 
 * celui-ci dirige tous les instructions de fonctionnalite de modeleur.
 * */
public class ModeleurController implements ActionListener, MouseListener, MouseMotionListener {
	/** ModeleurModel qui fournit tous les variables et instances, ainsi que les methodes de fonctionnalite */
	ModeleurModel mm;
	
	public ModeleurController(ModeleurModel mm) {
		this.mm = mm;
	}

	
	
	@Override
	public void mouseClicked(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
		
		for (Wall w : mm.room.getWalls()){
			w.getV1().select(x, y);
			w.getV2().select(x, y);
			w.select(x, y);
			if(w.getOpen()!=null){
				w.getOpen().getV1().select(x, y);
				w.getOpen().getV2().select(x, y);
			}
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
		float x = e.getX();
		float y = e.getY();
		
		for (Wall w : mm.room.getWalls()){
			if(w.isSelected()){
				float[] list=w.move(x, y);
				mm.room.nextWall(w).getV1().move(list[2], list[3]);
				mm.room.lastWall(w).getV2().move(list[0], list[1]);
				mm.graph.repaint();
			}	
			else if (w.getV1().isSelected()){
				w.getV1().move(x-25/2, y-25/2);
				mm.graph.repaint();
			}
			else if (w.getV2().isSelected()){
				w.getV2().move(x-25/2, y-25/2);
				mm.graph.repaint();
			}
			else if (w.getOpen() != null){
				float[] l=w.moveOpen(x, y);
				float r=w.ratioOpen(x, y);
				if(w.getOpen().getV1().isSelected()){
					w.getOpen().getV1().move(l[0], l[1]);
					w.getOpen().setR1(r);
				}
				else if (w.getOpen().getV2().isSelected()){
					w.getOpen().getV2().move(l[0], l[1]);
					w.getOpen().serR2(r);
				}
				mm.graph.repaint();
			}
		}
		
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		if (source == mm.bSave){
			try {
				mm.room.write();
				Room room2= new Room();
				room2.read("test.txt");
			} catch (IOException exception){
				
			}
		}
		
	}

}