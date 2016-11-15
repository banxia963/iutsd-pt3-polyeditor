/**
 * PT3 Poly Editor
 * DUT Informatique 2016/2017 
 * Auteur : HUANG Qijia
 * 			LU Yi
 * Tuteur : P. Even
 * */

package Blueprint;

import static com.jogamp.opengl.GL.GL_COLOR_BUFFER_BIT;
import static com.jogamp.opengl.GL.GL_DEPTH_BUFFER_BIT;
import static com.jogamp.opengl.fixedfunc.GLLightingFunc.GL_LIGHTING;

import java.awt.Graphics;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Scanner;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;

public class Room {
	private ArrayList<Wall> walls = new ArrayList();
	private String id;
	public Room(int nb, String id) {
		if (nb == 4){
			walls.add(new Wall(new Vertex(170,170),new Vertex(170,510)));
			walls.add(new Wall(new Vertex(170,510),new Vertex(510,510)));
			walls.add(new Wall(new Vertex(510,510),new Vertex(510,170)));
			walls.add(new Wall(new Vertex(510,170),new Vertex(170,170)));
		}
		this.id = id;
	}
	
	public ArrayList<Wall> getWalls(){
		return walls;
	}
	
	public void draw(Graphics g){
		for (Wall w : walls){
			w.draw(g);
		}
	}
	
	public void draw3D(GL2 gl, float x1, float z1, float x2, float z2){
		
		for(Wall w : walls){
			w.draw3d(gl, x1, z1, x2, z2);
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
