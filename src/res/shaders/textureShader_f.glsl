#version 330

in vec3 posW;
in vec2 uv;

out vec3 pixelFarbeW;
uniform sampler2D smplr;
void main() { 
	vec4 texel = texture(smplr, uv);
	pixelFarbeW = texel.rgb;
}