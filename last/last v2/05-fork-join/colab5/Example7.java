// =================================================================
//
// File: Example7.java
// Author(s):
// Description: This file contains the code to brute-force all
//				prime numbers less than MAXIMUM using Java's
//				Fork-Join.
//
// Copyright (c) 2020 by Tecnologico de Monterrey.
// All Rights Reserved. May be reproduced for any non-commercial
// purpose.
//
// =================================================================

/*----------------------------------------------------------------

*

* Multiprocesadores: Fork-Join 

* Fecha: 28-Sep-2021

* Autor: A01209400 Royer Donnet Arenas Camacho
		 A01654856 Hugo David Franco Ávila

*

*--------------------------------------------------------------*/

import java.util.Arrays;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.ForkJoinPool;

public class Example7 extends RecursiveAction {
	private static final int MAXIMUM = 1_000_000;
	private static final int MIN = 10_000;
	private boolean array[];
	private int start, end;

	public Example7(boolean array[], int start, int end) {
		this.array = array;
		this.start = start;
		this.end = end;
	}
	
	protected void computeDirectly() {
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

	@Override
	protected void compute() {
		if ( (end - start) <= MIN ) {
			computeDirectly();
		} else {
			int mid = start + ( (end - start) / 2 );
			invokeAll(new Example7(array,start, mid),
					  new Example7(array,mid, end));
		}
	}

	public static void main(String args[]) {
		long startTime, stopTime;
		boolean array[];
		double ms;
		ForkJoinPool pool;

		array = new boolean[MAXIMUM];
		
		System.out.println("At first, neither is a prime.");
		for (int i = 2; i < MAXIMUM; i++) {
			array[i] = false;
		}
		System.out.printf("Starting with %d threads...\n", Utils.MAXTHREADS);
		ms = 0;
		for (int i = 0; i < Utils.N; i++) {
			startTime = System.currentTimeMillis();

			pool = new ForkJoinPool(Utils.MAXTHREADS);
			pool.invoke(new Example7(array, 0, array.length));

			stopTime = System.currentTimeMillis();
			ms += (stopTime - startTime);
		}
		for (int i = 2; i < Utils.TOP_VALUE; i++) {
			if (array[i]) {
				System.out.print("" + i + ", ");
			}
		}
		System.out.print("\n");
		System.out.printf("avg time = %.5f ms\n", (ms / Utils.N));
	}
}
