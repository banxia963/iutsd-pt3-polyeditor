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
	private boolean entrant;

	public Door(String id, Vertex v1, Vertex v2) {
		super(id, v1, v2);
		midv=new Vertex((v1.getX()+v2.getX())/2, (v1.getY()+v2.getY())/2);
	}

	@Override
	public void draw(Graphics g) {
		getV1().draw(g);
		getV2().draw(g);
		midv.draw(g);
	}

	@Override
	public void draw(GL2 gl) {
		float weight = 10f;
		float doorheight_haut = 1.0f;
		float doorheight_bas = 0.75f;
		float b = 0;
		float x =0;
		float z =0;
		float X1=0,X2=0,X3=0,X4=0,Z1=0,Z2=0,Z3=0,Z4=0;
		
		Vertex v1,v2;
		try {
			v1 = (Vertex) getV1().clone();
			v2 = (Vertex) getV2().clone();
			
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
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		gl.glBegin(GL2.GL_QUADS);
		

			gl.glColor3f(1.0f, 0.0f, 0.0f); 
			gl.glVertex3f(X1/100, doorheight_haut, Z1/100);
			gl.glVertex3f(X2/100, doorheight_haut, Z2/100);
			gl.glVertex3f(X2/100, doorheight_bas, Z2/100);
			gl.glVertex3f(X1/100, doorheight_bas, Z1/100);
			
			gl.glVertex3f(X1/100, doorheight_haut, Z1/100);
			gl.glVertex3f(X3/100, doorheight_haut, Z3/100);
			gl.glVertex3f(X3/100, doorheight_bas, Z3/100);
			gl.glVertex3f(X1/100, doorheight_bas, Z1/100);

		

			gl.glVertex3f(X2/100, doorheight_haut, Z2/100);
			gl.glVertex3f(X4/100, doorheight_haut, Z4/100);
			gl.glVertex3f(X4/100, doorheight_bas, Z4/100);
			gl.glVertex3f(X2/100, doorheight_bas, Z2/100);
		
			gl.glVertex3f(X3/100, doorheight_haut, Z3/100);
			gl.glVertex3f(X4/100, doorheight_haut, Z4/100);
			gl.glVertex3f(X4/100, doorheight_bas, Z4/100);
			gl.glVertex3f(X3/100, doorheight_bas, Z3/100);



			gl.glVertex3f(X1/100, doorheight_bas, Z1/100);
			gl.glVertex3f(X2/100, doorheight_bas, Z2/100);
			gl.glVertex3f(X4/100, doorheight_bas, Z4/100);
			gl.glVertex3f(X3/100, doorheight_bas, Z3/100);
			
			gl.glVertex3f(X1/100, doorheight_haut, Z1/100);
			gl.glVertex3f(X2/100, doorheight_haut, Z2/100);
			gl.glVertex3f(X4/100, doorheight_haut, Z4/100);
			gl.glVertex3f(X3/100, doorheight_haut, Z3/100);


		gl.glEnd();
	}

	@Override
	public void move(float x1, float y1, float x2, float y2) {
		getV1().move(x1, y1);
		getV2().move(x2, y2);
		midv.move((x1+x2)/2,(y1+y2)/2);
	}

	@Override
	public void draw(GL2 gl, float tT, float tB, float tL, float tR) {
		// TODO Auto-generated method stub
		float weight = 10f;
		float doorheight_haut = 1.0f;
		float doorheight_bas = 0.75f;
		float b = 0;
		float x =0;
		float z =0;
		float X1=0,X2=0,X3=0,X4=0,Z1=0,Z2=0,Z3=0,Z4=0;
		Vertex v1,v2;
		try {
			v1 = (Vertex) getV1().clone();
			v2 = (Vertex) getV2().clone();
			if(v1.getY()-v2.getY()!=0){
				b = -((v1.getX()-v2.getX())/(v1.getY()-v2.getY()));
				x = (float) ((weight/2)*Math.sqrt(1/(1+b*b))); 
				z = (float) (Math.sqrt((weight*weight))/2*(1-(1/(1+b*b))));

				if ( b > 0){
					X1= v1.getX()+x;
					X2= v1.getX()-x;
					X3= v2.getX()+x;
					X4= v2.getX()-x;

					Z1 = v1.getY()+z;
					Z2 = v1.getY()-z;
					Z3 = v2.getY()+z;
					Z4 = v2.getY()-z;
				} else if( b < 0){
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
		
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		gl.glBegin(GL2.GL_QUADS);
		
		gl.glTexCoord2f(tL,tB);
		gl.glVertex3f(X1/100, doorheight_haut, Z1/100);
		gl.glTexCoord2f(tR, tB);
		gl.glVertex3f(X2/100, doorheight_haut, Z2/100);
		gl.glTexCoord2f(tR, tT);
		gl.glVertex3f(X2/100, doorheight_bas, Z2/100);
		gl.glTexCoord2f(tL, tT);
		gl.glVertex3f(X1/100, doorheight_bas, Z1/100);
		
		gl.glTexCoord2f(tR,tB);
		gl.glVertex3f(X1/100, doorheight_haut, Z1/100);
		gl.glTexCoord2f(tR, tT);
		gl.glVertex3f(X3/100, doorheight_haut, Z3/100);
		gl.glTexCoord2f(tL, tT);
		gl.glVertex3f(X3/100, doorheight_bas, Z3/100);
		gl.glTexCoord2f(tL, tB);
		gl.glVertex3f(X1/100, doorheight_bas, Z1/100);
	
		gl.glTexCoord2f(tL,tT);
		gl.glVertex3f(X2/100, doorheight_haut, Z2/100);
		gl.glTexCoord2f(tL, tB);
		gl.glVertex3f(X4/100, doorheight_haut, Z4/100);
		gl.glTexCoord2f(tR, tB);
		gl.glVertex3f(X4/100, doorheight_bas, Z4/100);
		gl.glTexCoord2f(tR, tT);
		gl.glVertex3f(X2/100, doorheight_bas, Z2/100);
	
		gl.glTexCoord2f(tR,tT);
		gl.glVertex3f(X3/100, doorheight_haut, Z3/100);
		gl.glTexCoord2f(tL, tT);
		gl.glVertex3f(X4/100, doorheight_haut, Z4/100);
		gl.glTexCoord2f(tL, tB);
		gl.glVertex3f(X4/100, doorheight_bas, Z4/100);
		gl.glTexCoord2f(tR, tB);
		gl.glVertex3f(X3/100, doorheight_bas, Z3/100);
	
		gl.glTexCoord2f(tR,tT);
		gl.glVertex3f(X1/100, doorheight_bas, Z1/100);
		gl.glTexCoord2f(tL, tT);
		gl.glVertex3f(X2/100, doorheight_bas, Z2/100);
		gl.glTexCoord2f(tL, tB);
		gl.glVertex3f(X4/100, doorheight_bas, Z4/100);
		gl.glTexCoord2f(tR, tB);
		gl.glVertex3f(X3/100, doorheight_bas, Z3/100);
		
		gl.glTexCoord2f(tL,tT);
		gl.glVertex3f(X1/100, doorheight_haut, Z1/100);
		gl.glTexCoord2f(tL, tB);
		gl.glVertex3f(X2/100, doorheight_haut, Z2/100);
		gl.glTexCoord2f(tR, tB);
		gl.glVertex3f(X4/100, doorheight_haut, Z4/100);
		gl.glTexCoord2f(tR, tT);
		gl.glVertex3f(X3/100, doorheight_haut, Z3/100);
		gl.glEnd();
		
	}

}
