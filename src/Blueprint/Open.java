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

public abstract class Open {
	protected String id, next=null;
	protected Vertex v1,v2;
	
	public Open(String id, Vertex v1, Vertex v2){
		this.id=id;
		this.v1=v1;
		this.v2=v2;
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
	
	public abstract void draw(Graphics g);

	public abstract void draw(GL2 gl);

}
