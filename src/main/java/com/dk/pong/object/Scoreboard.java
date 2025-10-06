package com.dk.pong.object;

import com.dk.pong.object.infra.RenderableObject;

import java.awt.*;

import static com.dk.pong.Main.WINDOW_WIDTH;
import static com.dk.pong.Main.getMainFont;

public class Scoreboard implements RenderableObject {
    private int points = 0;

    @Override
    public void render(Graphics graphics) {
        graphics.setColor(Color.WHITE);
        graphics.setFont(getMainFont().deriveFont(16f));
        final String text = "Score: %s".formatted(points);
        final FontMetrics fontMetrics = graphics.getFontMetrics();
        final int x = WINDOW_WIDTH - fontMetrics.stringWidth(text) - 8;
        final int y = 24;
        graphics.drawString(text, x, y);
    }

    public void addPoint() {
        points++;
    }
}
