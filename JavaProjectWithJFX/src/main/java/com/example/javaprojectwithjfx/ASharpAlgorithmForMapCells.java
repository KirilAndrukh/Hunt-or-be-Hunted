package com.example.javaprojectwithjfx;


import javafx.geometry.Point2D;

import java.util.ArrayList;
import java.util.List;

public class ASharpAlgorithmForMapCells {
    private MapCell start;
    private MapCell goal;
    private CheckPosition chPos;
    private boolean goalReached = false;
    private int sizeOfMap;

    private List<MapCell> mapCells = new ArrayList<>();
    private List<MapCell> openList = new ArrayList<>();
    private List<MapCell> checkedList = new ArrayList<>();
    private List<MapCell> path = new ArrayList<>();
    private MapCell currentTile;

    /**
     * Constructor of the ASharpAlgorithmForMapCells which uses its parameters to create a list of MapCells from the goal to the start
     * @param cells - cells which are selected for pathfinding
     * @param chPos - instance of CheckPosition
     * @param sizeOfMap - size of the map to validate coordinates
     * @param start - start cell
     * @param goal - goal cell
     */
    public ASharpAlgorithmForMapCells(List<MapCell> cells, CheckPosition chPos, int sizeOfMap, MapCell start, MapCell goal) {
        setMapCells(cells);
        setStart(start);
        setGoal(goal);
        setChPos(chPos);
        setSizeOfMap(sizeOfMap);
        setCostForEachMapCell();
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

            for (MapCell mapCell : mapCells) {
                if (mapCell.getX() == currentX && mapCell.getY() == currentY - 1) {
                    openTile(mapCell);
                } else if (mapCell.getX() == currentX - 1 && mapCell.getY() == currentY) {
                    openTile(mapCell);
                } else if (mapCell.getX() == currentX && mapCell.getY() == currentY + 1) {
                    openTile(mapCell);
                } else if (mapCell.getX() == currentX + 1 && mapCell.getY() == currentY) {
                    openTile(mapCell);
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
                for (MapCell mapCell : mapCells) {
                    mapCell.setgCost(0);
                    mapCell.sethCost(0);
                    mapCell.setfCost(0);
                    mapCell.setOpen(false);
                    mapCell.setChecked(false);
                    mapCell.setParent(null);
                }
            }
        }
    }

    private void openTile(MapCell tile) {
        if (tile.isOpen() == false && tile.isChecked() == false) {
            tile.setOpen(true);
            tile.setParent(currentTile);

            openList.add(tile);
        }
    }

    private void setCostForEachMapCell() {
        for (MapCell tile : mapCells) {
            if (!chPos.getAlreadyTakenPositions().contains(new Point2D(tile.getX(), tile.getX()))) {
                getCost(tile);
            }
        }
    }

    private void getCost(MapCell tile) {
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
        MapCell current = goal;
        path.add(current);

        while (current.getParent() != start) {
            current = current.getParent();
            path.add(current);
        }
    }

    public void setStart(MapCell start) {
        this.start = start;
    }

    public void setGoal(MapCell goal) {
        this.goal = goal;
    }

    public void setChPos(CheckPosition chPos) {
        this.chPos = chPos;
    }

    public void setMapCells(List<MapCell> mapCells) {
        this.mapCells = mapCells;
    }

    public void setSizeOfMap(int sizeOfMap) {
        this.sizeOfMap = sizeOfMap;
    }

    public List<MapCell> getPath() {
        return path;
    }
}
