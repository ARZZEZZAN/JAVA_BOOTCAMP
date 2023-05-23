package edu.school21.game.logic.ChaseLogic;

import edu.school21.game.logic.Character.Enemy;
import edu.school21.game.logic.Character.EnemyMovement;
import edu.school21.game.logic.Interfaces.Objects;

import java.util.List;

public class ChaseLogic {
    private int[][] map;
    private EnemyMovement enemyMovement;
    private boolean isCaught = false;
    private boolean step = false;
    private int[] playerCoordinates;

    public ChaseLogic(int[][] map) {
        this.map = map;
        enemyMovement = new EnemyMovement(map, this);
    }
    public void invokeEnemies(List<Enemy> enemies, int[] playerCoordinates, int height, int weight) {
        this.playerCoordinates = playerCoordinates;
        int playerX = playerCoordinates[0];
        int playerY = playerCoordinates[1];
        for(Enemy enemy : enemies) {
            step = Math.random() < 0.3;
            int enemyX = enemy.getCoordinates()[0];
            int enemyY = enemy.getCoordinates()[1];
            moveValidation(height, weight, playerX, playerY, enemy, enemyX, enemyY);
        }

    }

    private void moveValidation(int height, int weight, int playerX, int playerY, Enemy enemy, int enemyX, int enemyY) {
        if (enemyY + 1 == playerY && enemyX == playerX) {
            enemyMovement.moveRight(enemy, weight);
            isCaught = true;
        } else if (enemyY - 1 == playerY && enemyX == playerX) {
            enemyMovement.moveLeft(enemy, weight);
            isCaught = true;
        } else if (enemyX + 1 == playerX && enemyY == playerY) {
            enemyMovement.moveForward(enemy, height);
            isCaught = true;
        } else if (enemyX - 1 == playerX && enemyY == playerY) {
            enemyMovement.moveBack(enemy, height);
            isCaught = true;
        } else {
            if (!step) {
                if (enemyY < playerY) {
                    int dif = playerY - enemyY;
                    if ((weight - dif) > dif) {
                        enemyMovement.moveRight(enemy, weight);
                    } else {
                        enemyMovement.moveLeft(enemy, weight);
                    }
                } else if (enemyY > playerY) {
                    int dif = enemyY - playerY;
                    if ((weight - dif) > dif) {
                        enemyMovement.moveLeft(enemy, weight);
                    } else {
                        enemyMovement.moveRight(enemy, weight);
                    }
                }
            } else {
                if (enemyX < playerX) {
                    int dif = playerX - enemyX;
                    if ((weight - dif) > dif) {
                        enemyMovement.moveBack(enemy, height);
                    } else {
                        enemyMovement.moveForward(enemy, height);
                    }
                } else if (enemyX > playerX) {
                    int dif = enemyX - playerX;
                    if ((weight - dif) > dif) {
                        enemyMovement.moveForward(enemy, height);
                    } else {
                        enemyMovement.moveBack(enemy, height);
                    }
                }

            }
        }
    }

    public int[][] updateEnemyCoordinates(int[][] map, int x, int y, Objects object, Enemy enemy) {
        map[x][y] = object.getValue();
        enemy.setCoordinates(x, y);
        return map;
    }
    public void checkStatus(int x, int y) {
        if (x == playerCoordinates[0] && y == playerCoordinates[1]) {
            isCaught = true;
        }
    }
    public int[][] getMap() {
        return map;
    }
    public boolean getStatus() {
        return isCaught;
    }
}