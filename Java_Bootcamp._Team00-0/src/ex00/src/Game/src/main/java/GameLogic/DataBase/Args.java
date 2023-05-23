package GameLogic.DataBase;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

@Parameters(separators = "=")
public class Args {
    @Parameter(names = "--enemiesCount")
    private int enemiesCount;
    @Parameter(names = "--wallsCount")
    private int wallsCount;
    @Parameter(names = "--size")
    private int size;
    @Parameter(names = "--profile")
    private String profile;

    public int getEnemiesCount() {
        return enemiesCount;
    }

    public int getWallsCount() {
        return wallsCount;
    }

    public int getSize() {
        return size;
    }

    public String getProfile() {
        return profile;
    }

    @Override
    public String toString() {
        return "--enemiesCount=" + enemiesCount +
                " --wallsCount=" + wallsCount +
                " --size=" + size +
                " --profile=" + profile;
    }
}
