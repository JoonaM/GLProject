#version 420 core

layout (location = 0) in vec3 pos;
layout (location = 1) in float col;


vec3 unpackColor(float f) {
    vec3 color;
    
	color.b = floor(f / (256.0 * 256.0));
    color.g = floor((f - color.b * 256.0 * 256.0) / 256.0);
    color.r = floor(f - color.b * 256.0 * 256.0 - color.g * 256.0);
    
	return color / 256.0;
}

uniform mat4 translation;

out vec3 col0;

void main()
{
	gl_Position = translation * vec4(pos, 1);

	col0 = unpackColor(col);
}