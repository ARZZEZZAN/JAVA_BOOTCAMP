package GameLogic.DataBase;

import GameLogic.Character.Player;
import edu.school21.game.logic.Character.Enemy;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Data {
    private Map<String, String> properties;
    private Player player;
    private Args jargs;
    private List<Enemy> enemies;
    public Data(Map<String, String> properties, Args jargs) {
        this.properties = properties;
        this.jargs = jargs;
        player = new Player(this);
        enemies = new LinkedList<>();
    }
    public String getEnemyChar() {
        return properties.get("enemy.char");
    }
    public String getPlayerChar() {
        return properties.get("player.char");
    }
    public String getWallChar() {
        return properties.get("wall.char");
    }
    public String getGoalChar() {
        return properties.get("goal.char");
    }
    public String getEmptyChar() {
        return properties.get("empty.char");
    }
    public String getEnemyColor() {
        return properties.get("enemy.color");
    }
    public String getPlayerColor() {
        return properties.get("player.color");
    }
    public String getWallColor() {
        return properties.get("wall.color");
    }
    public String getGoalColor() {
        return properties.get("goal.color");
    }
    public String getEmptyColor() {
        return properties.get("empty.color");
    }
    public int getSizeMap() {
        return jargs.getSize();
    }
    public int getWallsCount() {
        return jargs.getWallsCount();
    }
    public int getEnemiesCount() {
        return jargs.getEnemiesCount();
    }
    public String getProfile() {
        return jargs.getProfile();
    }
    public int getWeight() {
        return getSizeMap() * 60 / 100;
    }
    public int getHeight() {
        return getSizeMap() - getWeight();
    }
    public Player getPlayer() {
        return player;
    }
    public int[] getPlayerCoordinates() {
        return player.getCoordinates();
    }
    public int getEnemyCoordinates(Enemy enemy) {
        return 0;
    }
    public void setPlayerCoordinates(int x, int y) {
        player.setCoordinates(x, y);
    }
    public List<Enemy> getEnemies() {
        return enemies;
    }
    public void setEnemies(Enemy enemy) {
        enemies.add(enemy);
    }
}
