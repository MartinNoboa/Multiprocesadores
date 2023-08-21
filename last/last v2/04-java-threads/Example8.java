import java.util.Arrays;

// =================================================================
//
// File: Example8.java
// Author: Pedro Perez
// Description: This file implements the enumeration sort algorithm.
// 				The time this implementation takes will be used as the
//				basis to calculate the improvement obtained with
//				parallel technologies.
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

public class Example8 extends Thread {
	
	private static final int SIZE = 100_000;
	private int array[],threadId;
	private int B[],C[];

	public Example8(int array[],int B[],int threadId) {
		this.array = array;
		this.threadId=threadId;
		this.B=B;
		C=new int[array.length];
	}

	public int [] getResult(){
		return B;
	}


	public void run(){
		int i=threadId,j;

		while(i<array.length){
			for(j=0;j<array.length;j++){
				if(array[i]>array[j]||array[i]==array[j]&&j<i){
					B[i]+=1;
				}
			}
			i+=Utils.MAXTHREADS;
		}
	}

	public static void main(String args[]) {
		long startTime, stopTime;
		int array[],C[],B[], block;
		Example8 threads[];
		double ms;

		array = new int[SIZE];
		C = new int[array.length];
		B = new int[array.length];
		Utils.randomArray(array);
		Utils.displayArray("before", array);

		block = SIZE / Utils.MAXTHREADS;
		threads = new Example8[Utils.MAXTHREADS];

		System.out.printf("Starting with %d threads...\n", Utils.MAXTHREADS);
		ms = 0;
		for (int j = 1; j <= Utils.N; j++) {
			for(int i=0;i<array.length;i++){
				B[i]=0;
			}
			for (int i = 0; i < threads.length; i++) {
				threads[i] = new Example8(Arrays.copyOf(array, array.length),B,i);
			}

			startTime = System.currentTimeMillis();
			for (int i = 0; i < threads.length; i++) {
				threads[i].start();
			}
			/** -------- */
			for (int i = 0; i < threads.length; i++) {
				try {
					threads[i].join();
					//System.out.println("joined thread" +i);
					
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			stopTime = System.currentTimeMillis();
			ms +=  (stopTime - startTime);

		}
		for(int i=0;i<array.length;i++){
			C[B[i]]=array[i];
		}

		for(int i=0;i<array.length;i++){
			array[i]=C[i];
		}
		Utils.displayArray("after", array);
		System.out.printf("avg time = %.5f\n", (ms / Utils.N));
	}
}
