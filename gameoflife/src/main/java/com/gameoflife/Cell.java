package com.gameoflife;

import com.gameoflife.utils.Constants.State;

public class Cell {
    protected State state;
    protected float i,j; // position dans le tableau

    public Cell(State state, float i, float j) {
        this.state = state;
        this.i = i;
        this.j = j;
    }
}
