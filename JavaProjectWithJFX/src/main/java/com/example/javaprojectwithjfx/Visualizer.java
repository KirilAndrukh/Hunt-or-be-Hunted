package com.example.javaprojectwithjfx;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.List;

public class Visualizer {
    private Map map;
    private ControlPanel panel;

    /**
     *Constructor of the Visualizer class which sets up Map and ControlPanel instances necessary for all other functions
     * of the class
     */
    public Visualizer(Map map, ControlPanel panel) {
        setMap(map);
        setPanel(panel);
    }

    /**
     * Method which basically draws out map by creating a group of tiles that shows there are all the map cells should
     * be
     * @return mapGroup - list of Rectangle instances which represent mapCells
     */
    public Group drawMap() {
        Group mapGroup = new Group();

        for (int i = 0; i < map.getSizeOfTheMap(); i++) {
            for (int j = 0; j < map.getSizeOfTheMap(); j++) {
                MapCell currentCell = map.getMapCells().get(i * map.getSizeOfTheMap() + j);
                Rectangle tile = new Rectangle();
                currentCell.setSprite(tile);
                tile.setWidth(map.getTILE_SIZE());
                tile.setHeight(map.getTILE_SIZE());
                tile.relocate(currentCell.getX() * map.getTILE_SIZE(), currentCell.getY() * map.getTILE_SIZE());
                Image image = new Image("file:" + currentCell.getSpritePath());
                tile.setFill(new ImagePattern(image));
                mapGroup.getChildren().add(tile);
            }
        }

        return mapGroup;
    }

    /**
     * Method which creates a group of tiles that shows there are all the sources/hideouts/roads should be on the map
     * @return staticObjectsGroup - list of Rectangle instances which represent sources/hideouts/roads
     */
    public List<Rectangle> drawStaticObjects() {
        List<Rectangle> staticObjectsGroup = new ArrayList<>();

        for (Road road : map.getRoads()) {
            Rectangle tile = new Rectangle();
            tile.setWidth(map.getTILE_SIZE());
            tile.setHeight(map.getTILE_SIZE());
            tile.relocate(road.getX() * map.getTILE_SIZE(), road.getY() * map.getTILE_SIZE());
            Image image = new Image("file:" + road.getSpritePath());
            tile.setFill(new ImagePattern(image));
            staticObjectsGroup.add(tile);
        }

        for (Plant plant : map.getPlants()) {
            Rectangle tile = new Rectangle();
            tile.setWidth(map.getTILE_SIZE());
            tile.setHeight(map.getTILE_SIZE());
            plant.setSprite(tile);
            tile.relocate(plant.getX() * map.getTILE_SIZE(), plant.getY() * map.getTILE_SIZE());
            Image image = new Image("file:" + plant.getSpritePath());
            tile.setFill(new ImagePattern(image));
            staticObjectsGroup.add(tile);
        }

        for (WaterSource waterSource : map.getWaterSources()) {
            Rectangle tile = new Rectangle();
            tile.setWidth(map.getTILE_SIZE());
            tile.setHeight(map.getTILE_SIZE());
            waterSource.setSprite(tile);
            tile.relocate(waterSource.getX() * map.getTILE_SIZE(), waterSource.getY() * map.getTILE_SIZE());
            Image image = new Image("file:" + waterSource.getSpritePath());
            tile.setFill(new ImagePattern(image));
            staticObjectsGroup.add(tile);
        }

        for (Hideout hideout : map.getHideouts()) {
            Rectangle tile = new Rectangle();
            tile.setWidth(map.getTILE_SIZE());
            tile.setHeight(map.getTILE_SIZE());
            hideout.setSprite(tile);
            tile.relocate(hideout.getX() * map.getTILE_SIZE(), hideout.getY() * map.getTILE_SIZE());
            Image image = new Image("file:" + hideout.getSpritePath());
            tile.setFill(new ImagePattern(image));
            staticObjectsGroup.add(tile);
        }

        return staticObjectsGroup;
    }

    /**
     * Method which creates a group of tiles that shows there are all the animals should be on the map
     * @return animalGroup - list of Rectangle instances which represent animals
     */
    public List<Rectangle> drawOutAnimals() {
        List<Rectangle> animalGroup = new ArrayList<>();

        List<Prey> preys = panel.getPreys();
        List<Predator> predators = panel.getPredators();

        for (Prey prey : preys) {
            Rectangle tile = new Rectangle();
            prey.setSizeOfTheMap(map.getSizeOfTheMap());
            prey.setRoads(map.getRoads());
            prey.setMap(map);
            tile.setWidth(map.getTILE_SIZE());
            tile.setHeight(map.getTILE_SIZE());
            tile.relocate(prey.getX() * map.getTILE_SIZE(), prey.getY() * map.getTILE_SIZE());
            Image image = new Image("file:" + prey.getSpritePath());
            tile.setFill(new ImagePattern(image));
            prey.setSprite(tile);
            animalGroup.add(tile);
        }

        for (Predator predator : predators) {
            Rectangle tile = new Rectangle();
            tile.setWidth(map.getTILE_SIZE());
            tile.setHeight(map.getTILE_SIZE());
            tile.relocate(predator.getX() * map.getTILE_SIZE(), predator.getY() * map.getTILE_SIZE());
            Image image = new Image("file:" + predator.getSpritePath());
            tile.setFill(new ImagePattern(image));
            predator.setMap(map);
            predator.setSprite(tile);
            animalGroup.add(tile);
        }

        return animalGroup;
    }

    public void setMap(Map map) {
        this.map = map;
    }

    public void setPanel(ControlPanel panel) {
        this.panel = panel;
    }
}
