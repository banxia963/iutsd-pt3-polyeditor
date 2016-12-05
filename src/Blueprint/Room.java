/**
 * PT3 Poly Editor
 * DUT Informatique 2016/2017 
 * Auteur : HUANG Qijia
 * 			LU Yi
 * Tuteur : P. Even
 * */

package Blueprint;

import java.awt.Graphics;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

import com.jogamp.opengl.GL2;

public class Room {
	private ArrayList<Wall> walls = new ArrayList();
	private String id;
	
	public Room(){
		
	}
	
	public Room(int nb, String id) {
		if (nb == 4){
			walls.add(new Wall(new Vertex(190,120),new Vertex(190,800)));
			walls.add(new Wall(new Vertex(190,800),new Vertex(850,800)));
			walls.add(new Wall(new Vertex(850,800),new Vertex(850,120)));
			walls.add(new Wall(new Vertex(850,120),new Vertex(190,120)));
		}
		this.id = id;
	}
	
	public ArrayList<Wall> getWalls(){
		return walls;
	}
	
	public void addVertex(){
		Wall tmp = null;
		Vertex v1 = null,v2 = null,v3 = null, v4 = null;
		
		for(Wall w: walls){
			if(w.isSelected()){    //il faut verifier si Open dans le mur est NULL
				v1=w.getV1();
				v2 = w.getV2();	
				tmp=w;
			}
		}
		if (tmp != null){
			v3 = new Vertex((v1.getX()+v2.getX())/2, (v1.getY()+v2.getY())/2);
			v4 = new Vertex((v1.getX()+v2.getX())/2, (v1.getY()+v2.getY())/2);
			int index = walls.indexOf(tmp);
			walls.remove(tmp);
			walls.add(index,new Wall(v4, v2));
			walls.add(index,new Wall(v1, v3));
			v3.select();
			v4.select();
		}
	}
	
	public void addDoor(String id){
		for(Wall w: walls){
			if(w.isSelected()){
				w.addDoor(id);
			}
		}
	}
	
	public void addWindow(String id) {
		for(Wall w: walls){
			if(w.isSelected()){
				w.addWindow(id);
			}
		}
		
	}
	
	public void draw(Graphics g){
		for (Wall w : walls){
			w.draw(g);
		}
	}

	public void draw(GL2 gl){
		for (Wall w : walls){
			w.draw(gl);
		}
		gl.glBegin(GL2.GL_POLYGON);
		gl.glColor3f(0.8f, 0.3f, 0.8f);
			for (Wall w: walls){
				gl.glVertex3f(w.getV1().getX()/100, 0.0f, w.getV1().getY()/100);
			}
		gl.glEnd();
		gl.glBegin(GL2.GL_POLYGON);
		gl.glColor3f(0.8f, 0.3f, 0.8f);
		for(Wall w : walls){
			
			gl.glVertex3f(w.getV1().getX()/100, 1.0f, w.getV1().getY()/100);

			}
		gl.glEnd();
	}
	
	public void draw(GL2 gl, float tT, float tB, float tL, float tR){
		for (Wall w : walls){
			w.draw(gl, tT,  tB,  tL, tR);
		}
		
		gl.glBegin(GL2.GL_POLYGON);
		for(Wall w : walls){
			gl.glVertex3f(w.getV1().getX()/100, 0.0f, w.getV1().getY()/100);
			}
		gl.glEnd();
		
		gl.glBegin(GL2.GL_POLYGON);
		for(Wall w : walls){
			gl.glVertex3f(w.getV1().getX()/100, 1.0f, w.getV1().getY()/100);

			}
		gl.glEnd();
	}
	
	public Wall nextWall(Wall w){
		int n=walls.indexOf(w);
		if(n==walls.size()-1){
			return walls.get(0);
		}
		return walls.get(n+1);
	}
	
	public Wall lastWall(Wall w){
		int n=walls.indexOf(w);
		if(n==0){
			return walls.get(walls.size()-1);
		}
		return walls.get(n-1);
	}
	
	public void write() throws IOException{
		PrintWriter in = null;
		try {
	         in = new PrintWriter(
	               new OutputStreamWriter(new FileOutputStream("test.txt")));
	         in.println(id);
	         for(Wall w : walls){
	        	 in.print(w.getV1().getX());
	        	 in.print(" ");
	        	 in.print(w.getV1().getY());
	        	 in.print(" ");
	        	 in.print(w.getV2().getX());
	        	 in.print(" ");
	        	 in.print(w.getV2().getY());
	        	 
	        	 if (w.getOpen()!=null){
	        		 in.print(" ");
	        		 in.print(w.getOpen().getID());
	        		 in.print(" ");
		        	 in.print(w.getOpen().getV1().getX());
		        	 in.print(" ");
		        	 in.print(w.getOpen().getV1().getY());
		        	 in.print(" ");
		        	 in.print(w.getOpen().getV2().getX());
		        	 in.print(" ");
		        	 in.print(w.getOpen().getV2().getY());
	        	 }
	        	 
	        	 in.print("\n");
	         }
		} finally {
			if (in != null)
				in.close();
		}	
	}
	
	public void read(String filename) throws IOException{
		BufferedReader in = null;
		try {
	         in = new BufferedReader(
	               new InputStreamReader(new FileInputStream(filename)));
	         id = in.readLine();
	         String line;
	         while ((line = in.readLine()) != null){
	        	 Scanner scanner = new Scanner(line).useDelimiter(" ");
	        	 walls.add(new Wall(new Vertex(scanner.nextFloat(),scanner.nextFloat()),new Vertex(scanner.nextFloat(),scanner.nextFloat())));
	        	 if (scanner.hasNext()){
	        		 String id=scanner.next();
	        		 if(id.startsWith("Door")){
	        			 walls.get(walls.size()-1).addDoor(id, scanner.nextFloat(), scanner.nextFloat(),scanner.nextFloat(),scanner.nextFloat());
	        		 } else if (id.startsWith("Window")){
	        			 walls.get(walls.size()-1).addWindow(id, scanner.nextFloat(), scanner.nextFloat(),scanner.nextFloat(),scanner.nextFloat());
	        		 }
	        	 }
	         } 
		} finally {
			if (in != null)
				in.close();
		}	
	}

	
}
