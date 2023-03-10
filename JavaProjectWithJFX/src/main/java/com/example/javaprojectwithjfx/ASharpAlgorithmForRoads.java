package com.example.javaprojectwithjfx;

import java.util.ArrayList;
import java.util.List;

public class ASharpAlgorithmForRoads {
    private ControlPanel panel;
    private Road start;
    private Road goal;
    private boolean goalReached = false;
    private int sizeOfMap;

    private List<Road> roads = new ArrayList<>();
    private List<Road> openList = new ArrayList<>();
    private List<Road> checkedList = new ArrayList<>();
    private List<Road> path = new ArrayList<>();
    private Road currentTile;

    /**
     * Constructor of the ASharpAlgorithmForRoads which uses its parameters to create a list of Roads from the goal to the start
     * @param roads - cells which are selected for pathfinding
     * @param sizeOfMap - size of the map to validate coordinates
     * @param start - start cell
     * @param goal - goal cell
     */
    public ASharpAlgorithmForRoads(List<Road> roads, ControlPanel panel, int sizeOfMap, Road start, Road goal) {
        setStart(start);
        setGoal(goal);
        setRoads(roads);
        setPanel(panel);
        setSizeOfMap(sizeOfMap);
        setCostForEachRoad();
        search();
    }

    private void search() {
        currentTile = start;
        openList.add(currentTile);

        while (!goalReached) {
            currentTile.setChecked(true);
            checkedList.add(currentTile);
            openList.remove(currentTile);

            int currentX = currentTile.getX();
            int currentY = currentTile.getY();

            for (Road road : roads) {
                if (road.getX() == currentX && road.getY() == currentY - 1) {
                    openTile(road);
                } else if (road.getX() == currentX - 1 && road.getY() == currentY) {
                    openTile(road);
                } else if (road.getX() == currentX && road.getY() == currentY + 1) {
                    openTile(road);
                } else if (road.getX() == currentX + 1 && road.getY() == currentY) {
                    openTile(road);
                }
            }

            int bestTileIndex = 0;
            int bestfCost = Integer.MAX_VALUE;

            if (!openList.isEmpty()) {
                for (int i = 0; i < openList.size(); i++) {
                    if (openList.get(i).getfCost() < bestfCost) {
                        bestTileIndex = i;
                        bestfCost = openList.get(i).getfCost();
                    } else if (openList.get(i).getfCost() == bestfCost) {
                        if (openList.get(i).getgCost() < openList.get(bestTileIndex).getgCost()) {
                            bestTileIndex = i;
                        }
                    }
                }
                currentTile = openList.get(bestTileIndex);
            }

            if (currentTile == goal) {
                goalReached = true;
                createPath();
                for (Road road : roads) {
                    road.setgCost(0);
                    road.sethCost(0);
                    road.setfCost(0);
                    road.setOpen(false);
                    road.setChecked(false);
                    road.setParent(null);
                }
            }
        }
    }


    private void openTile(Road tile) {
        if (tile.isOpen() == false && tile.isChecked() == false) {
            tile.setOpen(true);
            tile.setParent(currentTile);

            List<Prey> preys = panel.getPreys();
            List<Predator> predators = panel.getPredators();

            for (Prey prey : preys) {
                if (prey.getX() == tile.getX() && prey.getY() == tile.getY()) {
                    tile.setChecked(true);
                    checkedList.add(tile);
                }
            }
            for (Predator predator : predators) {
                if (predator.getX() == tile.getX() && predator.getY() == tile.getY()) {
                    tile.setChecked(true);
                    checkedList.add(tile);
                }
            }

            openList.add(tile);
        }

    }

    private void setCostForEachRoad() {
        for (Road tile : roads) {
            getCost(tile);
        }
    }

    private void getCost(Road tile) {
        int tileX = tile.getX();
        int tileY = tile.getY();

        //            G cost of the tile
        int xDistance = Math.abs(tileX - start.getX());
        int yDistance = Math.abs(tileY - start.getY());

        int gCost = xDistance + yDistance;
        tile.setgCost(gCost);

        //            H cost of the tile
        xDistance = Math.abs(tileX - goal.getX());
        yDistance = Math.abs(tileY - goal.getY());

        int hCost = xDistance + yDistance;
        tile.sethCost(hCost);

        int fCost = hCost + gCost;

        tile.setfCost(fCost);
    }

    private void createPath() {
        Road current = goal;
        path.add(goal);

        while (current.getParent() != start) {
            current = current.getParent();
            path.add(current);
        }
    }

    public void setStart(Road start) {
        this.start = start;
    }

    public void setGoal(Road goal) {
        this.goal = goal;
    }

    public void setRoads(List<Road> roads) {
        this.roads = roads;
    }

    public void setSizeOfMap(int sizeOfMap) {
        this.sizeOfMap = sizeOfMap;
    }

    public void setPanel(ControlPanel panel) {
        this.panel = panel;
    }

    public List<Road> getPath() {
        return path;
    }
}
