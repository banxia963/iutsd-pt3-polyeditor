
/**
 * PT3 Poly Editor
 * DUT Informatique 2016/2017 
 * Auteur : HUANG Qijia
 * 			LU Yi
 * Tuteur : P. Even
 * */


package Blueprint;

import java.awt.Graphics;
import java.util.ArrayList;

public abstract class Space {
	protected ArrayList<Wall> walls = new ArrayList();
	protected String id;
	
	public abstract void draw(Graphics g);
}
