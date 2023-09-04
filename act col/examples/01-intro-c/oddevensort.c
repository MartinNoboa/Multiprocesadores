// =================================================================
//
// File: example5.c
// Author: Pedro Perez
// Description: This file contains the code that implements the
//				bubble sort algorithm. The time this implementation takes
//				will be used as the basis to calculate the improvement
//				obtained with parallel technologies.
//
// Copyright (c) 2020 by Tecnologico de Monterrey.
// All Rights Reserved. May be reproduced for any non-commercial
// purpose.
//
// time 2976.44230 ms
// =================================================================

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "utils.h"

#define SIZE 10000


void oddEvenSort(int *arr, int size) {
	int odd, even, is_sorted = 0;

	while (!is_sorted) {

		// odd index
		odd = 1;
		for(int i = 1; i <= size - 2; i += 2) {
			if (arr[i] > arr[i + 1]) {
				swap(arr, i, i + 1);
				odd = 0;
			}
		}

		// even index
		even = 1;
		for(int i = 0; i <= size - 2; i += 2) {
			if (arr[i] > arr[i + 1]) {
				swap(arr, i, i + 1);
				even = 0;
			}
		}

		is_sorted = odd && even;
	}
}

int main(int argc, char* argv[]) {
	int i, *a, *aux;
	double ms;

	a = (int*) malloc(sizeof(int) * SIZE);
	aux = (int*) malloc(sizeof(int) * SIZE);
	random_array(a, SIZE);
	display_array("before", a);

	printf("Starting...\n");
	ms = 0;
	for (i = 0; i < N; i++) {
		start_timer();

		memcpy(aux, a, sizeof(int) * SIZE);
		oddEvenSort(aux, SIZE);

		ms += stop_timer();

		if (i == (N - 1)) {
			memcpy(a, aux, sizeof(int) * SIZE);
		}
	}
	display_array("after", a);
	printf("avg time = %.5lf ms\n", (ms / N));

	free(a); free(aux);
	return 0;
}
