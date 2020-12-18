/*
* This class models a city in the application.
* AS A GEN
*/

package com.hust.ga.tsp.entity;

public class City {
    int index;
    int x;
    int y;
    
    public City(int index,int x, int y){
        this.index=index;
        this.x = x;
        this.y = y;
    }
    public City(int x, int y){
        this.index=0;
        this.x = x;
        this.y = y;
    }
    public int getX(){
        return this.x;
    }
    public int getY(){
        return this.y;
    }

    public double distanceTo(City city){
        int xDistance = Math.abs(getX() - city.getX());
        int yDistance = Math.abs(getY() - city.getY());
        double distance = Math.sqrt( (xDistance*xDistance) + (yDistance*yDistance) );
        
        return distance;
    }
    
    @Override
    public String toString(){
        return (String.valueOf( this.index));
    }
}