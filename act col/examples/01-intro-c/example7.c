/*----------------------------------------------------------------

*

* Multiprocesadores: C

* Fecha: 24-sep-2021

* Autor: A01209400 - Royer Donnet Arenas Camacho
		 A01654856 - Hugo David Franco Ávila 

*

*--------------------------------------------------------------*/

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
#include "utils.h"

#define MAXIMUM 1000000 //1e6

// implement your code
void primeN(int * arg, int max){
	int i=0,j=0;
	int flag=0;
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
			arg[i]=1;
		}
	}
}

int main(int argc, char* argv[]) {
	int i, *a;
	double ms;

	a = (int *) malloc(sizeof(int) * (MAXIMUM + 1));
	printf("At first, neither is a prime. We will display to TOP_VALUE:\n");
	for (i = 2; i < TOP_VALUE; i++) {
		if (a[i] == 0) {
			printf("%i ", i);
		}
	}
	printf("\n");

	printf("Starting...\n");
	ms = 0;
	for (i = 0; i < N; i++) {
		start_timer();

		primeN(a, MAXIMUM);

		ms += stop_timer();
	}
	printf("Expanding the numbers that are prime to TOP_VALUE:\n");
	for (i = 2; i < TOP_VALUE; i++) {
		if (a[i] == 1) {
			printf("%i ", i);
		}
	}
	printf("\n");
	printf("avg time = %.5lf ms\n", (ms / N));

	free(a);
	return 0;
}
