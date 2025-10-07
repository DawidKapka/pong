package com.dk.pong.collision;

import com.dk.pong.infra.CollisionRule;
import com.dk.pong.object.Ball;
import com.dk.pong.object.Paddle;

public class PlayerPaddleCollisionRule extends CollisionRule<Paddle, Ball> {

    public PlayerPaddleCollisionRule(Paddle paddle, Ball ball) {
        super(paddle, ball);
    }

    @Override
    public void onCollision(Paddle paddle, Ball ball) {
        ball.handlePlayerCollision(paddle.getVelocity() / 2);
    }
}
