// =================================================================
//
// File: example11.cpp
// Author(s):
// Description: This file implements the code that transforms a
//				grayscale image. Using OpenCV and OpenMP, to compile:
//				g++ example11.cpp `pkg-config --cflags --libs opencv4` -fopenmp
//
//				The time this implementation takes will be used as the
//				basis to calculate the improvement obtained with
//				parallel technologies.
//
// Copyright (c) 2020 by Tecnologico de Monterrey.
// All Rights Reserved. May be reproduced for any non-commercial
// purpose.
//
// =================================================================

/*----------------------------------------------------------------

*

* Multiprocesadores: OpenMP

* Fecha: 12-oct-2021

* Autor: A01209400 - Royer Donnet Arenas Camacho
		 A01654856 - Hugo David Franco Ávila 

*

*--------------------------------------------------------------*/

#include <stdio.h>
#include <stdlib.h>
#include <opencv2/opencv.hpp>
#include <opencv2/core/core.hpp>
#include <opencv2/highgui/highgui.hpp>
#include <opencv2/highgui/highgui_c.h>
#include <opencv2/imgproc/imgproc.hpp>
#include <opencv2/imgcodecs/imgcodecs.hpp>
#include "utils.h"

// implement your code
class GrayScale
{
private:
	cv::Mat &src, &dest;

	void scaleToGray(int ren, int col) const {
		float r, g, b;

		r = 0;
		g = 0;
		b = 0;
		r += (float)src.at<cv::Vec3b>(ren, col)[RED];
		g += (float)src.at<cv::Vec3b>(ren, col)[GREEN];
		b += (float)src.at<cv::Vec3b>(ren, col)[BLUE];

		float gray = (r + g + b)/3.0;

		dest.at<cv::Vec3b>(ren, col)[RED] = (unsigned char)(gray);
		dest.at<cv::Vec3b>(ren, col)[GREEN] = (unsigned char)(gray);
		dest.at<cv::Vec3b>(ren, col)[BLUE] = (unsigned char)(gray);
	}

	public:
		GrayScale(cv::Mat &s, cv::Mat &d) : src(s), dest(d) {}

		void doTask() {
			int i,j;
			#pragma omp parallel for private(j)
			for (i = 0; i < src.rows; i++) {
				for (j = 0; j < src.cols; j++) {
					scaleToGray(i, j);
				}
			}
		}
};

int main(int argc, char *argv[])
{
	int i;
	double acum;

	if (argc != 2)
	{
		printf("usage: %s source_file\n", argv[0]);
		return -1;
	}

	cv::Mat src = cv::imread(argv[1], cv::IMREAD_COLOR);
	cv::Mat dest = cv::Mat(src.rows, src.cols, CV_8UC3);
	if (!src.data)
	{
		printf("Could not load image file: %s\n", argv[1]);
		return -1;
	}

	acum = 0;
	for (i = 0; i < N; i++)
	{
		start_timer();

		GrayScale obj(src, dest);
		obj.doTask();

		acum += stop_timer();
	}

	printf("avg time = %.5lf ms\n", (acum / N));

	/*
	cv::namedWindow("Original", cv::WINDOW_AUTOSIZE);
	cv::imshow("Original", src);

	cv::namedWindow("Gray scale", cv::WINDOW_AUTOSIZE);
	cv::imshow("Gray scale", dest);

	cv::waitKey(0);
	*/

	cv::imwrite("gray_scale.png", dest);

	return 0;
}
