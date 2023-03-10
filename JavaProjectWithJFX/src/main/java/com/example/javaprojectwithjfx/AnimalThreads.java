package com.example.javaprojectwithjfx;

import java.util.ArrayList;
import java.util.List;

public class AnimalThreads {
    List<Thread> animalThreads = new ArrayList<>();

    /**
     * Method which sets up all animal threads using animalThreads parameter
     * @param animalThreads - a list of threads that run Prey and Predator instances
     */
    public void setAnimalThreads(List<Thread> animalThreads) {
        this.animalThreads = animalThreads;
    }

    /**
     * Method which starts all animal threads
     * @throws InterruptedException
     */
    public void startAllAnimalThreads() throws InterruptedException {
        for (Thread animalThread : animalThreads) {
            animalThread.start();
        }
    }

    public void interruptAllAnimalThreads() {
        for (Thread animalThread : animalThreads) {
            animalThread.stop();
            animalThread = null;
        }
    }
}
