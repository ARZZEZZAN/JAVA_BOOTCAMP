package edu.school21.game.logic.Interfaces;

public enum Objects {
    ENEMY, PLAYER, WALLS, POINT;

    private final int value;

    Objects() {
        this.value = ordinal() + 1;
    }

    public int getValue() {
        return value;
    }
}
