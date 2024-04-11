package com.example;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class MovingButtonGame extends JFrame {
    private JButton winButton;
    private JButton moveButton;
    private JPanel mainPanel;
    private final Random random = new Random();

    public MovingButtonGame() {
        super("Moving Button Game");
        this.setSize(1920, 1080); // Ajustado para 1920x1080
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null); // Usar layout nulo para mover componentes libremente

        mainPanel = new JPanel();
        mainPanel.setLayout(null);
        mainPanel.setBounds(0, 0, 1920, 1080);

        winButton = new JButton("Ganar");
        moveButton = new JButton("Mover");

        // Ajustar las posiciones iniciales para una ventana más grande
        winButton.setBounds(860, 515, 100, 50); // Posición central ajustada
        moveButton.setBounds(960, 515, 100, 50); // Posición central ajustada

        winButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "¡Has ganado!");
            }
        });

        moveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Mover el botón a una posición aleatoria dentro del panel
                moveButton.setLocation(random.nextInt(mainPanel.getWidth() - moveButton.getWidth()),
                        random.nextInt(mainPanel.getHeight() - moveButton.getHeight()));
            }
        });

        mainPanel.add(winButton);
        mainPanel.add(moveButton);

        this.add(mainPanel);
        this.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MovingButtonGame();
            }
        });
    }
}
