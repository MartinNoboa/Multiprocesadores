// =================================================================
//
// File: example2.cpp
// Author(s):
// Description: This file contains the code to count the number of
//				even numbers within an array. The time this implementation
//				takes will be used as the basis to calculate the
//				improvement obtained with parallel technologies.
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
#include <climits>
#include <algorithm>
#include "utils.h"

const int SIZE = 1000000000; //1e9

using namespace std;

class Even {
private:
	int *A, size,result;

public:
	Even(int *a, int s) : A(a), size(s) {}

	double getResult() const {
		return result;
	}

	void doTask() {
		int i;
		result=0;
		for (i = 0; i < size; i++) {
			if((A[i]%2)==0){
				result +=1;
			}
		}
	}
};

int main(int argc, char* argv[]) {
	int *a;
	double ms;

	a = new int[SIZE];
	fill_array(a, SIZE);
	display_array("a", a);

	cout << "Starting..." << endl;
	ms = 0;
	// create object here
	Even obj(a,SIZE);
	for (int i = 0; i < N; i++) {
		start_timer();

		// call your method here.
		obj.doTask();

		ms += stop_timer();
	}
	cout << "result = "<< setprecision(15) << obj.getResult()<<endl;
	// display the result here
	cout << "avg time = " << setprecision(15) << (ms / N) << " ms" << endl;

	delete [] a;
	return 0;
}
