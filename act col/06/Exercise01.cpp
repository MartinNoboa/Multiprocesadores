// =================================================================
//
// File: exercise01.cpp
// Author(s):
//      Martin Adrian Noboa Monar - A01704052
//      Samuel Octavio González Azpeitia - A01704696
// Description: This file contains the code to count the number of
//				even numbers within an array using the TBB 
//				technology. To compile:
//				g++ exercise01.cpp -o app -I/usr/local/lib/tbb/include -L/usr/local/lib/tbb/lib/intel64/gcc4.4 -ltbb
//
// =================================================================

#include <iostream>
#include <iomanip>
#include <chrono>
#include <tbb/blocked_range.h>
#include <tbb/parallel_reduce.h>
#include "utils.h"

using namespace std;
using namespace std::chrono;
using namespace tbb;

// array size
#define SIZE 1000000000

// implement your code
int count_even(int *array, int size) {
    int result = 0;

    for (int i = 0; i < size; i++) {
        if (array[i] % 2 == 0) {
            result++;
        }
    }

    return 0;
}



int main(int argc, char* argv[]) {
	int *array, result;
	// These variables are used to keep track of the execution time.
	high_resolution_clock::time_point start, end;
	double timeElapsed;

	array = new int[SIZE];
	fill_array(array, SIZE);
	display_array("array", array);

	cout << "Starting...\n";
	timeElapsed = 0;
	for (int j = 0; j < N; j++) {
		start = high_resolution_clock::now();

		// call the implemented function
        parallel_reduce(blocked_range<int>(0, SIZE), 0, [&](const blocked_range<int>& r, int result) {
            for (int i = r.begin(); i != r.end(); i++) {
                if (array[i] % 2 == 0) {
                    result++;
                }
            }
            return result;
        }, [&](int x, int y) {
            return x + y;
        });
        

		end = high_resolution_clock::now();
		timeElapsed += 
			duration<double, std::milli>(end - start).count();
	}
	cout << "result = " << result << "\n";
	cout << "avg time = " << fixed << setprecision(3) 
		 << (timeElapsed / N) <<  " ms\n";

	delete [] array;
	return 0;
}