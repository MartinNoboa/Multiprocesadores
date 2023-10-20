// =================================================================
//
// File: exercise02.cpp
// Author(s):
//      Martin Adrian Noboa Monar - A01704052
//      Samuel Octavio González Azpeitia - A01704696
// Description: This file contains the code to brute-force all
//				prime numbers less than MAXIMUM using the TBB 
//				technology. To compile:
//				g++ exercise02.cpp -o app -I/usr/local/lib/tbb/include -L/usr/local/lib/tbb/lib/intel64/gcc4.4 -ltbb
//
// Reference:
// 	Read the document "exercise02.pdf"
//
// Copyright (c) 2022 by Tecnologico de Monterrey.
// All Rights Reserved. May be reproduced for any non-commercial
// purpose.
//
//			Sin Threads		Con Threads		Speed Up
//			622.582  ms		 67.225  ms		9.26
// =================================================================

#include <iostream>
#include <iomanip>
#include <math.h>
#include <chrono>
#include <tbb/blocked_range.h>
#include <tbb/parallel_reduce.h>
#include <tbb/parallel_for.h>
#include "utils.h"

using namespace std;
using namespace std::chrono;
using namespace tbb;

#define MAXIMUM 1000001 //1e6

// implement your code
class Exercise02{
private: 
    double result;

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

public:
    Exercise02(): result(0){}
    Exercise02(Exercise02 &other, split): result(0){}

    double getResult() const {
        return result;
    }

    void operator() (const blocked_range<int> &r) {
        for (int i = r.begin(); i != r.end(); i++) {
            if(isPrime(i)){
                result += i;
            }            
        }
    }

    void join(const Exercise02 &other) {
		result += other.result;
	}
};

int main(int argc, char* argv[]) {
	double result;
	// These variables are used to keep track of the execution time.
	high_resolution_clock::time_point start, end;
	double timeElapsed;

	cout << "Starting...\n";
	timeElapsed = 0;
	for (int j = 0; j < N; j++) {
		start = high_resolution_clock::now();

		// call the implemented function
        Exercise02 obj;
        parallel_reduce(blocked_range<int>(0, MAXIMUM),obj);
        result = obj.getResult();

		end = high_resolution_clock::now();
		timeElapsed += 
			duration<double, std::milli>(end - start).count();
	}
    cout << "result = " << fixed << setprecision(0) << result << "\n";
	cout << "avg time = " << fixed << setprecision(3) 
		 << (timeElapsed / N) <<  " ms\n";

	return 0;
}