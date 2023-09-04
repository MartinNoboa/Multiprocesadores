// =================================================================
// Multiprocesadores
// File: Exercise01.java
// Author: Martin Noboa - A01704052
// 		   Samuel Gonzalez - A01704696
// =================================================================



public class Exercise01 {
	private static final int SIZE = 100_000_000;
	private int array[];

	public Exercise01(int array[]) {
		this.array = array;
	}

	public int calculate() {
		
		int result = 0;
		
		for (int i = 0; i < array.length; i++) {
            if (array[i] % 2 == 0){
                result = result + 1;
            }
        }

		return result;
		
	}

	public static void main(String args[]) {
		int array[] = new int[SIZE];
		int result = 0;
		long startTime, stopTime;
		double elapsedTime = 0;

		Utils.fillArray(array);
		Utils.displayArray("array", array);

		Exercise01 obj = new Exercise01(array);
		
		elapsedTime = 0;
		System.out.printf("Starting...\n");
		for (int i = 0; i < Utils.N; i++) {
			startTime = System.currentTimeMillis();

			result = obj.calculate();

			stopTime = System.currentTimeMillis();

			elapsedTime += (stopTime - startTime);
		}
		System.out.printf("result = %d\n", result);
		System.out.printf("avg time = %.5f ms\n", (elapsedTime / Utils.N));
	}
}
