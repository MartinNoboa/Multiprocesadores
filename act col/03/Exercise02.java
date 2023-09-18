// =================================================================
//
// File: Exercise02.java
// Author(s):
//            A01704696 Samuel Octavio Gonzalez Azpeitia
//            A01704052 Martín Adrian Noboa Monar
// Description: This file contains the code to brute-force all
//				prime numbers less than MAXIMUM using Java's Threads.
//
// Reference:
// 	Read the document "exercise02.pdf"
//
// Copyright (c) 2023 by Tecnologico de Monterrey.
// All Rights Reserved. May be reproduced for any non-commercial
// purpose.
//
// =================================================================

public class Exercise02 extends Thread {
	private static final int SIZE = 5_000_001;
	private int start, end;
	public double result;

	public Exercise02(int start, int end) {
		// place your code here
        this.start = start;
        this.end = end;

	}

	// place your code here
    private boolean isPrime(int number){
        boolean flag = false;
        if (number < 2) {
            return false;
        }
        for (int i = 2; i <= Math.sqrt(number); i++) {
            if (number % i == 0) {
                    return false;
                }else{
                    flag = true;
                }
            }
        return flag;
    }

    public double getResult() {
        return result;
    }

	public void run() {
		// place yout code here
        for (int i = start; i < end; i++) {
            if( isPrime(i) )
                result += i;
        }
	}

	public static void main(String args[]) {
		long startTime, stopTime;
		double result = 0, elapsedTime;
		int blockSize;
		Exercise02 threads[];
		
		// place yout code here
        blockSize = SIZE / Utils.MAXTHREADS;
        threads = new Exercise02[Utils.MAXTHREADS];


		elapsedTime = 0;
		System.out.printf("Starting...\n");
		for (int i = 0; i < Utils.N; i++) {
			startTime = System.currentTimeMillis();

			// place yout code here
            for (int j = 0; j < threads.length; j++) {
                threads[j] = new Exercise02(j * blockSize, (j + 1) * blockSize);
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
		System.out.printf("result = %.0f\n", result);
		System.out.printf("avg time = %.5f ms\n", (elapsedTime / Utils.N));
	}
}