package mmix;


import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

public class MyRunnable implements Runnable{

    @Override
    public void run() {
        JFrame frame = new JFrame("MMIX");
        JTabbedPane pane = new JTabbedPane();
        JComponent panel1 = new GUI("codigo1");
        pane.addTab("Codigo 1", panel1);
        pane.setMnemonicAt(0, KeyEvent.VK_1);

        JComponent panel2 = new GUI("codigo2");
        pane.addTab("Codigo 2", panel2);
        pane.setMnemonicAt(1, KeyEvent.VK_2);

        frame.setState(Frame.NORMAL);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(pane);
        frame.pack();
        frame.setVisible(true);
    }
}