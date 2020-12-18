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
                    "/home/long/Documents/20201/artificial-intelligence/GA-TSP/src/com/hust/ga/tsp/main/att48.txt");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String line = myReader.nextLine();
                if (line.equals("EOF")) break;
                String[] lineSplit=line.split(" ");
                System.out.println(lineSplit[1]+" "+lineSplit[2]);
                CityList.addCity(new City(Integer.valueOf(lineSplit[0]),Integer.valueOf(lineSplit[1]),Integer.valueOf(lineSplit[2])));
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        if (false ) {
            Double totalTime = new Double(0);
            Long start, end;
            start = System.nanoTime();
            int gen = 0;
            for(int i =0;i<100;i++) {
                Population pop = new Population(100);
                pop.addRandomTour();
                pop = GA.evolvePopulation(pop);
                while (pop.getFittest().getDistance() >= 16000) {
                    gen++;
                    pop = GA.evolvePopulation(pop);
                }
                end = System.nanoTime();
                totalTime += (end - start) / 1000.0;

            }
            System.out.println("Finished :" + totalTime / 100);
            System.out.println("elite enable =" + GA.elitism + "  gen loop " + gen/100);
        }else {


            // MAIN
            Population pop = new Population(100);
            pop.addRandomTour();
            System.out.println("Initial distance: " + pop.getFittest().getDistance());


            Double totalTime = new Double(0);
            Long start, end;
            start = System.nanoTime();

            // Evolve population for 100 generations
            pop = GA.evolvePopulation(pop);
            for (int i = 0; i < 1000; i++) {
//            while (pop.getFittest().getDistance()>=35000){
                pop = GA.evolvePopulation(pop);
                System.out.println(" finess cua the he thu " +i+"  " + pop.getFittest().getDistance());
            }
            end = System.nanoTime();
            totalTime += (end - start) / 1000.0;
            // Print final results for nomal
            System.out.println("Finished :" + totalTime / 1000000);
            System.out.println("Final distance: " + pop.getFittest().getDistance());
            System.out.println("Solution:");
            System.out.println(pop.getFittest());

        }

    }
}