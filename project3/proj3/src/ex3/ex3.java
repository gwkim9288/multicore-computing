package ex3;

import java.util.concurrent.atomic.AtomicInteger;

class Restaurant {
	private int limit;
	AtomicInteger num = new AtomicInteger(0);
	public Restaurant(int seat) {
		if (seat < 0)
			seat = 0;
		limit = seat;
	}

	public int enter() { // enter parking garage
		int num;
		num = this.num.addAndGet(1);
		return num;
	}

	public int leave() { // leave parking garage
		int num;
		num = this.num.addAndGet(-1);
		return num;
	}
	
	public int getNum() {
		int num;
		num = this.num.get();
		return num;
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
			System.out.println(getName() + ": trying to enter, now number of person in restaurant: "+restaurant.getNum());
			System.out.println(getName() + ": entered, now number of person in restaurant:"+ restaurant.enter());
			try {
				sleep((int) (Math.random() * 10000)); // stay within restaurant
			} catch (InterruptedException e) {
			}
			System.out.println(getName() + ": left, now number of person in restaurant:" + restaurant.leave());
		}
	}
}

public class ex3 {
	public static void main(String[] args) {
		Restaurant restaurant = new Restaurant(10);
		for (int i = 1; i <= 40; i++) {
			Person p = new Person("Person " + i, restaurant);
		}
	}
}
