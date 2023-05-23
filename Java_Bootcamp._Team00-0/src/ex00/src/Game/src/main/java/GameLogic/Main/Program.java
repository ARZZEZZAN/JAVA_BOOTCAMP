package GameLogic.Main;

import GameLogic.DataBase.Args;
import com.beust.jcommander.JCommander;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class Program {

    public static void main(String[] args) {
        try {
            Args jargs = new Args();
            JCommander jCommander = new JCommander(jargs);
            jCommander.parse(args);
            String propertiesFileName = "application-" + jargs.getProfile() + ".properties";
            InputStream inputStream = Program.class.getClassLoader().getResourceAsStream(propertiesFileName);
            if (inputStream == null) {
                throw new FileNotFoundException("Properties file not found: " + propertiesFileName);
            }

            Path tempFile = Files.createTempFile("temp", ".properties");
            Files.copy(inputStream, tempFile, StandardCopyOption.REPLACE_EXISTING);

            GameManager gameManager = new GameManager(tempFile, jargs);
            gameManager.startGame();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
}