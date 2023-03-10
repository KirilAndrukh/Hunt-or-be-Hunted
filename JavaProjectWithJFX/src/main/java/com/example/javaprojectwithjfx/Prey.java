package com.example.javaprojectwithjfx;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Thread.sleep;

public class Prey extends Animal implements Runnable, EventHandler<ActionEvent> {
    private List<Road> roads = new ArrayList<>();
    private boolean readyToProcreate = true;
    private int sizeOfTheMap;
    private List<MapCell> path = new ArrayList<>();
    private int water;
    private int energy;

    /**
     *Constructor of the Prey class which sets up sprite path
     */
    public Prey() {
        setSpritePath("src/main/java/sprites/Sheep.png");
    }

    /**
     * run method which runs the code in the separate thread for multithreading realisation
     */
    @Override
    public void run() {
        if (getSprite() != null) {
            getSprite().setOnMouseClicked(e -> {
                if (!getMap().getInformationWindow().isReadyForChoice()) {
                    getMap().getInformationWindow().checkTheClass(this);
                }
            });
        }
        while (true) {
            try {
                if (getHealth() <= 0) {
                    die();
                } else {
                    for (int i = 0; i < getSpeed(); i++) {
                        gettingThirsty();
                        gettingTired();
                        beingFull();
                        if (getTarget() != null) {
                            createPath();

                            if (!path.isEmpty()) {
                                int index = path.size() - 1;

                                walkOnThePath(path.get(index));
                                path.remove(index);
                            }
                        }
                    }
                    sleep(1000);
                }
            } catch (InterruptedException e) {

            }
        }
    }

    private void gettingThirsty() {
        if (water == 0) {
            if (getTarget() == null) {
                boolean isInWaterSource = false;
                for (WaterSource waterSource : getMap().getWaterSources()) {
                    if (waterSource.getX() == getX() && waterSource.getY() == getY()) {
                        isInWaterSource = true;
                    }
                }
                if (!isInWaterSource) {
                    selectWaterSource();
                    synchronized (getTarget()) {
                        ((WaterSource)getTarget()).removePrey(this);
                    }
                }
            }
            if (getTarget() instanceof WaterSource) {
                if (getTarget().getX() == getX() && getTarget().getY() == getY()) {
                    synchronized (getTarget()) {
                        ((WaterSource) getTarget()).addPrey(this);
                    }
                    drinkFromWaterSource((WaterSource) getTarget());
                    if (water >= 30) {
                        setTarget(null);
                    }
                }
            }
        } else {
            water--;
        }
    }

    private void gettingTired() {
        if (energy == 0) {
            if (getTarget() == null) {
                boolean isInPlant = false;
                for (Plant plant : getMap().getPlants()) {
                    if (plant.getX() == getX() && plant.getY() == getY()) {
                        isInPlant = true;
                    }
                }
                if (!isInPlant) {
                    selectPlant();
                    synchronized (getTarget()) {
                        ((Plant)getTarget()).removePrey(this);
                    }
                }
            }
            if (getTarget() instanceof Plant) {
                if (getTarget().getX() == getX() && getTarget().getY() == getY()) {
                    synchronized (getTarget()) {
                        ((Plant)getTarget()).addPrey(this);
                    }
                    eatThePlant((Plant) getTarget());
                    if (energy >= 30) {
                        setTarget(null);
                    }
                }
            }
        } else {
            energy--;
        }
    }

    private void beingFull() {
        if (energy != 0 && water != 0) {
            if (getTarget() == null) {
                boolean isInHideout = false;
                for (Hideout hideout : getMap().getHideouts()) {
                    if (hideout.getX() == getX() && hideout.getY() == getY()) {
                        isInHideout = true;
                    }
                }
                if (!isInHideout) {
                    selectHideout();
                    synchronized (getTarget()) {
                        ((Hideout)getTarget()).removePrey(this);
                    }
                }
            }
            if (getTarget() instanceof Hideout) {
                if (getTarget().getX() == getX() && getTarget().getY() == getY()) {
                    synchronized (getTarget()) {
                        ((Hideout)getTarget()).addPrey(this);
                    }
                    reproduce();
                  setTarget(null);
                }
            }
        }
    }

    private void createPath() {
        Road start = new Road();
        start.setX(getX());
        start.setY(getY());
        Road goal = new Road();
        goal.setX(getTarget().getX());
        goal.setY(getTarget().getY());

        for (Road road : roads) {
            if (road.getX() == start.getX() && road.getY() == start.getY()) {
                start = road;
            }
            if (road.getX() == goal.getX() && road.getY() == goal.getY()) {
                goal = road;
            }
        }

        synchronized (roads) {
            ASharpAlgorithmForRoads aSharp = new ASharpAlgorithmForRoads(roads, getMap().getPanel(), sizeOfTheMap, start, goal);

            if (!aSharp.getPath().isEmpty()) {
                for (Road road: aSharp.getPath()){
                    for (MapCell mapCell : getMap().getMapCells()) {
                        if (road.getX() == mapCell.getX() && road.getY() == mapCell.getY()) {
                            path.add(mapCell);
                        }
                    }
                }
            }
            aSharp = null;
        }

        start = null;
        goal = null;
    }

    private void selectPlant() {
        int minIndex = 0;
        int minDistance = Integer.MAX_VALUE;

        for (int i = 0; i < getMap().getPlants().size(); i++) {
            int xDistance = Math.abs(getMap().getPlants().get(i).getX() - getX());
            int yDistance = Math.abs(getMap().getPlants().get(i).getY() - getY());

            if ((xDistance + yDistance) < minDistance && (xDistance + yDistance) != 0) {
                minIndex = i;
                minDistance = (Math.abs(getMap().getPlants().get(i).getX() - getX()) + (Math.abs(getMap().getPlants().get(i).getY() - getY())));
            }
        }

        setTarget(getMap().getPlants().get(minIndex));
    }

    private void selectWaterSource() {
        int minIndex = 0;
        int minDistance = Integer.MAX_VALUE;

        for (int i = 0; i < getMap().getWaterSources().size(); i++) {
            int xDistance = Math.abs(getMap().getWaterSources().get(i).getX() - getX());
            int yDistance = Math.abs(getMap().getWaterSources().get(i).getY() - getY());

            if ((xDistance + yDistance) < minDistance && (xDistance + yDistance) != 0) {
                minIndex = i;
                minDistance = (Math.abs(getMap().getWaterSources().get(i).getX() - getX()) + (Math.abs(getMap().getWaterSources().get(i).getY() - getY())));
            }
        }

        setTarget(getMap().getWaterSources().get(minIndex));
    }

    private void selectHideout() {
        int minIndex = 0;
        int minDistance = Integer.MAX_VALUE;

        for (int i = 0; i < getMap().getHideouts().size(); i++) {
            int xDistance = Math.abs(getMap().getHideouts().get(i).getX() - getX());
            int yDistance = Math.abs(getMap().getHideouts().get(i).getY() - getY());

            if ((xDistance + yDistance) < minDistance && (xDistance + yDistance) != 0) {
                minIndex = i;
                minDistance = (Math.abs(getMap().getHideouts().get(i).getX() - getX()) + (Math.abs(getMap().getHideouts().get(i).getY() - getY())));
            }
        }

        setTarget(getMap().getHideouts().get(minIndex));
    }

    private void eatThePlant(Plant plant) {
        while (energy < 30) {
            energy += plant.getFeedingSpeed();
        }
    }

    private void drinkFromWaterSource(WaterSource waterSource) {
        while (water < 30) {
            water += waterSource.getReplenishingSpeed();
        }
    }

    private void reproduce() {
        if (((Hideout) getTarget()).getPreys().size() > 1) {
            synchronized (((Hideout) getTarget()).getPreys()) {
                Prey partner = null;

                for (Prey prey : ((Hideout) getTarget()).getPreys()) {
                    if (prey != this && prey.readyToProcreate ) {
                        partner = prey;
                    }
                }

                Prey child = new Prey();
                child.setName(this.getName() + partner.getName());
                child.setX(this.getX());
                child.setY(this.getY());
                child.setHealth((this.getHealth() + partner.getHealth()) / 2);
                child.setSpeed((this.getSpeed() + partner.getSpeed()) / 2);
                child.setStrength((this.getStrength() + partner.getStrength()) / 2);
                child.setSpecieName(this.getSpecieName());
                child.setWater(0);
                child.setEnergy(0);
                child.setRoads(getMap().getRoads());
                child.setMap(getMap());

                getMap().getPanel().getPreys().add(child);

                Rectangle tile = new Rectangle();
                child.setSprite(tile);
                Image image = new Image("file:" + child.getSpritePath());
                tile.setFill(new ImagePattern(image));
                tile.setWidth(getMap().getTILE_SIZE());
                tile.setHeight(getMap().getTILE_SIZE());
                tile.relocate(child.getX() * getMap().getTILE_SIZE(), child.getY() * getMap().getTILE_SIZE());

                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        getLayout().getChildren().add(tile);
                    }
                });

                Thread childThread = new Thread(child);
                child.setThisThread(childThread);
                childThread.start();

                readyToProcreate = false;
            }
        }
    }

    public int getWater() {
        return water;
    }

    public void setWater(int water) {
        this.water = water;
    }

    public int getEnergy() {
        return energy;
    }

    public void setEnergy(int energy) {
        this.energy = energy;
    }

    public void setRoads(List<Road> roads) {
        this.roads = roads;
    }

    public void setSizeOfTheMap(int sizeOfTheMap) {
        this.sizeOfTheMap = sizeOfTheMap;
    }

    public void setPath(List<MapCell> path) {
        this.path = path;
    }

    @Override
    public void handle(ActionEvent actionEvent) {

    }
}
