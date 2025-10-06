package com.dk.pong.object;

import com.dk.pong.object.infra.CollidingObject;
import com.dk.pong.object.infra.InteractableObject;
import com.dk.pong.object.infra.RenderableObject;
import com.dk.pong.object.infra.UpdatableObject;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.function.Consumer;

import static com.dk.pong.Main.WINDOW_HEIGHT;
import static com.dk.pong.Main.WINDOW_WIDTH;

public class Paddle implements RenderableObject, InteractableObject, CollidingObject, UpdatableObject {
    private static final int WIDTH = 128;
    private static final int HEIGHT = 16;
    private static final double MOVEMENT_SPEED = 260.0;
    private double x = WINDOW_WIDTH / 2.0 - WIDTH / 2.0;
    private final int y = WINDOW_HEIGHT - 32;
    private double velocity = 0;

    @Override
    public void render(Graphics graphics) {
        graphics.setColor(Color.WHITE);
        graphics.fillRect((int) x, y, WIDTH, HEIGHT);
    }

    @Override
    public void registerListeners(Consumer<KeyAdapter> keyAdapterHook) {
        final KeyAdapter keyAdapter = createKeyAdapter();
        keyAdapterHook.accept(keyAdapter);
    }

    @Override
    public void update(double deltaTime) {
        x += velocity * deltaTime;

        if (velocity > 0) {
            velocity -= 1000 * deltaTime;
            if (velocity < 0) velocity = 0;
        } else if (velocity < 0) {
            velocity += 1000 * deltaTime;
            if (velocity > 0) velocity = 0;
        }

        if (x < 16) x = 16;
        if (x > WINDOW_WIDTH - WIDTH - 16) x = WINDOW_WIDTH - WIDTH - 16;
    }

    private void moveLeft() {
        velocity = -MOVEMENT_SPEED;
    }

    private void moveRight() {
        velocity = MOVEMENT_SPEED;
    }

    private KeyAdapter createKeyAdapter() {
        return new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int key = e.getKeyCode();
                if (key == KeyEvent.VK_A) moveLeft();
                if (key == KeyEvent.VK_D) moveRight();
            }

            @Override
            public void keyReleased(KeyEvent e) {
                int key = e.getKeyCode();
                if ((key == KeyEvent.VK_A && velocity < 0) ||
                        (key == KeyEvent.VK_D && velocity > 0)) {
                    velocity = 0;
                }
            }
        };
    }

    @Override
    public int getX() {
        return (int)x;
    }

    @Override
    public int getY() {
        return y;
    }

    @Override
    public int getWidth() {
        return WIDTH;
    }

    @Override
    public int getHeight() {
        return HEIGHT;
    }

    public double getVelocity() {
        return velocity;
    }
}
