package com.fireslug.gameutils;

import java.awt.*;

public class ColorScheme {

    public Color panelBackground;
    public Color buttonBackground;
    public Color buttonRestartBackground;
    public Color gridBorder;
    public Color gridBorderEx;
    public Color gridDefault;
    public Color gridZero;
    public Color gridEx;
    public Color gridLost;
    public Color gridLostHighlight;
    public Color gridWon;
    public Color gridWonZero;
    public Color textWonMine;
    public Color textMine;
    public Color textNum;
    public Color textGuess;


    public static ColorScheme scheme1 = new ColorScheme();
    public static ColorScheme scheme2 = new ColorScheme();

    static {
        initSchemes();
    }

    public ColorScheme() {

    }

    public static void initSchemes() {

        scheme1.panelBackground = new Color(0, 0, 0);
        scheme1.buttonBackground = new Color(192, 91, 135);
        scheme1.buttonRestartBackground = new Color(67, 169, 170);
        scheme1.gridBorder = new Color(128, 128, 128);
        scheme1.gridBorderEx = new Color(128, 128, 128);
        scheme1.gridDefault = new Color(192, 192, 192);
        scheme1.gridZero = new Color(25, 51, 14);
        scheme1.gridEx = new Color(40, 88, 21);
        scheme1.gridLost = new Color(95, 24, 70);
        scheme1.gridLostHighlight = new Color(255, 0, 0);
        scheme1.gridWon = new Color(66, 114, 151);
        scheme1.gridWonZero = new Color(48, 69, 96);
        scheme1.textWonMine = new Color(98, 187, 175);
        scheme1.textMine = new Color(255, 255, 255);
        scheme1.textNum = new Color(255, 255, 255);
        scheme1.textGuess = new Color(161, 81, 41);

        scheme2.panelBackground = new Color(0, 0, 0);
        scheme2.buttonBackground = new Color(136, 39, 156);
        scheme2.buttonRestartBackground = new Color(58, 39, 172);
        scheme2.gridBorder = new Color(168, 106, 46);
        scheme2.gridBorderEx = new Color(64, 70, 88);
        scheme2.gridDefault = new Color(0, 0, 0);
        scheme2.gridZero = new Color(46, 116, 117);
        scheme2.gridEx = new Color(51, 129, 130);
        scheme2.gridLost = new Color(53, 22, 56);
        scheme2.gridLostHighlight = new Color(134, 173, 96);
        scheme2.gridWon = new Color(70, 171, 36);
        scheme2.gridWonZero = new Color(57, 141, 30);
        scheme2.textWonMine = new Color(187, 145, 39);
        scheme2.textMine = new Color(0, 0, 0);
        scheme2.textNum = new Color(0, 0, 0);
        scheme2.textGuess = new Color(214, 27, 243);

    }



}
