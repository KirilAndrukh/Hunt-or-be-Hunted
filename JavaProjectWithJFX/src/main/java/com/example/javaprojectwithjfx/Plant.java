package com.example.javaprojectwithjfx;
public class Plant extends Places{
    private int feedingSpeed;

    /**
     * Constructor ot the Plant instance which sets SpritePath
     */
    public Plant() {
        setSpritePath("src/main/java/sprites/Plant.png");
    }

    public int getFeedingSpeed() {
        return feedingSpeed;
    }

    public void setFeedingSpeed(int feedingSpeed) {
        this.feedingSpeed = feedingSpeed;
    }
}
