package GameLogic.Main;

import GameLogic.Character.PlayerMovement;
import GameLogic.DataBase.Args;
import GameLogic.DataBase.Data;
import GameLogic.DataBase.ParseProperties;
import GameLogic.Map.MapGenerator;
import GameLogic.Map.MapPainter;
import edu.school21.game.logic.ChaseLogic.ChaseLogic;
import edu.school21.game.logic.Interfaces.Objects;

import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.util.Scanner;

public class GameManager {
    private static Scanner scanner;
    private Data data;
    private PlayerMovement playerMovement;
    private MapPainter mapPainter;
    private ChaseLogic chaseLogic;
    private int[][] map;
    private Path pathProperties;
    private Args jargs;
    private static boolean isRendered = false;
    private static boolean isDev = false;
    public GameManager(Path pathProperties, Args jargs) {
        this.pathProperties = pathProperties;
        this.jargs = jargs;
        scanner = new Scanner(System.in);
    }
    public void startGame() throws FileNotFoundException {
        if(!isRendered) {
            mapRendering();
            isRendered = false;
        }
        mapPainter.paintMap(map);
        String command = new String("");
        while(true) {
            System.out.print("Enter the movement command -> ");
            command = scanner.nextLine();
            if(command.equals("9")) {
                System.out.println("Bye bye!!!!");
                System.exit(-1);
            }
            switch (command) {
                case "W":
                case "w":
                    playerMovement.moveForward(map);
                    break;
                case "S":
                case "s":
                    playerMovement.moveBack(map);
                    break;
                case "A":
                case "a":
                    playerMovement.moveLeft(map);
                    break;
                case "D":
                case "d":
                    playerMovement.moveRight(map);
                    break;
                default:
                    isRendered = true;
                    startGame();
            }
        }
    }

    private void mapRendering() throws FileNotFoundException {
        data = new Data(ParseProperties.parserHandler(pathProperties), jargs);
        isDev = data.getProfile().equals("dev");
        mapPainter = new MapPainter(data);
        playerMovement = new PlayerMovement(data, this);
        MapGenerator mapGenerator = new MapGenerator(data);
        map = mapGenerator.generateMap();
        chaseLogic = new ChaseLogic(map);
    }

    public void checkGameStatus(int[][] map, int x, int y) throws FileNotFoundException {
        if(map[x][y] == Objects.POINT.getValue()) {
            System.out.println("You have been win!!!!");
            System.out.print("You can retry the game, do you want try it again? Yes/No? -> ");
            String command = scanner.nextLine();
            if(command.equals("Yes")) {
                isRendered = false;
                startGame();
            } else {
                System.exit(-1);
            }
        } else {
            updateMap(map, x, y, Objects.PLAYER);
        }
        if(chaseLogic.getStatus()) {
            endGame();
        }
    }

    private void updateMap(int[][] map, int x, int y, Objects object) throws FileNotFoundException {
        checkPlayerAround(map, x, y);
        map[x][y] = object.getValue();
        data.setPlayerCoordinates(x, y);
        mapPainter.paintMap(map);
        if(isDev) {
            System.out.print("Enter 8 for accept enemy step or 9 for exit -> ");
            String command = scanner.nextLine();
            if(command.equals("8")) {
                chaseLogic.invokeEnemies(data.getEnemies(), data.getPlayerCoordinates(), data.getHeight(), data.getWeight());
                map = chaseLogic.getMap();
                mapPainter.paintMap(map);
            } else if(command.equals("9")) {
                System.exit(-1);
            } else {
                updateMap(map, x, y, object);
            }
        } else {
            System.out.println();
            chaseLogic.invokeEnemies(data.getEnemies(), data.getPlayerCoordinates(), data.getHeight(), data.getWeight());
            map = chaseLogic.getMap();
            mapPainter.paintMap(map);
        }
    }

    private void checkPlayerAround(int[][] map, int x, int y) throws FileNotFoundException {
         if(map[x][y] == Objects.ENEMY.getValue()) {
            endGame();
        }
    }

    private void endGame() throws FileNotFoundException {
        System.out.println("You have been died!");
        System.out.print("You can retry the game, do you want try it again? Yes/No? -> ");
        String command = scanner.nextLine();
        if(command.equals("Yes")) {
            isRendered = false;
            startGame();
        } else {
            System.exit(-1);
        }
    }
}
