package com.dk.pong.object.infra;

import java.awt.event.KeyAdapter;
import java.util.function.Consumer;

public interface InteractableObject {
    void registerListeners(Consumer<KeyAdapter> keyAdapterHook);
}
