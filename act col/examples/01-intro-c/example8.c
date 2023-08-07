// =================================================================
//
// File: example8.c
// Author(s): 
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

* Multiprocesadores: C

* Fecha: 24-sep-2021

* Autor: A01209400 - Royer Donnet Arenas Camacho
		 A01654856 - Hugo David Franco Ávila 

*

*--------------------------------------------------------------*/

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "utils.h"

#define SIZE 100000

void nSort(int *a, int max){
	int i,j;
	int *b;

	b=(int*) malloc(sizeof(int)*max);
	for(i=0;i<max;i++){
		for(j=0;j<max;j++){
			if(a[i]>a[j]||a[i]==a[j]&&j<i){
				b[i]+=1;
			}
		}
	}

	int *c;

	c=(int*) malloc(sizeof(int)*max);

	for(i=0;i<max;i++){
		c[b[i]]=a[i];
	}

	for(i=0;i<max;i++){
		a[i]=c[i];
	}
}

int main(int argc, char* argv[]) {
	int i, *a;
	double ms;

	a = (int*) malloc(sizeof(int) * SIZE);
	random_array(a, SIZE);
	display_array("before", a);

	printf("Starting...\n");
	ms = 0;
	for (i = 0; i < N; i++) {
		start_timer();

		nSort(a,SIZE);

		ms += stop_timer();
	}
	display_array("after", a);
	printf("avg time = %.5lf ms\n", (ms / N));

	free(a);
	return 0;
}