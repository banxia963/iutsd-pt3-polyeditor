package Blueprint;

import java.awt.Graphics;
import java.util.ArrayList;

public class Corridor extends Space{
	
	/** Constructeur par default */
	public Corridor(){
		walls.add(new Wall(new Vertex(520,120),new Vertex(520,800)));
	}
	
	@Override
	public void draw(Graphics g) {
		for (Wall w : walls){
			w.draw(g);
		}
	}

}
