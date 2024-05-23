package main;

import view.GUIView;
import controller.TableController;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                GUIView view = new GUIView();
                new TableController(view);
                view.setVisible(true);
            }
        });
    }
}
