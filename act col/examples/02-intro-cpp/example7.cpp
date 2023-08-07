// =================================================================
//
// File: example7.cpp
// Author: Pedro Perez
// Description: This file contains the code to brute-force all
//				prime numbers less than MAXIMUM. The time this
//				implementation takes will be used as the basis to
//				calculate the improvement obtained with parallel
//				technologies.
//
// Copyright (c) 2020 by Tecnologico de Monterrey.
// All Rights Reserved. May be reproduced for any non-commercial
// purpose.
//
// =================================================================

/*----------------------------------------------------------------

*

* Multiprocesadores: C++

* Fecha: 30-agosto-2021

* Autor: A01209400 - Royer Donnet Arenas Camacho
		 A01654856 - Hugo David Franco Ávila 

*

*--------------------------------------------------------------*/

#include <iostream>
#include <iomanip>
#include <cstring>
#include <cmath>
#include <algorithm>
#include "utils.h"

#define MAXIMUM 1000000 //1e6

using namespace std;

// implement your class here
class Prime{
	private:
		int *A, max;
	public:
		Prime(int *a, int m):A(a),max(m) {}

	void doTask() {
		int i=0,j=0;
		int flag=0;
		int result=0;
		for(i=2;i<max;i++){
			flag=0;
			j=2;
			while((j*j)<=i){
				if(i%j==0){
					flag=1;
					break;
				}
				j++;
			}
			if(flag==0){
				A[i]=1;
			}
		}
	}
};

int main(int argc, char* argv[]) {
	int i, *a;
	double ms;

	a = new int[MAXIMUM + 1];
	cout << "At first, neither is a prime. We will display to TOP_VALUE:\n";
	for (i = 2; i < TOP_VALUE; i++) {
		cout << i << " ";
	}
	cout << "\n";

	cout << "Starting..." << endl;
	ms = 0;
	Prime obj(a,MAXIMUM);
	for (int i = 0; i < N; i++) {
		start_timer();

		obj.doTask();

		ms += stop_timer();
	}
	cout << "Expanding the numbers that are prime to TOP_VALUE:\n";
	for (i = 2; i < TOP_VALUE; i++) {
		if (a[i] == 1) {
			printf("%i ", i);
		}
	}
	cout << "avg time = " << setprecision(15) << (ms / N) << " ms" << endl;

	delete [] a;
	return 0;
}
