// =================================================================
//
// File: exercise03.cpp
// Author(s):
//      Martin Adrian Noboa Monar - A01704052
//      Samuel Octavio González Azpeitia - A01704696
// Description: This file contains the code that implements the
//				enumeration sort algorithm using the TBB 
//				technology. To compile:
//				g++ exercise03.cpp -o app -I/usr/local/lib/tbb/include -L/usr/local/lib/tbb/lib/intel64/gcc4.4 -ltbb
//
// Algorithm reference:
//	https://www.osys.se/Archive/Papers/parallel-sort/node3.html
//
// Copyright (c) 2020 by Tecnologico de Monterrey.
// All Rights Reserved. May be reproduced for any non-commercial
// purpose.
//
//			Sin Threads		Con Threads		Speed Up
//			636.750  ms		 179.651 ms		3.54
// =================================================================

#include <iostream>
#include <iomanip>
#include <chrono>
#include <cstring>
#include <cmath>
#include <tbb/blocked_range.h>
#include <tbb/parallel_for.h>
#include "utils.h"

using namespace std;
using namespace std::chrono;
using namespace tbb;

#define SIZE 10000

// implement your code
class Exercise03
{
private:
	int *array,*b,*c,size;

public:
	Exercise03(int *a, int s) : array(a),size(s) { 
        b = new int[size];
        c = new int[size];
    }


    void operator() (const blocked_range<int> &r) const {
		for (int i = r.begin(); i != r.end(); i++) {
			for (int j = 0; j < size; j++) {
				if (array[i] > array[j] || array[i] == array[j] && j < i) {
					b[i] += 1;
				}
			}
            for(int i = 0; i < size; i++){
			    c[b[i]] = array[i];
            }

            for(int i = 0; i < size; i++){
                array[i] = c[i];
            }
		}
        
    }
};

int main(int argc, char* argv[]) {
	int *array,*aux;
	// These variables are used to keep track of the execution time.
	high_resolution_clock::time_point start, end;
	double timeElapsed;

	array = new int[SIZE];
	aux = new int[SIZE];
	random_array(array, SIZE);
	display_array("before", array);
	cout << "Starting...\n";
	timeElapsed = 0;
	for (int j = 0; j < N; j++) {
        memcpy(aux, array, sizeof(int) * SIZE);
		start = high_resolution_clock::now();
		// call the implemented function
        Exercise03 obj(array,SIZE);
        parallel_for(blocked_range<int>(0, SIZE),  obj);

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