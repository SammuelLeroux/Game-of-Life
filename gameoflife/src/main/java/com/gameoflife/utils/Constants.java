package com.gameoflife.utils;

public class Constants {
    public static enum State { ALIVE, DEAD };

    public static final int CELL_WIDTH = 20;

    public static int calcGridWidth(int nbCells) {
        int width = CELL_WIDTH * nbCells;
        return width;
    }
}
