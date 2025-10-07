package com.dk.pong;

import com.dk.pong.infra.GameEngine;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Main {
    public static final int WINDOW_WIDTH = 800;
    public static final int WINDOW_HEIGHT = 600;

    public static void main(String[] args) {
        final PongLogic pongLogic = new PongLogic();
        final GameEngine gameEngine = new GameEngine(WINDOW_WIDTH, WINDOW_HEIGHT, pongLogic);
        setupFrame(gameEngine);
        gameEngine.start();
    }

    public static Font getMainFont() {
        try {
            return Font.createFont(Font.TRUETYPE_FONT, Main.class.getResourceAsStream("/fonts/PressStart2P-Regular.ttf"));
        } catch (Exception e) {
            throw new RuntimeException("Error loading font", e);
        }
    }

    private static void setupFrame(GameEngine gameEngine) {
        final Frame frame = new Frame("Retro Pong");
        frame.add(gameEngine);
        frame.pack();
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.addWindowListener(getWindowClosingAdapter(frame, gameEngine));
    }

    private static WindowAdapter getWindowClosingAdapter(Frame frame, GameEngine gameEngine) {
        return new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                gameEngine.stop();
                frame.dispose();
                System.exit(0);
            }
        };
    }
}
