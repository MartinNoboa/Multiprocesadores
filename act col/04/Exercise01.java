// =================================================================
//
// File: Exercise01.java
// Authors: 
//      Samuel Octavio González Azpeitia - A01704696
//      Martín Adrian Noboa Monar - A01704052
// Description: This file contains the code to count the number of
//				even numbers within an array using Java's Fork-Join 
//				technology.
//
// Copyright (c) 2023 by Tecnologico de Monterrey.
// All Rights Reserved. May be reproduced for any non-commercial
// purpose.
//
// =================================================================

import java.util.concurrent.RecursiveTask;
import java.util.concurrent.ForkJoinPool;

public class Exercise01 extends RecursiveTask<Integer> {
	private static final int SIZE = 100_000_000;
	private static final int MIN = 10_000;
	private int array[], start, end;

	public Exercise01(int start, int end, int array[]) {
		this.array = array;
		this.start = start;
		this.end = end;
		// place your code here
	}

	// place your code here

    public Integer computeDirectly(){
        int result = 0;
		for (int i = start; i < end; i++){
			if (array[i] % 2 == 0){
				result ++;
			}
		}
		return result;
        
    }

	@Override
    protected Integer compute(){
      if ((end - start) <= MIN) {
			return computeDirectly();
		} else {
			int mid = start + ((end - start) / 2);
			Exercise01 lowerMid = new Exercise01(start, mid, array);
			lowerMid.fork();
			Exercise01 upperMid = new Exercise01(mid, end, array);
			return (upperMid.compute() + lowerMid.join());
		}
    }



	public static void main(String args[]) {
		int array[] = new int[SIZE];
		int result = 0;
		long startTime, stopTime;
		double elapsedTime = 0;
		ForkJoinPool pool;

		Utils.fillArray(array);
		Utils.displayArray("array", array);

		elapsedTime = 0;
		System.out.printf("Starting...\n");
		for (int i = 0; i < Utils.N; i++) {
			startTime = System.currentTimeMillis();

			pool = new ForkJoinPool(Utils.MAXTHREADS);
			result = pool.invoke(new Exercise01(0, array.length, array));

			stopTime = System.currentTimeMillis();

			elapsedTime += (stopTime - startTime);
		}
		System.out.printf("result = %d\n", result);
		System.out.printf("avg time = %.5f ms\n", (elapsedTime / Utils.N));
	}
}