package com.example.javaprojectwithjfx;

import javafx.geometry.Point2D;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Map {
    private ControlPanel panel;
    private InformationWindow informationWindow = new InformationWindow(this);
    private static final int TILE_SIZE = 32;
    private static List<MapCell> mapCells = new ArrayList<>();
    private List<Plant> plants = new ArrayList<>();
    private List<WaterSource> waterSources = new ArrayList<>();
    private List<Hideout> hideouts = new ArrayList<>();
    private List<Road> roads = new ArrayList<>();
    private static final int sizeOfTheMap = 18;
    private CheckPosition chPos;

    Random random = new Random();

    /**
     *Method to create a list of map cells which are going to represent a map
     */
    public void createMap() {
        if (mapCells.isEmpty()) {
            for (int i = 0; i < getSizeOfTheMap(); i++) {
                for (int j = 0; j < getSizeOfTheMap(); j++) {
                    MapCell mapCell = new MapCell();
                    mapCell.setX(j);
                    mapCell.setY(i);
                    mapCells.add(mapCell);
                }
            }
        }
    }

    /**
     * Method which creates all static objects
     */
    public void createStaticObjects() {
        if (panel.getPreys().size() > 0) {
            int amountOfPlants = random.nextInt(1,9) + (panel.getPreys().size()/2);

            createPlants(amountOfPlants);
            createWaterSource(10 - amountOfPlants);
            createHideout(1);
            createRoads();
        }
    }

    private void createRoads() {
        List<Prey> preys = panel.getPreys();

        for (Prey prey : preys) {
            int x = prey.getX();
            int y = prey.getY();

            Road road = new Road();
            road.setX(x);
            road.setY(y);

            roads.add(road);
        }
        for (Plant plant : plants) {
            int x = plant.getX();
            int y = plant.getY();

            Road road = new Road();
            road.setX(x);
            road.setY(y);

            roads.add(road);
        }
        for (WaterSource waterSource : waterSources) {
            int x = waterSource.getX();
            int y = waterSource.getY();

            Road road = new Road();
            road.setX(x);
            road.setY(y);

            roads.add(road);
        }
        for (Hideout hideout : hideouts) {
            int x = hideout.getX();
            int y = hideout.getY();

            Road road = new Road();
            road.setX(x);
            road.setY(y);

            roads.add(road);
        }

        Road randomRoad = new Road();
        randomRoad.setX(random.nextInt(sizeOfTheMap));
        randomRoad.setY(random.nextInt(sizeOfTheMap));
        roads.add(randomRoad);

        createRoadsToRandomRoad(plants, randomRoad);
        createRoadsToRandomRoad(waterSources,randomRoad);
        createRoadsToRandomRoad(hideouts,randomRoad);
        createRoadsToRandomRoad(panel.getPreys(),randomRoad);
    }

    private void createRoadsToRandomRoad(List<? extends ObjectOnTheMap> startList, Road randomRoad) {
        for (int i = 0; i < startList.size(); i++) {
            MapCell start = startList.get(i);
            start.setX(startList.get(i).getX());
            start.setY(startList.get(i).getY());

            MapCell goal = new MapCell();
            goal.setX(randomRoad.getX());
            goal.setY(randomRoad.getY());

            for (MapCell mapCell : mapCells) {
                if (mapCell.getX() == start.getX() && mapCell.getY() == start.getY()) {
                    start = mapCell;
                }
                if (mapCell.getX() == goal.getX() && mapCell.getY() == goal.getY()) {
                    goal = mapCell;
                }
            }

            ASharpAlgorithmForMapCells aSharp = new ASharpAlgorithmForMapCells(mapCells, chPos, sizeOfTheMap, start, goal);

            List<MapCell> path = aSharp.getPath();

            for (MapCell mapCell : path) {
                int x = mapCell.getX();
                int y = mapCell.getY();

                Road road = new Road();
                road.setX(x);
                road.setY(y);

                roads.add(road);
            }
            start = null;
            aSharp = null;
        }
    }

    private void createWaterSource(int amountOfWaterSources) {
        for (int i = 0; i < amountOfWaterSources; i++) {
            WaterSource waterSource = new WaterSource();

            int xAndY[] = randomPlacement();
            waterSource.setX(xAndY[0]);
            waterSource.setY(xAndY[1]);
            waterSource.setReplenishingSpeed(random.nextInt(1,5));
            waterSource.setMaxNumberOfPrey(2 + random.nextInt(1));

            waterSources.add(waterSource);
            chPos.setWaterSources(waterSources);
        }
    }

    private void createPlants(int amountOfPlants) {
        for (int i = 0; i < amountOfPlants; i++) {
            Plant plant = new Plant();

            int xAndY[] = randomPlacement();
            plant.setX(xAndY[0]);
            plant.setY(xAndY[1]);
            plant.setFeedingSpeed(random.nextInt(1, 5));
            plant.setMaxNumberOfPrey(2 + random.nextInt(1));

            plants.add(plant);
            chPos.setPlants(plants);
        }
    }

    private void createHideout(int amountOfHideouts) {
        for (int i = 0; i < amountOfHideouts; i++) {
            Hideout hideout = new Hideout();

            int xAndY[] = randomPlacement();
            hideout.setX(xAndY[0]);
            hideout.setY(xAndY[1]);
            hideout.setMaxNumberOfPrey(2 + random.nextInt(1));

            hideouts.add(hideout);
            chPos.setHideouts(hideouts);
        }
    }

    private int[] randomPlacement() {
        int xAndY[] = {-1, -1};
        int x, y;

        boolean isSet = false;

        while (!isSet) {
            x = random.nextInt(sizeOfTheMap);
            y = random.nextInt(sizeOfTheMap);

            if (!chPos.getAlreadyTakenPositions().contains(new Point2D(x, y)) && validatePositionByMapSize(x, sizeOfTheMap)
                    && validatePositionByMapSize(y, sizeOfTheMap)) {
                isSet = true;
                xAndY[0] = x;
                xAndY[1] = y;
            }
        }

        return xAndY;
    }

    public static int getSizeOfTheMap() {
        return sizeOfTheMap;
    }

    public static List<MapCell> getMapCells() {
        return mapCells;
    }

    public static int getTILE_SIZE() {
        return TILE_SIZE;
    }

    private boolean validatePositionByMapSize(int pos, int sizeOfMap) {
        if (pos >= 1 && pos <= sizeOfMap) {
            return true;
        } else {
            return false;
        }
    }

    /**
     *Returns Check Position object
     * @return instance of CheckPosition
     */
    public CheckPosition getChPos() {
        return chPos;
    }

    /**
     *Sets Check Position object
     */
    public void setChPos(CheckPosition chPos) {
        this.chPos = chPos;
    }

    /**
     *Returns list of roads
     * @return list of roads
     */
    public List<Road> getRoads() {
        return roads;
    }

    /**
     *Returns list of roads
     * @return list of roads
     */
    public List<Plant> getPlants() {
        return plants;
    }

    public List<WaterSource> getWaterSources() {
        return waterSources;
    }

    public List<Hideout> getHideouts() {
        return hideouts;
    }

    public void setPanel(ControlPanel panel) {
        this.panel = panel;
    }

    public ControlPanel getPanel() {
        return panel;
    }

    public InformationWindow getInformationWindow() {
        return informationWindow;
    }

    public void setPlants(List<Plant> plants) {
        this.plants = plants;
    }

    public void setWaterSources(List<WaterSource> waterSources) {
        this.waterSources = waterSources;
    }

    public void setHideouts(List<Hideout> hideouts) {
        this.hideouts = hideouts;
    }

    public void setRoads(List<Road> roads) {
        this.roads = roads;
    }
}
