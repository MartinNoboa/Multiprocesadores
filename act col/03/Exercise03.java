// =================================================================
//
// File: Example03.java
// Authors:
//          A01704696 Samuel Octavio Gonzalez Azpeitia
//          A01704052 Martín Adrian Noboa Monar
// Description: This file implements the enumeration sort algorithm 
//				using Java's Threads.
//
// Copyright (c) 2023 by Tecnologico de Monterrey.
// All Rights Reserved. May be reproduced for any non-commercial
// purpose.
//
// =================================================================

import java.util.Arrays;

public class Exercise03 extends Thread {
	private static final int SIZE = 100_000;
	private int array[], start, end;

	public Exercise03(int start, int end, int array[]) {
		this.array = array;
		// place your code here
        this.start = start;
        this.end = end;
	}

    public void doTask(){
        int b[] = new int[SIZE];
        int c[] = new int[SIZE];
        for (int i = start; i < end; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (this.array[i] > this.array[j] || this.array[i] == this.array[j] && j < i) {
                    b[i] += 1;
                }
            }
        }
        for(int i = start; i < end; i++){
            c[b[i]] = this.array[i];
        }

        for(int i = start; i < end; i++){
            this.array[i] = c[i];
        }
    }

	public void run() {
		// place your code here
        doTask();

	}

	public static void main(String args[]) {
		int array[] = new int[SIZE];
		int aux[] = new int[SIZE];
		long startTime, stopTime;
		double elapsedTime;
		int blockSize;
		Exercise03 threads[];

		Utils.randomArray(array);
		Utils.displayArray("before", array);

		// place your code here
        blockSize = SIZE / Utils.MAXTHREADS;
        threads = new Exercise03[Utils.MAXTHREADS];

		System.out.printf("Starting...\n");
		elapsedTime = 0;
		for (int i = 0; i < Utils.N; i++) {
			System.arraycopy(array, 0, aux, 0, array.length);

			startTime = System.currentTimeMillis();

			// place your code here.
            for (int j = 0; j < threads.length; j++) {
                threads[j] = new Exercise03(j * blockSize, (j + 1) * blockSize, aux);

            }
            for (int j = 0; j < threads.length; j++) {
                threads[j].start();
            }
            for (int j = 0; j < threads.length; j++) {
                try {
                    threads[j].join();
                                        

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

			stopTime = System.currentTimeMillis();

			elapsedTime += (stopTime - startTime);
		}
		Utils.displayArray("after", aux);
		System.out.printf("avg time = %.5f\n", (elapsedTime / Utils.N));
	}
}