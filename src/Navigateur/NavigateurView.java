package Navigateur;

import static com.jogamp.opengl.GL.GL_COLOR_BUFFER_BIT;
import static com.jogamp.opengl.GL.GL_DEPTH_BUFFER_BIT;
import static com.jogamp.opengl.GL.GL_DEPTH_TEST;
import static com.jogamp.opengl.GL.GL_DONT_CARE;
import static com.jogamp.opengl.GL.GL_LEQUAL;
import static com.jogamp.opengl.GL.GL_LINEAR;
import static com.jogamp.opengl.GL.GL_NICEST;
import static com.jogamp.opengl.GL.GL_TEXTURE_2D;
import static com.jogamp.opengl.GL.GL_TEXTURE_MAG_FILTER;
import static com.jogamp.opengl.GL.GL_TEXTURE_MIN_FILTER;
import static com.jogamp.opengl.GL2ES1.GL_EXP;
import static com.jogamp.opengl.GL2ES1.GL_EXP2;
import static com.jogamp.opengl.GL2ES1.GL_FOG;
import static com.jogamp.opengl.GL2ES1.GL_FOG_COLOR;
import static com.jogamp.opengl.GL2ES1.GL_FOG_DENSITY;
import static com.jogamp.opengl.GL2ES1.GL_FOG_END;
import static com.jogamp.opengl.GL2ES1.GL_FOG_HINT;
import static com.jogamp.opengl.GL2ES1.GL_FOG_MODE;
import static com.jogamp.opengl.GL2ES1.GL_FOG_START;
import static com.jogamp.opengl.GL2ES1.GL_PERSPECTIVE_CORRECTION_HINT;
import static com.jogamp.opengl.GL2ES3.GL_QUADS;
import static com.jogamp.opengl.fixedfunc.GLLightingFunc.GL_AMBIENT;
import static com.jogamp.opengl.fixedfunc.GLLightingFunc.GL_DIFFUSE;
import static com.jogamp.opengl.fixedfunc.GLLightingFunc.GL_LIGHT1;
import static com.jogamp.opengl.fixedfunc.GLLightingFunc.GL_LIGHTING;
import static com.jogamp.opengl.fixedfunc.GLLightingFunc.GL_POSITION;
import static com.jogamp.opengl.fixedfunc.GLLightingFunc.GL_SMOOTH;
import static com.jogamp.opengl.fixedfunc.GLMatrixFunc.GL_MODELVIEW;
import static com.jogamp.opengl.fixedfunc.GLMatrixFunc.GL_PROJECTION;


import java.io.IOException;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLException;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureCoords;
import com.jogamp.opengl.util.texture.TextureIO;

import Blueprint.Room;
import Blueprint.Vertex;
import Blueprint.Wall;

@SuppressWarnings("serial")
public class NavigateurView extends GLCanvas implements GLEventListener{
	private GLU glu;

	private NavigateurModel model;
	int[] fogModes = { GL_EXP, GL_EXP2, GL_LINEAR }; // storage for 3 types of fogs
	int currFogFilter = 0;                           // which fog to use
	float[] fogColor = { 0.0f, 0.0f, 0.0f, 0.0f };   // fog color


	// ** Constructeur par default */
	public NavigateurView(NavigateurModel model) {
		this.model = model;
		this.addGLEventListener(this);
	}

	// ----- implement methods declared in GLEventListener -----

	

	/**
	 * Called back before the OpenGL context is destroyed. Release resource such as buffers.
	 * */
	@Override
	public void dispose(GLAutoDrawable drawable) {
		// TODO Auto-generated method stub

	}

	/**
	 * Called back immediately. after the OpenGL context is initialized. Can be used 
	 * to perform one-time initialization. Run only once.
	 * */
	@Override
	public void init(GLAutoDrawable drawable) {
		GL2 gl = drawable.getGL().getGL2();
		glu = new GLU();
		gl.glClearColor(1.0f, 1.0f, 1.0f, 1.0f); // clear to the color of the fog
		gl.glClearDepth(1.0f);
		gl.glEnable(GL_DEPTH_TEST);
		gl.glDepthFunc(GL_LEQUAL);
		gl.glHint(GL_PERSPECTIVE_CORRECTION_HINT, GL_NICEST);
		gl.glShadeModel(GL_SMOOTH);
		// Set up the lighting for Light-1
		// Ambient light does not come form a particular direction. Need some ambient 
		// light to light up the scene. Ambient's value in RGBA
		float[] lightAmbientValue = {1.0f,1.5f, 1.0f, 1.0f};
		// Diffuse light comes from a particulear location. Diffuse's value in RGBA
		float[] lightDiffuseValue = {2.0f, 2.0f, 2.0f, 2.0f};
		// Diffuse light location xyz (in front of the screen).
		float[] lightDiffusePosion = {0.0f, 0.0f, -2.0f, 1.0f};

		gl.glFogfv(GL_FOG_COLOR, fogColor, 0); // set fog color
	    gl.glFogf(GL_FOG_DENSITY, 0.35f);      // how dense will the fog be
	    gl.glHint(GL_FOG_HINT, GL_DONT_CARE);  // fog hint value
	    gl.glFogf(GL_FOG_START, 20.0f); // fog start depth
	    gl.glFogf(GL_FOG_END, 20.0f);   // fog end depth
	    gl.glEnable(GL_FOG);           // enables GL_FOG

		
		 try {
	         // Create a OpenGL Texture object from (URL, mipmap, file suffix)
	         // Use URL so that can read from JAR and disk file.
			 model.texture = TextureIO.newTexture(
	               getClass().getClassLoader().getResource(model.textureFileName), // relative to project root 
	               false, model.textureFileType);

	         // Use linear filter for texture if image is larger than the original texture
	         gl.glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
	         // Use linear filter for texture if image is smaller than the original texture
	         gl.glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);

	         // Texture image flips vertically. Shall use TextureCoords class to retrieve
	         // the top, bottom, left and right coordinates, instead of using 0.0f and 1.0f.
	         TextureCoords textureCoords = model.texture.getImageTexCoords();
	         model.textureTop = textureCoords.top();
	         model.textureBottom = textureCoords.bottom();
	         model.textureLeft = textureCoords.left();
	         model.textureRight = textureCoords.right();
	      } catch (GLException e) {
	         e.printStackTrace();
	      } catch (IOException e) {
	         e.printStackTrace();
	      }
		 gl.glLightfv(GL_LIGHT1, GL_AMBIENT, lightAmbientValue, 0);
			gl.glLightfv(GL_LIGHT1, GL_DIFFUSE, lightDiffuseValue, 0);
			gl.glLightfv(GL_LIGHT1, GL_POSITION, lightDiffusePosion, 0);
			gl.glEnable(GL_LIGHT1); // Enable Light-1
			gl.glDisable(GL_LIGHTING); // But disable lighting
	}
	/** 
	 * Called back by the animator to perform rendering
	 * */
	@Override
	public void display(GLAutoDrawable drawable) {
		GL2 gl = drawable.getGL().getGL2();
		gl.glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		gl.glLoadIdentity();

		// Rotate up and down to look up and down
		gl.glRotatef(model.getLookUpAngle(), 1.0f, 0, 0);

		// Player at headingY, Rotate the scene by -headingY instead (add 360 to get
		// positive angle)
		gl.glRotatef(360.0f - model.getHeadingY(), 0, 1.0f, 0);

		// Player is at (posX, 0, posZ), Translate the scene to (-posX, 0, -posZ)
		gl.glTranslatef(-model.getPosX(), -model.getWalkBias() - 0.5f, -model.getPosZ());
		gl.glFogi(GL_FOG_MODE, fogModes[currFogFilter]); // Fog Mode
		// Lighting
		if (model.getIsLigntOn()) {
			gl.glEnable(GL_LIGHTING);
		} else {
			gl.glDisable(GL_LIGHTING);
		}
		  // Enables this texture's target in the current GL context's state.
	      model.texture.enable(gl);
	        // same as gl.glEnable(texture.getTarget());
	      // gl.glTexEnvi(GL.GL_TEXTURE_ENV, GL.GL_TEXTURE_ENV_MODE, GL.GL_REPLACE);
	      // Binds this texture to the current GL context.
	      model.texture.bind(gl);  // same as gl.glBindTexture(texture.getTarget(), texture.getTextureObject());
		// ----- creer des objets -----

		// first room
		//gl.glColor3f(0.1f, 0.5f, 0.5f);
		Room r=new Room();
		try {
			r.read("test.txt");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		r.draw(gl, model.textureTop, model.textureBottom, model.textureLeft,model.textureRight);
	     
	}
	/**
	 * Call-back handler for window re-size event. Also called when the drawable is
	 * first set to visible.
	 * */
	@Override
	public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
		GL2 gl = drawable.getGL().getGL2();

		if (height == 0) height =1; // eviter de diviser par zero
		float aspect = (float) width / height;

		// set the view port (display area) to cover the entire window
		gl.glViewport(0, 0, width, height);

		// set up perspective projection, with aspect ratio mathes viewport
		gl.glMatrixMode(GL_PROJECTION);
		gl.glLoadIdentity();
		glu.gluPerspective(45.0, aspect, 0.1, 100.0);

		// Enable the model-view transform
		gl.glMatrixMode(GL_MODELVIEW);
		gl.glLoadIdentity();

	}

}