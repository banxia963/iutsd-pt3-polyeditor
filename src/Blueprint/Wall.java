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


	
	
	/** Rayon de Vertex */
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
	
	public boolean between(int x, int y, float x1, float y1, float x2, float y2){
		float maxX=x1;
		float minX=x2;
		float maxY=y1;
		float minY=y2;
		
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
		
		return betweenX && betweenY;
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
		
		if (o == null){
			
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
		
		} else{
			if(l.ptLineDist(x,y) <= r && ( between(x,y,v1.getX()+r+supX*2,v1.getY()+r+supY*2,o.getV1().getX()+r-supX*2,o.getV1().getY()+r-supY*2)
										|| between(x,y,o.getV1().getX()+r+supX*2,o.getV1().getY()+r+supY*2,o.getV2().getX()+r-supX*2,o.getV2().getY()+r-supY*2)
										|| between(x,y,o.getV2().getX()+r+supX*2,o.getV2().getY()+r+supY*2,v2.getX()+r-supX*2,v2.getY()+r-supY*2)
										)){
				selected = true;
			}else{
				selected=false;
			}
		}
	}
	
	public float[] move(float x, float y){
		Line2D l = new Line2D.Float(v1.getX()+r, v1.getY()+r, v2.getX()+r, v2.getY()+r);
		float v1x,v1y,v2x,v2y;
		
		if (v1.getX()==v2.getX()){
			v1x=x; 
			v1y=v1.getY();
			v2x=x; 
			v2y=v2.getY();
		}
		else if (v1.getY()==v2.getY()){
			v1x=v1.getX();
			v1y=y;
			v2x=v2.getX();
			v2y=y;
		}
		else {
			
			float a1=(v2.getY()-v1.getY())/(v2.getX()-v1.getX());
			float b1=v1.getY()-a1*v1.getX();
			float a2=-1/a1;
			float b2=y-a2*x;
			float vx=(b2-b1)/(a1-a2);
			float vy=vx*a1+b1;
			float mx=x-vx;
			float my=y-vy;
			v1x=v1.getX()+mx;
			v1y=v1.getY()+my;
			v2x=v2.getX()+mx;
			v2y=v2.getY()+my;
	
		}
		v1.move(v1x, v1y);
		v2.move(v2x, v2y);
		
		float[] list= {v1x,v1y,v2x,v2y};
		
		return list;
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
	
	public void updateOpen(){
		float disX = v2.getX()-v1.getX();
		float disY = v2.getY()-v1.getY();
		float disXY = (float) Math.sqrt(disX*disX+disY*disY);
		float f1 = o.getR1() * disXY;
		float f2 = o.getR2() * disXY;
		float supX1 = f1 * (disX/disXY);
		float supY1 = f1 * (disY/disXY);
		float supX2 = f2 * (disX/disXY);
		float supY2 = f2 * (disY/disXY);
		o.move(v1.getX()+supX1, v1.getY()+supY1, v1.getX()+supX2, v1.getY()+supY2);
		
	}
	
	public void addWindow(String id) {
		float midX=(v1.getX()+v2.getX())/2;
		float midY=(v1.getY()+v2.getY())/2;
		Vertex c1 = new Vertex((v1.getX()+midX)/2,(v1.getY()+midY)/2);
		Vertex c2 = new Vertex((v2.getX()+midX)/2,(v2.getY()+midY)/2);
		o = new Window(id, c1, c2);
	}
	
	public void addWindow(String id, float f, float g, float h, float i) {
		Vertex c1 = new Vertex(f,g);
		Vertex c2 = new Vertex(h,i);
		o = new Window(id, c1, c2);
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
			updateOpen();
			o.draw(g);
		}
	
	}
	
	public void draw(GL2 gl)
	{
		float weight = 10f;
		float b = 0;
		float x =0;
		float z =0;
		float X1=0,X2=0,X3=0,X4=0,Z1=0,Z2=0,Z3=0,Z4=0;
		
		if(o==null){
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
				gl.glVertex3f(X3/100, 0.0f, Z3/100);
	
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
			
			new Wall(v1,o.getV1()).draw(gl);
			o.draw(gl);
			new Wall(o.getV2(),v2).draw(gl);
		}
	}

	
	public void draw(GL2 gl, float tT, float tB, float tL, float tR){
		float weight = 10f;
		float b = 0;
		float x =0;
		float z =0;
		float X1=0,X2=0,X3=0,X4=0,Z1=0,Z2=0,Z3=0,Z4=0;
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
		gl.glBegin(GL2.GL_QUADS);
		
		gl.glTexCoord2f(tL,tB);
		gl.glVertex3f(X1/100, 2.0f, Z1/100);
		gl.glTexCoord2f(tR, tB);
		gl.glVertex3f(X2/100, 2.0f, Z2/100);
		gl.glTexCoord2f(tR, tT);
		gl.glVertex3f(X2/100, 0.0f, Z2/100);
		gl.glTexCoord2f(tL, tT);
		gl.glVertex3f(X1/100, 0.0f, Z1/100);
		
		gl.glTexCoord2f(tR,tB);
		gl.glVertex3f(X1/100, 2.0f, Z1/100);
		gl.glTexCoord2f(tR, tT);
		gl.glVertex3f(X3/100, 2.0f, Z3/100);
		gl.glTexCoord2f(tL, tT);
		gl.glVertex3f(X3/100, 0.0f, Z3/100);
		gl.glTexCoord2f(tL, tB);
		gl.glVertex3f(X1/100, 0.0f, Z1/100);
	
		gl.glTexCoord2f(tL,tT);
		gl.glVertex3f(X2/100, 2.0f, Z2/100);
		gl.glTexCoord2f(tL, tB);
		gl.glVertex3f(X4/100, 2.0f, Z4/100);
		gl.glTexCoord2f(tR, tB);
		gl.glVertex3f(X4/100, 0.0f, Z4/100);
		gl.glTexCoord2f(tR, tT);
		gl.glVertex3f(X2/100, 0.0f, Z2/100);
	
		gl.glTexCoord2f(tR,tT);
		gl.glVertex3f(X3/100, 2.0f, Z3/100);
		gl.glTexCoord2f(tL, tT);
		gl.glVertex3f(X4/100, 2.0f, Z4/100);
		gl.glTexCoord2f(tL, tB);
		gl.glVertex3f(X4/100, 0.0f, Z4/100);
		gl.glTexCoord2f(tR, tB);
		gl.glVertex3f(X3/100, 0.0f, Z3/100);
	
		gl.glTexCoord2f(tR,tT);
		gl.glVertex3f(X1/100, 0.0f, Z1/100);
		gl.glTexCoord2f(tL, tT);
		gl.glVertex3f(X2/100, 0.0f, Z2/100);
		gl.glTexCoord2f(tL, tB);
		gl.glVertex3f(X4/100, 0.0f, Z4/100);
		gl.glTexCoord2f(tR, tB);
		gl.glVertex3f(X3/100, 0.0f, Z3/100);
		
		gl.glTexCoord2f(tL,tT);
		gl.glVertex3f(X1/100, 2.0f, Z1/100);
		gl.glTexCoord2f(tL, tB);
		gl.glVertex3f(X2/100, 2.0f, Z2/100);
		gl.glTexCoord2f(tR, tB);
		gl.glVertex3f(X4/100, 2.0f, Z4/100);
		gl.glTexCoord2f(tR, tT);
		gl.glVertex3f(X3/100, 2.0f, Z3/100);
	
		
		gl.glEnd();

	}
	
	
	
}
	

