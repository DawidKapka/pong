package com.dk.pong.object;

import com.dk.pong.object.infra.UpdatableObject;

import static com.dk.pong.Main.WINDOW_WIDTH;
import static java.awt.image.ImageObserver.WIDTH;

public class BallPositionPrediction implements UpdatableObject {
    private final Ball ball;
    private double predictedX = WINDOW_WIDTH / 2.0 - WIDTH / 2.0;
    private boolean storeVelocity = true;
    private double ballOriginalVelocity;

    public BallPositionPrediction(Ball ball) {
        this.ball = ball;
    }

    @Override
    public void update(double deltaTime) {
        if (ball.isGoingUp()) {
            if (storeVelocity) {
                ballOriginalVelocity = ball.getVelocity();
                storeVelocity = false;
            }
            calculatePrediction(deltaTime);
        } else {
            storeVelocity = true;
        };
    }

    public double getPredictedX() {
        return predictedX;
    }

    public int getBallWidth() {
        return ball.getWidth();
    }

    private void calculatePrediction(double deltaTime) {
        double predictedXTemp = ball.getX();
        double predictedYTemp = ball.getY();

        while (predictedYTemp > 32) {
            predictedYTemp -= 300.0 * deltaTime;
            predictedXTemp += ballOriginalVelocity * deltaTime;
            if (predictedXTemp <= 0 || predictedYTemp >= WINDOW_WIDTH) {
                ballOriginalVelocity = -ballOriginalVelocity;
            }
        }
        predictedX = (int) predictedXTemp;
    }
}
