// =================================================================
//
// File: Example11.java
// Author(s):
// Description: This file implements the code that transforms a
//				grayscale image. The time this implementation takes will
//				be used as the basis to calculate the improvement obtained
// 				with parallel technologies.
//
// Copyright (c) 2020 by Tecnologico de Monterrey.
// All Rights Reserved. May be reproduced for any non-commercial
// purpose.
//
// =================================================================
/*----------------------------------------------------------------

*

* Multiprocesadores: Threads en Java.

* Fecha: 21-Sep-2021

* Autor: A01209400 Royer Donnet Arenas Camacho
		 A01654856 Hugo David Franco Avila

*

*--------------------------------------------------------------*/

import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

public class Example11 extends Thread{

	private int src[], dest[],start,end;

	public Example11(int src[], int dest[],int start,int end) {
		this.src = src;
		this.dest = dest;
		this.start=start;
		this.end=end;
	}

	// place your code here

	private int scaleToGray(int color){
		int blue = color & 0xff;
		int green = (color & 0xff00) >> 8;
		int red = (color & 0xff0000) >> 16;
		int gray = (blue + green + red)/3;
		int alpha = (color & 0xff000000) >>> 24;
		int newPixel = (alpha & 0x000000ff) << 24 | (gray & 0x000000ff) << 16 | (gray & 0x000000ff) << 8 | (gray & 0x000000ff);
		return newPixel;
	}

	public void run() {
		for(int i=start;i<end;i++){
			dest[i]=scaleToGray(src[i]);
		}
	}
	public static void main(String args[]) throws Exception {
		long startTime, stopTime;
		Example11 threads[];
		double ms;
		int block;

		if (args.length != 1) {
			System.out.println("usage: java Example11 image_file");
			System.exit(-1);
		}

		final String fileName = args[0];
		File srcFile = new File(fileName);
        final BufferedImage source = ImageIO.read(srcFile);

		int w = source.getWidth();
		int h = source.getHeight();
		int src[] = source.getRGB(0, 0, w, h, null, 0, w);
		int dest[] = new int[src.length];
		block = src.length / Utils.MAXTHREADS;
		threads = new Example11[Utils.MAXTHREADS];
		System.out.printf("Starting...\n");
		ms = 0;
		for (int j = 0; j < Utils.N; j++) {
			for (int i = 0; i < threads.length; i++) {
				if (i != threads.length - 1) {
					threads[i] = new Example11(src,dest,(i * block), ((i + 1) * block));
				} else {
					threads[i] = new Example11(src,dest,(i * block), src.length);
				}
			}
			startTime = System.currentTimeMillis();
			for (int i = 0; i < threads.length; i++) {
				threads[i].start();
			}
			
			for (int i = 0; i < threads.length; i++) {
				try {
					threads[i].join();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			stopTime = System.currentTimeMillis();

			ms += (stopTime - startTime);
		}
		System.out.printf("avg time = %.5f\n", (ms / Utils.N));
		final BufferedImage destination = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
		destination.setRGB(0, 0, w, h, dest, 0, w);

		javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
               ImageFrame.showImage("Original - " + fileName, source);
            }
        });

		javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
               ImageFrame.showImage("Gray - " + fileName, destination);
            }
        });
	}
}
