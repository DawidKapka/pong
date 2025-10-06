package com.dk.pong.infra;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.util.function.Consumer;

public interface GameLogic {
    void setup(Consumer<KeyAdapter> keyAdapterHook);
    void update(double deltaTime);
    void render(Graphics graphics);
}
