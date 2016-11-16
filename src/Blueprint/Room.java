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
			if(w.isSelected()){
				v1 = w.getV1();
				v2 = w.getV2();	
				tmp=w;
			}
		}
		v3 = new Vertex((v1.getX()+v2.getX())/2, (v1.getY()+v2.getY())/2);
		v4 = new Vertex((v1.getX()+v2.getX())/2, (v1.getY()+v2.getY())/2);
		walls.remove(tmp);
		walls.add(new Wall(v1, v3));
		walls.add(new Wall(v4, v2));
		v3.select();
		v4.select();
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
	        	 walls.add(new Wall(new Vertex(scanner.nextInt(),scanner.nextInt()),new Vertex(scanner.nextInt(),scanner.nextInt())));
	         }
		} finally {
			if (in != null)
				in.close();
		}	
	}
}
