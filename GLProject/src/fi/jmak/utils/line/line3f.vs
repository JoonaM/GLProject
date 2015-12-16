#version 420 core

layout (location = 0) in vec3 pos;

uniform mat4 translation;

void main()
{
	gl_Position = translation * vec4(pos, 1);
}