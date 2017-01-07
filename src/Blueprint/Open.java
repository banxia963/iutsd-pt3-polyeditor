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

/** class Open */
public abstract class Open {
	private String id, next=null;
	protected Vertex v1,v2;
	private float r1,r2;
	private String nextOpen=null;
	
	public Open(String id, Vertex v1, Vertex v2){
		this.id=id;
		this.v1=v1;
		this.v2=v2;
		this.r1=(float)1/4;
		this.r2=(float)3/4;
	}

	public String getID(){
		return id;
	}
	
	public Vertex getV1(){
		return v1;
	}
	
	public Vertex getV2(){
		return v2;
	}
	
	public float getR1(){
		return r1;
	}
	
	public float getR2(){
		return r2;
	}
	
	public void setR1(float r1){
		this.r1=r1;
	}
	
	public void serR2(float r2){
		this.r2=r2;
	}
	
	public abstract void move(float x1, float y1, float x2, float y2);
	
	public abstract void draw(Graphics g);

	public abstract void draw(GL2 gl);

	public abstract void draw(GL2 gl, float tT, float tB, float tL, float tR);
		
	}


