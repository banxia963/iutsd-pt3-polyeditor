package Navigateur;

import static java.awt.event.KeyEvent.VK_A;
import static java.awt.event.KeyEvent.VK_D;
import static java.awt.event.KeyEvent.VK_DOWN;
import static java.awt.event.KeyEvent.VK_L;
import static java.awt.event.KeyEvent.VK_S;
import static java.awt.event.KeyEvent.VK_UP;
import static java.awt.event.KeyEvent.VK_W;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;



public class NavigateurController implements KeyListener {
	private NavigateurModel model;

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

}

