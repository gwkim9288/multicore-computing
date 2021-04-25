package problem1;

public class pc_dynamic {
	private final static int NUM_THREAD = 10;
	static MyThreadDynamic[] threads;
	private final static int NUM_END = 200000;
	private static int count =0;
	private static int num =1;
	
	public static void main(String[] args ) {
		threads = new MyThreadDynamic[NUM_THREAD];
		long startTime = System.currentTimeMillis();
		for(int i =0; i<NUM_THREAD;i++) {
			threads[i] = new MyThreadDynamic(i);
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
	
	public synchronized static int getCurrentNum(){
		if(num > NUM_END)
			return -1;
		else {
			num++;
			return num-1;
		}
	}
}

class MyThreadDynamic extends Thread{
	private int num;
	private int count =0;
	MyThreadDynamic(int num){
		this.num = num;
	}
	
	public void run() {
		int current;
		long startTime = System.currentTimeMillis();
		while(true) {
			current = pc_dynamic.getCurrentNum();
			if(current == -1) {
				break;
			}else {
				if(isPrime(current))
					count++;
			}
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