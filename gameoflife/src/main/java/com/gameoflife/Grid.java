package com.gameoflife;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Random;

public class Grid {
    public int[][] grid;
    protected int gridDimension;

    public Grid(String gridDimension) {
        this.gridDimension = Integer.parseInt(gridDimension);
        init();
    }
    public Grid(int gridDimension) {
        this.gridDimension = gridDimension;
        init();
    }

    public int getDimension() {
        return this.gridDimension;
    }

    public void init() {
        this.grid = new int[this.gridDimension][this.gridDimension];

        int nbCellulesVivantes = (int) Math.round(this.gridDimension / 3.0);

        System.out.println(nbCellulesVivantes);

        // on genere n*0.3 cellules vivantes aléatoirement dans un tableau
        int[][] livingCellsPos = generateLivingCells(nbCellulesVivantes);

        // on met des cellules mortes partout
        for (int i = 0; i < this.gridDimension; i++) {
            for (int j = 0; j < this.gridDimension; j++) {
                setCell(i, j, 0);
            }
        }

        // on met en place les cellules vivantes
        for (int i = 0; i < nbCellulesVivantes; i++) {
            int x = livingCellsPos[i][0];
            int y = livingCellsPos[i][1];
            setCell(x, y, 1);
        }
    }

    private int[][] generateLivingCells(int nbCellulesVivantes) {
        Random rand = new Random();
        int[][] livingCellsPos = new int[nbCellulesVivantes][2];

        for (int i = 0; i < nbCellulesVivantes; i++) {
            int x = rand.nextInt(this.gridDimension);
            int y = rand.nextInt(this.gridDimension);
            
            livingCellsPos[i][0] = x;
            livingCellsPos[i][1] = y;
        }
        return livingCellsPos;
    }

    public int getCell(int x, int y) {
        if (x >= this.gridDimension || x < 0) return 0;

        if (y >= this.gridDimension || y < 0) return 0;

        return this.grid[x][y];
    }

    public void setCell(int x, int y, int value) {
        this.grid[x][y] = value;
    }

    public void getNearCell(int i, int j) {
        // renvoie les cellules voisines
    }
}
