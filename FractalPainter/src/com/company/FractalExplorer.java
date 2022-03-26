package com.company;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.geom.Rectangle2D;
import java.awt.event.*;

public class FractalExplorer {
    private int displaySize;
    private JImageDisplay jImageDisplay;
    private FractalGenerator fractalGenerator;
    private Rectangle2D.Double range;

    public FractalExplorer(int size) {
        this.displaySize = size;
        this.jImageDisplay = new JImageDisplay(displaySize, displaySize);
        this.range = new Rectangle2D.Double();
        fractalGenerator = new Mandelbrot();
        fractalGenerator.getInitialRange(range);
    }

    public void createAndShowGUI() {
        jImageDisplay.setLayout(new BorderLayout());
        JFrame frame = new JFrame("Fractals");
        JPanel buttonPanel = new JPanel();
        JButton resetButton = new JButton("Reset");
        ButtonHandler resetHandler = new ButtonHandler();
        MouseHandler clickHandler = new MouseHandler();

        buttonPanel.add(resetButton);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(jImageDisplay, BorderLayout.CENTER);
        frame.add(buttonPanel, BorderLayout.SOUTH);
        frame.addMouseListener(clickHandler);
        frame.pack();
        frame.setVisible(true);
        frame.setResizable(false);
        resetButton.addActionListener(resetHandler);

    }

    public void drawFractal() {
        for(int y = 0; y < displaySize; y++) {
            for(int x = 0; x < displaySize; x++) {
                // координаты пикселя, который будет обрабатываться далее в методе
                double xCoord = fractalGenerator.getCoord(range.x, range.x + range.width, displaySize, x);
                double yCoord = fractalGenerator.getCoord(range.y, range.y + range.height, displaySize, y);

                //кол-во итераций для пикселя с данными координатами
                int i = fractalGenerator.numIterations(xCoord, yCoord);
                if (i == -1) {
                    //если кол-во итерация больше максимума, ставим черный цвет
                    jImageDisplay.drawPixel(x, y, 0);
                } else {
                    //hue - оттенок, если кол-во итерация меньше максимума.
                    float hue = 0.7f + (float) i/200f;
                    int rgbColor = Color.HSBtoRGB(hue, 1f, 1f);
                    //нарисовать пискель с получившимся оттенком
                    jImageDisplay.drawPixel(x, y, rgbColor);
                }
            }
        }
        jImageDisplay.repaint();
    }
    //Внутренний класс, предназначенный чисто для обработки ивента кнопки сброса
    private class ButtonHandler implements ActionListener {
        public void actionPerformed(ActionEvent e) { //сбрасываем диапазон к начальному и рисуем фрактал заново
            String command = e.getActionCommand();
            if (command.equals("Reset")) {
                fractalGenerator.getInitialRange(range);
                drawFractal();
            }
        }
    }

    private class MouseHandler extends MouseAdapter {
        public void mouseClicked(MouseEvent e) {
            int x = e.getX();
            int y = e.getY();

            double xCoord = fractalGenerator.getCoord(range.x, range.x + range.width, displaySize, x);
            double yCoord = fractalGenerator.getCoord(range.y, range.y + range.height, displaySize, y);

            fractalGenerator.recenterAndZoomRange(range, xCoord, yCoord, 0.5);
            drawFractal();

        }
    }
}
