package com.fireslug;

import com.fireslug.gameutils.Coord2;
import com.fireslug.subpanels.PanelGame;
import com.fireslug.subpanels.PanelMenu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainFrame extends JFrame {

    private PanelMenu panelMenu;
    private PanelGame panelGame;

    public MainFrame() {

        this.setBackground(Color.BLACK);
        this.setSize(new Dimension(1200, 1000));
        this.setLocation(50, 20);

        this.panelMenu = new PanelMenu();
        this.panelGame = new PanelGame();

        this.add(this.panelMenu);

        this.init();
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setVisible(true);


    }

    private void init() {

        this.panelMenu.buttonStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                MainFrame.this.invalidate();
                MainFrame.this.remove(MainFrame.this.panelMenu);
                MainFrame.this.add(MainFrame.this.panelGame);
                MainFrame.this.panelGame.regen();
                MainFrame.this.repaint();
                MainFrame.this.revalidate();


                Font font_temp = null;

                for(int j=0; j<Reference.HEIGHT; ++j) {
                    for (int i = 0; i < Reference.WIDTH; ++i) {

                        JButton temp = MainFrame.this.panelGame.buttonCells.get(new Coord2(i, j));

                        if(font_temp == null)
                            font_temp = new Font("monospace", Font.PLAIN, temp.getWidth() * 4 / 5);
                        temp.setFont(font_temp);

                    }
                }


            }
        });

        this.panelGame.buttonQuit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                MainFrame.this.invalidate();
                MainFrame.this.remove(MainFrame.this.panelGame);
                MainFrame.this.add(MainFrame.this.panelMenu);
                MainFrame.this.repaint();
                MainFrame.this.revalidate();

            }
        });

        ;

    }

}
