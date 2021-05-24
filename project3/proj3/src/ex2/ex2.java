package ex2;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

class Restaurant {
	private Integer num=0;
	private int limit;
	private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
	private final Lock wl = readWriteLock.writeLock();
	private final Lock rl = readWriteLock.readLock();
	public Restaurant(int seat) {
		if (seat < 0)
			seat = 0;
		limit = seat;
	}

	public void enter() { // enter parking garage
		wl.lock();
		try {
			num++;
		} finally{
			
			wl.unlock();
		}
	}

	public void leave() { // leave parking garage
		wl.lock();
		try {
			num --;
		} finally {
			wl.unlock();
		}
	}
	
	public int getNum() {
		int num;
		rl.lock();
		try {
			num = this.num;
		} finally {
			rl.unlock();
		}
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
			restaurant.enter();
			System.out.println(getName() + ": entered, now number of person in restaurant:"+ restaurant.getNum());
			try {
				sleep((int) (Math.random() * 10000)); // stay within restaurant
			} catch (InterruptedException e) {
			}
			restaurant.leave();
			System.out.println(getName() + ": left, now number of person in restaurant:" + restaurant.getNum());
		}
	}
}

public class ex2 {
	public static void main(String[] args) {
		Restaurant restaurant = new Restaurant(10);
		for (int i = 1; i <= 40; i++) {
			Person p = new Person("Person " + i, restaurant);
		}
	}
}