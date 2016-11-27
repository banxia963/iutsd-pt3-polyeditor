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
		float weight = 10f;
		float b = 0;
		float x =0;
		float z =0;
		float X1=0,X2=0,X3=0,X4=0,Z1=0,Z2=0,Z3=0,Z4=0;

		if (v1.getY()-v2.getY()!=0) {
			b = -((v1.getX()-v2.getX())/(v1.getY()-v2.getY()));
			x = (float) ((weight/2)*Math.sqrt(1/(1+b*b)));
			z = (float) (Math.sqrt((weight*weight))/2*(1-(1/(1+b*b))));
		
			if(b> 0){
				X1= v1.getX()+x;
				X2= v1.getX()-x;
				X3= v2.getX()+x;
				X4= v2.getX()-x;

				Z1 = v1.getY()+z;
				Z2 = v1.getY()-z;
				Z3 = v2.getY()+z;
				Z4 = v2.getY()-z;
			} else if(b < 0){
				X1= v1.getX()-x;
				X2= v1.getX()+x;
				X3= v2.getX()-x;
				X4= v2.getX()+x;

				Z1 = v1.getY()+z;
				Z2 = v1.getY()-z;
				Z3 = v2.getY()+z;
				Z4 = v2.getY()-z;
			} else {
				X1=v1.getX()+weight/2;
				X2=v1.getX()-weight/2;
				X3=v1.getX()+weight/2;
				X4=v1.getX()-weight/2;
				
				Z1 = v1.getY();
				Z2 = v1.getY();
				Z3 = v2.getY();
				Z4 = v2.getY();
			}
		} else {
			X1=v1.getX();
			X2=v1.getX();
			X3=v2.getX();
			X4=v2.getX();
			Z1 = v1.getY()+weight/2;
			Z2 = v1.getY()-weight/2;
			Z3 = v2.getY()+weight/2;
			Z4 = v2.getY()-weight/2;
		}

		
		gl.glBegin(GL2.GL_QUADS);
		
			gl.glColor3f(1.0f, 0.0f, 0.0f); 
			gl.glVertex3f(X1/100, 2.0f, Z1/100);
			gl.glVertex3f(X2/100, 2.0f, Z2/100);
			gl.glVertex3f(X2/100, 1.5f, Z2/100);
			gl.glVertex3f(X1/100, 1.5f, Z1/100);
			
			gl.glVertex3f(X1/100, 2.0f, Z1/100);
			gl.glVertex3f(X3/100, 2.0f, Z3/100);
			gl.glVertex3f(X3/100, 1.5f, Z3/100);
			gl.glVertex3f(X1/100, 1.5f, Z1/100);
		
			gl.glVertex3f(X2/100, 2.0f, Z2/100);
			gl.glVertex3f(X4/100, 2.0f, Z4/100);
			gl.glVertex3f(X4/100, 1.5f, Z4/100);
			gl.glVertex3f(X2/100, 1.5f, Z2/100);
		
			gl.glVertex3f(X3/100, 2.0f, Z3/100);
			gl.glVertex3f(X4/100, 2.0f, Z4/100);
			gl.glVertex3f(X4/100, 1.5f, Z4/100);
			gl.glVertex3f(X3/100, 1.5f, Z3/100);

			gl.glVertex3f(X1/100, 1.5f, Z1/100);
			gl.glVertex3f(X2/100, 1.5f, Z2/100);
			gl.glVertex3f(X4/100, 1.5f, Z4/100);
			gl.glVertex3f(X3/100, 1.5f, Z3/100);
			
			gl.glVertex3f(X1/100, 2.0f, Z1/100);
			gl.glVertex3f(X2/100, 2.0f, Z2/100);
			gl.glVertex3f(X4/100, 2.0f, Z4/100);
			gl.glVertex3f(X3/100, 2.0f, Z3/100);

		gl.glEnd();
		
	}

	@Override
	public void move(float x1, float y1, float x2, float y2) {
		v1.move(x1, y1);
		v2.move(x2, y2);
		midv.move((x1+x2)/2,(y1+y2)/2);
	}

}
