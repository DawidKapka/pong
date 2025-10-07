package com.dk.pong.object;

import com.dk.pong.object.infra.CallbackObject;
import com.dk.pong.object.infra.InteractableObject;
import com.dk.pong.object.infra.RenderableObject;
import com.dk.pong.object.infra.UpdatableObject;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.function.Consumer;

import static com.dk.pong.Main.WINDOW_HEIGHT;
import static com.dk.pong.Main.WINDOW_WIDTH;
import static com.dk.pong.Main.getMainFont;

public class VictoryScreen extends CallbackObject implements RenderableObject, InteractableObject, UpdatableObject {
    public static final String RESTART_CALLBACK = "restart";
    private static final String INSTRUCTION = "Press any key to play again...";
    private static final String TITLE = "You Won!";

    private final int score;
    private boolean instructionVisible = true;
    private double timeSinceLastUpdate = 0f;
    private boolean restarting = false;
    private int secondsUntilRestart = 3;

    public VictoryScreen(int score) {
        this.score = score;
    }

    @Override
    public void registerListeners(Consumer<KeyAdapter> keyAdapterHook) {
        keyAdapterHook.accept(createKeyAdapter());
    }

    @Override
    public void render(Graphics graphics) {
        renderScore(graphics);
        renderTitle(graphics, restarting ? "%s".formatted(secondsUntilRestart) : TITLE);
        if (instructionVisible & !restarting) {
            renderInstructions(graphics);
        }
    }

    @Override
    public void update(double deltaTime) {
        if (!restarting) {
            toggleInstruction(deltaTime);
        } else {
            restartGame(deltaTime);
        }
    }


    private KeyAdapter createKeyAdapter() {
        return new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                restart();
            }
        };
    }

    private void restart() {
        restarting = true;
        timeSinceLastUpdate = 0f;
    }

    private void restartGame(double deltaTime) {
        timeSinceLastUpdate += deltaTime;
        if (timeSinceLastUpdate >= 1f) {
            if (secondsUntilRestart > 1) {
                secondsUntilRestart--;
            } else {
                executeCallback(RESTART_CALLBACK);
            }
            timeSinceLastUpdate = 0f;
        }
    }

    private void toggleInstruction(double deltaTime) {
        timeSinceLastUpdate += deltaTime;
        if (timeSinceLastUpdate >= 1f) {
            instructionVisible = !instructionVisible;
            timeSinceLastUpdate = 0f;
        }
    }

    private void renderInstructions(Graphics graphics) {
        graphics.setColor(Color.LIGHT_GRAY);
        final Font titleFont = getMainFont().deriveFont(16f);
        graphics.setFont(titleFont);
        final FontMetrics fontMetrics = graphics.getFontMetrics();
        final int y = (WINDOW_HEIGHT - 32) / 2 + 32;
        final int x = (WINDOW_WIDTH - fontMetrics.stringWidth(INSTRUCTION)) / 2;
        graphics.drawString(INSTRUCTION, x, y);
    }

    private void renderScore(Graphics graphics) {
        final String text = "Score: %s".formatted(score);
        graphics.setColor(Color.RED);
        final Font titleFont = getMainFont().deriveFont(32f);
        graphics.setFont(titleFont);
        final FontMetrics fontMetrics = graphics.getFontMetrics();
        final int y = (WINDOW_HEIGHT - 32) / 4;
        final int x = (WINDOW_WIDTH - fontMetrics.stringWidth(text)) / 2;
        graphics.drawString(text, x, y);
    }

    private void renderTitle(Graphics graphics, String title) {
        graphics.setColor(Color.WHITE);
        final Font titleFont = getMainFont().deriveFont(32f);
        graphics.setFont(titleFont);
        final FontMetrics fontMetrics = graphics.getFontMetrics();
        final int y = (WINDOW_HEIGHT - 32) / 2;
        final int x = (WINDOW_WIDTH - fontMetrics.stringWidth(title)) / 2;
        graphics.drawString(title, x, y);
    }
}
