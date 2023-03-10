package com.example.javaprojectwithjfx;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static java.lang.Thread.sleep;

public class Predator extends Animal implements Runnable, EventHandler<ActionEvent> {
    private List<Prey> preys;
    private List<MapCell> path = new ArrayList<>();
    private Random random = new Random();

    /**
     *Constructor of the Predator class which sets up sprite path
     */
    public Predator() {
        setSpritePath("src/main/java/sprites/Lion.png");
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
                        if (getTarget() == null) {
                            roaming();
                        } else {
                            followThePrey();
                            checkIfCanAttack();
                        }
                    }
                }
                sleep(1000);
            } catch (InterruptedException e) {

            }
        }
    }

    private void roaming() {
        if (getTarget() == null) {
            if (path.isEmpty()) {
                createRandomPath();
            } else {
                int index = path.size() - 1;

                walkOnThePath(path.get(index));
                path.remove(index);
            }
        } else if (getTarget().getX() == getX() && getTarget().getY() == getY()) {
            setTarget(null);
        }

        lookForPrey();
    }

    private void createRandomPath() {
        int randX = random.nextInt(getMap().getSizeOfTheMap());
        int randY = random.nextInt(getMap().getSizeOfTheMap());

        MapCell randCell = new MapCell();
        MapCell start = new MapCell();

        for (MapCell mapCell : getMap().getMapCells()) {
            if (mapCell.getX() == randX && mapCell.getY() == randY) {
                randCell = mapCell;
            }
            if (mapCell.getX() == getX() && mapCell.getY() == getY()) {
                start = mapCell;
            }
        }

        synchronized (getMap().getMapCells()) {
            ASharpAlgorithmForMapCells aSharp = new ASharpAlgorithmForMapCells(getMap().getMapCells(), getMap().getChPos(), getMap().getSizeOfTheMap(), start, randCell);

            path.addAll(aSharp.getPath());

            start = null;
            randCell = null;
        }
    }

    private void lookForPrey() {
        if (preys == null) {
            preys = getMap().getPanel().getPreys();
        }

        for (int i = -5; i < 6; i++) {
            for (int j = -5; j < 6; j++) {
                for (Prey prey : preys) {
                    if (prey.getX() == getX() + i && prey.getY() == getY() + j) {
                        setTarget(prey);
                    }
                }
            }
        }
    }

    private void followThePrey() {
            for (Prey prey : preys) {
                if (!getMap().getChPos().getAlreadyTakenPositions().contains(new Point2D(prey.getX(), prey.getY()))) {
                    path = new ArrayList<>();
                    createPath();
                } else {
                    setTarget(null);
                }
            }
            if (!path.isEmpty()) {
                int index = path.size() - 1;
                walkOnThePath(path.get(index));
                path.remove(index);
            }
    }

    private void checkIfCanAttack() throws InterruptedException {
        if (getTarget() != null) {
            if ((Math.abs(getX() - getTarget().getX()) == 1 && (getY() - getTarget().getY()) == 0) ||
                ((getX() - getTarget().getX()) == 0 && Math.abs(getY() - getTarget().getY()) == 1)) {
                attackPrey();
            }
        }
    }

    private void createPath() {
        MapCell start = new MapCell();
        start.setX(getX());
        start.setY(getY());
        MapCell goal = new MapCell();
        goal.setX(getTarget().getX());
        goal.setY(getTarget().getY());

        for (MapCell mapCell : getMap().getMapCells()) {
            if (mapCell.getX() == start.getX() && mapCell.getY() == start.getY()) {
                start = mapCell;
            }
            if (mapCell.getX() == goal.getX() && mapCell.getY() == goal.getY()) {
                goal = mapCell;
            }
        }

            ASharpAlgorithmForMapCells aSharp = new ASharpAlgorithmForMapCells(getMap().getMapCells(), getMap().getChPos(), getMap().getSizeOfTheMap(), start, goal);

            if (!aSharp.getPath().isEmpty()) {
                path = aSharp.getPath();
            }

            aSharp = null;

        start = null;
        goal = null;
    }

    private void attackPrey() throws InterruptedException {
        if (getTarget() instanceof Prey) {
            Prey prey = null;

            for (Prey searchPrey : preys) {
                if (getTarget().getX() == searchPrey.getX() && getTarget().getY() == searchPrey.getY()) {
                    prey = searchPrey;
                }
            }
            prey.setHealth(prey.getHealth() - Math.abs(getStrength() - prey.getStrength()));

            if (((Prey) getTarget()).getHealth() <= 0) {
                setTarget(null);
                relaxation();
            }
        }
    }

    private void relaxation() throws InterruptedException {
        sleep(10000);
    }

    @Override
    public void handle(ActionEvent actionEvent) {

    }
}
