// =================================================================
//
// File: Example03.java
// Authors:
//          A01704696 Samuel Octavio Gonzalez Azpeitia
//          A01704052 Martín Adrian Noboa Monar
// Description: This file implements the enumeration sort algorithm 
//				using Java's Fork-Join technology.
//
// Copyright (c) 2022 by Tecnologico de Monterrey.
// All Rights Reserved. May be reproduced for any non-commercial
// purpose.
//
//      Sin Threads     Con Threads     Speed Up
//	    202.50000 ms	1124.1000 ms	0.1801
// ================================================================
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.ForkJoinPool;
import java.util.Arrays;

public class Exercise03 extends RecursiveAction {
	private static final int SIZE = 100_000;
	private static final int MIN = 10_000;
	private int array[], aux[], start, end;

	public Exercise03(int array[],int aux[],int start,int end) {
		this.array = array;
		this.aux = aux;
		this.start = start;
		this.end = end;
	}

	protected void computeDirectly() {
        int b[] = new int[SIZE];
        int c[] = new int[SIZE];
		for(int i = start; i < end; i++){
			for(int j = 0; j < aux.length; j++){
				if(array[i] > array[j] || array[i] == array[j] && j < i){
					aux[i] += 1;
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

	@Override
	protected void compute() {
		if ((end - start) <= MIN) {
			computeDirectly();
		} else {
			int mid = start + ((end - start) / 2);
			invokeAll(new Exercise03(array, aux, start, mid),
					new Exercise03(array, aux, mid, end));
		}
	}

	public static void main(String args[]) {
		long startTime, stopTime;
        int array[] = new int[SIZE];
		int aux[] = new int[SIZE];
		double ms;
		ForkJoinPool pool;

		Utils.randomArray(array);
		Utils.displayArray("before", array);
		System.out.printf("Starting...\n");
		ms = 0;
		for (int i = 0; i < Utils.N; i++) {
            System.arraycopy(array, 0, aux, 0, array.length);
			startTime = System.currentTimeMillis();

			pool = new ForkJoinPool(Utils.MAXTHREADS);
			pool.invoke(new Exercise03(array, aux, 0, SIZE));

			stopTime = System.currentTimeMillis();
			ms += (stopTime - startTime);
		}
		Utils.displayArray("after", aux);
		System.out.printf("avg time = %.5f ms\n", (ms / Utils.N));
	}
}
