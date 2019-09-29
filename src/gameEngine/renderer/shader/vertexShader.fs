#version 400 core

in vec3 position;
in vec2 uvCoords;

out vec2 uvCoordsOut;

uniform mat4 transformation;
uniform mat4 perspective;
uniform mat4 camera;

void main(void){
    mat4 mvp = perspective * camera * transformation;
    gl_Position = mvp * vec4(position, 1.0);

    uvCoordsOut = uvCoords;
}