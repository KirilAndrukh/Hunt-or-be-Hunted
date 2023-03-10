package com.example.javaprojectwithjfx;

import javafx.geometry.Point2D;
import javafx.scene.effect.Light;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CheckPosition <T extends ObjectOnTheMap> {
    private List<Prey> preys = new ArrayList<>();
    private List<Predator> predators = new ArrayList<>();
    private List<Plant> plants = new ArrayList<>();
    private List<WaterSource> waterSources = new ArrayList<>();
    private List<Hideout> hideouts = new ArrayList<>();
    private List<Point2D> alreadyTakenPositions = new ArrayList<>();

    /**
     * Constructor of class CheckPosition to create and set lists of different objects on the map and when store their
     * coordinates
     * @param lPreys - list of Prey
     * @param lPredators - list of Predator
     * @param lPlants - list of Plant
     * @param lWaterSources - list of WaterSource
     * @param lHideouts - list of Hideout
     */
    public CheckPosition(List<Prey> lPreys, List<Predator> lPredators, List<Plant> lPlants,
                         List<WaterSource> lWaterSources, List<Hideout> lHideouts) {
        setPreys(lPreys);
        setPredators(lPredators);
        setPlants(lPlants);
        setWaterSources(lWaterSources);
        setHideouts(lHideouts);

        if (alreadyTakenPositions.isEmpty()) {
            addAllPositions();
        }
    }

    private void addAllPositions() {
        addPositions(preys);
        addPositions(predators);
        addPositions(plants);
        addPositions(waterSources);
    }

    /**
     * Method to add coordinates of the list of the ObjectOnTheMap instances
     * @param list - list of an ObjectOnTheMap instances
     */
    public void addPositions(List<? extends ObjectOnTheMap> list) {
        for (int i = 0; i < list.size(); i++) {
            int x = list.get(i).getX();
            int y = list.get(i).getY();

            addPosition(new Point2D(x, y));
        }
    }
    /**
     * Method to add coordinates of the ObjectOnTheMap instance
     * @param point - point which represents coordinates
     */
    public void addPosition(Point2D point) {
        if (!alreadyTakenPositions.contains(point)) {
            alreadyTakenPositions.add(point);
        }
    }

    /**
     * Method to delete coordinates of the Animal instances
     */
    public void deletePositionsForAnimals() {
        for (Prey prey : preys) {
            for (int i = 0; i < alreadyTakenPositions.size(); i++) {
                if (Objects.equals(alreadyTakenPositions.get(i), new Point2D(prey.getX(), prey.getY()))) {
                    alreadyTakenPositions.remove(i);
                    i++;
                }
            }
        }

        for (Predator predator : predators) {
            for (int i = 0; i < alreadyTakenPositions.size(); i++) {
                if (Objects.equals(alreadyTakenPositions.get(i), new Point2D(predator.getX(), predator.getY()))) {
                    alreadyTakenPositions.remove(i);
                    i++;
                }
            }
        }
    }


    public void setPreys(List<Prey> preys) {
        this.preys = preys;
        addPositions(preys);
    }

    public void setPredators(List<Predator> predators) {
        this.predators = predators;
        addPositions(predators);
    }

    public void setPlants(List<Plant> plants) {
        this.plants = plants;
        addPositions(plants);
    }

    public void setWaterSources(List<WaterSource> waterSources) {
        this.waterSources = waterSources;
        addPositions(waterSources);
    }

    public void setHideouts(List<Hideout> hideouts) {
        this.hideouts = hideouts;
        addPositions(hideouts);
    }

    public List<Point2D> getAlreadyTakenPositions() {
        return alreadyTakenPositions;
    }
}
