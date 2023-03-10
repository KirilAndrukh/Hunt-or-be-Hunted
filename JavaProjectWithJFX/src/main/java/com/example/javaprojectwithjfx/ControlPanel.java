package com.example.javaprojectwithjfx;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class ControlPanel <T extends Animal> implements EventHandler<ActionEvent> {
    private Map map;
    private List<Prey> preys = new ArrayList<>();
    private List<Predator> predators = new ArrayList<>();
    private CheckPosition chPos;

    boolean xWasSet = false;
    boolean yWasSet = false;

    /**
     * Method to create a Scene of Control Panel for creation of the Prey instance
     * @param prevScene - previous Scene
     * @param prevStage - previous Stage
     * @param sizeOfMap - size of the Map
     * @param TILE_SIZE - size of the cells' sprite
     * @return sceneCreatePrey - scene in which you can create a Prey instance
     */
    public Scene createPrey(Scene prevScene, Stage prevStage, int sizeOfMap, int TILE_SIZE) {
        T prey = (T) new Prey();

        VBox layoutCreatePrey = new VBox(8);
        Scene sceneCreatePrey = new Scene(layoutCreatePrey, 420, sizeOfMap * TILE_SIZE);
        Button checkDataOfPrey = new Button("Check all the data of prey");

        Label preyName = new Label("NAME OF PREY");
        Label preyX = new Label("PREY'S X(FROM 1 TO " + (sizeOfMap) + ")");
        Label preyY = new Label("PREY'S Y(FROM 1 TO " + (sizeOfMap) + ")");
        Label preyHealth = new Label("HEALTH OF PREY");
        Label preySpeed = new Label("SPEED OF PREY");
        Label preyStrength = new Label("STRENGTH OF PREY");
        Label preySpecieName = new Label("PREY'S SPECIE NAME");
        Label preyWater = new Label("WATER OF PREY");
        Label preyEnergy= new Label("ENERGY OF PREY");

        TextField nameOfPrey = new TextField();
        TextField xOfPrey = new TextField();
        TextField yOfPrey = new TextField();
        TextField healthOfPrey = new TextField();
        TextField speedOfPrey = new TextField();
        TextField strengthOfPrey = new TextField();
        TextField specieNameOfPrey = new TextField();
        TextField waterOfPrey = new TextField();
        TextField energyOfPrey = new TextField();
        layoutCreatePrey.getChildren().addAll(preyName, nameOfPrey, preyX, xOfPrey, preyY, yOfPrey,
                preyHealth, healthOfPrey, preySpeed, speedOfPrey, preyStrength, strengthOfPrey, preySpecieName,
                specieNameOfPrey, preyWater, waterOfPrey, preyEnergy, energyOfPrey, checkDataOfPrey);

        checkDataOfPrey.setOnAction(e -> {
            addData(prey, prevStage, prevScene, sizeOfMap, nameOfPrey, xOfPrey, yOfPrey, healthOfPrey, speedOfPrey,
                    strengthOfPrey, specieNameOfPrey, waterOfPrey, energyOfPrey);
        });

        return sceneCreatePrey;
    }

    /**
     * Method to create a Scene of Control Panel for creation of the Predator instance
     * @param prevScene - previous Scene
     * @param prevStage - previous Stage
     * @param sizeOfMap - size of the Map
     * @param TILE_SIZE - size of the cells' sprite
     * @return sceneCreatePredator - scene in which you can create a Predator instance
     */
    public Scene createPredator(Scene prevScene, Stage prevStage, int sizeOfMap, int TILE_SIZE) {
        T predator = (T) new Predator();

        VBox layoutCreatePredator = new VBox(8);
        Scene sceneCreatePredator = new Scene(layoutCreatePredator, 420, sizeOfMap * TILE_SIZE);
        Button checkDataOfPredator = new Button("Check all the data of predator");

        Label predatorName = new Label("NAME OF PREDATOR");
        Label predatorX = new Label("PREDATOR'S X(FROM 1 TO " + (sizeOfMap) + ")");
        Label predatorY = new Label("PREDATOR'S Y(FROM 1 TO " + (sizeOfMap) + ")");
        Label predatorHealth = new Label("HEALTH OF PREDATOR");
        Label predatorSpeed = new Label("SPEED OF PREDATOR");
        Label predatorStrength = new Label("STRENGTH OF PREDATOR");
        Label predatorSpecieName = new Label("PREDATOR'S SPECIE NAME");

        TextField nameOfPredator = new TextField();
        TextField xOfPredator = new TextField();
        TextField yOfPredator = new TextField();
        TextField healthOfPredator = new TextField();
        TextField speedOfPredator = new TextField();
        TextField strengthOfPredator = new TextField();
        TextField specieNameOfPredator = new TextField();
        layoutCreatePredator.getChildren().addAll(predatorName, nameOfPredator, predatorX, xOfPredator, predatorY,
                yOfPredator, predatorHealth, healthOfPredator, predatorSpeed, speedOfPredator, predatorStrength,
                strengthOfPredator, predatorSpecieName, specieNameOfPredator, checkDataOfPredator);

        checkDataOfPredator.setOnAction(e -> {
            addData(predator, prevStage, prevScene, sizeOfMap, nameOfPredator, xOfPredator, yOfPredator, healthOfPredator,
                    speedOfPredator, strengthOfPredator, specieNameOfPredator, null, null);
        });

        return sceneCreatePredator;
    }

    private void addData(T animal, Stage prevStage, Scene prevScene, int sizeOfMap, TextField nameOfAnimal, TextField xOfAnimal,
                         TextField yOfAnimal, TextField healthOfAnimal, TextField speedOfAnimal, TextField strengthOfAnimal,
                         TextField specieNameOfAnimal, TextField waterOfAnimal, TextField energyOfAnimal) {
        waterOfAnimal = waterOfAnimal != null ? waterOfAnimal : new TextField();
        energyOfAnimal = energyOfAnimal != null ? energyOfAnimal : new TextField();

        String message = "";
        int x = -1, y = -1;

        String text = nameOfAnimal.getText();
        if (!containsDigits(text) && containsLetters(text)) {
            animal.setName(text);
        } else {
            message += "Name of the animal should contain letters and should not contain digits.\n";
        }

        if (validateNumbers(xOfAnimal)) {
            if (validatePositionByMapSize(Integer.valueOf(xOfAnimal.getText()), sizeOfMap)) {
                x = Integer.valueOf(xOfAnimal.getText());
                xWasSet = true;
            } else {
                xWasSet = false;
                message += "X should be bigger than 0 and less than " + (sizeOfMap) + ".\n";
            }
        } else {
            message += "X of the animal should contain digits and should not contain letters.\n";
        }
        if (validateNumbers(yOfAnimal)) {
            if (validatePositionByMapSize(Integer.valueOf(yOfAnimal.getText()), sizeOfMap)) {
                y = Integer.valueOf(yOfAnimal.getText());
                yWasSet = true;
            } else {
                yWasSet = false;
                message += "Y should be bigger than 0 and less than " + (sizeOfMap) + ".\n";
            }
        } else {
            message += "Y of the animal should contain digits and should not contain letters.\n";
        }

        if (validateNumbers(healthOfAnimal)) {
            if (Integer.valueOf(healthOfAnimal.getText()) < 31 && Integer.valueOf(healthOfAnimal.getText()) > 0) {
                int health = Integer.valueOf(healthOfAnimal.getText());
                animal.setHealth(health);
            } else {
                message += "Health should be bigger than 0 and less than 31.\n";
            }
        } else {
            message += "Health of the animal should contain digits and should not contain letters.\n";
        }
        if (validateNumbers(speedOfAnimal)) {
            if (Integer.valueOf(speedOfAnimal.getText()) < 5 && Integer.valueOf(speedOfAnimal.getText()) > 0) {
                int speed = Integer.valueOf(speedOfAnimal.getText());
                animal.setSpeed(speed);
            } else {
                message += "Speed should be bigger than 0 and less than 5.\n";
            }
        } else {
            message += "Speed of the animal should contain digits and should not contain letters.\n";
        }
        if (validateNumbers(strengthOfAnimal)) {
            if (Integer.valueOf(strengthOfAnimal.getText()) < 5 && Integer.valueOf(strengthOfAnimal.getText()) > 0) {
                int strength = Integer.valueOf(strengthOfAnimal.getText());
                animal.setStrength(strength);
            } else {
                message += "Strength should be bigger than 0 and less than 5.\n";
            }
        } else {
            message += "Strength of the animal should be a number and should not contain letters.\n";
        }

        text = specieNameOfAnimal.getText();
        if (!containsDigits(text) && containsLetters(text)) {
            animal.setSpecieName(text);
        } else {
            message += "Specie name of the animal should contain letters and should not contain digits.\n";
        }

        if (animal instanceof Prey) {
            if (validateNumbers(waterOfAnimal)) {
                if (Integer.valueOf(waterOfAnimal.getText()) < 31 && Integer.valueOf(waterOfAnimal.getText()) > 0) {
                    int water = Integer.valueOf(waterOfAnimal.getText());
                    ((Prey) animal).setWater(water);
                } else {
                    message += "Water should be bigger than 0 and less than 31.\n";
                }
            } else {
                message += "Water of prey should be a number and should not contain letters.\n";
            }
            if (validateNumbers(energyOfAnimal)) {
                if (Integer.valueOf(energyOfAnimal.getText()) < 31 && Integer.valueOf(energyOfAnimal.getText()) > 0) {
                    int energy = Integer.valueOf(energyOfAnimal.getText());
                    ((Prey) animal).setWater(energy);
                } else {
                    message += "Energy should be bigger than 0 and less than 31.\n";
                }
            } else {
                message += "Energy of prey should be a number and should not contain letters.\n";
            }
        }

        if (xWasSet && yWasSet) {
            if (!validatePositionByXAndY(x, y)) {
                message += "Something is already placed on this position(X, Y)";
            }
        }

        if (message != "") {
            Alert alert = new Alert("Invalid input", message);
            animal.setX(x - 1);
            animal.setY(y - 1);
            xWasSet = false;
            yWasSet = false;
        } else {
            if (animal instanceof Prey) {
                preys.add((Prey) animal);
                chPos.setPreys(preys);
                Alert alert = new Alert("Success", "The prey instance was created successfully!");
            } else {
                predators.add((Predator) animal);
                chPos.setPredators(predators);
                Alert alert = new Alert("Success", "The predator instance was created successfully!");
            }
            xWasSet = false;
            yWasSet = false;
            prevStage.setScene(prevScene);
        }
    }

    private boolean containsLetters(String text){
        char[] chars = text.toCharArray();
        int sb = 0;
        for(char c : chars){
            if(Character.isLetter(c)){
                sb += 1;
            }
        }
        return sb > 0;
    }

    private boolean containsDigits(String text){
        char[] chars = text.toCharArray();
        int sb = 0;
        for(char c : chars){
            if(Character.isDigit(c)){
                sb += 1;
            }
        }
        return sb > 0;
    }

    private boolean validateNumbers(TextField field) {
        String text = field.getText();
        if (containsDigits(text) && !containsLetters(text)) {
            return true;
        } else {
            return false;
        }
    }

    private boolean validatePositionByMapSize(int pos, int sizeOfMap) {
        if (pos >= 1 && pos < sizeOfMap) {
            return true;
        } else {
            return false;
        }
    }

    private boolean validatePositionByXAndY(int x, int y) {
        Point2D xAndY = new Point2D(x - 1, y - 1);

        if (chPos.getAlreadyTakenPositions().contains(xAndY)) {
            return false;
        } else {
            return true;
        }
    }

    public List<Prey> getPreys() {
        return preys;
    }

    public List<Predator> getPredators() {
        return predators;
    }

    public CheckPosition getChPos() {
        return chPos;
    }

    public void setChPos(CheckPosition chPos) {
        this.chPos = chPos;
    }

    public void setMap(Map map) {
        this.map = map;
    }

    public void setPreys(List<Prey> preys) {
        this.preys = preys;
    }

    public void setPredators(List<Predator> predators) {
        this.predators = predators;
    }

    @Override
    public void handle(ActionEvent event) {

    }
}
