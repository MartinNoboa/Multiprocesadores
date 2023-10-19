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
class Exercise01{
private:
    double result;
    int *array;
public:
    Exercise01(int* a): array(a), result(0){};

    Exercise01(Exercise01 &other, split): array(other.array), result(0) {}

    double getResult() const {
        return result;
    }

    void operator() (const blocked_range<int> &r) {
        for (int i = r.begin(); i != r.end(); i++) {
            if (array[i] % 2 == 0) {
                result++;
            }
        }
    }

    void join(const Exercise01 &other) {
		result += other.result;
	}
};



int main(int argc, char* argv[]) {
	int *array;
    double result;
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
        Exercise01 even(array);
        parallel_reduce(blocked_range<int>(0, SIZE), even);
		result = even.getResult();

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