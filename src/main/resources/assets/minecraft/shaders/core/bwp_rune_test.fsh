#version 150

#moj_import <fog.glsl>

uniform sampler2D Sampler0;

uniform vec4 ColorModulator;
uniform float FogStart;
uniform float FogEnd;
uniform vec4 FogColor;

in float vertexDistance;
in vec4 vertexColor;
in vec4 lightMapColor;
in vec4 overlayColor;
in vec2 texCoord0;
in vec4 normal;
in float GameTime;


out vec4 fragColor;

void main() {
    vec4 color = texture(Sampler0, texCoord0);
    if (color.a < 0.001) {
        color.a = color.r = color.g = color.b = 0.;
    }
    float ty = texCoord0.y * 6. - 2.8;
    float offset = sin(texCoord0.y * 0.5) * cos(texCoord0.x * 1. - 0.5 + (GameTime / 32.));
    ty = max(min(ty + offset, 1.8), -1.6);
    float r = cos(ty + offset);
    if (ty + offset >= 1.8 || ty + offset <= -1.6) {
        r = 0.;
    }
    float g = r * r;
    float b = g * g * g;
    float a = b;
    g *= r;
    b *= r;
    r *= r;
    a += color.a;
    if (a < 0.001) {
        discard;
    }
    r += color.r;
    g += color.g;
    b += color.b;
    a = min(1., a);
    g = min(1., g);
    r = min(1., r);
    b = min(1., b);
    fragColor = linear_fog(vec4(r,g,b,a), vertexDistance, FogStart, FogEnd, FogColor);
}