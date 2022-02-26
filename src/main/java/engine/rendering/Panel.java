package engine.rendering;

import engine.GameProperties;
import engine.input.keyboard.KeyboardAdapter;
import engine.input.mouse.MouseAdapter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Panel extends JPanel implements ActionListener {
    private GameProperties gameProperties;
    private Timer timer;

    public Panel(GameProperties gameProperties) {
        this.setPreferredSize(new Dimension(gameProperties.getScreenWidth(), gameProperties.getScreenHeight()));
        this.setBackground(Color.black);
        this.setFocusable(true);
        this.addKeyListener(new KeyboardAdapter(gameProperties.getKeyboardListener()));
        this.addMouseListener(new MouseAdapter(gameProperties.getMouseListener()));

        this.gameProperties = gameProperties;
        this.timer = new Timer(gameProperties.getGameSpeed(), this);
        this.timer.start();
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2D = (Graphics2D) g.create();
        drawGrid(g2D);

        g2D.dispose();
    }

    private void drawGrid(Graphics2D g2D) {
        for(int index = 0; index < gameProperties.getScreenWidth(); index += 45) {
            g2D.drawLine(index, 0, index, gameProperties.getScreenHeight());
        }

        for(int index = 0; index < gameProperties.getScreenHeight(); index += 45) {
            g2D.drawLine(0, index, gameProperties.getScreenWidth(), index);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.repaint();
    }
}
