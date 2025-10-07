package com.dk.pong.scene;

import com.dk.pong.infra.Scene;
import com.dk.pong.object.GameOverScreen;

public class GameOverScene extends Scene {
    public static final String RESTART_CALLBACK = "restart";
    private final int score;

    public GameOverScene(int score) {
        this.score = score;
    }

    @Override
    protected void setupObjects() {
        final GameOverScreen gameOverScreen = new GameOverScreen(score);
        gameOverScreen.registerCallback(GameOverScreen.RESTART_CALLBACK, () -> executeCallback(RESTART_CALLBACK));
        objects.add(gameOverScreen);
    }
}
