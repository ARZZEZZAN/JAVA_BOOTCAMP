package GameLogic.Map;

import com.diogonunes.jcdp.color.ColoredPrinter;
import com.diogonunes.jcdp.color.api.Ansi;
import GameLogic.DataBase.Data;
import edu.school21.game.logic.Interfaces.Objects;

public class MapPainter {
    private Data data;
    private MapGenerator mapGenerator;
    public MapPainter(Data data) {
        this.data = data;
    }
    public void paintMap(int[][] map) {
        System.out.print("\033[H\033[2J");
        System.out.flush();
        int weight = data.getWeight();
        int height = data.getHeight();
        ColoredPrinter cp = new ColoredPrinter.Builder(1, false).background(Ansi.BColor.WHITE).build();
        for(int i = 0; i < height; i++) {
            for(int j = 0; j < weight; j++) {
                if (map[i][j] == Objects.ENEMY.getValue()) {
                    cp.print(data.getEnemyChar(), Ansi.Attribute.NONE, Ansi.FColor.NONE, Ansi.BColor.valueOf(data.getEnemyColor()));
                } else if(map[i][j] == Objects.WALLS.getValue()) {
                    cp.print(data.getWallChar(), Ansi.Attribute.NONE, Ansi.FColor.NONE, Ansi.BColor.valueOf(data.getWallColor()));
                } else if(map[i][j] == Objects.PLAYER.getValue()) {
                    cp.print(data.getPlayerChar(), Ansi.Attribute.NONE, Ansi.FColor.NONE, Ansi.BColor.valueOf(data.getPlayerColor()));
                } else if(map[i][j] == Objects.POINT.getValue()) {
                    cp.print(data.getGoalChar(), Ansi.Attribute.NONE, Ansi.FColor.NONE, Ansi.BColor.valueOf(data.getGoalColor()));
                } else {
                    cp.print(data.getEmptyChar(), Ansi.Attribute.NONE, Ansi.FColor.NONE, Ansi.BColor.valueOf(data.getEmptyColor()));
                }
            }
            System.out.println();
        }
    }

}
