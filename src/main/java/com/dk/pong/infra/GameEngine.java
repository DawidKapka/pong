package com.dk.pong.infra;

import java.awt.*;
import java.awt.image.BufferStrategy;

import static com.dk.pong.Main.WINDOW_HEIGHT;
import static com.dk.pong.Main.WINDOW_WIDTH;

public class GameEngine extends Canvas implements Runnable {
    private final GameLogic gameLogic;
    private boolean running = false;
    private static int currentFps;

    public GameEngine(int width, int height, GameLogic gameLogic) {
        this.gameLogic = gameLogic;
        gameLogic.setup(this::addKeyListener);
        setPreferredSize(new Dimension(width, height));
        setBackground(Color.BLACK);
        setFocusable(true);
        requestFocus();
    }

    public synchronized void start() {
        if (running) {
            return;
        }
        running = true;
        Thread gameThread = new Thread(this);
        gameThread.start();
    }

    public synchronized void stop() {
        if (!running) {
            return;
        }
        running = false;
    }

    @Override
    public void run() {
        createBufferStrategy(2);
        final BufferStrategy bufferStrategy = getBufferStrategy();

        final double targetFPS = 60.0;
        final double nsPerFrame = 1_000_000_000.0 / targetFPS;

        long lastTime = System.nanoTime();
        long timer = System.currentTimeMillis();
        int frames = 0;

        while (running) {
            long now = System.nanoTime();
            double deltaTime = (now - lastTime) / 1_000_000_000.0;
            lastTime = now;

            update(deltaTime);
            render(bufferStrategy);
            frames++;

            long frameTime = System.nanoTime() - now;
            long sleepTime = (long) nsPerFrame - frameTime;
            if (sleepTime > 0) {
                try {
                    Thread.sleep(sleepTime / 1_000_000, (int) (sleepTime % 1_000_000));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            if (System.currentTimeMillis() - timer >= 1000) {
                currentFps = frames;
                frames = 0;
                timer += 1000;
            }
        }
    }

    public static int getCurrentFps() {
        return currentFps;
    }

    public void update(double deltaTime) {
        gameLogic.update(deltaTime);
    }

    public void render(BufferStrategy bufferStrategy) {
        Graphics graphics = bufferStrategy.getDrawGraphics();
        graphics.setColor(Color.BLACK);
        graphics.fillRect(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);
        gameLogic.render(graphics);
        graphics.dispose();
        bufferStrategy.show();
    }
}
