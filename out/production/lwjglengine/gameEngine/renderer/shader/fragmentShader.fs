#version 400 core

in vec2 uvCoordsOut;

out vec4 out_Color;

uniform sampler2D textureSampler;

void main(void){
    out_Color = texture(textureSampler, uvCoordsOut);
}