// =================================================================
//
// File: Example7.java
// Author(s):
// Description: This file contains the code to brute-force all
//				prime numbers less than MAXIMUM. The time this
//				implementation takes will be used as the basis to
//				calculate the improvement obtained with parallel
//				technologies.
//
// Copyright (c) 2020 by Tecnologico de Monterrey.
// All Rights Reserved. May be reproduced for any non-commercial
// purpose.
//
// =================================================================
/*----------------------------------------------------------------

*

* Multiprocesadores: Threads en Java.

* Fecha: 21-Sep-2021

* Autor: A01209400 Royer Donnet Arenas Camacho
		 A01654856 Hugo David Franco Avila

*

*--------------------------------------------------------------*/
public class Example7 extends Thread {
	private static final int SIZE = 1_000_000;
	private boolean array[];
	private int start, end;
	
	public Example7(boolean array[], int start, int end) {
		this.array = array;
		this.start = start;
		this.end = end;
	}

	public void run(){
		int i=0,j=0;
		int flag=0;
		for(i=start;i<end;i++){
			flag=0;
			j=2;
			while((j*j)<=i){
				if(i%j==0){
					flag=1;
					break;
				}
				j++;
			}
			if(flag==0){
				array[i]=true;
			}
		}
	}


	public static void main(String args[]) {
		long startTime, stopTime;
		int block;
		boolean array[];
		Example7 threads[];
		double ms;

		array = new boolean[SIZE];

		block = SIZE / Utils.MAXTHREADS;
		threads = new Example7[Utils.MAXTHREADS];

		System.out.println("At first, neither is a prime. We will display to TOP_VALUE:");
		for (int i = 2; i < Utils.TOP_VALUE; i++) {
			array[i] = false;
			System.out.print("" + i + ", ");
		}
		System.out.println("");

		System.out.printf("Starting with %d threads...\n", Utils.MAXTHREADS);
		ms = 0;
		for (int j = 1; j <= Utils.N; j++) {
			for (int i = 0; i < threads.length; i++) {
				if (i != threads.length - 1) {
					threads[i] = new Example7(array, (i * block), ((i + 1) * block));
				} else {
					threads[i] = new Example7(array, (i * block), SIZE);
				}
			}
			startTime = System.currentTimeMillis();
			for (int i = 0; i < threads.length; i++) {
				threads[i].start();
			}
			/** -------- */
			for (int i = 0; i < threads.length; i++) {
				try {
					threads[i].join();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			stopTime = System.currentTimeMillis();
			ms +=  (stopTime - startTime);
		}
		//System.out.printf("sum = %d\n", result);
		System.out.println("Expanding the numbers that are prime to TOP_VALUE:");
		for (int i = 2; i < Utils.TOP_VALUE; i++) {
			if (array[i]) {
				System.out.print("" + i + ", ");
			}
		}
		System.out.println();
		System.out.printf("avg time = %.5f ms\n", (ms / Utils.N));
	}
}
