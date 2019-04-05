package ab3;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.glDrawArrays;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glBufferData;
import static org.lwjgl.opengl.GL15.glGenBuffers;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.*;

import org.lwjgl.glfw.*;
import static org.lwjgl.glfw.GLFW.*;
import lenz.opengl.KeyBoardHandler;
import lenz.opengl.Texture;
import lenz.opengl.AbstractOpenGLBase;
import lenz.opengl.ShaderProgram;
public class Aufgabe3undFolgende extends AbstractOpenGLBase {
	private GLFWKeyCallback keyCallback;

	private ShaderProgram phongShader;
	private ShaderProgram normalShader;
	private ShaderProgram pTexShader;
	private Matrix4 proj = new Matrix4(1f, 5f);
	private Matrix4 mat4 = new Matrix4();
	private Matrix4 mat42 = new Matrix4();
	private Matrix4 mat43 = new Matrix4();
	private float winkel = 0.1f;
	private float scale =0.4f; //bei Dodekaeder
	private int loc,loc1,loc2,loc3,loc4,loc5;
	private float x,y,z;
	private Koerper koerper = new Koerper();
	public static void main(String[] args) {
		new Aufgabe3undFolgende().start("CG Aufgabe 3", 700, 700);
	}

	private float[][] getDodekaeder() {
		// Dodekaeder Koordinaten
		// (http://www.hattendoerfer.de/friedrich/anageo/kap008-platon-koerper.pdf)
		float[] a = new float[] { 1, 1, 1, 0.5f, 0, 0 };
		float[] b = new float[] { -1, 1, 1, 0, 0.5f, 0.3f };
		float[] c = new float[] { -1, -1, 1, 1, 0, 0 };
		float[] d = new float[] { 1, -1, 1, 0, 1, 0 };
		float[] e = new float[] { 1, 1, -1, 1, 0.5f, 0.2f };
		float[] f = new float[] { -1, 1, -1, 0, 0.6f, 0.2f };
		float[] g = new float[] { -1, -1, -1, 0.4f, 0.6f, 0 };
		float[] h = new float[] { 1, -1, -1, 0.1f, 0, 0.6f };
		float s = 0.618f;
		float[] k = new float[] { s, s + 1, 0, 1, 0.3f, 0 };
		float[] l = new float[] { -s, s + 1, 0, 0, 0.4f, 1 };
		float[] m = new float[] { 0, s, 1 + s, 0.5f, 0.5f, 0 };
		float[] n = new float[] { 0, -s, 1 + s, 0, 0.5f, 0.5f };
		float[] o = new float[] { 1 + s, 0, -s, 0, 0, 1 };
		float[] p = new float[] { 1 + s, 0, s, 1, 1, 0 };
		float[] q = new float[] { -(1 + s), 0, s, 0.8f, 0, 0 };
		float[] r = new float[] { -(1 + s), 0, -s, 0, 0.7f, 0.3f };
		float[] S = new float[] { -s, -(1 + s), 0, 0.4f, 0, 0 };
		float[] t = new float[] { s, -(1 + s), 0, 0, 1, 1 };
		float[] u = new float[] { 0, s, -(1 + s), 0.2f, 0.8f, 0.3f };
		float[] v = new float[] { 0, -s, -(1 + s), 0.2f, 0.5f, 0.1f };

		float[][] dodekaeder = koerper.getDodekaeder(a, b, c, d, e, f, g, h, k, l, m, n, o, p, q, r, S, t, u, v);
		return dodekaeder;
	}

	public float[][] getWuerfel() {
		// Würfel Koordinaten
		// xyz Koordinaten, rgb, uv
		float[] A = new float[] { 0.5f, -0.5f, 0.5f, 1, 0, 0, 0, 0 };
		float[] B = new float[] { 0.5f, 0.5f, 0.5f, 1, 1, 0, 1, 0 };
		float[] C = new float[] { -0.5f, 0.5f, 0.5f, 1, 1, 1, 0, 1 };
		float[] D = new float[] { -0.5f, -0.5f, 0.5f, 0.5f, -0.5f, -0.5f, 1, 1 };

		float[] E = new float[] { 0.5f, -0.5f, -0.5f, 0.816f, 0.1255f, 0.5647f, 0, 0 };
		float[] F = new float[] { 0.5f, 0.5f, -0.5f, 0, 1, 0, 1, 0 };
		float[] G = new float[] { -0.5f, 0.5f, -0.5f, 0, 1, 1, 0, 1 };
		float[] H = new float[] { -0.5f, -0.5f, -0.5f, 0, 0, 1, 1, 1 };

		float[][] wuerfel = koerper.getWuerfel(A, B, C, D, E, F, G, H);
		return wuerfel;	
	}

	@Override
	protected void init() {
		glfwSetKeyCallback(window, keyCallback = new KeyBoardHandler());
		phongShader = new ShaderProgram("phongShader");
		glUseProgram(phongShader.getId());

		normalShader = new ShaderProgram("aufgabe3");
		pTexShader = new ShaderProgram("pTexShader");
		// Texturen
		Texture tex = new Texture("texturL.jpg",8);
		glBindTexture(GL_TEXTURE_2D, tex.getId());
		glGenerateMipmap(GL_TEXTURE_2D);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR_MIPMAP_LINEAR);
		//glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
		//glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);

		glEnable(GL_DEPTH_TEST); // z-Buffer aktivieren
		glEnable(GL_CULL_FACE); // backface culling aktivieren

		loc = glGetUniformLocation(phongShader.getId(), "view");
		loc2= glGetUniformLocation(phongShader.getId(), "projection");
		glUniformMatrix4fv(loc, true, mat4.getValuesAsArray());	
		glUniformMatrix4fv(loc2, true, proj.getValuesAsArray());		


		glUseProgram(normalShader.getId());

		glEnable(GL_DEPTH_TEST); // z-Buffer aktivieren
		glEnable(GL_CULL_FACE); // backface culling aktivieren

		loc1 = glGetUniformLocation(normalShader.getId(), "view");
		loc3= glGetUniformLocation(normalShader.getId(), "projection");
		glUniformMatrix4fv(loc1, true, mat42.getValuesAsArray());	
		glUniformMatrix4fv(loc3, true, proj.getValuesAsArray());
		
		glUseProgram(pTexShader.getId());

		glEnable(GL_DEPTH_TEST); // z-Buffer aktivieren
		glEnable(GL_CULL_FACE); // backface culling aktivieren

		loc4 = glGetUniformLocation(pTexShader.getId(), "view");
		loc5= glGetUniformLocation(pTexShader.getId(), "projection");
		glUniformMatrix4fv(loc4, true, mat43.getValuesAsArray());	
		glUniformMatrix4fv(loc5, true, proj.getValuesAsArray());
	}

	public void setvBold(int i, int length, float[] values) {
		glBufferData(GL_ARRAY_BUFFER, values, GL_STATIC_DRAW);
		glVertexAttribPointer(i, length, GL_FLOAT, false, 0, 0);
		glEnableVertexAttribArray(i);
		glBindBuffer(GL_ARRAY_BUFFER, glGenBuffers());
	}

	@Override
	public void update() {
		// Transformation durchführen (Matrix anpassen)
		mat4 = new Matrix4();
		mat42 = new Matrix4();
		mat43 = new Matrix4();

		//Tastatureingaben
		if(KeyBoardHandler.isKeyDown(GLFW_KEY_DOWN))    		
			y-=0.1f;

		if(KeyBoardHandler.isKeyDown(GLFW_KEY_UP))
			y+=0.1f;

		if(KeyBoardHandler.isKeyDown(GLFW_KEY_LEFT))
			x-=0.1f;

		if(KeyBoardHandler.isKeyDown(GLFW_KEY_RIGHT))
			x+=0.1f;

		if(KeyBoardHandler.isKeyDown(GLFW_KEY_MINUS))//ß-Key
			scale-=0.01f;

		if(KeyBoardHandler.isKeyDown(GLFW_KEY_KP_ADD))//Nummern-Block +
			scale+=0.01f;

		mat42.scale(scale);
		mat42.rotateX(winkel).rotateY(winkel).rotateZ(winkel);
		mat42.translate(x, y+1, z-2);

		mat4.rotateX(winkel).rotateY(winkel).rotateZ(winkel);
		mat4.translate(-0.5f,-0.5f, -2);
		
		mat43.rotateX(winkel).rotateY(winkel).rotateZ(winkel);
		mat43.translate(1,-0.5f, -2);

		winkel += 0.01;	
	}

	@Override
	protected void render() {
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

		//1. Shader Wuerfel + phong
		glUseProgram(phongShader.getId());
		glUniformMatrix4fv(loc, true, mat4.getValuesAsArray());		
		int wVAO = glGenVertexArrays();
		glBindVertexArray(wVAO);
		int wVBO = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, wVBO);
		float[][]wuerfel = getWuerfel();
		setvBold(0, 3, wuerfel[0]);
		setvBold(1, 3, wuerfel[1]);
		setvBold(2, 3, wuerfel[2]);
		setvBold(3, 2, wuerfel[3]);		
		glDrawArrays(GL_TRIANGLES, 0, 36);//wuerfel
		
		
		//3.Shader prozedurale Textur	
		glUseProgram(pTexShader.getId());
		glUniformMatrix4fv(loc4, true, mat43.getValuesAsArray());			
		glDrawArrays(GL_TRIANGLES, 0, 36);//wuerfel

		//2.Shader Dodekaeder 		
		glUseProgram(normalShader.getId());
		glUniformMatrix4fv(loc1, true, mat42.getValuesAsArray());			
		int dVAO = glGenVertexArrays();
		glBindVertexArray(dVAO);
		int dVBO = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, dVBO);
		float[][]dode = getDodekaeder();
		setvBold(0, 3, dode[0]);
		setvBold(1, 3, dode[1]);
		setvBold(2, 3, dode[2]);		
		glDrawArrays(GL_TRIANGLES, 0, 108);	
	}

	@Override
	public void destroy() {
	}
}
