package a2;

import static org.lwjgl.opengl.GL30.*;

import lenz.opengl.AbstractOpenGLBase;
import lenz.opengl.ShaderProgram;

public class Aufgabe2 extends AbstractOpenGLBase {

	public static void main(String[] args) {
		new Aufgabe2().start("CG Aufgabe 2", 700, 700);
	}

	@Override
	protected void init() {
		// folgende Zeile läd automatisch "aufgabe2.v" (vertex) und "aufgabe2.f" (fragment)
		ShaderProgram shaderProgram = new ShaderProgram("aufgabe2");
		glUseProgram(shaderProgram.getId());

		// Koordinaten, VAO, VBO, ... hier anlegen und im Grafikspeicher ablegen
		float [] dreiecksKoordinaten = new float[] {-1,0,1, 1,0,0, 0,1,1};
		float [] color = new float[] {1,0,0,0,1,0,0,0,1};
		int vaold = glGenVertexArrays();
		glBindVertexArray(vaold);
		int vbold = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, vbold);
		glBufferData(GL_ARRAY_BUFFER, dreiecksKoordinaten, GL_STATIC_DRAW);
		glVertexAttribPointer(0,2,GL_FLOAT, false, 0, 0);
		glEnableVertexAttribArray(0);
		
		glBindBuffer(GL_ARRAY_BUFFER, glGenBuffers());
		glBufferData(GL_ARRAY_BUFFER, color, GL_STATIC_DRAW);
		glVertexAttribPointer(1,3,GL_FLOAT, false, 0, 0);
		glEnableVertexAttribArray(1);
		
				
		
	}

	@Override
	public void update() {
	}

	@Override
	protected void render() {
		glClear(GL_COLOR_BUFFER_BIT); // Zeichenfläche leeren

		// hier vorher erzeugte VAOs zeichnen
		glDrawArrays(GL_TRIANGLES,0,3);
	}

	@Override
	public void destroy() {
	}
}
