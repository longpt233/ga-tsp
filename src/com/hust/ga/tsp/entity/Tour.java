/*
* different instances of a trip
* AS A CHROMOSOME
*/

package com.hust.ga.tsp.entity;

import com.hust.ga.tsp.utils.CityList;

import java.util.ArrayList;
import java.util.Collections;

public class Tour{

    // tour of cities
    private ArrayList tour = new ArrayList<City>();
  
    private double fitness = 0;
    private int distance = 0;
    
    // Constructs a blank tour
    public Tour(){
        for (int i = 0; i < CityList.numberOfCities(); i++) {
            tour.add(null);
        }
    }

    public void generateRandomTour() {
        for (int cityIndex = 0; cityIndex < CityList.numberOfCities(); cityIndex++) {
          this.setCity(cityIndex,CityList.getCity(cityIndex));
        }
        // Randomly reorder the tour
        Collections.shuffle(tour);
    }

    public City getCity(int tourPosition) {
        return (City)tour.get(tourPosition);
    }

    public void setCity(int pos, City city) {
        tour.set(pos, city);
        // reset
        fitness = 0;
        distance = 0;
    }

    public double getFitness() {
        // if not calculate before
        if (fitness == 0) {
            fitness = 1/(double)getDistance();
        }
        return fitness;
    }

    public int getDistance(){
        if (distance == 0) {
            int tourDistance = 0;
            for (int cityIndex=0; cityIndex < getTourSize(); cityIndex++) {
                City fromCity = getCity(cityIndex);
                City destinationCity;
                // for last
                if(cityIndex+1 < getTourSize()){
                    destinationCity = getCity(cityIndex+1);
                }
                else{
                    destinationCity = getCity(0);
                }
                tourDistance += fromCity.distanceTo(destinationCity);
            }
            distance = tourDistance;
        }
        return distance;
    }


    public int getTourSize() {
        return tour.size();
    }
    

    public boolean containsCity(City city){
        return tour.contains(city);
    }
    
    @Override
    public String toString() {
        String geneString = "|";
        for (int i = 0; i < getTourSize(); i++) {
            geneString += getCity(i)+"|";
        }
        return geneString;
    }
}