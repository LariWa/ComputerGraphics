#version 330
layout(location=0) in vec3 eckenAusJavaW;
layout(location=1) in vec3 colorW;
layout(location=2) in vec3 normalW;
uniform mat4 view;
uniform mat4 projection;

out vec3 farbeW;
out vec3 normaleW;
out vec3 posW;

void main() {
	gl_Position = projection*view * vec4(eckenAusJavaW,1.0); 
	farbeW = colorW;	
	}