/**
 * PT3 Poly Editor
 * DUT Informatique 2016/2017 
 * Auteur : HUANG Qijia
 * 			LU Yi
 * Tuteur : P. Even
 * */

package Navigateur;

import static java.awt.event.KeyEvent.VK_A;
import static java.awt.event.KeyEvent.VK_D;
import static java.awt.event.KeyEvent.VK_DOWN;
import static java.awt.event.KeyEvent.VK_L;
import static java.awt.event.KeyEvent.VK_S;
import static java.awt.event.KeyEvent.VK_UP;
import static java.awt.event.KeyEvent.VK_W;
import static java.awt.event.KeyEvent.VK_SPACE;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.jogamp.newt.event.MouseEvent;
import com.jogamp.newt.event.MouseListener;
import Blueprint.Room;



public class NavigateurController implements KeyListener,ActionListener,MouseListener{
	private NavigateurModel model;
	private NavigateurView renderer;

	public NavigateurController(NavigateurModel model){
		this.model = model;

	}

	@Override
	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()){
		case VK_A: // player turns left (scene rotates right)
			model.turnLeft();
			break;
		case VK_D: // player turns right (scene rotates left)
			model.turnRight();
			break;
		case VK_W:	// player move in, posX and posZ become smaller
			model.moveIn();
			break;
		case VK_S: //player move out, posX and posZ become bigger
			model.moveOut();
			break;
		case VK_UP: // player looks up, scene rotates in negative x-axis
			model.lookUp();
			break;
		case VK_DOWN: // player looks down, scene rotates in positive x-axis
			model.lookDown();
			break;
		case VK_L: // toggle light on/off
			model.turnLight();
			break;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void actionPerformed(ActionEvent e ) {
		// TODO Auto-generated method stub 
		Object source = e.getSource();
		if ( source == model.openItem){
			new Thread() {
				public void run() {
					if (model.animator.isStarted()){
						model.animator.stop();
						model.openDia.setVisible(true);  
						String dirPath = model.openDia.getDirectory();  
						String fileName = model.openDia.getFile(); 
						model.filename=dirPath+fileName;
						model.animator.start();
					}
				}
			}.start();
		}


		if(source == model.menutexture){
			new Thread() {
				public void run() {
					if (model.animator.isStarted()){
						model.animator.stop();
						model.textureDia.setVisible(true);				
						String TextureName = model.textureDia.getFile();
						model.textureFileName="images/"+TextureName;
						model.textureFileName=TextureName.substring(TextureName.lastIndexOf('.'));
						List<String> Key = new ArrayList<String>(model.texture.keySet());   
						// mettre la condition de tous les textures en false
						for(String s : Key){
							if(renderer.getText().get(model.texture.get(s))){
								renderer.getText().remove(s);
								renderer.getText().put(s, false);
							}
						}
						//charge la condition de texutre qu'on a choisi
						renderer.getText().remove(model.textureFileName);
						renderer.getText().put(model.textureFileName,true);
						
						model.animator.start();
					}
				}
			}.start();
		}
		if (source == model.closeItem){
			System.exit(0);
		}

	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseWheelMoved(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}


}