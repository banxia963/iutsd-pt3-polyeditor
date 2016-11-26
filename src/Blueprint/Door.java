/**
 * PT3 Poly Editor
 * DUT Informatique 2016/2017 
 * Auteur : HUANG Qijia
 * 			LU Yi
 * Tuteur : P. Even
 * */

package Blueprint;

import java.awt.Graphics;

import com.jogamp.opengl.GL2;

public class Door extends Open {
	private Vertex midv;

	public Door(String id, Vertex v1, Vertex v2) {
		super(id, v1, v2);
		midv=new Vertex((v1.getX()+v2.getX())/2, (v1.getY()+v2.getY())/2);
	}

	@Override
	public void draw(Graphics g) {
		v1.draw(g);
		v2.draw(g);
		midv.draw(g);
	}

	@Override
	public void draw(GL2 gl) {
		gl.glVertex3f(v1.getX()/100, 1.0f, v1.getY()/100);
		gl.glVertex3f(v2.getX()/100, 1.0f, v2.getY()/100);
		gl.glVertex3f(v2.getX()/100, 0.8f, v2.getY()/100);
		gl.glVertex3f(v1.getX()/100, 0.8f, v1.getY()/100);
		
	}

}
