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

* Multiprocesadores: Java

* Fecha: 10-Sep-2021

* Autor: A01209400 Royer Donnet Arenas Camacho
		 A01654856 Hugo David Franco Avila

*

*--------------------------------------------------------------*/

import java.util.Arrays;

public class Example8 {
	private static final int SIZE = 100_000;
	private int array[];

	public Example8(int array[]) {
		this.array = array;
	}

	public void doTask() {
		int B[]=new int[array.length];
		int C[]=new int[array.length];
		int i,j;
		for(i=0;i<array.length;i++){
			for(j=0;j<array.length;j++){
				if(array[i]>array[j]||array[i]==array[j]&&j<i){
					B[i]+=1;
				}
			}
		}

		for(i=0;i<array.length;i++){
			C[B[i]]=array[i];
		}

		for(i=0;i<array.length;i++){
			array[i]=C[i];
		}
	}

	public int[] getSortedArray() {
		return array;
	}

	public static void main(String args[]) {
		int array[] = new int[SIZE];
		long startTime, stopTime;
		double ms;
		Example8 obj = null;

		Utils.randomArray(array);
		Utils.displayArray("before", array);

		System.out.printf("Starting...\n");
		ms = 0;

		for (int i = 0; i < Utils.N; i++) {
			startTime = System.currentTimeMillis();

			obj = new Example8(Arrays.copyOf(array, array.length));
			obj.doTask();

			stopTime = System.currentTimeMillis();

			ms += (stopTime - startTime);
		}
		Utils.displayArray("after", obj.getSortedArray());
		System.out.printf("avg time = %.5f\n", (ms / Utils.N));
	}
}
