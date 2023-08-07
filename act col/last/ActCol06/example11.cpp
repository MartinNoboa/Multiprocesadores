// =================================================================
//
// File: example11.cpp
// Author(s):
// Description: This file implements the code that transforms a
//				grayscale image. Using OpenCV and OpenMP, to compile:
//				g++ example11.cpp `pkg-config --cflags --libs opencv4` -fopenmp
//
// Copyright (c) 2020 by Tecnologico de Monterrey.
// All Rights Reserved. May be reproduced for any non-commercial
// purpose.
//
// =================================================================
// No pudimos probar el speedup debido a que tuvimos problemas con la instalación de OpenCV y el servidor nos mandaba un error en la librería.

#include <stdio.h>
#include <stdlib.h>
#include <opencv2/opencv.hpp>
#include <opencv2/core/core.hpp>
#include <opencv2/highgui/highgui.hpp>
#include <opencv2/highgui/highgui_c.h>
#include <opencv2/imgproc/imgproc.hpp>
#include <opencv2/imgcodecs/imgcodecs.hpp>
#include "utils.h"

void grayscale(cv::Mat &src, cv::Mat &dest) {
	int i, j;
	float r, g, b, gray;

	#pragma omp parallel for shared(src, dest) private(i, j, r, g, b, gray)
	for (i = 0; i < src.rows; i++) {
		for (j = 0; j < src.cols; j++) {
			r = 0;
			g = 0;
			b = 0;
			r += (float)src.at<cv::Vec3b>(i, j)[RED];
			g += (float)src.at<cv::Vec3b>(i, j)[GREEN];
			b += (float)src.at<cv::Vec3b>(i, j)[BLUE];

			gray = (r + g + b)/3.0;

			dest.at<cv::Vec3b>(i, j)[RED] = (unsigned char)(gray);
			dest.at<cv::Vec3b>(i, j)[GREEN] = (unsigned char)(gray);
			dest.at<cv::Vec3b>(i, j)[BLUE] = (unsigned char)(gray);
			
		}
	}
}

int main(int argc, char* argv[]) {
	int i;
	double acum;

	if (argc != 2) {
	printf("usage: %s source_file\n", argv[0]);
		return -1;
	}

	cv::Mat src = cv::imread(argv[1], cv::IMREAD_COLOR);
	cv::Mat dest = cv::Mat(src.rows, src.cols, CV_8UC3);
	if (!src.data) {
		printf("Could not load image file: %s\n", argv[1]);
		return -1;
	}

	acum = 0;
	for (i = 0; i < N; i++) {
		start_timer();
		grayscale(src, dest);
		acum += stop_timer();
	}

	printf("avg time = %.5lf ms\n", (acum / N));

	/*
	cv::namedWindow("Original", cv::WINDOW_AUTOSIZE);
    cv::imshow("Original", src);

	cv::namedWindow("Gray", cv::WINDOW_AUTOSIZE);
    cv::imshow("Gray", dest);

	cv::waitKey(0);
	*/
	cv::imwrite("gray_scale.png", dest);

	return 0;
}
