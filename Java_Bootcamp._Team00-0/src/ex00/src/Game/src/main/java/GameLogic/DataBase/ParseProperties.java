package GameLogic.DataBase;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ParseProperties {
    public static Map<String, String> parserHandler(Path pathProperties) throws FileNotFoundException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(pathProperties.toFile()));
        List<String> lines = bufferedReader.lines().toList();
        Map<String, String> properties = new HashMap<>(lines.size());
        for(String line : lines) {
            String[] strings = line.split("\\s+");
            if(strings.length != 3) {
                properties.put(strings[0], " ");
            } else {
                properties.put(strings[0], strings[2]);
            }
        }
        return properties;
    }
}
