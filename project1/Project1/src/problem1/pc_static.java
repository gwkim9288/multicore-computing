package problem1;

public class pc_static {
	private final static int NUM_THREAD = 6;
	static MyThread[] threads;
	final static int NUM_END = 200000;
	private static int count =0;
	
	public static void main(String[] args) {
		threads = new MyThread[NUM_THREAD];
		long startTime = System.currentTimeMillis();
		for(int i =0; i<NUM_THREAD;i++) {
			threads[i] = new MyThread(i,NUM_END/NUM_THREAD,NUM_THREAD);
			threads[i].start();
		}
		try{
			for(int i=0;i<NUM_THREAD;i++) {
				threads[i].join();
				count += threads[i].getCount();
			}
			
		} catch(InterruptedException e) {}
		long endTime = System.currentTimeMillis();
		long diff = endTime - startTime;
		System.out.println("num of prime number = "+count+" excution time = "+diff);
	}
	
}

class MyThread extends Thread{
	private int num_start;
	private int num_end;
	private int count=0;
	private int num;
	
	MyThread(int num,int size,int num_thread){
		this.num = num;
		num_start = size*num+1;
		if(num == num_thread-1)
			num_end = pc_static.NUM_END;
		else
			num_end = num_start + size-1;
	}
	
	public void run() {
		long startTime = System.currentTimeMillis();
		for(int i=num_start;i<=num_end;i++) {
			if(isPrime(i))
				count++;
		}
		long endTime = System.currentTimeMillis();
		long diff = endTime-startTime;
		System.out.println("Thread"+num+": num of prime number = "+count+" excution time = "+diff);
	}
	
	public static boolean isPrime(int x) {
		if(x<=1) return false;
		for(int i=2;i<x;i++) {
			if((x%i==0)&&(i!=x)) return false;
		}
		return true;
	}
	
	public int getCount() {
		return count;
	}
}