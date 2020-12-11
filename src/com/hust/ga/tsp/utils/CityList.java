

package com.hust.ga.tsp.utils;


import com.hust.ga.tsp.entity.City;

import java.util.ArrayList;

public  class CityList {

    // Holds our cities
    private static ArrayList cities = new ArrayList<City>();

    public static void addCity(City city) {
        cities.add(city);
    }
    
    // Get a city
    public static City getCity(int index){
        return (City)cities.get(index);
    }
    
    // Get the number of  cities
    public static int numberOfCities(){
        return cities.size();
    }
}
