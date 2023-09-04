// =================================================================
// Multiprocesadores
// File: Exercise03.java
// Author: Martin Noboa - A01704052
// 		   Samuel Gonzalez - A01704696
// =================================================================

import java.util.Arrays;

public class Exercise03 {
	private static final int SIZE = 10000;
	private int array[];

	public Exercise03(int array[]) {
		this.array = array;
	}

	public void doTask() {
		int b[] = new int[SIZE];
        int c[] = new int[SIZE];
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (this.array[i] > this.array[j] || this.array[i] == this.array[j] && j < i) {
                    b[i] += 1;
                }
            }
        }
        for(int i = 0; i < SIZE; i++){
            c[b[i]] = this.array[i];
        }

        for(int i = 0; i < SIZE; i++){
            this.array[i] = c[i];
        }
	}
	public static void main(String args[]) {
		int array[] = new int[SIZE];
		int aux[] = new int[SIZE];
		long startTime, stopTime;
		double elapsedTime;
		

		Utils.randomArray(array);
		Utils.displayArray("before", array);

		Exercise03 obj = new Exercise03(array);

		System.out.printf("Starting...\n");
		elapsedTime = 0;
		for (int i = 0; i < Utils.N; i++) {
			System.arraycopy(array, 0, aux, 0, array.length);

			startTime = System.currentTimeMillis();
			obj.doTask();
			stopTime = System.currentTimeMillis();

			elapsedTime += (stopTime - startTime);
		}
		Utils.displayArray("after", aux);
		System.out.printf("avg time = %.5f\n", (elapsedTime / Utils.N));
	}
}
