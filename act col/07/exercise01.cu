// =================================================================
//
// File: exercise01.cu
// Author(s):
//      Martin Adrian Noboa Monar - A01704052
//      Samuel Octavio González Azpeitia - A01704696
//
//			Sin Threads		Con Threads		Speed Up
//			636.083  ms		
// =================================================================

#include <iostream>
#include <iomanip>
#include <chrono>
#include "utils.h"
#include <cuda_runtime.h>

using namespace std;
using namespace std::chrono;

// array size
#define SIZE 1000000000
#define THREADS	256
#define BLOCKS	MMIN(32, ((SIZE / THREADS) + 1))
// implement your code
__global__ void even(int *array,int * results){
    __shared__ int cache[THREADS];

    int tid = threadIdx.x + (blockIdx.x * blockDim.x);
    int cacheIndex = threadIdx.x;

    int aux = INT_MAX;
    while (tid < SIZE) {
        if((array[tid]%2)==0){
			aux += array[tid];
		}
        tid += blockDim.x * gridDim.x;
    }

	cache[cacheIndex] = aux;

    __syncthreads();

    int i = blockDim.x / 2;
    while (i > 0) {
        if (cacheIndex < i) {
            cache[cacheIndex] += cache[cacheIndex+1];
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
    int *d_a, *d_r;
    
    // These variables are used to keep track of the execution time.
	high_resolution_clock::time_point start, end;
	double timeElapsed;

	a =  new int[SIZE];
	random_array(a, SIZE);
	display_array("a", a);

	results = new int[BLOCKS];

	cudaMalloc( (void**) &d_a, SIZE * sizeof(int) );
	cudaMalloc( (void**) &d_r, BLOCKS * sizeof(int) );

	cudaMemcpy(d_a, a, SIZE * sizeof(int), cudaMemcpyHostToDevice);

	cout << "Starting...\n";
    timeElapsed = 0;
	for (int j = 0; j < N; j++) {
		start = high_resolution_clock::now();

		// call the implemented function
        even<<<BLOCKS, THREADS>>> (d_a, d_r);

		end = high_resolution_clock::now();
		timeElapsed += 
			duration<double, std::milli>(end - start).count();
	}
	cudaMemcpy(results, d_r, BLOCKS * sizeof(int), cudaMemcpyDeviceToHost);

    double aux = 0;
    for (i = 0; i < BLOCKS; i++) {
        aux += results[i];
    }

    cout << "result = " << aux << "\n";
    cout << "avg time = " << fixed << setprecision(3) 
        << (timeElapsed / N) <<  " ms\n";

    cudaFree(d_r);
    cudaFree(d_a);

    delete [] a;
    delete [] results;

    return 0;
}
