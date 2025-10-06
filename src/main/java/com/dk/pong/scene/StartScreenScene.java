package com.dk.pong.scene;

import com.dk.pong.infra.Scene;
import com.dk.pong.object.StartScreen;

public class StartScreenScene extends Scene {
    public static final String START_CALLBACK = "start";

    @Override
    protected void setupObjects() {
        final StartScreen startScreen = new StartScreen();
        startScreen.registerCallback(StartScreen.START_CALLBACK, () -> callCallback(START_CALLBACK));
        objects.add(startScreen);
    }

}
