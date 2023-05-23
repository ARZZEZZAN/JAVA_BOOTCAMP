package edu.school21.game.logic.Character;

public class Enemy {

    private int[] coordinates;
    public Enemy(int[] playerCoordinates) {
        coordinates = new int[2];
    }

    public int[] getCoordinates() {
        return coordinates;
    }


    public void setCoordinates(int x, int y) {
        coordinates[0] = x;
        coordinates[1] = y;
    }
}
