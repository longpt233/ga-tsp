/*
* Population.java
* This class manages the  population of candidate trips.
* AS A POPOLATION
*/

package com.hust.ga.tsp.entity;

public class Population {

    Tour[] tours;

    public Population(int populationSize) {
        tours = new Tour[populationSize];
    }

    public void addRandomTour(){
        for (int i = 0; i < getPopulationSize(); i++) {
            Tour newTour = new Tour();
            newTour.generateRandomTour();
            setTour(i, newTour);
        }
    }

    public void setTour(int index, Tour tour) {
        tours[index] = tour;
    }

    public Tour getTour(int index) {
        return tours[index];
    }

    public Tour getFittest() {
        Tour fittest = tours[0];
        for (int i = 1; i < getPopulationSize(); i++) {
            if (fittest.getFitness() <= getTour(i).getFitness()) {
                fittest = getTour(i);
            }
        }
        return fittest;
    }

    public int getWoriestIndex() {
        int res =0;
        for (int i = 1; i < getPopulationSize(); i++) {
            if (tours[res].getFitness() < getTour(i).getFitness()) {
                res = i;
            }
        }
        return res;
    }

    // Gets population size
    public int getPopulationSize() {
        return tours.length;
    }
}