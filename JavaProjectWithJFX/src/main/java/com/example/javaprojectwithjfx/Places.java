package com.example.javaprojectwithjfx;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

public class Places extends ObjectOnTheMap {
    private int maxNumberOfPrey = 0;
    private List<Prey> preys = new ArrayList<>();
    private List<Prey> preysWaiting = new ArrayList<>();

    /**
     * Method which check if the Prey instance can be added to the list of Prey instances inside the source/hideout using semaphore
     * @param prey - Prey to add
     */
    public void addPrey(Prey prey) {
        synchronized(preys) {
            if (preys.size() <= maxNumberOfPrey) {
                System.out.println("added");
                preys.add(prey);
            }
        }
    }

    /**
     * Method which deletes the prey instance from the list of Prey instances inside the source/hideout using semaphore
     * @param prey - Prey to remove
     */
    public void removePrey(Prey prey) {
        synchronized(preys) {
            if (preys.size() > 0) {
                System.out.println("removed");
                preys.remove(prey);
            }
        }
    }
    public int getMaxNumberOfPrey() {
        return maxNumberOfPrey;
    }

    public List<Prey> getPreys() {
        return preys;
    }

    public void setMaxNumberOfPrey(int maxNumberOfPrey) {
        this.maxNumberOfPrey = maxNumberOfPrey;
    }
}
