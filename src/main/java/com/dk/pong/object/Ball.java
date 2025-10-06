package com.dk.pong.object;

import com.dk.pong.object.infra.CallbackObject;
import com.dk.pong.object.infra.CollidingObject;
import com.dk.pong.object.infra.RenderableObject;
import com.dk.pong.object.infra.UpdatableObject;

import java.awt.*;

import static com.dk.pong.Main.WINDOW_HEIGHT;
import static com.dk.pong.Main.WINDOW_WIDTH;

public class Ball extends CallbackObject implements RenderableObject, UpdatableObject, CollidingObject {
    public static final String PLAYER_POINT = "playerPoint";
    private static final int HEIGHT = 16;
    private static final int WIDTH = 16;
    private static final double SPEED = 300.0;
    private double x = (WINDOW_WIDTH - 16) / 2.0;
    private double y = (WINDOW_HEIGHT - 16) / 2.0;
    private BallDirection direction = BallDirection.DOWN;
    private double velocityY = 0.0;
    private boolean started = false;
    private double timeSinceStart = 0f;

    @Override
    public void render(Graphics graphics) {
        graphics.setColor(Color.RED);
        graphics.fillRect((int)x, (int)y, WIDTH, HEIGHT);
    }

    @Override
    public void update(double deltaTime) {
        if (timeSinceStart < 1f) {
            timeSinceStart += deltaTime;
        } else {
            processWallCollision();
            if (goingDown()) {
                moveDown(deltaTime);
            } else {
                moveUp(deltaTime);
            }
            checkCeilingCollision();
            checkBottomCollision();
        }
    }

    @Override
    public int getX() {
        return (int)x;
    }

    @Override
    public int getY() {
        return (int)y;
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
        return velocityY;
    }

    public boolean isGoingUp() {
        return direction == BallDirection.UP;
    }

    public void playerPoint() {
        executeCallback(PLAYER_POINT);
    }

    private void checkBottomCollision() {
        if (isCollidingWithBottom()) {
            y = WINDOW_HEIGHT - HEIGHT;
            velocityY = 0;
        }
    }

    private void checkCeilingCollision() {
        if (isCollidingWithCeiling()) {
            y = 0;
            velocityY = 0;
        }
    }

    private boolean isCollidingWithBottom() {
        return y >= WINDOW_HEIGHT - HEIGHT;
    }

    private boolean isCollidingWithCeiling() {
        return y <= 0;
    }

    private void processWallCollision() {
        if (isCollidingWithWall()) {
            velocityY = -velocityY;
        }
    }

    private boolean isCollidingWithWall() {
        return x <= 0 || x >= WINDOW_WIDTH - WIDTH;
    }

    private void moveDown(double deltaTime) {
        y += SPEED * deltaTime;
        x -= velocityY * deltaTime;
    }

    private void moveUp(double deltaTime) {
        y -= SPEED * deltaTime;
        x += velocityY * deltaTime;
    }

    private boolean goingDown() {
        return direction == BallDirection.DOWN;
    }

    private void switchDirection() {
        if (direction == BallDirection.UP) {
            direction = BallDirection.DOWN;
        } else {
            direction = BallDirection.UP;
        }
    }

    private void addVelocity(double velocity) {
        if (velocity == 0) {
            velocityY = -velocityY;
        }
        velocityY += velocity;
    }

    public void handlePlayerCollision(double velocity) {
        switchDirection();
        addVelocity(velocity);
        playerPoint();
    }

    public void handleComputerCollision(double velocity) {
        switchDirection();
        addVelocity(velocity);
    }
}
