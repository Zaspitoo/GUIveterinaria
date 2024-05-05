package com.example.UI;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;

/**
 * Clase BackgroundPanel que extiende JPanel para permitir la inclusión de una imagen de fondo.
 */
public class BackgroundPanel extends JPanel {
    private Image backgroundImage; // Variable para almacenar la imagen de fondo.

    /**
     * Constructor de BackgroundPanel.
     * imagePath Ruta de la imagen que se usará como fondo.
     */
    public BackgroundPanel(String imagePath) {
        try {
            // Intenta cargar la imagen desde el recurso proporcionado. getResourceAsStream() carga la imagen como un stream.
            backgroundImage = ImageIO.read(getClass().getResourceAsStream(imagePath));
            // Si la imagen es nula, es decir, no se encontró o no se pudo leer, se imprime un mensaje de error.
            if (backgroundImage == null) {
                System.err.println("Error al cargar la imagen de fondo: " + imagePath);
            }
        } catch (IOException e) {
            // Captura cualquier error de E/S que pueda ocurrir durante la lectura de la imagen y lo imprime junto con un stack trace.
            System.err.println("No se pudo cargar la imagen de fondo: " + imagePath);
            e.printStackTrace();
        }
    }

    /**
     * Sobrescribe el método paintComponent de JPanel para dibujar la imagen de fondo.
     * @param g El objeto Graphics que se usa para dibujar la imagen.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); // Llama a la implementación de la superclase para asegurar que otros componentes se dibujen correctamente.
        if (backgroundImage != null) {
            // Si hay una imagen de fondo disponible, la dibuja ajustándola al tamaño del panel.
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        } else {
            // Si no hay imagen de fondo disponible, imprime un mensaje de error en la consola.
            System.err.println("No hay imagen de fondo para dibujar.");
        }
    }
}
