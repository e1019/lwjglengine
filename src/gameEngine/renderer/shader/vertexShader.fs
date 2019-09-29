#version 400 core

in vec3 position;
in vec2 uvCoords;

out vec2 uvCoordsOut;

uniform mat4 transformation;
uniform mat4 perspective;
uniform mat4 camera;

void main(void){
    gl_Position = perspective * camera * transformation * vec4(position, 2);

    uvCoordsOut = uvCoords;
}