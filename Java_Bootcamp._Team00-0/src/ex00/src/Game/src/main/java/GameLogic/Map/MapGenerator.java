package GameLogic.Map;

import GameLogic.DataBase.Data;
import edu.school21.game.logic.Character.Enemy;
import edu.school21.game.logic.Interfaces.Objects;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
class IllegalParametersException extends RuntimeException{}
public class MapGenerator {
    private static Data data;
    private static int weight;
    private static int height;
    public MapGenerator(Data data) {
        MapGenerator.data = data;
        weight = data.getWeight();
        height = data.getHeight();
    }
    public int[][] generateMap() {
        int i = 0;
        AtomicInteger randomX = new AtomicInteger(0);
        AtomicInteger randomY = new AtomicInteger(0);
        int[][] map = new int[height][weight];
        ArrayList<Integer> coordinateX = new ArrayList<>(Collections.nCopies(height, 0));
        ArrayList<Integer> coordinateY = new ArrayList<>(Collections.nCopies(weight, 0));
        if((data.getEnemiesCount() + data.getWallsCount()) > (data.getHeight() * data.getWeight())) {
            throw new IllegalParametersException();
        }
        for(i = 0; i < data.getEnemiesCount(); i++) {
            generateCoordinates(randomX, randomY);
            checkCoordinates(randomX, randomY, coordinateX, coordinateY, i);
            addEnemy(data, randomX, randomY);
            map[randomY.get()][randomX.get()] = Objects.ENEMY.getValue();
        }
        int wallsCount = data.getWallsCount() + i;
        for(; i < wallsCount; i++) {
            generateCoordinates(randomX, randomY);
            checkCoordinates(randomX, randomY, coordinateX, coordinateY, i);
            map[randomY.get()][randomX.get()] = Objects.WALLS.getValue();
        }
        generateCoordinates(randomX, randomY);
        checkCoordinates(randomX, randomY, coordinateX, coordinateY, i);
        map[randomY.get()][randomX.get()] = Objects.PLAYER.getValue();
        data.setPlayerCoordinates(randomY.get(), randomX.get());
        generateCoordinates(randomX, randomY);
        checkCoordinates(randomX, randomY, coordinateX, coordinateY, i);
        map[randomY.get()][randomX.get()] = Objects.POINT.getValue();
        return map;
    }

    private static void addEnemy(Data data, AtomicInteger randomX, AtomicInteger randomY) {
        Enemy enemy = new Enemy(data.getPlayerCoordinates());
        enemy.setCoordinates(randomY.get(), randomX.get());
        data.setEnemies(enemy);
    }

    private static void checkCoordinates(AtomicInteger randomX, AtomicInteger randomY, List<Integer> coordinateX, List<Integer> coordinateY, int index) {
        while(coordinateX.get(index) == randomX.get() && coordinateY.get(index) == randomY.get()) {
            generateCoordinates(randomX, randomY);
        }
        addToCoordinates(randomX, randomY, coordinateX, coordinateY, index);
    }

    private static void addToCoordinates(AtomicInteger randomX, AtomicInteger randomY, List<Integer> coordinateX, List<Integer> coordinateY, int index) {
        coordinateX.add(index, randomX.get());
        coordinateY.add(index, randomY.get());
    }

    private static void generateCoordinates(AtomicInteger randomX, AtomicInteger randomY) {
        randomX.set((int) (Math.random()  * weight));
        randomY.set((int) (Math.random()  * height));
    }
}
