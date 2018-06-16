package com.fireslug.subpanels;

import com.fireslug.Reference;
import com.fireslug.gameutils.Coord2;
import com.fireslug.gameutils.MineCell;
import javafx.scene.layout.Pane;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.plaf.ButtonUI;
import javax.swing.plaf.metal.MetalButtonUI;
import java.awt.*;
import java.awt.event.*;
import java.sql.Ref;
import java.util.*;
import java.util.List;

public class PanelGame extends JPanel {

    public JPanel panelInterior;
    public JButton buttonQuit;
    public JButton buttonRestart;
    public JLabel labelBombs;
    public JLabel labelTime;

    public Map<Coord2, JButton> buttonCells;
    public Map<Coord2, MineCell> cells;

    private JLabel blank0;
    private JLabel blank1;
    private JLabel blank2;
    private JLabel blank3;

    public int bombsRemaining;
    public int time;

    private boolean isRightDown = false;
    private boolean isLeftDown = false;

    public static final int MAX_HEIGHT = 850;
    public static final int MAX_WIDTH = 1875;

    public PanelGame() {

        this.setLayout(new FlowLayout(FlowLayout.CENTER));
        this.setBackground(Color.BLACK);
        this.init();




    }

    private void init() {

        this.bombsRemaining = Reference.BOMBS;

        this.buttonQuit = new JButton();
        this.buttonQuit.setText("quit");
        this.buttonQuit.setFocusPainted(false);
        this.buttonQuit.setPreferredSize(new Dimension(130, 45));
        this.buttonQuit.setBackground(Reference.SCHEME.buttonBackground);
        this.buttonQuit.setForeground(new Color(255, 255, 255));
        this.buttonQuit.setFont(Reference.MONOSPACE_30);

        this.buttonRestart = new JButton();
        this.buttonRestart.setText("restart");
        this.buttonRestart.setFocusPainted(false);
        this.buttonRestart.setPreferredSize(new Dimension(130, 45));
        this.buttonRestart.setBackground(Reference.SCHEME.buttonBackground);
        this.buttonRestart.setForeground(new Color(255, 255, 255));
        this.buttonRestart.setFont(Reference.MONOSPACE_30);

        this.labelBombs = new JLabel();
        this.labelBombs.setText(String.valueOf(this.bombsRemaining));
        this.labelBombs.setPreferredSize(new Dimension(50, 45));
        this.labelBombs.setForeground(new Color(255, 255, 255));
        this.labelBombs.setFont(Reference.MONOSPACE_30);

        this.labelTime = new JLabel();
        //this.labelTime.setText(String.valueOf())

        this.blank0 = new JLabel();
        this.blank0.setPreferredSize(new Dimension(3000, 20));
        this.blank1 = new JLabel();
        this.blank1.setPreferredSize(new Dimension(3000, 30));
        this.blank2 = new JLabel();
        this.blank2.setPreferredSize(new Dimension(3000, 10));

        this.cells = new HashMap<>();
        this.buttonCells = new HashMap<>();

        this.buttonRestart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PanelGame.this.getParent().invalidate();
                PanelGame.this.regen();

                PanelGame.this.getParent().repaint();
                PanelGame.this.refreshFont();


            }
        });

    }

    public void regen() {

        if(this.panelInterior != null)
            this.panelInterior.removeAll();
        this.bombsRemaining = Reference.BOMBS;
        this.labelBombs.setText(String.valueOf(this.bombsRemaining));


        this.panelInterior = new JPanel();
        int width, height;

        if(Reference.WIDTH / Reference.HEIGHT > MAX_WIDTH / MAX_HEIGHT) {

            width = MAX_WIDTH;
            height = width * Reference.HEIGHT / Reference.WIDTH;

        }
        else {

            height = MAX_HEIGHT;
            width = height * Reference.WIDTH / Reference.HEIGHT;

        }

        this.panelInterior.setPreferredSize(new Dimension(width, height));
        this.panelInterior.setLayout(new GridLayout(Reference.HEIGHT, Reference.WIDTH));
        this.panelInterior.setBackground(new Color(0, 0, 0));

        this.removeAll();
        this.add(this.blank0);
        this.add(this.panelInterior);
        this.add(this.blank1);
        this.add(this.buttonRestart);
        this.add(this.labelBombs);
        this.add(this.buttonQuit);

        Font font_temp = new Font("monospace", Font.PLAIN, this.panelInterior.getWidth() / Reference.WIDTH / 2);


        for(int j=0; j<Reference.HEIGHT; ++j) {
            for (int i=0; i<Reference.WIDTH; ++i) {

                Coord2 coord = new Coord2(i, j);
                this.cells.put(coord, new MineCell(this.cells, coord, false));
                JButton temp = new JButton();

                temp.setBackground(Reference.SCHEME.gridDefault);
                temp.setBorder(BorderFactory.createLineBorder(Reference.SCHEME.gridBorder, 1, false));
                temp.setFocusPainted(false);
                temp.setForeground(Reference.SCHEME.textNum);
                temp.setUI(new MetalButtonUI() {
                    @Override
                    protected Color getDisabledTextColor() {
                        return Reference.SCHEME.textNum;
                    }
                });
                temp.setFont(font_temp);

                temp.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseReleased(MouseEvent e) {
                        if(temp.isEnabled()) {
                            if (SwingUtilities.isRightMouseButton(e)) {

                                if (temp.getText() == "") {
                                    temp.setText("O");
                                    temp.setForeground(Reference.SCHEME.textGuess);
                                    PanelGame.this.bombsRemaining -= 1;
                                } else if (temp.getText() == "O") {
                                    temp.setText("");
                                    temp.setForeground(Reference.SCHEME.textNum);
                                    PanelGame.this.bombsRemaining += 1;
                                }

                                PanelGame.this.labelBombs.setText(String.valueOf(PanelGame.this.bombsRemaining));

                            } else if (SwingUtilities.isLeftMouseButton(e) && !temp.getText().equals("O")) {
                                MineCell cell = PanelGame.this.cells.get(coord);
                                if (cell.isBomb) {
                                    PanelGame.this.loose(coord);
                                } else {

                                    if (cell.calcAdjBombs() > 0) {

                                        cell.ex = true;
                                        PanelGame.this.refreshButtons();

                                    } else if (cell.calcAdjBombs() == 0) {

                                        cell.update();
                                        PanelGame.this.refreshButtons();

                                    }

                                }
                            }
                        }
                        else {

                            if(SwingUtilities.isMiddleMouseButton(e)) {

                                MineCell cell = PanelGame.this.cells.get(coord);
                                ArrayList<MineCell> adjs = cell.getAdjacents();

                                boolean flag = false;
                                int count = 0;

                                for(MineCell other : adjs) {

                                    if(PanelGame.this.buttonCells.get(other.coord).getText() == "O") {
                                        count++;
                                    }
                                }

                                if(count == cell.calcAdjBombs()) {

                                    for(MineCell other : adjs) {
                                        if(!PanelGame.this.buttonCells.get(other.coord).getText().equals("O")) {

                                            if(other.isBomb) {
                                                PanelGame.this.loose(other.coord);
                                                break;
                                            }
                                            else {
                                                other.update();
                                                if(PanelGame.this.refreshButtons())
                                                    break;
                                            }

                                        }

                                    }

                                }

                            }

                        }

                    }
                });

                this.buttonCells.put(coord, temp);
                this.panelInterior.add(temp);

            }
        }



        this.placeBombs();


    }

    public boolean refreshButtons() {

        boolean flag = true;

        for(int j=0; j<Reference.HEIGHT; ++j) {
            for (int i = 0; i < Reference.WIDTH; ++i) {

                MineCell cell = this.cells.get(new Coord2(i, j));
                JButton button = this.buttonCells.get(new Coord2(i, j));

                if(cell.ex) {

                    if(cell.calcAdjBombs() == 0) {
                        button.setBackground(Reference.SCHEME.gridZero);
                        button.setText("");
                        button.setEnabled(false);
                        button.setForeground(Reference.SCHEME.textNum);
                        button.setBorder(BorderFactory.createLineBorder(Reference.SCHEME.gridBorderEx, 1, false));
                    }
                    else if(cell.calcAdjBombs() > 0) {
                        button.setBackground(Reference.SCHEME.gridEx);
                        button.setText(String.valueOf(cell.calcAdjBombs()));
                        button.setEnabled(false);
                        button.setForeground(Reference.SCHEME.textNum);
                        button.setBorder(BorderFactory.createLineBorder(Reference.SCHEME.gridBorderEx, 1, false));
                    }

                }

                if(!cell.ex && !cell.isBomb)
                    flag = false;

            }
        }

        int bombs = Reference.BOMBS;

        for(int j=0; j<Reference.HEIGHT; ++j) {
            for (int i = 0; i < Reference.WIDTH; ++i) {

                MineCell cell = this.cells.get(new Coord2(i, j));
                JButton button = this.buttonCells.get(new Coord2(i, j));

                if(button.getText() == "O")
                    bombs -= 1;

            }
        }

        this.bombsRemaining = bombs;
        this.labelBombs.setText(String.valueOf(this.bombsRemaining));

        if(flag) {

            for(int j=0; j<Reference.HEIGHT; ++j) {
                for (int i = 0; i < Reference.WIDTH; ++i) {

                    MineCell cell = this.cells.get(new Coord2(i, j));
                    JButton button = this.buttonCells.get(new Coord2(i, j));

                    if(cell.isBomb) {
                        button.setText("x");
                        button.setForeground(Reference.SCHEME.textWonMine);
                    }
                    else if(cell.calcAdjBombs() > 0) {
                        button.setBackground(Reference.SCHEME.gridWon);
                    }
                    else if(cell.calcAdjBombs() == 0) {
                        button.setBackground(Reference.SCHEME.gridWonZero);
                    }
                    button.setBorder(BorderFactory.createLineBorder(Reference.SCHEME.gridBorder, 1, false));

                }

            }

        }

        return flag;

    }

    public void loose(Coord2 coord) {

        MineCell cell = this.cells.get(coord);

        if(cell.isBomb) {

            for(int j=0; j<Reference.HEIGHT; ++j) {
                for (int i = 0; i < Reference.WIDTH; ++i) {


                    Coord2 currCoord = new Coord2(i, j);
                    JButton button = this.buttonCells.get(currCoord);

                    button.setBackground(Reference.SCHEME.gridLost);
                    if(this.cells.get(currCoord).isBomb)
                        button.setText("x");
                    else if(this.cells.get(currCoord).calcAdjBombs() > 0)
                        button.setText(String.valueOf(this.cells.get(currCoord).calcAdjBombs()));
                    else
                        button.setText("");

                    button.setEnabled(false);


                }
            }

            this.buttonCells.get(coord).setBackground(Reference.SCHEME.gridLostHighlight);
            this.buttonCells.get(coord).setText("x");
            this.buttonCells.get(coord).setUI(new MetalButtonUI() {
                @Override
                protected Color getDisabledTextColor() {
                    return Reference.SCHEME.textMine;
                }
            });
        }

    }

    public void placeBombs() {

        List<Coord2> copy = new ArrayList<>(this.cells.keySet());
        Collections.shuffle(copy);

        for(Coord2 coord : copy.subList(0, Reference.BOMBS)) {

            this.cells.get(coord).isBomb = true;

        }

    }

    public void refreshFont() {

        Font font_temp = new Font("monospace", Font.PLAIN, this.panelInterior.getWidth() / Reference.WIDTH / 2);
        for(int j=0; j<Reference.HEIGHT; ++j) {
            for (int i = 0; i < Reference.WIDTH; ++i) {

                this.buttonCells.get(new Coord2(i, j)).setFont(font_temp);
            }
        }

    }



}

