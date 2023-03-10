package com.example.javaprojectwithjfx;
public class WaterSource extends Places {
    private int replenishingSpeed;

    /**
     * Constructor ot the WaterSource instance which sets SpritePath
     */
    public WaterSource() {
        setSpritePath("src/main/java/sprites/WaterSource.png");
    }

    public int getReplenishingSpeed() {
        return replenishingSpeed;
    }

    public void setReplenishingSpeed(int replenishingSpeed) {
        this.replenishingSpeed = replenishingSpeed;
    }
}
