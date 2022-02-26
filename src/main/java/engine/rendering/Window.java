package engine.rendering;

import engine.GameProperties;
import engine.input.keyboard.KeyboardAdapter;
import engine.input.mouse.MouseAdapter;

import javax.swing.*;

public class Window extends JFrame {
    public Window(GameProperties gameProperties) {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.getContentPane().add(new Panel(gameProperties));
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
}
