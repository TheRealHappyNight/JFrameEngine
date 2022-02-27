package engine.rendering;

import javax.swing.*;

public class Window extends JFrame {
    public Window() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.getContentPane().add(new Panel());
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
}
