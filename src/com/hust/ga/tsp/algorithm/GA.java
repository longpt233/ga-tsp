/*
* GA.java
* Manages the generic algorithms for evolving population of trips.
*/

package com.hust.ga.tsp.algorithm;

import com.hust.ga.tsp.entity.*;

public class GA {


    private static final double mutationRate = 0.015;
    private static final int tournamentSize = 5;
    private static final boolean elitism = true;

    // main loop
    public static Population evolvePopulation(Population pop) {
        Population newPopulation = new Population(pop.getPopulationSize());

        // Keep our best individual if elitism is enabled
        int elitismOffset = 0;
        if (elitism) {
            newPopulation.setTour(0, pop.getFittest());
            elitismOffset = 1;
        }
        // chosse parent =>crossover
        for (int i = elitismOffset; i < newPopulation.getPopulationSize(); i++) {

// SELECTION :tounament seclection
            // tounament seclection or fitness selection
            Tour parent1 = tournamentSelection(pop);
            Tour parent2 = tournamentSelection(pop);
            // because random of chosse=> parent is change
// CROSSOVER
            Tour child = crossover(parent1, parent2);
            newPopulation.setTour(i, child);
        }

// MUTATE: swap 2 GEN
        for (int i = elitismOffset; i < newPopulation.getPopulationSize(); i++) {
            mutate(newPopulation.getTour(i));
        }

        return newPopulation;
    }

    public static Tour crossover(Tour parent1, Tour parent2) {

        Tour child = new Tour();

        int startPos = (int) (Math.random() * parent1.getTourSize());
        int endPos = (int) (Math.random() * parent1.getTourSize());

        // if start== end => continue normally
        // Loop and add the sub tour from parent1 to our child
        for (int i = 0; i < child.getTourSize(); i++) {
            // add GEN betwen 2 point cross
            if (startPos < endPos && i > startPos && i < endPos) {
                child.setCity(i, parent1.getCity(i));
            }
            // in opposite
            else if (startPos > endPos) {
                if (!(i < startPos && i > endPos)) {
                    child.setCity(i, parent1.getCity(i));
                }
            }
        }

        // Loop through parent2's city
        for (int i = 0; i < parent2.getTourSize(); i++) {
            // If child doesn't have the city add it
            if (!child.containsCity(parent2.getCity(i))) {
                // add to empty
                for (int j = 0; j < child.getTourSize(); j++) {
                    if (child.getCity(j) == null) {
                        child.setCity(j, parent2.getCity(i));
                        break;
                    }
                }
            }
        }
        return child;
    }

    private static void mutate(Tour tour) {
        for(int tourPos1=0; tourPos1 < tour.getTourSize(); tourPos1++){
            // rand to mutation
            if(Math.random() < mutationRate){
                // chosse random pos2 and swap
                int tourPos2 = (int) (tour.getTourSize() * Math.random());
                City city1 = tour.getCity(tourPos1);
                City city2 = tour.getCity(tourPos2);
                tour.setCity(tourPos2, city1);
                tour.setCity(tourPos1, city2);
            }
        }
    }

    // Selects random tounamentSize from input population, then return fitness tour
    private static Tour tournamentSelection(Population pop) {
        Population tournament = new Population(tournamentSize);
        for (int i = 0; i < tournamentSize; i++) {
            int randomId = (int) (Math.random() * pop.getPopulationSize());
            tournament.setTour(i, pop.getTour(randomId));
        }
        Tour fittest = tournament.getFittest();
        return fittest;
    }
}
