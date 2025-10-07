package com.dk.pong.scene;

import com.dk.pong.infra.Scene;
import com.dk.pong.object.GameOverScreen;
import com.dk.pong.object.VictoryScreen;

public class VictoryScene extends Scene {
    public static final String RESTART_CALLBACK = "restart";
    private final int score;

    public VictoryScene(int score) {
        this.score = score;
    }

    @Override
    protected void setupObjects() {
        final VictoryScreen victoryScreen = new VictoryScreen(score);
        victoryScreen.registerCallback(GameOverScreen.RESTART_CALLBACK, () -> executeCallback(RESTART_CALLBACK));
        objects.add(victoryScreen);
    }
}
