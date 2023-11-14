// =================================================================
// Multiprocesadores
// File: Exercise03.cpp
// Author: Martin Noboa - A01704052
// 		   Samuel Gonzalez - A01704696
//
//			Sin Threads		Con Threads		Speed Up
//			636.750  ms		281.456  ms		2.26
// =================================================================

#include <iostream>
#include <iomanip>
#include <chrono>
#include "utils.h"
#include <cuda_runtime.h>

using namespace std;
using namespace std::chrono;

#define SIZE 10000

// implement your code

int main(int argc, char* argv[]) {
	int *array, result;
	// These variables are used to keep track of the execution time.
	high_resolution_clock::time_point start, end;
	double timeElapsed;

	array = new int[SIZE];
	random_array(array, SIZE);
	display_array("before", array);

	cout << "Starting...\n";
	timeElapsed = 0;
	for (int j = 0; j < N; j++) {
		start = high_resolution_clock::now();

		// call the implemented function

		end = high_resolution_clock::now();
		timeElapsed += 
			duration<double, std::milli>(end - start).count();
	}
	display_array("after", array);
	cout << "avg time = " << fixed << setprecision(3) 
		 << (timeElapsed / N) <<  " ms\n";

	delete [] array;
	return 0;
}
