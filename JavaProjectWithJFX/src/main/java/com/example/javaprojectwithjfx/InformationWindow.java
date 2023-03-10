package com.example.javaprojectwithjfx;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;

public class InformationWindow implements EventHandler<ActionEvent> {
    private Map map;
    private Class consideredClass;
    private Stage stage = new Stage();
    private boolean readyForChoice = false;
    private ObjectOnTheMap consideredObject;
    private ObjectOnTheMap newTarget = null;

    public InformationWindow(Map map) {
        setMap(map);
    }

    /**
     * Method to check the class of an object obj and then show this object's parameters in the separate window
     * @param obj - Object, which information is going to be displayed in information window
     */
    public void checkTheClass(ObjectOnTheMap obj) {
        setConsideredClass(obj.getClass());
        consideredObject = obj;

        showInformationAboutObject();
    }

    private void showInformationAboutObject() {
        BorderPane layout = new BorderPane();
        VBox layout1 = new VBox(8);
        Button deleteObject = null;
        Button changeTarget = null;
        Label changeTargetLabel = null;

        String message = "";

        if (consideredObject instanceof ObjectOnTheMap){
            message += "Name = " + getName(consideredObject) + ".\n" +
                    "X = " + getX(consideredObject) + ".\n" +
                    "Y = " + getY(consideredObject) + ".\n";

            if (consideredObject instanceof Animal) {
                message += "Health = " + getHealth((Animal) consideredObject) + ".\n" +
                        "Speed = " + getSpeed((Animal) consideredObject) + ".\n" +
                        "Strength = " + getStrength((Animal) consideredObject) + ".\n" +
                        "Specie Name = " + getSpecieName((Animal) consideredObject) + ".\n" +
                        "Target = " + getTarget((Animal) consideredObject) + ".\n";
                deleteObject = new Button("Delete the animal");
                deleteObject.setOnAction(e -> {
                    ((Animal) consideredObject).die();
                    ((Animal) consideredObject).die();
                    stage.close();
                });

                layout.getChildren().add(deleteObject);

                if (consideredObject instanceof Prey) {
                    message += "Water = " + getWater((Prey) consideredObject) + ".\n" +
                            "Energy = " + getEnergy((Prey) consideredObject) + ".\n";
                    changeTarget = new Button("Set the animal's target");
                    changeTarget.setOnAction(e -> {
                        readyForChoice = true;
                    });


                    changeTargetLabel = new Label("Firstly click on the button bellow,\nthen click on the object which you\nwant to make a target.");
                }
            }

            if (consideredObject instanceof Places) {
                message += "Number of Prey = " + getNumbOfPrey((Places) consideredObject) + ".\n" +
                        "Max Number of Prey = " + getMaxNumbOfPrey((Places) consideredObject) + ".\n";

                if (consideredObject instanceof WaterSource) {
                    message += "Replenishing Speed = " + getReplenishingSpeed((WaterSource) consideredObject)  + ".\n";
                }
                if (consideredObject instanceof Plant) {
                    message += "Feeding Speed = " + getFeedingSpeed((Plant) consideredObject)  + "\n";
                }
            }
        }

        Label label = new Label(message);

        layout.setCenter(layout1);

        layout1.getChildren().add(label);
        if (deleteObject != null) {
            layout1.getChildren().add(deleteObject);
        }
        if (changeTargetLabel != null) {
            layout1.getChildren().add(changeTargetLabel);
        }
        if (changeTarget != null) {
            layout1.getChildren().add(changeTarget);
        }

        if (map.getInformationWindow() != this) {
            stage.close();
        }

        Scene scene = new Scene(layout, 240, 480);

        if (stage.getScene() == null) {
            stage.setScene(scene);
        } else {
            stage.setScene(scene);
            stage.close();
        }

        stage.show();

        stage.setOnCloseRequest(e -> {
            readyForChoice = false;
        });
    }

    public void targetApprove() {
        if (!readyForChoice && newTarget != null) {
            ((Prey) consideredObject).setPath(new ArrayList<>());
            ((Prey) consideredObject).setTarget(newTarget);
        }
    }

    public void setNewTarget(ObjectOnTheMap newTarget) {
        this.newTarget = newTarget;
    }

    public ObjectOnTheMap getNewTarget() {
        return newTarget;
    }

    public boolean isReadyForChoice() {
        return readyForChoice;
    }

    public void setReadyForChoice(boolean readyForChoice) {
        this.readyForChoice = readyForChoice;
    }

    public ObjectOnTheMap getConsideredObject() {
        return consideredObject;
    }

    public void setMap(Map map) {
        this.map = map;
    }

    private void setConsideredClass(Class cl) {
        this.consideredClass = cl;
    }

    private String getName(ObjectOnTheMap obj) {
        return obj.getName();
    }

    private int getX(ObjectOnTheMap obj) {
        return obj.getX();
    }

    private int getY(ObjectOnTheMap obj) {
        return obj.getY();
    }

    private int getHealth(Animal obj) {
        return obj.getHealth();
    }

    private int getSpeed(Animal obj) {
        return obj.getSpeed();
    }

    private int getStrength(Animal obj) {
        return obj.getStrength();
    }

    private String getSpecieName(Animal obj) {
        return obj.getSpecieName();
    }

    private ObjectOnTheMap getTarget(Animal obj) {
        return obj.getTarget();
    }

    private int getEnergy(Prey obj) {
        return obj.getEnergy();
    }

    private int getWater(Prey obj) {
        return obj.getWater();
    }

    private int getNumbOfPrey(Places obj) {
        return obj.getPreys().size();
    }

    private int getMaxNumbOfPrey(Places obj) {
        return obj.getMaxNumberOfPrey();
    }

    private int getReplenishingSpeed(WaterSource obj) {
        return obj.getReplenishingSpeed();
    }

    private int getFeedingSpeed(Plant obj) {
        return obj.getFeedingSpeed();
    }

    @Override
    public void handle(ActionEvent actionEvent) {

    }
}
