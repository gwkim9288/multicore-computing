package ex4;

import java.util.concurrent.*;

class ParkingGarage {
	  
	  private final Semaphore semaphore;
	  
	  public ParkingGarage(int places) {
	    semaphore = new Semaphore(places);
	  }
	  public void enter() { // enter parking garage
	    try {
			semaphore.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	  }
	  public void leave() { // leave parking garage
	    semaphore.release();
	  }
	}


	class Car extends Thread {
	  private ParkingGarage parkingGarage;
	  public Car(String name, ParkingGarage p) {
	    super(name);
	    this.parkingGarage = p;
	    start();
	  }
	  public void run() {
	    while (true) {
	      try {
	        sleep((int)(Math.random() * 10000)); // drive before parking
	      } catch (InterruptedException e) {}
	      System.out.println(getName()+": trying to enter");
	      parkingGarage.enter();
	      System.out.println(getName()+": entered");
	      try {
	        sleep((int)(Math.random() * 20000)); // stay within the parking garage
	      } catch (InterruptedException e) {}
	      parkingGarage.leave();
	      System.out.println(getName()+": left");
	    }
	  }
	}


	public class ParkingSemaphore {
	  public static void main(String[] args){
	    ParkingGarage parkingGarage = new ParkingGarage(10);
	    for (int i=1; i<= 40; i++) {
	      Car c = new Car("Car "+i, parkingGarage);
	    }
	  }
	}