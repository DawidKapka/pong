package com.dk.pong.object.infra;

import java.util.HashMap;
import java.util.Map;

public abstract class CallbackObject {
    private final Map<String, Runnable> callbacks = new HashMap<>();

    public void registerCallback(String name, Runnable runnable) {
        callbacks.put(name, runnable);
    }

    protected void executeCallback(String callback) {
        if (callbacks.containsKey(callback)) {
            callbacks.get(callback).run();
        } else {
            throw new IllegalArgumentException("Callback '%s' is not registered to object".formatted(callback));
        }
    }
}
