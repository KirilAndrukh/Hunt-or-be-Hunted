package com.example.javaprojectwithjfx;

import javafx.scene.shape.Rectangle;

public class MapCell {
    private int x;
    private int y;
    private Rectangle sprite;
    private String spritePath = "src/main/java/sprites/MapCell.png";

    private MapCell parent;
    private boolean isOpen = false;
    private boolean isChecked = false;
    private int gCost;
    private int hCost;
    private int fCost;

    public String getSpritePath() {
        return spritePath;
    }

    public void setSpritePath(String spritePath) {
        this.spritePath = spritePath;
    }

    public Rectangle getSprite() {
        return sprite;
    }
    public void setSprite(Rectangle sprite) {
        this.sprite = sprite;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public MapCell getParent() {
        return parent;
    }

    public void setParent(MapCell parent) {
        this.parent = parent;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public int getgCost() {
        return gCost;
    }

    public void setgCost(int gCost) {
        this.gCost = gCost;
    }

    public int gethCost() {
        return hCost;
    }

    public void sethCost(int hCost) {
        this.hCost = hCost;
    }

    public int getfCost() {
        return fCost;
    }

    public void setfCost(int fCost) {
        this.fCost = fCost;
    }
}
