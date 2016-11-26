package Blueprint;

import java.awt.Graphics;

import com.jogamp.opengl.GL2;

public class Window extends Open {

	public Window(String id, Vertex v1, Vertex v2) {
		super(id, v1, v2);
	}

	@Override
	public void draw(Graphics g) {
		v1.draw(g);
		v2.draw(g);
	}

	@Override
	public void draw(GL2 gl) {
		// TODO Auto-generated method stub
		
	}

}
