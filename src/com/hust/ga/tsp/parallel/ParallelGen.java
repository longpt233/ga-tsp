package com.hust.ga.tsp.parallel;

import com.hust.ga.tsp.algorithm.GA;
import com.hust.ga.tsp.entity.City;
import com.hust.ga.tsp.entity.Population;
import com.hust.ga.tsp.entity.Tour;
import com.hust.ga.tsp.utils.CityList;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


public class ParallelGen {

    public   static Tour bestTour;
    private static int NUM_THREADS;
    private static int POP_SIZE=20;
    private final static int TEST_PER_THREADS_VALUE=100;
    private final static int DISTANCE_VALUE_STOP=15000;

    // tra ve so lan lap cho toi khi dc nghiem nho hon
    public static int para() throws Exception{
        bestTour=new Tour();
        bestTour.generateRandomTour();
        Population pop=new Population(POP_SIZE);
        pop.addRandomTour();
//        System.out.println("vao para "+ pop.getFittest().getDistance()+"bestTour"+bestTour.getDistance());

        int generation = 0;
        while (true) {

//            System.out.println("while  "+generation+"    " +pop.getFittest().getDistance()+"bestTour"+bestTour.getDistance());
            generation++;

            if(pop.getFittest().getDistance()<DISTANCE_VALUE_STOP) return generation;
            for (int i = 0; i < NUM_THREADS; i++) {
                MyThread temp = new MyThread("" + i, pop);
                temp.start();
                temp.join();
            }

            // tien hanh selection kieu khac : chi day thang con xin nhat vao thay the cho thang con toi nhat
            if(pop.getFittest().getFitness()<bestTour.getFitness()){
                // thi day no vao trong pop va loai ra thang toi nhat
                pop.setTour(pop.getWoriestIndex(),bestTour);
//                System.out.println(pop.getTour(pop.getWoriestIndex()).getDistance()+"duoc thay the bang"+bestTour.getDistance());
            }

        }
    }

    public static void main(String[] args) throws Exception {
        try {
            File myObj = new File(
                    "/home/long/Documents/20201/artificial-intelligence/GA-TSP/src/com/hust/ga/tsp/parallel/a280.txt");
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


        System.out.println("doc file xong");
//        for (POP_SIZE=0;POP_SIZE< 10;POP_SIZE++){
            for (NUM_THREADS=1;NUM_THREADS<6;NUM_THREADS++){
                int sum = 0;
                Double totalTime = new Double(0);
                Long start, end;
                for (int i = 0; i < TEST_PER_THREADS_VALUE; i ++) {

//            System.out.println("lan thu thu "+i);
                    start = System.currentTimeMillis();
                    sum+=para();
                    end = System.currentTimeMillis();
                    totalTime += (end-start)/10.0;
                }

                double avg_generations = (double)sum/TEST_PER_THREADS_VALUE;
                double avg_elapsed_time = totalTime/TEST_PER_THREADS_VALUE;
                System.out.println("num thread="+ParallelGen.NUM_THREADS +", pop size="+ ParallelGen.POP_SIZE+ ", gen to goals " + avg_generations+",time ="+avg_elapsed_time);

            }
//        }

    }

}


// thread co muc dich tim ra thang con xin nhat mot cach nhanh nhet
// neu chay nhieu luon thi kha nang tim ra ca ther vuot troi se lon hon
// chi chon ra mot thang con fit nhat, co the cai tien chon ra nhieu con fit nhat va chay nhieu luon hown
class MyThread extends Thread{

    private String name;

    private  Population pop;

    MyThread(String name, Population pop){
        this.name=name;
        this.pop=pop;
    }

    public void run(){
        Tour parent1 = GA.tournamentSelection(pop);
        Tour parent2 = GA.tournamentSelection(pop);
        Tour child = GA.crossover(parent1, parent2);
        GA.mutate(child);

//        System.out.println("running");

        synchronized(ParallelGen.bestTour){
            if(child.getFitness() > ParallelGen.bestTour.getFitness()){
                ParallelGen.bestTour = child;
            }
        }
    }
}
