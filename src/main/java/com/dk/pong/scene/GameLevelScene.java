package com.dk.pong.scene;

import com.dk.pong.ComputerPaddleCollisionRule;
import com.dk.pong.PlayerPaddleCollisionRule;
import com.dk.pong.infra.Scene;
import com.dk.pong.object.*;

public class GameLevelScene extends Scene {

    @Override
    protected void setupObjects() {
        final Paddle paddle = new Paddle();
        final Ball ball = new Ball();
        final Scoreboard pointsBoard = new Scoreboard();
        final ComputerPaddle computerPaddle = new ComputerPaddle(ball);
        ball.registerCallback(Ball.PLAYER_POINT, pointsBoard::addPoint);
        objects.add(paddle);
        objects.add(ball);
        objects.add(pointsBoard);
        objects.add(computerPaddle);
        objects.add(new FPSDisplay());
        collisionRules.add(new PlayerPaddleCollisionRule(paddle, ball));
        collisionRules.add(new ComputerPaddleCollisionRule(computerPaddle, ball));
    }
}
