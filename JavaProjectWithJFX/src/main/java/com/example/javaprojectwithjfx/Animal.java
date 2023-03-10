package com.example.javaprojectwithjfx;

import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

import java.util.List;

public abstract class Animal extends ObjectOnTheMap {
    private Thread thisThread;
    private int health;
    private int speed;
    private int strength;
    private Map map;
    private Pane layout;
    private boolean isWaiting = false;
    private String specieName;
    private ObjectOnTheMap target;

    /**
     * Method which moves the Animal instance and uses monitor to check if there is only one animal on the map cell
     * @param road - the MapCell instance which is checked
     */
    protected void walkOnThePath(MapCell road) {
        synchronized (road) {
            List<Prey> preys = map.getPanel().getPreys();
            List<Predator> predators = map.getPanel().getPredators();

            if ((this instanceof Prey) ||
                (this instanceof Predator)) {
                boolean occupied = true;

                while (occupied) {
                    int i = 0;

                    for (Prey prey : preys) {
                        if (prey.getX() == road.getX() && prey.getY() == road.getY()) {
                            occupied = true;
                            if (getTarget() != null && prey.getX() == getTarget().getX() && prey.getY() == getTarget().getY()) {
                                i++;
                            }
                        } else {
                            i++;
                        }
                    }

                    for (Predator predator : predators) {
                        if (predator.getX() == road.getX() && predator.getY() == road.getY()) {
                            occupied = true;
                            if (getTarget() != null && predator.getX() == getTarget().getX() && predator.getY() == getTarget().getY()) {
                                i++;
                            }
                        } else {
                            i++;
                        }
                    }

                    if (i == preys.size() + predators.size()) {
                        occupied = false;
                    }
                }

                if (road.getX() == (getX() - 1)) {
                    setX(getX() - 1);
                } else if (road.getX() == (getX() + 1)) {
                    setX(getX() + 1);
                } else if (road.getY() == (getY() - 1)) {
                    setY(getY() - 1);
                } else if (road.getY() == (getY() + 1)) {
                    setY(getY() + 1);
                }

                getSprite().setLayoutX(map.getTILE_SIZE() * getX());
                getSprite().setLayoutY(map.getTILE_SIZE() * getY());
            }
        }
    }

    /**
     * Method which makes the sprite of the animal disappear and removes it from the logic of the game
     */
    protected void die() {
        if (getMap().getPanel().getPreys().contains(this) || getMap().getPanel().getPredators().contains(this)) {
            getMap().getPanel().getPreys().remove(this);
            getMap().getPanel().getPredators().remove(this);
        }
        getSprite().setVisible(false);
        getThisThread().stop();
    }

    private void waitForThePlace(){
        isWaiting = true;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public String getSpecieName() {
        return specieName;
    }

    public void setSpecieName(String specieName) {
        this.specieName = specieName;
    }

    public ObjectOnTheMap getTarget() {
        return target;
    }

    public void setTarget(ObjectOnTheMap target) {
        this.target = target;
    }

    public Map getMap() {
        return map;
    }

    public void setMap(Map map) {
        this.map = map;
    }

    public boolean isWaiting() {
        return isWaiting;
    }

    public void setWaiting(boolean waiting) {
        isWaiting = waiting;
    }

    public Thread getThisThread() {
        return thisThread;
    }

    public void setThisThread(Thread thisThread) {
        this.thisThread = thisThread;
    }

    public Pane getLayout() {
        return layout;
    }

    public void setLayout(Pane layout) {
        this.layout = layout;
    }
}
