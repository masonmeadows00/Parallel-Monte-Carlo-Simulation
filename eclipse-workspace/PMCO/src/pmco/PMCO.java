package pmco;

import java.util.Random;
import java.util.Scanner;
import java.lang.Math;
/*
 *  CSC428 Operating Systems
 *  DePauw University
 *  Parallel Monte Carlo Simulation
 *  Simulates darts hitting a dart board in order to estimate pi
 *  Coded By Mason Meadows @masonmeadows_2022@depauw.edu
 *  11/5/2020
 */
class MultiThreads extends Thread{
	
	private int arrows;  //stores number of arrows
	private int hit;    //records amount of hits
	double pi;         // stores value of pi for the thread
	
	//constructor allows for amount of arrows recorded in main to pass into threads
	public MultiThreads(int x) {
		 arrows = x;
	}
	
	public void run() {
			Random r = new Random();
			hit = 0;
			for(int i = 0; i < arrows; i++) {
				double x = (r.nextDouble()*2) - 1; //generates a random value between -1 and 1 as coordinates of arrow 
				double y = (r.nextDouble()*2) - 1; //generates a random value between -1 and 1 as coordinates of arrow 
				
				//calculates if the arrow hits within the dart board
				if(Math.sqrt(x*x + y*y) <= 1)   
					hit++;
			}
	
			pi = (double)(hit*4) / (double)arrows; //calculates pi
			
 }
	
}
	
public class PMCO {

	
	static Scanner s = new Scanner(System.in);
	static int threads; //stores amount of threads designated by user
	static int arrows;  //stores amount of arrows designated by user
	static double p;  //pi
	
	public static void main(String[] args) throws InterruptedException {
			
			MultiThreads multithreads[] = new MultiThreads[20]; //an array of threads 
			
			System.out.println("Hello, This program is a Parallel Monte Carlo Simulation using arrows and a dart board");
			System.out.println("Please indicate a number of threads, a number between 1-20 ");
			threads = s.nextInt();
			
			//makes sure the user enters a number between 1 and 20a
			while(threads > 20 || threads < 1){
				System.out.println("Please Indicate a number between 1 and 20");
				threads = s.nextInt();
	}
			System.out.println("Please enter the number of arrows you want to throw in each thread");
			arrows = s.nextInt();
			
			//starts the threads
			for(int i = 0; i < threads; i++) {
				multithreads[i] = new MultiThreads(arrows);
				multithreads[i].start();
			}
			
			//waits for all threads to finish
			for(int i = 0; i < threads; i++) {
				multithreads[i].join();
			}
			
			//calculates an average pi from all threads
			for(int i = 0; i < threads; i++) {
				p += multithreads[i].pi;
			}
				System.out.println("Pi = " + p/threads);
		}	
}
