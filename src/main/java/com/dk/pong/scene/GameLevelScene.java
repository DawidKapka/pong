package com.dk.pong.scene;

import com.dk.pong.collision.ComputerPaddleCollisionRule;
import com.dk.pong.collision.PlayerPaddleCollisionRule;
import com.dk.pong.infra.Scene;
import com.dk.pong.object.*;

public class GameLevelScene extends Scene {
    public static final String PLAYER_WIN_CALLBACK = "playerWin";
    public static final String PLAYER_LOSS_CALLBACK = "playerLoss";
    private final Scoreboard scoreboard = new Scoreboard();

    @Override
    protected void setupObjects() {
        final Paddle paddle = new Paddle();
        final Ball ball = new Ball();
        final ComputerPaddle computerPaddle = new ComputerPaddle(ball);
        ball.registerCallback(Ball.PLAYER_POINT_CALLBACK, scoreboard::addPoint);
        ball.registerCallback(Ball.PLAYER_WIN_CALLBACK, () -> executeCallback(PLAYER_WIN_CALLBACK));
        ball.registerCallback(Ball.PLAYER_LOSS_CALLBACK, () -> executeCallback(PLAYER_LOSS_CALLBACK));
        objects.add(paddle);
        objects.add(ball);
        objects.add(scoreboard);
        objects.add(computerPaddle);
        objects.add(new FPSDisplay());
        collisionRules.add(new PlayerPaddleCollisionRule(paddle, ball));
        collisionRules.add(new ComputerPaddleCollisionRule(computerPaddle, ball));
    }

    public int getScore() {
        return scoreboard.getScore();
    }
}
