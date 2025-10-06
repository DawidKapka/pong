package com.dk.pong.object;

import com.dk.pong.object.infra.RenderableObject;

import java.awt.*;

import static com.dk.pong.infra.GameEngine.getCurrentFps;

public class FPSDisplay implements RenderableObject {

    @Override
    public void render(Graphics graphics) {
        graphics.setColor(Color.RED);
        graphics.setFont(new Font("Courier New", Font.PLAIN, 16));
        final String fpsString = "FPS: %s".formatted(getCurrentFps());
        graphics.drawString(fpsString, 4, 20);
    }
}
