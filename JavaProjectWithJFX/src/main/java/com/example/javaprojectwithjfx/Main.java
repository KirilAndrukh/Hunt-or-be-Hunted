package com.example.javaprojectwithjfx;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Thread.sleep;

public class Main extends Application implements EventHandler<ActionEvent> {
    private Pane layout1;

    private CheckPosition chPos;
    private ControlPanel panel;
    private Map map;
    private Visualizer visualizer;

    /**
     * The main function of the program
     * @param args
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Method which starts the program and creates all the necessary UI
     * @param stage1
     */
    @Override
    public void start(Stage stage1) {
        Rectangle2D bounds = Screen.getPrimary().getVisualBounds();
        BorderPane root1 = new BorderPane();

        map = new Map();
        panel = new ControlPanel();
        chPos = new CheckPosition(panel.getPreys(), panel.getPredators(), map.getPlants(), map.getWaterSources(),
                map.getHideouts());
        map.setChPos(chPos);
        panel.setChPos(chPos);
        map.setPanel(panel);
        panel.setMap(map);
        visualizer = new Visualizer(map, panel);
        visualizer.setPanel(panel);

//        Map
        layout1 = new Pane();
        map.createMap();

        layout1.getChildren().add(visualizer.drawMap());

        root1.setCenter(layout1);
        stage1.setTitle("Hunt or be Hunted");

        Scene scene1 = new Scene(root1, map.getSizeOfTheMap() * map.getTILE_SIZE(), map.getSizeOfTheMap() * map.getTILE_SIZE());
        stage1.setScene(scene1);
        stage1.setX(bounds.getMinX() + (bounds.getMaxX() - scene1.getWidth()) * 0.2);
        stage1.setY(bounds.getMinY());
        stage1.show();

//        Control Panel
        Stage stage2 = new Stage();
        stage2.setTitle("Control Panel");

        BorderPane root2 = new BorderPane();
        VBox layout2_1 = new VBox(20);
        HBox layout2_2 = new HBox(245);
        root2.setTop(layout2_1);
        root2.setBottom(layout2_2);

        Button bCreatePrey = new Button("Create Prey");
        Button bCreatePredator = new Button("Create Predator");

        Button startTheMap = new Button("Start the map");

        Label header = new Label("This is control panel, below you can set all the things needed\nusing all the edit panels");
        header.setFont(Font.font("Arial", FontPosture.REGULAR, 16));

        Scene scene2 = new Scene(root2, 420, map.getSizeOfTheMap() * map.getTILE_SIZE());
        layout2_1.getChildren().addAll(header, bCreatePrey, bCreatePredator);
        layout2_2.getChildren().add(startTheMap);
        stage2.setScene(scene2);
        stage2.setX(bounds.getMinX() + (stage1.getX() + scene1.getWidth()));
        stage2.setY(bounds.getMinY());
        stage2.show();

        stage1.setOnCloseRequest(e -> {stage2.close(); System.exit(0);});
        stage2.setOnCloseRequest(e -> {stage1.close(); System.exit(0);});

        bCreatePrey.setOnAction(e -> stage2.setScene(panel.createPrey(scene2, stage2, map.getSizeOfTheMap(), map.getTILE_SIZE())));
        bCreatePredator.setOnAction(e -> stage2.setScene(panel.createPredator(scene2, stage2, map.getSizeOfTheMap(), map.getTILE_SIZE())));

        BorderPane layout3 = new BorderPane();
        Pane layout3_1 = new Pane();
        Button stopTheMap = new Button("Stop the map");
        layout3_1.getChildren().add(stopTheMap);
        layout3.setBottom(layout3_1);
        Scene scene3 = new Scene(layout3, 420, map.getSizeOfTheMap() * map.getTILE_SIZE());

        startTheMap.setOnAction(e -> {
            if (panel.getPreys().size() == 0) {
                Alert alert = new Alert("Error", "First of all, you need to create at least one Prey instance");
            } else {
                stage2.setScene(scene3);

                map.createStaticObjects();

                List<Rectangle> staticObjects = visualizer.drawStaticObjects();
                List<Rectangle> animals = visualizer.drawOutAnimals();

                for (Rectangle tile : staticObjects) {
                    tile.setOnMouseClicked((MouseEvent mouseEvent) -> {
                        List<Plant> plants = map.getPlants();
                        List<WaterSource> waterSources = map.getWaterSources();
                        List<Hideout> hideouts = map.getHideouts();

                        for (Plant plant : plants) {
                            if (plant.getSprite() == tile) {
                                if (map.getInformationWindow().isReadyForChoice()) {
                                    map.getInformationWindow().setReadyForChoice(false);
                                    map.getInformationWindow().setNewTarget(plant);
                                    map.getInformationWindow().targetApprove();
                                } else {
                                    map.getInformationWindow().checkTheClass(plant);
                                }
                            }
                        }
                        for (WaterSource waterSource : waterSources) {
                            if (waterSource.getSprite() == tile) {
                                if (map.getInformationWindow().isReadyForChoice()) {
                                    map.getInformationWindow().setReadyForChoice(false);
                                    map.getInformationWindow().setNewTarget(waterSource);
                                    map.getInformationWindow().targetApprove();
                                } else {
                                    map.getInformationWindow().checkTheClass(waterSource);
                                }
                            }
                        }
                        for (Hideout hideout : hideouts) {
                            if (hideout.getSprite() == tile) {
                                if (map.getInformationWindow().isReadyForChoice()) {
                                    map.getInformationWindow().setReadyForChoice(false);
                                    map.getInformationWindow().setNewTarget(hideout);
                                    map.getInformationWindow().targetApprove();
                                } else {
                                    map.getInformationWindow().checkTheClass(hideout);
                                }
                            }
                        }
                    });
                }

                layout1.getChildren().addAll(staticObjects);
                layout1.getChildren().addAll(animals);

                List<Thread> animalThreads = new ArrayList<>();

                List<Prey> preys = panel.getPreys();

                AnimalThreads game = new AnimalThreads();

                for (Prey prey : preys) {
                    prey.setLayout(layout1);
                    Thread preyThread = new Thread(prey);
                    prey.setThisThread(preyThread);
                    animalThreads.add(preyThread);
                }

                List<Predator> predators = panel.getPredators();

                for (Predator predator : predators) {
                    Thread predatorThread = new Thread(predator);
                    predator.setThisThread(predatorThread);
                    animalThreads.add(predatorThread);
                }

                chPos.deletePositionsForAnimals();

                if (!map.getRoads().isEmpty() && !map.getWaterSources().isEmpty() && !map.getPlants().isEmpty() &&
                        !map.getHideouts().isEmpty()) {
                    game.setAnimalThreads(animalThreads);
                }

                try {
                    game.startAllAnimalThreads();
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }

                stopTheMap.setOnAction(eventStop -> {
                    game.interruptAllAnimalThreads();
                    layout1.getChildren().removeAll(animals);
                    layout1.getChildren().removeAll(staticObjects);

                    for (Road road : map.getRoads()) {
                        road.setgCost(0);
                        road.sethCost(0);
                        road.setfCost(0);
                        road.setOpen(false);
                        road.setChecked(false);
                        road.setParent(null);
                    }

                    for (MapCell mapCell : map.getMapCells()) {
                        mapCell.setgCost(0);
                        mapCell.sethCost(0);
                        mapCell.setfCost(0);
                        mapCell.setOpen(false);
                        mapCell.setChecked(false);
                        mapCell.setParent(null);
                    }

                    map.setPlants(new ArrayList<>());
                    map.setWaterSources(new ArrayList<>());
                    map.setHideouts(new ArrayList<>());
                    map.setRoads(new ArrayList<>());

                    panel.setPreys(new ArrayList<>());
                    panel.setPredators(new ArrayList<>());

                    stage2.setScene(scene2);
                });
            }
        });
    }

    @Override
    public void handle(ActionEvent event) {

    }
}