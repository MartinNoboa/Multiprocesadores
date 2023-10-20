// =================================================================
//
// File: example7.cpp
// Author: Pedro Perez
// Description: This file contains the code that implements the
//				enumeration sort algorithm using Intel's TBB.
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
#include <chrono>
#include <iomanip>
#include <cstring>
#include <cmath>
#include <tbb/parallel_for.h>
#include <tbb/blocked_range.h>
#include "utils.h"

const int SIZE = 100000;

using namespace std;
using namespace tbb;
using namespace std::chrono;

// place your code here

class EnumSort
{
private:
	int *array,*b,*c, size;

public:
	EnumSort(int *a,int *b,int *c, int s) : array(a),b(b),c(c), size(s) { }


    void operator() (const blocked_range<int> &r) const {
		for (int i = r.begin(); i != r.end(); i++) {
			for (int j = 0; j < size; j++) {
				if (array[i] > array[j] || array[i] == array[j] && j < i) {
					b[i] += 1;
				}
			}
		}
	}

    void last(){
        for(int i = 0; i < size; i++){
			c[b[i]] = array[i];
		}

		for(int i = 0; i < size; i++){
			array[i] = c[i];
		}
    }
};

int main(int argc, char *argv[])
{
	int *a,*b,*c;
	double ms;
    high_resolution_clock::time_point start, end;
	double timeElapsed;

    timeElapsed = 0;

	a = new int[SIZE];
    b = new int[SIZE];
    c = new int[SIZE];
	random_array(a, SIZE);
	display_array("before", a);
	int *aux = new int[SIZE];
    int *aux1 = new int[SIZE];
    int *aux2 = new int[SIZE];

	cout << "Starting..." << endl;
	ms = 0;
	for (int i = 0; i < N; i++) {
		memcpy(aux, a, sizeof(int) * SIZE);
        memcpy(aux1, b, sizeof(int) * SIZE);
        memcpy(aux2, c, sizeof(int) * SIZE);
        start = high_resolution_clock::now();
		//EnumSort en(aux, SIZE);
		
		// call your method here.
		//en.sort();
        EnumSort obj(a,b,c,SIZE);
        parallel_for(blocked_range<int>(0, SIZE),  obj);
        obj.last();
        end = high_resolution_clock::now();
		timeElapsed += 
			duration<double, std::milli>(end - start).count();
	}

	// memcpy(a, aux, sizeof(int) * SIZE);
	display_array("after", a);
	cout << "avg time = " << fixed << setprecision(3) 
		 << (timeElapsed / N) <<  " ms\n";
	delete[] a;
	// delete [] aux;
	return 0;
}

