// =================================================================
//
// File: Exercise01.java
// Authors: 
//             A01704696 Samuel Octavio Gonzalez Azpeitia
//             A01704052 Martín Adrian Noboa Monar   
// Description: This file contains the code to count the number of
//				even numbers within an array using Java's Threads.
//
// Copyright (c) 2023 by Tecnologico de Monterrey.
// All Rights Reserved. May be reproduced for any non-commercial
// purpose.
//
// =================================================================

public class Exercise01 extends Thread {
	private static final int SIZE = 100_000_000;
	private int array[], start, end, result;

	public Exercise01(int start, int end, int array[]) {
		// place your code here
        this.array = array;
        this.start = start;
        this.end = end;
		
	}

    public int getResult() {    
        return result;
    }

	public void run() {
		// place your code here
        result = 0;
        for (int i = start; i < end; i++) {
            if (array[i] % 2 == 0) {
                result++;
            }
        }
	}

	public static void main(String args[]) {
		int array[] = new int[SIZE];
		int result = 0;
		long startTime, stopTime;
		double elapsedTime = 0;
		int blockSize;
		Exercise01 threads[];

		Utils.fillArray(array);
		Utils.displayArray("array", array);

		// place your code here
        blockSize = SIZE / Utils.MAXTHREADS;
        threads = new Exercise01[Utils.MAXTHREADS];
		
		elapsedTime = 0;
		System.out.printf("Starting...\n");
		for (int i = 0; i < Utils.N; i++) {
			startTime = System.currentTimeMillis();

			// place your code here
            for (int j = 0; j < threads.length; j++) {
                threads[j] = new Exercise01(j * blockSize, (j + 1) * blockSize, array);
            }

            for (int j = 0; j < threads.length; j++) {
                threads[j].start();
            }

            for (int j = 0; j < threads.length; j++) {
                try {
                    threads[j].join();
                    result += threads[j].getResult();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

			stopTime = System.currentTimeMillis();

			elapsedTime += (stopTime - startTime);
		}
        


		System.out.printf("result = %d\n", result);
		System.out.printf("avg time = %.5f ms\n", (elapsedTime / Utils.N));
	}
}