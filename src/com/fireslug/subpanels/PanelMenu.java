package com.fireslug.subpanels;

import com.fireslug.Reference;

import javax.swing.*;
import java.awt.*;

public class PanelMenu extends JPanel {

    public JButton buttonStart;
    public JLabel labelTitle;

    private JLabel blank0;
    private JLabel blank1;


    public PanelMenu() {

        this.setLayout(new FlowLayout(FlowLayout.CENTER));
        this.setBackground(Color.BLACK);

        this.init();

    }

    private void init() {

        this.buttonStart = new JButton();
        this.buttonStart.setText("Start");
        this.buttonStart.setPreferredSize(new Dimension(200, 50));
        this.buttonStart.setBackground(new Color(95, 167, 64));
        this.buttonStart.setForeground(new Color(255, 255, 255));
        this.buttonStart.setFont(Reference.MONOSPACE_30);
        this.buttonStart.setFocusPainted(false);

        this.labelTitle = new JLabel("Minesweeper", SwingConstants.CENTER);
        this.labelTitle.setPreferredSize(new Dimension(500, 60));
        this.labelTitle.setFont(Reference.GOTHIC_40);
        this.labelTitle.setForeground(new Color(160, 255, 232));

        this.blank0 = new JLabel();
        this.blank0.setPreferredSize(new Dimension(2000, 30));
        this.blank1 = new JLabel();
        this.blank1.setPreferredSize(new Dimension(2000, 50));


        this.add(this.blank0);
        this.add(this.labelTitle);
        this.add(this.blank1);
        this.add(this.buttonStart);

    }
}
