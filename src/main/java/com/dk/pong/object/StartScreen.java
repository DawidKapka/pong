package com.dk.pong.object;

import com.dk.pong.object.infra.CallbackObject;
import com.dk.pong.object.infra.InteractableObject;
import com.dk.pong.object.infra.RenderableObject;
import com.dk.pong.object.infra.UpdatableObject;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.function.Consumer;

import static com.dk.pong.Main.*;

public class StartScreen extends CallbackObject implements RenderableObject, InteractableObject, UpdatableObject {
    public static final String START_CALLBACK = "start";
    private static final String TITLE = "Pong";
    private static final String INSTRUCTION = "Press any key to start...";

    private boolean instructionVisible = true;
    private double timeSinceLastUpdate = 0f;
    private boolean starting = false;
    private int secondsUntilStart = 3;


    @Override
    public void registerListeners(Consumer<KeyAdapter> keyAdapterHook) {
        keyAdapterHook.accept(createKeyAdapter());
    }

    @Override
    public void update(double deltaTime) {
        if (!starting) {
            toggleInstruction(deltaTime);
        } else {
            startGame(deltaTime);
        }
    }

    @Override
    public void render(Graphics graphics) {
        renderTitle(graphics, starting ? "%s".formatted(secondsUntilStart) : TITLE);
        if (instructionVisible & !starting) {
            renderInstructions(graphics);
        }
    }

    private KeyAdapter createKeyAdapter() {
        return new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                start();
            }
        };
    }

    private void start() {
        starting = true;
        timeSinceLastUpdate = 0f;
    }

    private void startGame(double deltaTime) {
        timeSinceLastUpdate += deltaTime;
        if (timeSinceLastUpdate >= 1f) {
            if (secondsUntilStart > 1) {
                secondsUntilStart--;
            } else {
                executeCallback(START_CALLBACK);
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
