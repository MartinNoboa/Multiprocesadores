// =================================================================
//
// File: Example4.java
// Authors:
// Description: This file contains the code to count the number of
//				even numbers within an array using Threads.
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


public class Example4 extends Thread {
	private static final int SIZE = 100_000_000;
	private int array[], start, end;
	private long result;

	public Example4(int array[], int start, int end) {
		this.array = array;
		this.start = start;
		this.end = end;
		this.result = 0;
	}

	public long getResult(){
		return this.result;
	}

	public void run(){
		for(int i = start; i < end;i++){
			if(array[i]%2==0){
				result++;
			}
		}
	}
	// place your code here
	public static void main(String args[]) {
		long startTime, stopTime;
		int array[], block;
		Example4 threads[];
		double ms;
		long result = 0;

		array = new int[SIZE];
		Utils.fillArray(array);
		Utils.displayArray("array", array);

		block = SIZE / Utils.MAXTHREADS;
		threads = new Example4[Utils.MAXTHREADS];

		System.out.printf("Starting with %d threads...\n", Utils.MAXTHREADS);
		ms = 0;
		for (int j = 1; j <= Utils.N; j++) {
			for (int i = 0; i < threads.length; i++) {
				if (i != threads.length - 1) {
					threads[i] = new Example4(array, (i * block), ((i + 1) * block));
				} else {
					threads[i] = new Example4(array, (i * block), SIZE);
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

			if (j == Utils.N) {
				result = 0;
				for (int i = 0; i < threads.length; i++) {
					result += threads[i].getResult();
				}
			}
		}
		System.out.printf("sum = %d\n", result);
		System.out.printf("avg time = %.5f ms\n", (ms / Utils.N));
	}
}
