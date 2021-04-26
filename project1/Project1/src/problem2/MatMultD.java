package problem2;

import java.util.Scanner;

public class MatMultD {
	private static Scanner sc = new Scanner(System.in);
	static int[][] c;
	  public static void main(String [] args)
	  {
	    int thread_no=0;
	    if (args.length==1) thread_no = Integer.valueOf(args[0]);
	    else thread_no = 1;
	    MyThread[] threads = new MyThread[thread_no];
	    int a[][]=readMatrix();
	    int b[][]=readMatrix();
	    c = new int [a[0].length][b[0].length];
	    long startTime = System.currentTimeMillis();
	    int len = b.length;
	    int start =0;
	    for(int i =0;i<thread_no;i++) {
	    	int size = len/(thread_no-i);
	    	len -=size;
	    	threads[i] = new MyThread(i,a,b,start,size);
	    	start +=size;
	    	threads[i].start();
	    }

	    try {
	    	for(int i=0;i<thread_no;i++) {
		    	threads[i].join();
		    }
	    } catch (InterruptedException e) {}
	    
	    
	    long endTime = System.currentTimeMillis();
	    long diff = endTime - startTime;
	    
	    printMatrix(c);
	    System.out.println("Excution time: "+ diff );
	 
	  }

	   public static int[][] readMatrix() {
	       int rows = sc.nextInt();
	       int cols = sc.nextInt();
	       int[][] result = new int[rows][cols];
	       for (int i = 0; i < rows; i++) {
	           for (int j = 0; j < cols; j++) {
	              result[i][j] = sc.nextInt();
	           }
	       }
	       return result;
	   }
	   
	   public static void printMatrix(int[][] mat) {
		   System.out.println("Matrix["+mat.length+"]["+mat[0].length+"]");
		     int rows = mat.length;
		     int columns = mat[0].length;
		     int sum = 0;
		     for (int i = 0; i < rows; i++) {
		       for (int j = 0; j < columns; j++) {
		         System.out.printf("%4d " , mat[i][j]);
		         sum+=mat[i][j];
		       }
		       System.out.println();
		     }
		     System.out.println();
		     System.out.println("Matrix Sum = " + sum + "\n");
	   }
	   
	   

}

class MyThread extends Thread{
	private int num;
	private int[][] a;
	private int[][] b;
	private int size;
	private int start;
	
	MyThread(int num,int[][] a, int[][] b,int start,int size){
		this.num = num;
		this.a = a;
		this.b = b;
		this.size = size;
		this.start = start;
	}
	
	public void run() {
		int n = a[0].length;
	    int p = b[0].length;
	    long startTime = System.currentTimeMillis();

		for(int i = start;i<start+size;i++) {
			for(int j=0;j<p;j++) {
				for(int k=0 ; k<n;k++) {
			        MatMultD.c[i][j] += a[i][k] * b[k][j];
				}
			}
		}
	    long endTime = System.currentTimeMillis();
	    long diff = endTime - startTime;
		System.out.println("Thread"+num+": excution time = "+diff);

	}
	
}
