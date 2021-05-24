package proj3;

import java.util.concurrent.ArrayBlockingQueue;


class Restaurant {
	private ArrayBlockingQueue<String> qu;

	public Restaurant(int seat) {
		if (seat < 0)
			seat = 0;
		qu = new ArrayBlockingQueue<String>(seat);
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

class Person extends Thread {
	private Restaurant restaurant;

	public Person(String name, Restaurant p) {
		super(name);
		this.restaurant = p;
		start();
	}

	public void run() {
		while (true) {
			try {
				sleep((int) (Math.random() * 10000)); // waiting
			} catch (InterruptedException e) {
			}
			System.out.println(getName() + ": trying to enter");
			restaurant.enter();
			System.out.println(getName() + ": entered");
			try {
				sleep((int) (Math.random() * 10000)); // stay within restaurant
			} catch (InterruptedException e) {
			}
			restaurant.leave();
			System.out.println(getName() + ": left");
		}
	}
}

public class ex1 {
	public static void main(String[] args) {
		Restaurant restaurant = new Restaurant(10);
		for (int i = 1; i <= 40; i++) {
			Person c = new Person("Person " + i, restaurant);
		}
	}
}