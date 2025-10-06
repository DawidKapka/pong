package com.dk.pong;

import com.dk.pong.infra.GameLogic;
import com.dk.pong.infra.Scene;
import com.dk.pong.scene.GameLevelScene;
import com.dk.pong.scene.StartScreenScene;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.util.function.Consumer;

import static com.dk.pong.infra.GameEngine.getCurrentFps;

public class PongLogic implements GameLogic {
    private Scene currentScene;
    private Scene nextScene;
    private Consumer<KeyAdapter> keyAdapterHook;

    @Override
    public void setup(Consumer<KeyAdapter> keyAdapterHook) {
        this.keyAdapterHook = keyAdapterHook;
        setupScene();
    }

    private void setupScene() {
        final StartScreenScene startScreenScene = new StartScreenScene();
        startScreenScene.registerCallback(StartScreenScene.START_CALLBACK, this::onStartGame);
        setCurrentScene(startScreenScene);
    }

    private void setCurrentScene(Scene scene) {
        nextScene = scene;
    }

    private void onStartGame() {
        final GameLevelScene gameLevelScene = new GameLevelScene();
        setCurrentScene(gameLevelScene);
    }

    @Override
    public void update(double deltaTime) {
        if (currentScene != null) {
            currentScene.update(deltaTime);
        }

        if (nextScene != null) {
            if (currentScene != null) {
                currentScene.teardown();
            }
            currentScene = nextScene;
            currentScene.setup(keyAdapterHook);
            nextScene = null;
        }
    }

    @Override
    public void render(Graphics graphics) {
        currentScene.render(graphics);
    }

}
