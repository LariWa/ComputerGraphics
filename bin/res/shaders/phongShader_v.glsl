#version 330
layout(location=0) in vec3 eckenAusJavaW;
layout(location=1) in vec3 colorW;
layout(location=2) in vec3 normalW;
layout(location=3) in vec2 uvW;
uniform mat4 view;
uniform mat4 projection;

out vec3 farbeW;
out vec3 normaleW;
out vec3 posW;
out vec2 uv;
void main() {
	gl_Position = projection*view * vec4(eckenAusJavaW,1.0); 
	farbeW = colorW;
	normaleW=vec3(view*vec4(normalW,1.0));//inverse!
	posW= vec3(view*vec4(eckenAusJavaW,1.0));
	uv = uvW;
}