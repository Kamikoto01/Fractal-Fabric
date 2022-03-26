package com.company;

import java.awt.geom.Rectangle2D;

public class Mandelbrot extends FractalGenerator {

    public static final int MAX_ITERATIONS = 2000;
    //выделяет область, в которой будет рисоваться фрактал
    @Override
    public void getInitialRange(Rectangle2D.Double range) {
        range.x = -2 ;
        range.y = -1.5 ;
        range.width = 3;
        range.height = 3;
    }

    public int numIterations(double x, double y) {
        int iteration = 0; //Начать итерацию с нуля
        Complex cxnum = new Complex(x, y);
        //кол-во итераций меньше установленного максимума или модуль числа меньше 2
        while (iteration < MAX_ITERATIONS && cxnum.abs2() < 4) {
            cxnum.Iteration();
            iteration++;
        }

        // В случае если достигнут максимум итераций 2000, то возвращается -1
        if (iteration == MAX_ITERATIONS)
            return -1;
        else {
            return iteration;
        }
    }
}
//++++++++++++++++++++++++done++++++++++++++++++++++++