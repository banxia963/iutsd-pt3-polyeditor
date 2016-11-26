/**
 * PT3 Poly Editor
 * DUT Informatique 2016/2017 
 * Auteur : HUANG Qijia
 * 			LU Yi
 * Tuteur : P. Even
 * */

package Blueprint;

import static com.jogamp.opengl.GL.GL_LINEAR;
import static com.jogamp.opengl.GL.GL_TEXTURE_2D;
import static com.jogamp.opengl.GL.GL_TEXTURE_MAG_FILTER;
import static com.jogamp.opengl.GL.GL_TEXTURE_MIN_FILTER;

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.io.IOException;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLException;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureCoords;
import com.jogamp.opengl.util.texture.TextureIO;

import Modeleur.ModeleurModel;
import Navigateur.NavigateurView;

public class Wall {
	private Vertex v1;
	private Vertex v2;
	private boolean selected=false;
	private Open o=null;


	
	
	//Rayon
	private float r = 25/2;

	public Wall(Vertex v1, Vertex v2) {
		super();
		this.v1 = v1;
		this.v2 = v2;
	}
	
	public Vertex getV1(){
		return v1;
	}
	
	public Vertex getV2(){
		return v2;
	}
	
	public Open getOpen(){
		return o;
	}
	
	public boolean isSelected(){
		return selected;
	}
	
	public void select() {
		selected = !selected;	
	}
	
	public void select(int x, int y){
		if(selected) {
			selected=!selected;
			return;
			}
		
		float disX = v2.getX()-v1.getX();
		float disY = v2.getY()-v1.getY();
		float disXY = (float) Math.sqrt(disX*disX+disY*disY);
		float supX = r * (disX/disXY);
		float supY = r * (disY/disXY);
		
		Line2D l = new Line2D.Float(v1.getX()+r+supX*2, v1.getY()+r+supY*2, v2.getX()+r-supX*2, v2.getY()+r-supY*2);
		
		float maxX=v1.getX()+r+supX*2;
		float minX=v2.getX()+r-supX*2;
		float maxY=v1.getY()+r+supY*2;
		float minY=v2.getY()+r-supY*2;
		
		if(maxX<minX){
			float tmp=maxX;
			maxX=minX;
			minX=tmp;
		}
		
		if(maxY<minY){
			float tmp=maxY;
			maxY=minY;
			minY=tmp;
		}
		
		boolean betweenX= x <= maxX && x >= minX ;
		boolean betweenY= y <= maxY && y >= minY;
		
		if(minX==maxX) betweenX=true;
		if(maxY==minY) betweenY=true;
		
		if (l.ptLineDist(x, y) <= r && betweenX && betweenY){
			selected=true;			
		}
		else{
			selected=false;
		}
		
	}
	
	public void move(int x, int y){
		
	}
	
	public void draw(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		if (selected){
			g2.setColor(ModeleurModel.WHITE);
		}
		else{
			g2.setColor(ModeleurModel.BLACK);
		}
		g2.setStroke(new BasicStroke(10));
		g2.draw(new Line2D.Float(v1.getX()+r, v1.getY()+r, v2.getX()+r, v2.getY()+r));
		
		v1.draw(g);
		v2.draw(g);
		
		if(o!=null){
			addDoor("Door");
			o.draw(g);
		}
	
	}
	
	public void draw(GL2 gl)
	{
		if(o==null){
			float weight = 20f;
			float b = 0;
			float x =0;
			float z =0;
			float X1=0,X2=0,X3=0,X4=0,Z1=0,Z2=0,Z3=0,Z4=0;
			if(v1.getY()-v2.getY()!=0){
			b = -((v1.getX()-v2.getX())/(v1.getY()-v2.getY()));
			x = (float) ((weight/2)*Math.sqrt(1/(1-b*b)));
			z = (float) ((weight/2)*Math.sqrt(b*b/(1-b*b)));
			
			if(b> 0){
				X1= v1.getX()+x;
				X2= v1.getX()-x;
				X3= v2.getX()+x;
				X4= v2.getX()-x;

				Z1 = v1.getY()+z;
				Z2 = v1.getY()-z;
				Z3 = v2.getY()+z;
				Z4 = v2.getY()-z;
			}
			if(b< 0){
				X1= v1.getX()-x;
				X2= v1.getX()+x;
				X3= v2.getX()-x;
				X4= v2.getX()+x;

				Z1 = v1.getY()+z;
				Z2 = v1.getY()-z;
				Z3 = v2.getY()+z;
				Z4 = v2.getY()-z;
			}
			if(b==0){
				X1=v1.getX()+weight/2;
				X2=v1.getX()-weight/2;
				X3=v1.getX()+weight/2;
				X4=v1.getX()-weight/2;
				Z1 = v1.getY();
				Z2 = v1.getY();
				Z3 = v2.getY();
				Z4 = v2.getY();
			}
			}
			else{
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
			
			
			gl.glVertex3f(X1/100, 2.0f, Z1/100);
			gl.glVertex3f(X2/100, 2.0f, Z2/100);
			gl.glVertex3f(X2/100, 0.0f, Z2/100);
			gl.glVertex3f(X1/100, 0.0f, Z1/100);
			
			gl.glVertex3f(X1/100, 2.0f, Z1/100);
			gl.glVertex3f(X3/100, 2.0f, Z3/100);
			gl.glVertex3f(X3/100, 0.0f, Z3/100);
			gl.glVertex3f(X1/100, 0.0f, Z1/100);
		
			gl.glVertex3f(X2/100, 2.0f, Z2/100);
			gl.glVertex3f(X4/100, 2.0f, Z4/100);
			gl.glVertex3f(X4/100, 0.0f, Z4/100);
			gl.glVertex3f(X2/100, 0.0f, Z2/100);
		
			gl.glVertex3f(X3/100, 2.0f, Z3/100);
			gl.glVertex3f(X4/100, 2.0f, Z4/100);
			gl.glVertex3f(X4/100, 0.0f, Z4/100);
			gl.glVertex3f(X2/100, 0.0f, Z2/100);

			gl.glVertex3f(X1/100, 0.0f, Z1/100);
			gl.glVertex3f(X2/100, 0.0f, Z2/100);
			gl.glVertex3f(X4/100, 0.0f, Z4/100);
			gl.glVertex3f(X3/100, 0.0f, Z3/100);
			
			gl.glVertex3f(X1/100, 2.0f, Z1/100);
			gl.glVertex3f(X2/100, 2.0f, Z2/100);
			gl.glVertex3f(X4/100, 2.0f, Z4/100);
			gl.glVertex3f(X3/100, 2.0f, Z3/100);
		
			
			
			gl.glEnd();
			
		} else {
			gl.glBegin(GL2.GL_QUADS);
			
			gl.glColor3f(0.8f, 0.3f, 0.8f);
			gl.glVertex3f(v1.getX()/100, 1.0f, v1.getY()/100);
			gl.glVertex3f(o.getV1().getX()/100, 1.0f, o.getV1().getY()/100);
			gl.glVertex3f(o.getV1().getX()/100, 0.0f, o.getV1().getY()/100);
			gl.glVertex3f(v1.getX()/100, 0.0f, v1.getY()/100);
			
			o.draw(gl);
			
			gl.glVertex3f(o.getV2().getX()/100, 1.0f, o.getV2().getY()/100);
			gl.glVertex3f(v2.getX()/100, 1.0f, v2.getY()/100);
			gl.glVertex3f(v2.getX()/100, 0.0f, v2.getY()/100);
			gl.glVertex3f(o.getV2().getX()/100, 0.0f, o.getV2().getY()/100);
		
		gl.glEnd();
		}
	}

	public void draw(GL2 gl, float tT, float tB, float tL, float tR){
		float weight = 20f;
		float b = 0;
		float x =0;
		float z =0;
		float X1=0,X2=0,X3=0,X4=0,Z1=0,Z2=0,Z3=0,Z4=0;
		if(v1.getY()-v2.getY()!=0){
		b = -((v1.getX()-v2.getX())/(v1.getY()-v2.getY()));
		x = (float) ((weight/2)*Math.sqrt(1/(1-b*b)));
		z = (float) ((weight/2)*Math.sqrt(b*b/(1-b*b)));
		
		if(b> 0){
			X1= v1.getX()+x;
			X2= v1.getX()-x;
			X3= v2.getX()+x;
			X4= v2.getX()-x;

			Z1 = v1.getY()+z;
			Z2 = v1.getY()-z;
			Z3 = v2.getY()+z;
			Z4 = v2.getY()-z;
		}
		if(b< 0){
			X1= v1.getX()-x;
			X2= v1.getX()+x;
			X3= v2.getX()-x;
			X4= v2.getX()+x;

			Z1 = v1.getY()+z;
			Z2 = v1.getY()-z;
			Z3 = v2.getY()+z;
			Z4 = v2.getY()-z;
		}
		if(b==0){
			X1=v1.getX()+weight/2;
			X2=v1.getX()-weight/2;
			X3=v1.getX()+weight/2;
			X4=v1.getX()-weight/2;
			Z1 = v1.getY();
			Z2 = v1.getY();
			Z3 = v2.getY();
			Z4 = v2.getY();
		}
		}
		else{
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
		
		gl.glTexCoord2f(tL,tB);
		gl.glVertex3f(X1/100, 2.0f, Z1/100);
		gl.glTexCoord2f(tR, tB);
		gl.glVertex3f(X2/100, 2.0f, Z2/100);
		gl.glTexCoord2f(tR, tT);
		gl.glVertex3f(X2/100, 0.0f, Z2/100);
		gl.glTexCoord2f(tL, tT);
		gl.glVertex3f(X1/100, 0.0f, Z1/100);
		
		gl.glTexCoord2f(tL,tB);
		gl.glVertex3f(X1/100, 2.0f, Z1/100);
		gl.glTexCoord2f(tR, tB);
		gl.glVertex3f(X3/100, 2.0f, Z3/100);
		gl.glTexCoord2f(tR, tT);
		gl.glVertex3f(X3/100, 0.0f, Z3/100);
		gl.glTexCoord2f(tL, tT);
		gl.glVertex3f(X1/100, 0.0f, Z1/100);
	
		gl.glTexCoord2f(tL,tB);
		gl.glVertex3f(X2/100, 2.0f, Z2/100);
		gl.glTexCoord2f(tR, tB);
		gl.glVertex3f(X4/100, 2.0f, Z4/100);
		gl.glTexCoord2f(tR, tT);
		gl.glVertex3f(X4/100, 0.0f, Z4/100);
		gl.glTexCoord2f(tL, tT);
		gl.glVertex3f(X2/100, 0.0f, Z2/100);
	
		gl.glTexCoord2f(tL,tB);
		gl.glVertex3f(X3/100, 2.0f, Z3/100);
		gl.glTexCoord2f(tR, tB);
		gl.glVertex3f(X4/100, 2.0f, Z4/100);
		gl.glTexCoord2f(tR, tT);
		gl.glVertex3f(X4/100, 0.0f, Z4/100);
		gl.glTexCoord2f(tL, tT);
		gl.glVertex3f(X2/100, 0.0f, Z2/100);
	
		gl.glTexCoord2f(tL,tB);
		gl.glVertex3f(X1/100, 0.0f, Z1/100);
		gl.glTexCoord2f(tR, tB);
		gl.glVertex3f(X2/100, 0.0f, Z2/100);
		gl.glTexCoord2f(tR, tT);
		gl.glVertex3f(X3/100, 0.0f, Z3/100);
		gl.glTexCoord2f(tL, tT);
		gl.glVertex3f(X4/100, 0.0f, Z4/100);
	
		gl.glTexCoord2f(tL,tB);
		gl.glVertex3f(X1/500, 2.0f, Z1/500);
		gl.glTexCoord2f(tR, tB);
		gl.glVertex3f(X2/500, 2.0f, Z2/500);
		gl.glTexCoord2f(tR, tT);
		gl.glVertex3f(X3/500, 2.0f, Z3/500);
		gl.glTexCoord2f(tL, tT);
		gl.glVertex3f(X4/500, 2.0f, Z4/500);
	
		
		gl.glEnd();
	}
	

	public void addDoor(String id) {
		float midX=(v1.getX()+v2.getX())/2;
		float midY=(v1.getY()+v2.getY())/2;
		Vertex c1 = new Vertex((v1.getX()+midX)/2,(v1.getY()+midY)/2);
		Vertex c2 = new Vertex((v2.getX()+midX)/2,(v2.getY()+midY)/2);
		o = new Door(id, c1, c2);		
	}

	public void addDoor(String id, float f, float g, float h, float i){
		Vertex c1 = new Vertex(f,g);
		Vertex c2 = new Vertex(h,i);
		o = new Door(id, c1, c2);
	}
	
}
