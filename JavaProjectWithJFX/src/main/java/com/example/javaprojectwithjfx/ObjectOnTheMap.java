package com.example.javaprojectwithjfx;

import javafx.scene.shape.Rectangle;

public abstract class ObjectOnTheMap extends MapCell{
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
