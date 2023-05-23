package GameLogic.Character;

import GameLogic.DataBase.Data;

public class Player {
    private int[] coordinates;
    private Data data;
    public Player(Data data) {
        this.data = data;
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