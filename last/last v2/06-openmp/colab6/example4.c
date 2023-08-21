#include <stdio.h>
#include <stdlib.h>
#include "utils.h"

/*----------------------------------------------------------------

*

* Multiprocesadores: OpenMP

* Fecha: 12-oct-2021

* Autor: A01209400 - Royer Donnet Arenas Camacho
		 A01654856 - Hugo David Franco Ávila 

*

*--------------------------------------------------------------*/

// array size
#define SIZE 1000000000


int evenNumbers(int *array,int size){
	int i, acum;
	acum = 0;
	#pragma omp parallel for shared(array, size) reduction(+:acum)
	for (i = 0; i < size; i++) {
		if((array[i]%2)==0){
			acum +=1;
		}
	}
	return acum;
}

int main(int argc, char* argv[]) {
	int i, *a, result;
	double ms;

	a = (int *) malloc(sizeof(int) * SIZE);
	fill_array(a, SIZE);
	display_array("a", a);

	printf("Starting...\n");
	ms = 0;
	for (i = 0; i < N; i++) {
		start_timer();
		
		result=evenNumbers(a,SIZE);
		
		ms += stop_timer();
	}
	printf("result = %i\n", result);
	printf("avg time = %.5lf ms\n", (ms / N));
	// must display: result = 500000000

	free(a);
	return 0;
}
