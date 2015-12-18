#version 420 core

out vec4 color;
in vec3 col0;

void main()
{
	color = vec4(col0, 1);
}