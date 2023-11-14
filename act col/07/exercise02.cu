// =================================================================
//
// File: exercise02.cpp
// Author(s):
//          Martin Adrian Noboa Monar - A01704052
//          Samuel Octavio González Azpeitia - A01704696
//
//			Sin Threads		Con Threads		Speed Up
//			622.582  ms		
// =================================================================

#include <iostream>
#include <iomanip>
#include <chrono>
#include "utils.h"
#include <cuda_runtime.h>

using namespace std;
using namespace std::chrono;

#define MAXIMUM 1000001 //1e6
#define THREADS 256
#define BLOCKS	MMIN(32, ((MAXIMUM / THREADS) + 1))
// implement your code

bool isPrime(int number) const{
        bool flag = false;
        if (number < 2) {
            return false;
        }
        for (int i = 2; i <= sqrt(number); i++) {
            if (number % i == 0) {
                return false;
            }
        }
        return true;
}

__global__ void prime(){
    int tid = threadIdx.x + (blockIdx.x * blockDim.x);
    double result = 0;
	while (tid < MAXIMUM) {
        if (isPrime(tid)){
            result+= tid;
        }
        tid += blockDim.x * gridDim.x;
	}
}

int main(int argc, char* argv[]) {
	double result;
    double * d_result;
	// These variables are used to keep track of the execution time.
	high_resolution_clock::time_point start, end;
	double timeElapsed;

	cout << "Starting...\n";
	timeElapsed = 0;
	for (int j = 0; j < N; j++) {
		start = high_resolution_clock::now();

		// call the implemented function
        prime<<<BLOCKS, THREADS>>> ();

		end = high_resolution_clock::now();
		timeElapsed += 
			duration<double, std::milli>(end - start).count();
	}
	cout << "result = " << result << "\n";
	cout << "avg time = " << fixed << setprecision(3) 
		 << (timeElapsed / N) <<  " ms\n";

	return 0;
}
