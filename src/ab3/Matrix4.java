package ab3;

//Alle Operationen �ndern das Matrixobjekt selbst und geben das eigene Matrixobjekt zur�ck
//Dadurch kann man Aufrufe verketten, z.B.
//Matrix4 m = new Matrix4().scale(5).translate(0,1,0).rotateX(0.5f);
public class Matrix4 {
	private float[] matrix;
	public Matrix4() {
		// TODO mit der Identit�tsmatrix initialisieren
		matrix = new float[] {1,0,0,0,
							  0,1,0,0,
							  0,0,1,0,
							  0,0,0,1};
	}
	
	public Matrix4(Matrix4 copy) {
		matrix = new float[] {};
		System.arraycopy(copy, 0, matrix, 0, copy.matrix.length);
		// TODO neues Objekt mit den Werten von "copy" initialisieren
	}

	public Matrix4(float near, float far) {
		float d = near;
		float f =far;
		matrix = new float[] {d,0,0,0,
							  0,d,0,0,
							  0,0,(-f-d)/(f-d),(-2*d*f)/(f-d),
							  0,0,-1,0};
		// TODO erzeugt Projektionsmatrix mit Abstand zur nahen Ebene "near" und Abstand zur fernen Ebene "far", ggf. weitere Parameter hinzuf�gen
	}

	public Matrix4 multiply(Matrix4 other) {
		// TODO hier Matrizenmultiplikation "this = other * this" einfügen
		float[] temp = new float[16];
		 for(int y=0; y<4; y++) {
			 for(int x =0; x<4; x++) {
		 			temp[y*4+x]=other.matrix[y*4]*this.matrix[x]+other.matrix[y*4+1]*this.matrix[x+4]+other.matrix[y*4+2]*this.matrix[x+8]+other.matrix[y*4+3]*this.matrix[x+12];
				}
		 }
		this.matrix=temp;
		return this;
	}
	
	public Matrix4 multiply(float[] other) {
		// TODO hier Matrizenmultiplikation "this = other * this" einfügen
		float[] temp = new float[] {};
		 for(int y=0; y<4; y++) {
			 for(int x =0; x<4; x++) {
		 			temp[y*4+x]=other[y*4]*this.matrix[x]+other[y*4+x+1]*this.matrix[x+4]+other[y*4+x+2]*this.matrix[x+8]+other[y*4+x+3]*this.matrix[x+12];
				}
		 }
		this.matrix=temp;
		return this;
	}

	public Matrix4 translate(float x, float y, float z) {
		this.matrix[3]=this.matrix[3]+x;
		this.matrix[7]=this.matrix[7]+y;
		this.matrix[11]=this.matrix[11]+z;
		// TODO Verschiebung um x,y,z zu this hinzuf�gen
		return this;
	}

	public Matrix4 scale(float uniformFactor) {
		// TODO gleichmäßige Skalierung um Faktor "uniformFactor" zu this hinzufügen
		for(int i=0; i<16; i++) {
			this.matrix[i]=this.matrix[i]*uniformFactor;
		}
		return this;
	}

	public Matrix4 scale(float sx, float sy, float sz) {
		// TODO ungleichförmige Skalierung zu this hinzuf�gen
		 for(int y=0; y<3; y++) {
			 for(int x =0; x<4; x++) {
				 if(y==0)
					 this.matrix[x+y*4]*=sx;
				 if(y==1)
					 this.matrix[x+y*4]*=sy;
				 if(y==2)
					 this.matrix[x+y*4]*=sz;
				 }				 
			 }
		return this;
	}

	public Matrix4 rotateX(float winkel) {
		// TODO Rotation um X-Achse zu this hinzufügen
		Matrix4 rotX = new Matrix4();
		rotX.matrix = new float[] {1,0,0,0,
				  0, (float) Math.cos(winkel), -(float) Math.sin(winkel),0,
				  0, (float) Math.sin(winkel), (float) Math.cos(winkel),0,
				  0,0,0,1};
		this.multiply(rotX);
		return this;
	}

	public Matrix4 rotateY(float winkel) {
		// TODO Rotation um Y-Achse zu this hinzufügen
		Matrix4 rotY = new Matrix4();
		rotY.matrix = new float[] {(float) Math.cos(winkel),0,-(float)Math.sin(winkel),0,
				  0,1,0,0,
				  (float) Math.sin(winkel),0,(float)Math.cos(winkel),0,
				  0,0,0,1};
		this.multiply(rotY);
		return this;
	}

	public Matrix4 rotateZ(float winkel) {
		// TODO Rotation um Z-Achse zu this hinzufügen
		Matrix4 rotZ = new Matrix4();
		rotZ.matrix = new float[] {(float)Math.cos(winkel),-(float) Math.sin(winkel),0,0,
				  (float)Math.sin(winkel),(float) Math.cos(winkel),0,0,
				  0,0,1,0,
				  0,0,0,1};
		this.multiply(rotZ);
		return this;
	}

	public float[] getValuesAsArray() {
		// TODO hier Werte in einem Float-Array mit 16 Elementen (spaltenweise gefüllt) herausgeben
			return matrix;
	}
}