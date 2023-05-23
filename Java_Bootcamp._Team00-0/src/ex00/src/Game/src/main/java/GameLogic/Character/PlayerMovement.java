package GameLogic.Character;

import GameLogic.DataBase.Data;
import GameLogic.Main.GameManager;
import edu.school21.game.logic.Interfaces.Objects;

import java.io.FileNotFoundException;

public class PlayerMovement {
    private Data data;
    private GameManager gameManager;

    public PlayerMovement(Data data, GameManager gameManager) {
        this.data = data;
        this.gameManager = gameManager;
    }

    public void moveForward(int[][] map) throws FileNotFoundException {
        int x = data.getPlayerCoordinates()[0];
        int y = data.getPlayerCoordinates()[1];
        if (x == 0) {
            if (map[data.getHeight() - 1][y] == Objects.WALLS.getValue()) {
            } else {
                map[x][y] = 0;
                x = data.getHeight() - 1;
                gameManager.checkGameStatus(map, x, y);
            }
        } else if (map[x - 1][y] == Objects.WALLS.getValue()) {
            gameManager.checkGameStatus(map, x, y);
        } else {
            map[x][y] = 0;
            x -= 1;
            gameManager.checkGameStatus(map, x, y);
        }
    }

    public void moveBack(int[][] map) throws FileNotFoundException {
        int x = data.getPlayerCoordinates()[0];
        int y = data.getPlayerCoordinates()[1];
        if (x == data.getHeight() - 1) {
            if (map[0][y] == Objects.WALLS.getValue()) {
            } else {
                map[x][y] = 0;
                x = 0;
                gameManager.checkGameStatus(map, x, y);
            }
        } else if (map[x + 1][y] == Objects.WALLS.getValue()) {
            gameManager.checkGameStatus(map, x, y);
        } else {
            map[x][y] = 0;
            x += 1;
            gameManager.checkGameStatus(map, x, y);
        }
    }

    public void moveLeft(int[][] map) throws FileNotFoundException {
        int x = data.getPlayerCoordinates()[0];
        int y = data.getPlayerCoordinates()[1];
        if (y == 0) {
            if (map[x][data.getWeight() - 1] == Objects.WALLS.getValue()) {
            } else {
                map[x][y] = 0;
                y = data.getWeight() - 1;
                gameManager.checkGameStatus(map, x, y);
            }
        } else if (map[x][y-1] == Objects.WALLS.getValue()) {
            gameManager.checkGameStatus(map, x, y);
        } else {
            map[x][y] = 0;
            y -= 1;
            gameManager.checkGameStatus(map, x, y);
        }
    }

    public void moveRight(int[][] map) throws FileNotFoundException {
        int x = data.getPlayerCoordinates()[0];
        int y = data.getPlayerCoordinates()[1];
        if (y == data.getWeight() - 1) {
            if (map[x][0] == Objects.WALLS.getValue()) {
            } else {
                map[x][y] = 0;
                y = 0;
                gameManager.checkGameStatus(map, x, y);
            }
        } else if (map[x][y+1] == Objects.WALLS.getValue()) {
            gameManager.checkGameStatus(map, x, y);
        } else {
            map[x][y] = 0;
            y += 1;
            gameManager.checkGameStatus(map, x, y);
        }
    }
}
