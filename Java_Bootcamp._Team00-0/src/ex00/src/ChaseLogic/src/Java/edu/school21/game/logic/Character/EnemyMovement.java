package edu.school21.game.logic.Character;

import edu.school21.game.logic.ChaseLogic.ChaseLogic;
import edu.school21.game.logic.Interfaces.Objects;

public class EnemyMovement {
    private int[][] map;
    private ChaseLogic chaseLogic;
    public EnemyMovement(int[][] map, ChaseLogic chaseLogic) {
        this.map = map;
        this.chaseLogic = chaseLogic;
    }
    public void moveForward(Enemy enemy, int height) {
        int x = enemy.getCoordinates()[0];
        int y = enemy.getCoordinates()[1];
        if (x == 0) {
            if (map[height - 1][y] == Objects.WALLS.getValue()
                    || map[height - 1][y] == Objects.POINT.getValue()
                    || map[height - 1][y] == Objects.ENEMY.getValue()) {
            } else {
                chaseLogic.checkStatus(x, y);
                map[x][y] = 0;
                x = height - 1;
                chaseLogic.checkStatus(x, y);
                chaseLogic.updateEnemyCoordinates(map, x, y, Objects.ENEMY, enemy);
            }
        } else if (map[x - 1][y] == Objects.WALLS.getValue()
                || map[x - 1][y] == Objects.POINT.getValue()
                || map[x - 1][y] == Objects.ENEMY.getValue()) {
            chaseLogic.checkStatus(x, y);
            chaseLogic.updateEnemyCoordinates(map, x, y, Objects.ENEMY, enemy);
        } else {
            chaseLogic.checkStatus(x, y);
            map[x][y] = 0;
            x -= 1;
            chaseLogic.checkStatus(x, y);
            chaseLogic.updateEnemyCoordinates(map, x, y, Objects.ENEMY, enemy);
        }
    }
    public void moveBack(Enemy enemy, int height) {
        int x = enemy.getCoordinates()[0];
        int y = enemy.getCoordinates()[1];
        if (x == height - 1) {
            if (map[0][y] == Objects.WALLS.getValue()
                    || map[0][y] == Objects.POINT.getValue()
                    || map[0][y] == Objects.ENEMY.getValue()) {
            } else {
                chaseLogic.checkStatus(x, y);
                map[x][y] = 0;
                x = 0;
                chaseLogic.checkStatus(x, y);
                chaseLogic.updateEnemyCoordinates(map, x, y, Objects.ENEMY, enemy);
            }
        } else if (map[x + 1][y] == Objects.WALLS.getValue()
                || map[x + 1][y] == Objects.POINT.getValue()
                || map[x + 1][y] == Objects.ENEMY.getValue()) {
            chaseLogic.checkStatus(x, y);
            chaseLogic.updateEnemyCoordinates(map, x, y, Objects.ENEMY, enemy);
        } else {
            chaseLogic.checkStatus(x, y);
            map[x][y] = 0;
            x += 1;
            chaseLogic.checkStatus(x, y);
            chaseLogic.updateEnemyCoordinates(map, x, y, Objects.ENEMY, enemy);
        }
    }

    public void moveLeft(Enemy enemy, int weight) {
        int x = enemy.getCoordinates()[0];
        int y = enemy.getCoordinates()[1];
        if (y == 0) {
            if (map[x][weight - 1] == Objects.WALLS.getValue()
                    || map[x][weight - 1] == Objects.POINT.getValue()
                    || map[x][weight - 1] == Objects.ENEMY.getValue()) {
            } else {
                chaseLogic.checkStatus(x, y);
                map[x][y] = 0;
                y = weight - 1;
                chaseLogic.checkStatus(x, y);
                chaseLogic.updateEnemyCoordinates(map, x, y, Objects.ENEMY, enemy);
            }
        } else if (map[x][y-1] == Objects.WALLS.getValue()
                || map[x][y-1] == Objects.POINT.getValue()
                || map[x][y-1] == Objects.ENEMY.getValue()) {
            chaseLogic.checkStatus(x, y);
            chaseLogic.updateEnemyCoordinates(map, x, y, Objects.ENEMY, enemy);
        } else {
            chaseLogic.checkStatus(x, y);
            map[x][y] = 0;
            y -= 1;
            chaseLogic.checkStatus(x, y);
            chaseLogic.updateEnemyCoordinates(map, x, y, Objects.ENEMY, enemy);
        }
    }
    public void moveRight(Enemy enemy, int weight) {
        int x = enemy.getCoordinates()[0];
        int y = enemy.getCoordinates()[1];
        if (y == weight - 1) {
            if (map[x][0] == Objects.WALLS.getValue()
                    || map[x][0] == Objects.POINT.getValue()
                    || map[x][0] == Objects.ENEMY.getValue()) {
            } else {
                chaseLogic.checkStatus(x, y);
                map[x][y] = 0;
                y = 0;
                chaseLogic.checkStatus(x, y);
                chaseLogic.updateEnemyCoordinates(map, x, y, Objects.ENEMY, enemy);
            }
        } else if (map[x][y+1] == Objects.WALLS.getValue()
                || map[x][y+1] == Objects.POINT.getValue()
                || map[x][y+1] == Objects.ENEMY.getValue()) {
            chaseLogic.checkStatus(x, y);
            chaseLogic.updateEnemyCoordinates(map, x, y, Objects.ENEMY, enemy);
        } else {
            chaseLogic.checkStatus(x, y);
            map[x][y] = 0;
            y += 1;
            chaseLogic.checkStatus(x, y);
            chaseLogic.updateEnemyCoordinates(map, x, y, Objects.ENEMY, enemy);
        }
    }
}

