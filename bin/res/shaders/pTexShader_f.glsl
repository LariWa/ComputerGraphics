#version 330

in vec3 farbeW;
in vec2 uv;
out vec3 pixelFarbeW;
void main() { 	
	float ptex = (1+sin(uv.x*50))/2;	
	pixelFarbeW = farbeW*ptex;
}