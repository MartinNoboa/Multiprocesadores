
#include <iostream>
#include <iomanip>
#include <chrono>
#include "utils.h"

using namespace std;
using namespace std::chrono;

#define SIZE 10000

// implement your code
void enumeration_sort(int *array,int size) {
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

int main(int argc, char* argv[]) {
	int *array, result;
	// These variables are used to keep track of the execution time.
	high_resolution_clock::time_point start, end;
	double timeElapsed;

	array = new int[SIZE];
	random_array(array, SIZE);
	display_array("before", array);

	cout << "Starting...\n";
	timeElapsed = 0;
	for (int j = 0; j < N; j++) {
		start = high_resolution_clock::now();

		// call the implemented function
        enumeration_sort(array, SIZE);

		end = high_resolution_clock::now();
		timeElapsed += 
			duration<double, std::milli>(end - start).count();
	}
	display_array("after", array);
	cout << "avg time = " << fixed << setprecision(3) 
		 << (timeElapsed / N) <<  " ms\n";

	delete [] array;
	return 0;
}