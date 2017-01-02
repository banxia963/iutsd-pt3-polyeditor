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

public class Corridor implements Space{
	private String id;
	private ArrayList<Wall> traces = new ArrayList<Wall>();
	private int height;
	private int width;
	private int nbStairs;
	private String idNextRoom;
	private String idLastRoom;
	
	/** Constructeur par default */
	public Corridor(String id){
		this.id=id;
		traces.add(new Wall(new Vertex(520,120),new Vertex(520,800)));
		height=0;
		nbStairs=0;
		idNextRoom=null;
		idLastRoom=null;
	}
	
	public Corridor() {
		// TODO Auto-generated constructor stub
	}

	public void addVertex(){
		Wall tmp = null;
		Vertex v1 = null,v2 = null,v3 = null, v4 = null;
		
		for(Wall w: traces){
			if(w.isSelected()){    //il faut verifier si Open dans le mur est NULL
				v1=w.getV1();
				v2 = w.getV2();	
				tmp=w;
			}
		}
		if (tmp != null){
			v3 = new Vertex((v1.getX()+v2.getX())/2, (v1.getY()+v2.getY())/2);
			v4 = new Vertex((v1.getX()+v2.getX())/2, (v1.getY()+v2.getY())/2);
			int index = traces.indexOf(tmp);
			traces.remove(tmp);
			traces.add(index,new Wall(v4, v2));
			traces.add(index,new Wall(v1, v3));
			v3.select();
			v4.select();
		}
	}
	
	public void delVertex(){
		Wall tmp =null;
		Vertex v1=null;
		for (Wall w:traces){
			if(w.getV2().isSelected()){
				tmp=w;
				v1=w.getV1();
			}
		}
		if (tmp != null){
			Wall next = nextWall(tmp);
			Vertex v2 = next.getV2();
			int index = traces.indexOf(tmp);
			traces.remove(tmp);
			traces.add(index, new Wall(v1,v2));
			traces.remove(next);
			
		}	
		
	}
	
	public Wall nextWall(Wall w){
		int n=traces.indexOf(w);
		if(n==traces.size()-1){
			return null;
		}
		return traces.get(n+1);
	}
	
	public Wall lastWall(Wall w){
		int n=traces.indexOf(w);
		if(n==0){
			return null;
		}
		return traces.get(n-1);
	}
	
	
	public ArrayList<Wall> getTraces(){
		return traces;
	}
	
	public int getHeight(){
		return height;
	}
	
	public void setHeight(int height){
		this.height=height;
	}
	
	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}
	
	public int getNbStairs(){
		return nbStairs;
	}
	
	public void setNbStairs(int nbStairs) {
		this.nbStairs = nbStairs;
	}
	
	public String getIdNextRoom() {
		return idNextRoom;
	}

	public void setIdNextRoom(String idNextRoom) {
		this.idNextRoom = idNextRoom;
	}

	public String getIdLastRoom() {
		return idLastRoom;
	}

	public void setIdLastRoom(String idLastRoom) {
		this.idLastRoom = idLastRoom;
	}
	
	@Override
	public void draw(Graphics g) {
		for (Wall w : traces){
			w.draw(g);
		}
	}
	public void drawwall(GL2 gl){}
	
	public void draw(GL2 gl) {
		
		if(traces.size()==1){
			traces.get(0).draw(gl);
		}
		if(traces.size()==2){
			float b = 0;
			float x =0;
			float z =0;
			float X1=0,X2=0,X3=0,X4=0,Z1=0,Z2=0,Z3=0,Z4=0;
			Wall w1=traces.get(0);
			Wall w2 = traces.get(1);
			Vertex v1,v2;
			try {
				v1 = (Vertex) w1.getV1().clone();
				v2 = (Vertex) w2.getV2().clone();
				
				if (v1.getY()-v2.getY()!=0) {
					b = -((v1.getX()-v2.getX())/(v1.getY()-v2.getY()));
					x = (float) ((width/2)*Math.sqrt(1/(1+b*b)));
					z = (float) (Math.sqrt((width*width))/2*(1-(1/(1+b*b))));
				
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
						X1=v1.getX()+width/2;
						X2=v1.getX()-width/2;
						X3=v1.getX()+width/2;
						X4=v1.getX()-width/2;
						
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
					Z1 = v1.getY()+width/2;
					Z2 = v1.getY()-width/2;
					Z3 = v2.getY()+width/2;
					Z4 = v2.getY()-width/2;
				}
			} catch (CloneNotSupportedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(traces.size()>=3){
			
			
		}
	}
	
	public void draw(GL2 gl, float tT, float tB, float tL, float tR) {
		
		
		}
	public void write(String filepath) throws IOException{
		PrintWriter in = null;
		try {
	         in = new PrintWriter(
	               new OutputStreamWriter(new FileOutputStream(filepath)));
	         in.println(id);
	         for(Wall w : traces){
	        	 in.print("TRACE");
	        	 in.print(" ");
	        	 in.print(w.getV1().getX());
	        	 in.print(" ");
	        	 in.print(w.getV1().getY());
	        	 in.print(" ");
	        	 in.print(w.getV2().getX());
	        	 in.print(" ");
	        	 in.print(w.getV2().getY());
	        	 in.print("\n");
	         }
	         in.print("HEIGHT");
	         in.print(" ");
	         in.print(height);
	         in.print("\n");
	         in.print("WIDTH");
	         in.print(" ");
	         in.print(width);
	         in.print("\n");
	         in.print("STAIRS");
	         in.print(" ");
	         in.print(nbStairs);
	         in.print("\n");
	         in.print("LAST");
	         in.print(" ");
	         in.print(idLastRoom);
	         in.print("\n");
	         in.print("NEXT");
	         in.print(" ");
	         in.print(idNextRoom);
	         in.print("\n");
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
	        	 String l=scanner.next();
	        	 if(l.startsWith("HEIGHT")) height=scanner.nextInt();
	        	 else if(l.startsWith("WIDTH")) width=scanner.nextInt();
	        	 else if(l.startsWith("STAIRS")) nbStairs=scanner.nextInt();
	        	 else if(l.startsWith("LAST")) idLastRoom=scanner.next();
	        	 else if(l.startsWith("NEXT")) idNextRoom=scanner.next();
	        	 else
	        	 traces.add(new Wall(new Vertex(scanner.nextFloat(),scanner.nextFloat()),new Vertex(scanner.nextFloat(),scanner.nextFloat())));
	         }
		} finally {
			if (in != null)
				in.close();
		}	
	}

}
