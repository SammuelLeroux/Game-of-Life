package com.gameoflife;

import java.util.Random;

public class Grid {
    public int[][] grid;
    protected int gridDimension;
    protected int nbInitLivingCells;
    protected int nbLivingCells;

    public Grid(String gridDimension) {
        this.gridDimension = Integer.parseInt(gridDimension);
        init();
    }
    public Grid(int gridDimension) {
        this.gridDimension = gridDimension;
        init();
    }
    public Grid(int gridDimension, boolean init) {
        this.gridDimension = gridDimension;
        if (init) initEmpty();
        else init();
    }

    public int getDimension() {
        return this.gridDimension;
    }

    public void init() {
        this.grid = new int[this.gridDimension][this.gridDimension];

        this.nbInitLivingCells = (int) Math.round(this.gridDimension * this.gridDimension / 3.0);
        this.nbLivingCells = this.nbInitLivingCells;

        // on genere n*0.3 cellules vivantes aléatoirement dans un tableau
        int[][] livingCellsPos = generateLivingCells();

        // on met des cellules mortes partout
        for (int i = 0; i < this.gridDimension; i++) {
            for (int j = 0; j < this.gridDimension; j++) {
                setCell(i, j, 0);
            }
        }

        // on met en place les cellules vivantes
        for (int i = 0; i < this.nbInitLivingCells; i++) {
            int x = livingCellsPos[i][0];
            int y = livingCellsPos[i][1];
            setCell(x, y, 1);
        }
    }

    public void initEmpty() {
        this.grid = new int[this.gridDimension][this.gridDimension];

        // on met des cellules mortes partout
        for (int i = 0; i < this.gridDimension; i++) {
            for (int j = 0; j < this.gridDimension; j++) {
                setCell(i, j, 0);
            }
        }
    }

    private int[][] generateLivingCells() {
        Random rand = new Random();
        int[][] livingCellsPos = new int[this.nbInitLivingCells][2];

        for (int i = 0; i < this.nbInitLivingCells; i++) {
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

    private void countLivingCells() {
        int count = 0;
        for (int i = 0; i < this.gridDimension; i++) {
            for (int j = 0; j < this.gridDimension; j++) {
                if (this.grid[i][j] == 1) count++;
            }
        }
        this.nbLivingCells = count;
    }

    public int getNbLivingCells()  {
        countLivingCells();
        return this.nbLivingCells;
    }
}
