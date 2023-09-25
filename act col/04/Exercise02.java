// =================================================================
//
// File: Exercise02.java
// Author(s):
// 		Samuel Octavio González Azpeitia - A01704696
//		Martín Adrian Noboa Monar - A01704052
// Description: This file contains the code to brute-force all
//				prime numbers less than MAXIMUM using Java's 
//				Fork-Join technology.
//
// Copyright (c) 2022 by Tecnologico de Monterrey.
// All Rights Reserved. May be reproduced for any non-commercial
// purpose.
//
// 250.6000
//      Sin Threads     Con Threads     Speed Up
//	    871.80000 ms	250.60000 ms	3.4788
// ================================================================

import java.util.concurrent.RecursiveTask;
import java.util.concurrent.ForkJoinPool;
import java.util.Arrays;

public class Exercise02 extends RecursiveTask<Long> {
	private static final int SIZE = 5_000_001;
	private static final int MIN = 10_000;
	private int start, end;

	public Exercise02(int start, int end) {
		// place your code here
		this.start = start;
		this.end = end;
	}

	// place your code here
	public Long calculate() {
		long result = 0;
        for (int i = this.start; i < this.end; i++) {
            if( isPrime(i) )
                result += i;
        }
        return result;
	}

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
	

	@Override
	protected Long compute() {
		if ( (end - start) <= MIN ) {
			return calculate();
		} else {
			int mid = start + ( (end - start) / 2 );
			Exercise02 lowerMid = new Exercise02(start, mid);
			lowerMid.fork();
			Exercise02 upperMid = new Exercise02(mid, end);
			return upperMid.compute() + lowerMid.join();
		}
	}

	public static void main(String args[]) {
		long startTime, stopTime;
		double result = 0, elapsedTime;
		int blockSize;
		ForkJoinPool pool;
		
		elapsedTime = 0;
		
		System.out.printf("Starting...\n");
		for (int i = 0; i < Utils.N; i++) {
			startTime = System.currentTimeMillis();

			// place yout code here
			pool = new ForkJoinPool(Utils.MAXTHREADS);
			result = pool.invoke(new Exercise02(0, SIZE));

			stopTime = System.currentTimeMillis();

			elapsedTime += (stopTime - startTime);
		}
		System.out.printf("result = %.0f\n", result);
		System.out.printf("avg time = %.5f ms\n", (elapsedTime / Utils.N));
	}
}