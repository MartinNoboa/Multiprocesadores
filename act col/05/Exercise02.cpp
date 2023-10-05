// =================================================================
//
// File: exercise02.cpp
// Author(s):
//          Martin Adrian Noboa Monar - A01704052
//          Samuel Octavio González Azpeitia - A01704696
// Description: This file contains the code to brute-force all
//				prime numbers less than MAXIMUM using the OpenMP 
//				technology. To compile:
//				g++ -o app -fopenmp exercise02.cpp
//
// Reference:
// 	Read the document "exercise02.pdf"
//
// Copyright (c) 2022 by Tecnologico de Monterrey.
// All Rights Reserved. May be reproduced for any non-commercial
// purpose.
//
// =================================================================

#include <iostream>
#include <iomanip>
#include <chrono>
#include <omp.h>
#include <math.h>
#include "utils.h"

using namespace std;
using namespace std::chrono;

#define MAXIMUM 1000001 //1e6

// implement your code

bool isPrime(int number) {
    bool flag = false;
    if (number < 2) {
        return false;
    }
    for (int i = 2; i <= sqrt(number); i++) {
        if (number % i == 0) {
                return false;
            }else{
                flag = true;
            }
        }
    return flag;
    
}

double sum_primes(int maximum){
 	#pragma omp parallel for shared(maximum) reduction(+ : result)
	double result = 0;
    for (int i = 0; i < maximum; i++) {
        if( isPrime(i) )
            result += i;
    }
    return result;
}

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
		result = sum_primes(MAXIMUM);
		end = high_resolution_clock::now();
		timeElapsed += 
			duration<double, std::milli>(end - start).count();
	}
	cout << "result = " << result << "\n";
	cout << "avg time = " << fixed << setprecision(3) 
		 << (timeElapsed / N) <<  " ms\n";

	return 0;
}