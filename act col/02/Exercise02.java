// =================================================================
// Multiprocesadores
// File: Exercise02.java
// Author: Martin Noboa - A01704052
// 		   Samuel Gonzalez - A01704696
// =================================================================
import java.lang.Math;
public class Exercise02 {
	private static final int SIZE = 1_000_001;

	public Exercise02() {
	}

	public double calculate() {
		double result = 0;
        for (int i = 0; i < this.SIZE; i++) {
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

	public static void main(String args[]) {
		long startTime, stopTime;
		double result = 0, elapsedTime;
		
		Exercise02 obj = new Exercise02();

		elapsedTime = 0;
		System.out.printf("Starting...\n");
		for (int i = 0; i < Utils.N; i++) {
			startTime = System.currentTimeMillis();

			result = obj.calculate();

			stopTime = System.currentTimeMillis();

			elapsedTime += (stopTime - startTime);
		}
		System.out.printf("result = %.0f\n", result);
		System.out.printf("avg time = %.5f ms\n", (elapsedTime / Utils.N));
	}
}
