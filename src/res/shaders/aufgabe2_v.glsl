#version 330
layout(location=0) in vec2 eckenAusJava;
layout(location=1) in vec3 color;
float w = 0.2;
mat2 rot = mat2(cos(w),sin(w),-sin(w),cos(w));
out vec3 farbe;
void main() {
	//hier kann Transformation erfolgen
	vec2 ecken = rot*eckenAusJava;
	gl_Position = vec4(ecken,0.0, 1.0);
	farbe = color;
}