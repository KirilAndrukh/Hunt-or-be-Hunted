package com.example.javaprojectwithjfx;
public class Road extends MapCell{
    private Road parent;

    /**
     *Constructor of the Road class which sets up sprite path
     */
    public Road() {
        setSpritePath("src/main/java/sprites/Road.png");
    }

    @Override
    public Road getParent() {
        return parent;
    }

    public void setParent(Road parent) {
        this.parent = parent;
    }
}
