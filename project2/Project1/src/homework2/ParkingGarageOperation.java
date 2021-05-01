package homework2;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

class ParkingGarage {
	private ArrayBlockingQueue<String> qu;

	public ParkingGarage(int places) {
		if (places < 0)
			places = 0;
		qu = new ArrayBlockingQueue<String>(places);
	}

	public void enter() { // enter parking garage
		try {
			qu.put("enter");
		} catch (InterruptedException e) {
		}
	}

	public void leave() { // leave parking garage
		try {
			qu.take();
		} catch (InterruptedException e) {
		}
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
				sleep((int) (Math.random() * 10000)); // drive before parking
			} catch (InterruptedException e) {
			}
			System.out.println(getName() + ": trying to enter");
			parkingGarage.enter();
			System.out.println(getName() + ": entered");
			try {
				sleep((int) (Math.random() * 20000)); // stay within the parking garage
			} catch (InterruptedException e) {
			}
			parkingGarage.leave();
			System.out.println(getName() + ": left");
		}
	}
}

public class ParkingGarageOperation {
	public static void main(String[] args) {
		ParkingGarage parkingGarage = new ParkingGarage(10);
		for (int i = 1; i <= 40; i++) {
			Car c = new Car("Car " + i, parkingGarage);
		}
	}
}
