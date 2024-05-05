package com.example;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class BackgroundPanel extends JPanel {
    private Image backgroundImage;

    public BackgroundPanel(String imagePath) {
        try {
            backgroundImage = ImageIO.read(getClass().getResourceAsStream(imagePath));
            if (backgroundImage == null) {
                System.err.println("Error al cargar la imagen de fondo: " + imagePath);
            }
        } catch (IOException e) {
            System.err.println("No se pudo cargar la imagen de fondo: " + imagePath);
            e.printStackTrace();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        } else {
            System.err.println("No hay imagen de fondo para dibujar.");
        }
    }
}
