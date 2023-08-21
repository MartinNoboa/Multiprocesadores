// =================================================================
//
// File: example4.cu
// Author(s):
// Description: This file contains the code to count the number of
//				even numbers within an array using CUDA.
//
// =================================================================

/*----------------------------------------------------------------

*

* Multiprocesadores: CUDA

* Fecha: 14-Nov-2021



* Autor: A01209400 - Royer Donnet Arenas Camacho
		 A01654856 - Hugo David Franco Ávila 

*

--------------------------------------------------------------*/
#include <stdio.h>
#include <stdlib.h>
#include <cuda_runtime.h>
#include "utils.h"

#define SIZE 1000000000
#define THREADS	256
#define BLOCKS	MMIN(32, ((SIZE / THREADS) + 1))

__global__ void evenNumbers(int *array,int * results){
    __shared__ int cache[THREADS];

	int tid = threadIdx.x + (blockIdx.x * blockDim.x);
	int cacheIndex = threadIdx.x;
	int acum = 0;
	while (tid < SIZE) {
		if((array[tid]%2)==0){
			acum +=1;
		}
        tid += blockDim.x * gridDim.x;
	}


	cache[cacheIndex] = acum;
    //printf("%i\n", acum);
	__syncthreads();

	int i = blockDim.x / 2;
	while (i > 0) {
		if (cacheIndex < i) {
			cache[cacheIndex] += cache[cacheIndex + i];
		}
		__syncthreads();
		i /= 2;
	}

	if (cacheIndex == 0) {
		results[blockIdx.x] = cache[cacheIndex];
	}
}

int main(int argc, char* argv[]) {
	int i, *a, *results;
    int *d_a, *d_results;
	double ms;

	a = (int *) malloc(sizeof(int) * SIZE);
    results = (int *) malloc(sizeof(int) * BLOCKS);

    cudaMalloc( (void**) &d_a, sizeof(int) * SIZE);
    cudaMalloc( (void**) &d_results, sizeof(int) * BLOCKS);

	fill_array(a, SIZE);
    cudaMemcpy(d_a, a, sizeof(int) * SIZE, cudaMemcpyHostToDevice);
	display_array("a", a);

	printf("Starting...\n");
	ms = 0;
	for (i = 0; i < N; i++) {
		start_timer();
		
		evenNumbers<<<BLOCKS, THREADS>>>(d_a, d_results);
		
		ms += stop_timer();
	}
    cudaMemcpy(results, d_results, BLOCKS * sizeof(int), cudaMemcpyDeviceToHost);
    int result = 0;
	for (i = 0; i < BLOCKS; i++) {
		result += results[i];
	}
	printf("result = %i\n", result);
	printf("avg time = %.5lf ms\n", (ms / N));
	// must display: result = 500000000

    cudaFree(d_a);
    cudaFree(d_results);
	free(a);
    free(results);
	return 0;
}