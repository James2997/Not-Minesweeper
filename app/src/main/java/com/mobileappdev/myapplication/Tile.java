package com.mobileappdev.myapplication;

public class Tile {
    public static final int Mine = -1;
    public static final int BLANK = 0;

    private boolean isRevealed;
    private boolean isFlagged;
    private int value;

    public Tile(int value){
        this.value = value;
        this.isRevealed = false;
        this.isFlagged = false;
    }

    public boolean isRevealed() {
        return isRevealed;
    }

    public boolean isHidden() {
        return !isRevealed;
    }

    public void setRevealed(boolean revealed) {
        isRevealed = revealed;
    }

    public boolean isFlagged() {
        return isFlagged;
    }

    public void setFlagged(boolean flagged) {
        isFlagged = flagged;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
