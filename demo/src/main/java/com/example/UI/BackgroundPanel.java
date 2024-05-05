package com.example.UI;
import javax.swing.*;
import java.awt.*;

public class BackgroundPanel extends JPanel {
    private Image backgroundImage;

    public BackgroundPanel(String imagePath) {
        try {
            // Asegúrate de que la imagen se carga correctamente.
            backgroundImage = new ImageIcon(getClass().getResource(imagePath)).getImage();
            if (backgroundImage == null) {
                System.out.println("La imagen no se pudo cargar: " + imagePath);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error al cargar la imagen: " + imagePath);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        } else {
            System.out.println("No se encontró la imagen de fondo.");
        }
    }
}
