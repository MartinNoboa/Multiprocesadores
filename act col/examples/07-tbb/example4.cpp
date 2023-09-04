// =================================================================
//
// File: Example3.cpp
// Authors:
// Description: This file contains the code to count the number of
//				even numbers within an array using Intel's TBB.
//
// Copyright (c) 2020 by Tecnologico de Monterrey.
// All Rights Reserved. May be reproduced for any non-commercial
// purpose.
//
// =================================================================
/*----------------------------------------------------------------

*

* Multiprocesadores: TBB

* Fecha: 19-oct-2021

* Autor: A01209400 Royer Donnet Arenas Camacho
         A01654856 - Hugo David Franco Ávila 

*

*--------------------------------------------------------------*/


#include <iostream>
#include <iomanip>
#include <cmath>
#include <tbb/parallel_reduce.h>
#include <tbb/blocked_range.h>
#include "utils.h"

#define SIZE 1000000000 //1e9

using namespace std;
using namespace tbb;

// place your code here
class Even {
private:
	int *A, size,result;

public:
	Even(int *a, int s) : A(a), size(s),result(0) {}
    Even(Even &obj,split) : A(obj.A), size(obj.size),result(0) {}
    
	double getResult() const {
		return result;
	}

    void operator() (const blocked_range<int> &r){
		for (int i = r.begin(); i != r.end(); i++) {
			if((A[i]%2)==0){
				result +=1;
			}
		}
    }


    void join(const Even &x) {
		result += x.result;
	}
};

int main(int argc, char* argv[]) {
	int *a;
	double ms;
    int result;

	a = new int[SIZE];
	fill_array(a, SIZE);
	display_array("a", a);

	cout << "Starting..." << endl;
	ms = 0;
	
	for (int i = 0; i < N; i++) {
		start_timer();

		// call your method here.
		//obj.doTask();
        Even obj(a,SIZE);
        parallel_reduce(blocked_range<int>(0, SIZE), obj);
        result = obj.getResult();
		ms += stop_timer();
	}
	cout << "result = "<< setprecision(15) << result<<endl;
	// display the result here
	cout << "avg time = " << setprecision(15) << (ms / N) << " ms" << endl;

	delete [] a;
	return 0;
}
