// =================================================================
//
// File: Example11.java
// Author(s):
// Description: This file implements the code that transforms a
//				grayscale image using Java's Fork-Join.
//
// Copyright (c) 2020 by Tecnologico de Monterrey.
// All Rights Reserved. May be reproduced for any non-commercial
// purpose.
//
// =================================================================
/*----------------------------------------------------------------

*

* Multiprocesadores: Fork-Join 

* Fecha: 28-Sep-2021

* Autor: A01209400 Royer Donnet Arenas Camacho
		 A01654856 Hugo David Franco Ávila

*

*--------------------------------------------------------------*/
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.ForkJoinPool;

public class Example11 extends RecursiveAction {
	private static final int MIN = 1_00000;
	private int src[], dest[],start,end;

	public Example11(int src[], int dest[],int start,int end) {
		this.src = src;
		this.dest = dest;
		this.start=start;
		this.end=end;
	}

	private int scaleToGray(int color){
		int blue = color & 0xff;
		int green = (color & 0xff00) >> 8;
		int red = (color & 0xff0000) >> 16;
		int gray = (blue + green + red)/3;
		int alpha = (color & 0xff000000) >>> 24;
		int newPixel = (alpha & 0x000000ff) << 24 | (gray & 0x000000ff) << 16 | (gray & 0x000000ff) << 8 | (gray & 0x000000ff);
		return newPixel;
	}

	protected void computeDirectly() {
		for(int i=start;i<end;i++){
			dest[i]=scaleToGray(src[i]);
		}
	}

	@Override
	protected void compute() {
		if ( (end - start) <= MIN ) {
			computeDirectly();
		} else {
			int mid = start + ( (end - start) / 2 );
			invokeAll(new Example11(src,dest,start,mid),
					  new Example11(src,dest,mid,end));
		}
	}

	public static void main(String args[]) throws Exception {
		long startTime, stopTime;
		Example11 threads[];
		double ms;
		int block;
		ForkJoinPool pool;

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

			startTime = System.currentTimeMillis();

			pool = new ForkJoinPool(Utils.MAXTHREADS);
			pool.invoke(new Example11(src,dest,0,src.length));
			
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
