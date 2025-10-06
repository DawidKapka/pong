package com.dk.pong;

import com.dk.pong.infra.CollisionRule;
import com.dk.pong.object.Ball;
import com.dk.pong.object.ComputerPaddle;

public class ComputerPaddleCollisionRule extends CollisionRule<ComputerPaddle, Ball> {

    public ComputerPaddleCollisionRule(ComputerPaddle paddle, Ball ball) {
        super(paddle, ball);
    }

    @Override
    public void onCollision(ComputerPaddle paddle, Ball ball) {
        ball.handleComputerCollision(paddle.getVelocity() / 2);
    }
}