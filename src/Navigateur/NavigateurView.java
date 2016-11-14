package Navigateur;

import static com.jogamp.opengl.GL.GL_COLOR_BUFFER_BIT;
import static com.jogamp.opengl.GL.GL_DEPTH_BUFFER_BIT;
import static com.jogamp.opengl.GL.GL_DEPTH_TEST;
import static com.jogamp.opengl.GL.GL_LEQUAL;
import static com.jogamp.opengl.GL.GL_NICEST;
import static com.jogamp.opengl.GL2ES1.GL_PERSPECTIVE_CORRECTION_HINT;
import static com.jogamp.opengl.fixedfunc.GLLightingFunc.GL_AMBIENT;
import static com.jogamp.opengl.fixedfunc.GLLightingFunc.GL_DIFFUSE;
import static com.jogamp.opengl.fixedfunc.GLLightingFunc.GL_LIGHT1;
import static com.jogamp.opengl.fixedfunc.GLLightingFunc.GL_LIGHTING;
import static com.jogamp.opengl.fixedfunc.GLLightingFunc.GL_POSITION;
import static com.jogamp.opengl.fixedfunc.GLLightingFunc.GL_SMOOTH;
import static com.jogamp.opengl.fixedfunc.GLMatrixFunc.GL_MODELVIEW;
import static com.jogamp.opengl.fixedfunc.GLMatrixFunc.GL_PROJECTION;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.glu.GLU;

@SuppressWarnings("serial")
public class NavigateurView extends GLCanvas implements GLEventListener {
	private GLU glu;

	private NavigateurModel model;

	// ** Constructeur par default */
	public NavigateurView(NavigateurModel model) {
		this.model = model;
		this.addGLEventListener(this);
	}

	// ----- implement methods declared in GLEventListener -----

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

		// Lighting
		if (model.getIsLigntOn()) {
			gl.glEnable(GL_LIGHTING);
		} else {
			gl.glDisable(GL_LIGHTING);
		}

		// ----- creer des objets -----

		// first room
		gl.glBegin(GL2.GL_LINES);

		gl.glColor3f(0.1f, 0.5f, 0.5f);

		gl.glVertex3f(0.0f, 0.0f, 0.0f);
		gl.glVertex3f(4.0f, 0.0f, 0.0f);

		gl.glVertex3f(0.0f, 0.0f, 0.0f);
		gl.glVertex3f(0.0f, 1.f, 0.0f);

		gl.glVertex3f(0.0f, 0.0f, 0.0f);
		gl.glVertex3f(0.0f, 0.0f, -4.0f);

		gl.glVertex3f(4.0f, 0.0f, 0.0f);
		gl.glVertex3f(4.0f, 0.0f, -4.0f);

		gl.glVertex3f(4.0f, 0.0f, 0.0f);
		gl.glVertex3f(4.0f, 1.0f, 0.0f);

		gl.glVertex3f(0.0f, 0.0f, -4.0f);
		gl.glVertex3f(4.0f, 0.0f, -4.0f);

		gl.glVertex3f(0.0f, 0.0f, -4.0f);
		gl.glVertex3f(0.0f, 1.0f, -4.0f);

		gl.glVertex3f(4.0f, 0.0f, -4.0f);
		gl.glVertex3f(4.0f, 1.0f, -4.0f);

		gl.glVertex3f(0.0f, 1.0f, -4.0f);
		gl.glVertex3f(4.0f, 1.0f, -4.0f);

		gl.glVertex3f(0.0f, 1.0f, -4.0f);
		gl.glVertex3f(0.0f, 1.0f, 0.0f);

		gl.glVertex3f(4.0f, 1.0f, -4.0f);
		gl.glVertex3f(4.0f, 1.0f, 0.0f);

		gl.glVertex3f(4.0f, 1.0f, 0.0f);
		gl.glVertex3f(0.0f, 1.0f, 0.0f);

		// door

		gl.glVertex3f(1.8f, 0.8f, 0.0f);
		gl.glVertex3f(1.8f, 0.0f, 0.0f);

		gl.glVertex3f(2.2f, 0.8f, 0.0f);
		gl.glVertex3f(2.2f, 0.0f, 0.0f);

		gl.glVertex3f(1.8f, 0.8f, 0.0f);
		gl.glVertex3f(2.2f, 0.8f, 0.0f);


		// second room

		gl.glVertex3f(6.0f, 0.0f, 0.0f);
		gl.glVertex3f(10.0f, 0.0f, 0.0f);

		gl.glVertex3f(6.0f, 0.0f, 0.0f);
		gl.glVertex3f(6.0f, 1.f, 0.0f);

		gl.glVertex3f(6.0f, 0.0f, 0.0f);
		gl.glVertex3f(6.0f, 0.0f, -4.0f);

		gl.glVertex3f(10.0f, 0.0f, 0.0f);
		gl.glVertex3f(10.0f, 0.0f, -4.0f);

		gl.glVertex3f(10.0f, 0.0f, 0.0f);
		gl.glVertex3f(10.0f, 1.0f, 0.0f);

		gl.glVertex3f(6.0f, 0.0f, -4.0f);
		gl.glVertex3f(10.0f, 0.0f, -4.0f);

		gl.glVertex3f(6.0f, 0.0f, -4.0f);
		gl.glVertex3f(6.0f, 1.0f, -4.0f);

		gl.glVertex3f(10.0f, 0.0f, -4.0f);
		gl.glVertex3f(10.0f, 1.0f, -4.0f);

		gl.glVertex3f(6.0f, 1.0f, -4.0f);
		gl.glVertex3f(10.0f, 1.0f, -4.0f);

		gl.glVertex3f(6.0f, 1.0f, -4.0f);
		gl.glVertex3f(6.0f, 1.0f, 0.0f);

		gl.glVertex3f(10.0f, 1.0f, -4.0f);
		gl.glVertex3f(10.0f, 1.0f, 0.0f);

		gl.glVertex3f(10.0f, 1.0f, 0.0f);
		gl.glVertex3f(6.0f, 1.0f, 0.0f);

		//door 1

		gl.glVertex3f(4.0f, 0.8f, -1.8f);
		gl.glVertex3f(4.0f, 0.0f, -1.8f);

		gl.glVertex3f(4.0f, 0.8f, -2.2f);
		gl.glVertex3f(4.0f, 0.0f, -2.2f);

		gl.glVertex3f(4.0f, 0.8f, -1.8f);
		gl.glVertex3f(4.0f, 0.8f, -2.2f);

		//door 2

		gl.glVertex3f(6.0f, 0.8f, -1.8f);
		gl.glVertex3f(6.0f, 0.0f, -1.8f);

		gl.glVertex3f(6.0f, 0.8f, -2.2f);
		gl.glVertex3f(6.0f, 0.0f, -2.2f);

		gl.glVertex3f(6.0f, 0.8f, -1.8f);
		gl.glVertex3f(6.0f, 0.8f, -2.2f);

		// hallway

		gl.glVertex3f(4.0f, 0.8f, -1.8f);
		gl.glVertex3f(6.0f, 0.8f, -1.8f);

		gl.glVertex3f(4.0f, 0.8f, -2.2f);
		gl.glVertex3f(6.0f, 0.8f, -2.2f);

		gl.glVertex3f(4.0f, 0.0f, -1.8f);
		gl.glVertex3f(6.0f, 0.0f, -1.8f);

		gl.glVertex3f(4.0f, 0.0f, -2.2f);
		gl.glVertex3f(6.0f, 0.0f, -2.2f);

		gl.glEnd();

	}

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
		gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
		gl.glClearDepth(1.0f);
		gl.glEnable(GL_DEPTH_TEST);
		gl.glDepthFunc(GL_LEQUAL);
		gl.glHint(GL_PERSPECTIVE_CORRECTION_HINT, GL_NICEST);
		gl.glShadeModel(GL_SMOOTH);

		// Set up the lighting for Light-1
		// Ambient light does not come form a particular direction. Need some ambient 
		// light to light up the scene. Ambient's value in RGBA
		float[] lightAmbientValue = {0.5f, 0.5f, 0.5f, 1.0f};
		// Diffuse light comes from a particulear location. Diffuse's value in RGBA
		float[] lightDiffuseValue = {1.0f, 1.0f, 1.0f, 1.0f};
		// Diffuse light location xyz (in front of the screen).
		float[] lightDiffusePosion = {0.0f, 0.0f, -2.0f, 1.0f};

		gl.glLightfv(GL_LIGHT1, GL_AMBIENT, lightAmbientValue, 0);
		gl.glLightfv(GL_LIGHT1, GL_DIFFUSE, lightDiffuseValue, 0);
		gl.glLightfv(GL_LIGHT1, GL_POSITION, lightDiffusePosion, 0);
		gl.glEnable(GL_LIGHT1); // Enable Light-1
		gl.glDisable(GL_LIGHTING); // But disable lighting

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

