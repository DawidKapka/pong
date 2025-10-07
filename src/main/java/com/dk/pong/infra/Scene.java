package com.dk.pong.infra;

import com.dk.pong.object.infra.InteractableObject;
import com.dk.pong.object.infra.RenderableObject;
import com.dk.pong.object.infra.UpdatableObject;

import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.awt.event.KeyAdapter;
import java.util.ArrayList;
import java.util.Map;
import java.util.function.Consumer;

public abstract class Scene {
    protected final List<RenderableObject> objects = new ArrayList<>();
    protected final List<CollisionRule<?, ?>> collisionRules = new ArrayList<>();
    private final Map<String, Runnable> callbacks = new HashMap<>();

    public void registerCallback(String name, Runnable callback) {
        callbacks.put(name, callback);
    }

    public void executeCallback(String name) {
        if (callbacks.containsKey(name)) {
            callbacks.get(name).run();
        } else {
            throw new IllegalArgumentException("No callback with the key %s found".formatted(name));
        }
    }

    public void setup(Consumer<KeyAdapter> keyAdapterHook) {
        setupObjects();
        registerInteractions(keyAdapterHook);
    };
    public void render(Graphics graphics) {
        objects.forEach(object -> object.render(graphics));
    };

    public void update(double deltaTime) {
        checkCollisions();
        objects.stream()
                .filter(UpdatableObject.class::isInstance)
                .map(UpdatableObject.class::cast)
                .forEach(object -> object.update(deltaTime));
    };

    public void teardown() {
        objects.clear();
        collisionRules.clear();
    }

    public void registerInteractions(Consumer<KeyAdapter> keyAdapterHook) {
        objects.stream()
                .filter(InteractableObject.class::isInstance)
                .map(InteractableObject.class::cast)
                .forEach(object -> object.registerListeners(keyAdapterHook));
    };

    private void checkCollisions() {
        collisionRules.forEach(CollisionRule::checkCollision);
    }

    protected abstract void setupObjects();
}
