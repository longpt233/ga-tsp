/*
* TSP_GA.java
* Create a tour and evolve a solution
*/

package com.hust.ga.tsp.main;

import com.hust.ga.tsp.entity.City;
import com.hust.ga.tsp.entity.Population;
import com.hust.ga.tsp.utils.CityList;
import com.hust.ga.tsp.algorithm.GA;

import java.io.EOFException;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Ga_tsp {

    public static void main(String[] args) {

        try {
            File myObj = new File(
                    "/home/long/Documents/20201/artificial-intelligence/GA-TSP/src/com/hust/ga/tsp/main/a280.txt");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String line = myReader.nextLine();
                if (line.equals("EOF")) break;
                String[] lineSplit=line.split(" ");
                System.out.println(lineSplit[1]+" "+lineSplit[2]);
                CityList.addCity(new City(Integer.valueOf(lineSplit[1]),Integer.valueOf(lineSplit[2])));
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        // Initialize population
        Population pop = new Population(100);
        pop.addRandomTour();
        System.out.println("Initial distance: " + pop.getFittest().getDistance());

        // Evolve population for 100 generations
        pop = GA.evolvePopulation(pop);
        for (int i = 0; i < 100; i++) {
            pop = GA.evolvePopulation(pop);
        }

        // Print final results
        System.out.println("Finished");
        System.out.println("Final distance: " + pop.getFittest().getDistance());
        System.out.println("Solution:");
        System.out.println(pop.getFittest());
    }
}