package com.dk.pong.infra;

import com.dk.pong.object.infra.CollidingObject;

public abstract class CollisionRule<T extends CollidingObject, V extends CollidingObject> {
    private final T obj1;
    private final V obj2;

    public CollisionRule(T obj1, V obj2) {
        this.obj1 = obj1;
        this.obj2 = obj2;
    };

    public void checkCollision() {
        if (isColliding()) {
            onCollision(obj1, obj2);
        }
    }

    private boolean isColliding() {
        return obj1.getX() < obj2.getX() + obj2.getWidth() &&
                obj1.getX() + obj1.getWidth() > obj2.getX() &&
                obj1.getY() < obj2.getY() + obj2.getHeight() &&
                obj1.getY() + obj1.getHeight() > obj2.getY();
    }

    public abstract void onCollision(T obj1, V obj2);
}
