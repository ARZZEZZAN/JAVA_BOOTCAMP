package edu.school21.chat.Main;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public final class Application {
    private static final Properties PROPERTIES = new Properties();
    private Application() {

    }
    static {
        try {
            loadProperties();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private static void loadProperties() throws IOException {
        try (var inputStream = new FileInputStream("Chat/src/main/resources/application.properties")) {
            PROPERTIES.load(inputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static String get(String key) {
        return PROPERTIES.getProperty(key);
    }
}