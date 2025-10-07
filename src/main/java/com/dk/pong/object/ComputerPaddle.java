package com.dk.pong.object;

import com.dk.pong.object.infra.CollidingObject;
import com.dk.pong.object.infra.RenderableObject;
import com.dk.pong.object.infra.UpdatableObject;

import java.awt.*;

import static com.dk.pong.Main.WINDOW_WIDTH;

public class ComputerPaddle implements RenderableObject, CollidingObject, UpdatableObject {
    private static final int WIDTH = 128;
    private static final int HEIGHT = 16;
    private static final double MOVEMENT_SPEED = 300.0;
    private double x = WINDOW_WIDTH / 2.0 - WIDTH / 2.0;
    private final int y = 16;
    private double velocity = 0;
    private final BallPositionPrediction ballPositionPrediction;
    private double timeSinceLastMove = 0f;

    public ComputerPaddle(Ball ball) {
        this.ballPositionPrediction = new BallPositionPrediction(ball);
    }

    @Override
    public void render(Graphics graphics) {
        graphics.setColor(Color.WHITE);
        graphics.fillRect((int) x, y, WIDTH, HEIGHT);
    }

    @Override
    public void update(double deltaTime) {
        ballPositionPrediction.update(deltaTime);
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
        moveToPrediction(deltaTime);
    }

    private void moveToPrediction(double deltaTime) {
        timeSinceLastMove += deltaTime;
        if (timeSinceLastMove >= .05f) {
            timeSinceLastMove = 0f;
            final double desiredX = ballPositionPrediction.getPredictedX() - WIDTH / 2 + ballPositionPrediction.getBallWidth() / 2;
            if (x < desiredX && desiredX - x > 10) {
                x += 5;
            } else if (x > desiredX && x - desiredX > 10) {
                x -= 5;
            }
        }
    }

    private void moveLeft() {
        velocity = -MOVEMENT_SPEED;
    }

    private void moveRight() {
        velocity = MOVEMENT_SPEED;
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
