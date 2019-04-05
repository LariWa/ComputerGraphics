#version 330

in vec3 farbeW;
in vec3 normaleW;
in vec3 posW;
in vec2 uv;
out vec3 pixelFarbeW;
uniform sampler2D smplr;
void main() { 
	//ambientLight
	float ambientStrength =0.4;
	vec3 lightColor = vec3(1.0,1.0,1.0);
	vec3 ambient = ambientStrength*lightColor;
	
	//diffuseLight
	vec3 lightPosition = vec3(0.0,0.0,-5.0);
	vec3 normal = normalize(normaleW);
	vec3 lightDirection = normalize(lightPosition - posW);
	float diffuseStrength = max(dot(normal, lightDirection),0.0);
	vec3 diffuse = diffuseStrength*lightColor;
	vec3 objectColor = vec3(0,0,0.5);
	
	//specularLight
	vec3 camera = vec3(0.0,0.0,-5.0);
	float specularStrength = 0.7;
	vec3 cameraDirect = normalize(camera-posW);
	vec3 reflectDir = reflect(-lightDirection, normal);
	float spec = pow(max(dot(cameraDirect, reflectDir),0.0),30);
	vec3 specular = specularStrength*spec*lightColor;
	
	
	vec3 result = (ambient+diffuse+specular)*objectColor;
	vec4 texel = texture(smplr, uv);
	
	
	float ptex = (1+sin(uv.x*50))/2;
	pixelFarbeW = texel.rgb *result; //Textur
	
	//pixelFarbeW = texel.rgb *result*ptex; //prozedurale Textur+Textur+ Phong
	
	//pixelFarbeW = result; //Phong
}