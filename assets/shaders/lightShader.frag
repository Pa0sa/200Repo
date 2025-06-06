#ifdef GL_ES
precision mediump float;
#endif

varying vec2 v_texCoords;
uniform sampler2D u_texture;

uniform vec2 u_lightPos;  // normalized screen position (0 to 1)
uniform float u_radius;   // in normalized units (e.g., 0.2)
uniform float u_strength; // how dark the surroundings are (e.g., 0.7)

void main() {
    vec4 texColor = texture2D(u_texture, v_texCoords);

    float dist = distance(v_texCoords, u_lightPos);
    float shadow = smoothstep(u_radius, u_radius * 1.5, dist); // smooth gradient

    texColor.rgb *= mix(1.0, 1.0 - u_strength, shadow); // darken only

    gl_FragColor = texColor;
}
