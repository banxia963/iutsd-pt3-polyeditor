/**
 * PT3 Poly Editor
 * DUT Informatique 2016/2017 
 * Auteur : HUANG Qijia
 * 			LU Yi
 * Tuteur : P. Even
 * */

package Blueprint;

import java.awt.Graphics;

import Modeleur.ModeleurModel;

public class Vertex {
	private float x;
	private float y;
	private boolean selected = false;
	
	public Vertex(float x, float y) {
		super();
		this.x = x;
		this.y = y;
	}
	
	public float getX() {
		return x;
	}
	
	public float getY() {
		return y;
	}
	
	public float ptDisVt(int x, int Y){
		return (float) Math.sqrt((this.x-x)*(this.x-x)+(this.y-y)*(this.y-y));
	}
	
	public float VtDisVt(Vertex v){
		return (float) Math.sqrt((this.x-v.getX())*(this.x-v.getX())+(this.y-v.getY())*(this.y-v.getY()));
	}
	
	public void select(){
		selected=!selected;
	}
	
	public void select(int x, int y){
		if(selected) {
			selected=!selected;
			return;
			}
		
		float disX, disY;
		disX=x-this.x;
		disY=y-this.y;
		
		if ( disX<25 && disX>0 && disY<25 && disY>0){
			selected = true;
		}
		else{
			selected = false;
		}
	}
	
	public void move(float x, float y){
		this.x = x;
		this.y = y;
	}
	
	public void draw (Graphics g){
		if (selected) {
			g.setColor(ModeleurModel.WHITE);
			g.fillOval((int)x, (int)y, 25, 25);
			return;
		}
		g.setColor(ModeleurModel.BLACK);
		g.fillOval((int)x, (int)y, 25, 25);
		return;
	}
	
	public boolean isSelected(){
		return selected;
	}
}
