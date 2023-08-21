// =================================================================
//
// File: example5.cpp
// Author: Pedro Perez
// Description: This file contains the code that implements the
//				enumeration sort algorithm. The time this implementation
//				takes ill be used as the basis to calculate the
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

* Fecha: 3-Sep-2021

* Autores: A01209400 Royer Donnet Arenas Camacho
		   A01654856 Hugo David Franco Ávila

*

*--------------------------------------------------------------*/

#include <iostream>
#include <iomanip>
#include <cstring>
#include "utils.h"

const int SIZE = 100000; //1e5

using namespace std;

// implement your class here
class EnumSort
{
private:
	int *array, size;

public:
	EnumSort(int *a, int s) : array(a), size(s) { }

	void sort() {
		int *b = new int[size];
		int *c = new int[size];
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				if (array[i] > array[j] || array[i] == array[j] && j < i) {
					b[i] += 1;
				}
			}
		}
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
	int *a;
	double ms;

	a = new int[SIZE];
	random_array(a, SIZE);
	display_array("before", a);
	int *aux = new int[SIZE];

	cout << "Starting..." << endl;
	ms = 0;
	for (int i = 0; i < N; i++) {
		start_timer();
		
		memcpy(aux, a, sizeof(int) * SIZE);
		EnumSort en(aux, SIZE);
		
		// call your method here.
		en.sort();

		ms += stop_timer();
	}

	memcpy(a, aux, sizeof(int) * SIZE);
	display_array("after", a);
	cout << "avg time = " << setprecision(15) << (ms / N) << " ms" << endl;
	delete[] a;
	delete [] aux;
	return 0;
}
