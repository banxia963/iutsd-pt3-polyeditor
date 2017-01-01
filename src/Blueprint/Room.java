/**
 * PT3 Poly Editor
 * DUT Informatique 2016/2017 
 * Auteur : HUANG Qijia
 * 			LU Yi
 * Tuteur : P. Even
 * */

package Blueprint;

import java.awt.Graphics;
import java.awt.geom.Line2D;
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

public class Room implements Space {
	private String id;
	private ArrayList<Wall> walls = new ArrayList();
	
	public Room(){
	}
	
	public Room(int nb, String id) {
		this.id=id;
		switch (nb) {
		case 4: walls.add(new Wall(new Vertex(190,120),new Vertex(190,800)));
				walls.add(new Wall(new Vertex(190,800),new Vertex(850,800)));
				walls.add(new Wall(new Vertex(850,800),new Vertex(850,120)));
				walls.add(new Wall(new Vertex(850,120),new Vertex(190,120)));
				walls.get(1).addDoor("Door");;
				break;
		case 6: walls.add(new Wall(new Vertex(340,120),new Vertex(190,460)));
				walls.add(new Wall(new Vertex(190,460),new Vertex(340,800)));
				walls.add(new Wall(new Vertex(340,800),new Vertex(700,800)));
				walls.add(new Wall(new Vertex(700,800),new Vertex(850,460)));
				walls.add(new Wall(new Vertex(850,460),new Vertex(700,120)));
				walls.add(new Wall(new Vertex(700,120),new Vertex(340,120)));
				walls.get(2).addDoor("Door");
				break;
		case 8: walls.add(new Wall(new Vertex(355,120),new Vertex(190,290)));
				walls.add(new Wall(new Vertex(190,290),new Vertex(190,630)));
				walls.add(new Wall(new Vertex(190,630),new Vertex(355,800)));
				walls.add(new Wall(new Vertex(355,800),new Vertex(685,800)));
				walls.add(new Wall(new Vertex(685,800),new Vertex(850,630)));
				walls.add(new Wall(new Vertex(850,630),new Vertex(850,290)));
				walls.add(new Wall(new Vertex(850,290),new Vertex(685,120)));
				walls.add(new Wall(new Vertex(685,120),new Vertex(355,120)));
				walls.get(3).addDoor("Door");
				break;
		default: break;
		}
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
	
	public void delVertex(){
		Wall tmp =null;
		Vertex v1=null;
		for (Wall w:walls){
			if(w.getV2().isSelected()){
				tmp=w;
				v1=w.getV1();
			}
		}
		if (tmp != null){
			Wall next = nextWall(tmp);
			Vertex v2 = next.getV2();
			int index = walls.indexOf(tmp);
			walls.remove(tmp);
			walls.add(index, new Wall(v1,v2));
			walls.remove(next);
			
		}
		
		
	}
	
	public static int ptPosition(float x, float y, Vertex vA, Vertex vB){
		float r=(float)25/2;
		float position = (vB.getX()-r-vA.getX()-r)*(y-r-vA.getY()-r)-(vB.getY()-r-vA.getY()-r)*(x-r-vA.getX()-r);
		if (position > 0) return 1;
		else if (position < 0 ) return -1;
		return 0;
	}
	
	public static float[] findIntersectionPoint(Wall w1, Wall w2){
		float x1 = w1.getV1().getX();
		float y1 = w1.getV1().getY();
		float x2 = w1.getV2().getX();
		float y2 = w1.getV2().getY();
		float x3 = w2.getV1().getX();
		float y3 = w2.getV1().getY();
		float x4 = w2.getV2().getX();
		float y4 = w2.getV2().getY();
		float a1 = (y1-y2)/(x1-x2);
		float a2 = (y3-y4)/(x3-x4);
		if(a1==a2) return null;
		
		float b1 = y1-x1*a1;
		float b2 = y3-x3*a2;
		
		float[] point = new float[2];
		point[0]=(b2-b1)/(a1-a2);
		point[1]=a1*point[0]+b1;
		
		return point;
	}
	
	public static boolean betweenWalls(float x, float y, Wall w, Wall w1, Wall w2){
		float r=(float)25/2;
		int pos = ptPosition(x, y, w.getV1(), w2.getV1());
		if (pos == -1){
			return false;
		}
		else if(pos == 0)
		{
			return true;
		}
		else {
			Line2D l1 = new Line2D.Float(w1.getV1().getX()-r, w1.getV1().getY()-r, w1.getV2().getX()-r, w1.getV2().getY()-r);
			Line2D l2 = new Line2D.Float(w2.getV1().getX()-r, w2.getV1().getY()-r, w2.getV2().getX()-r, w2.getV2().getY()-r);
			float disLines = w.getV1().getX()-w2.getV1().getX();
			if(disLines == 0){
				disLines = w.getV1().getY()-w2.getV1().getY();
			}
			if(disLines < 0){
				disLines=-disLines;
			}
			double disPtLines = l1.ptLineDist(x-r, y-r)+l2.ptLineDist(x-r, y-r);
			if(disPtLines - disLines < 0.001) return true;
			return false;
		}
	}
	
	public static boolean isInTriangle(float x, float y, float x3, float y3, Vertex v1, Vertex v2){
		float x1 = v1.getX(), y1 = v1.getY();
		float x2 = v2.getX(), y2 = v2.getY();

		double ABC = Math.abs (x1 * (y2 - y3) + x2 * (y3 - y1) + x3 * (y1 - y2));
		double ABP = Math.abs (x1 * (y2 - y) + x2 * (y - y1) + x * (y1 - y2));
		double APC = Math.abs (x1 * (y - y3) + x * (y3 - y1) + x3 * (y1 - y));
		double PBC = Math.abs (x * (y2 - y3) + x2 * (y3 - y) + x3 * (y - y2));

		boolean isInTriangle = ABP + APC + PBC == ABC;
		
		return isInTriangle;
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
	
	public void write(String filepath) throws IOException{
		PrintWriter in = null;
		try {
	         in = new PrintWriter(
	               new OutputStreamWriter(new FileOutputStream(filepath)));
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
