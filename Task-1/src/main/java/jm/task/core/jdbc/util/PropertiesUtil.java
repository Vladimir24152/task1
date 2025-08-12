package jm.task.core.jdbc.util;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public final class PropertiesUtil {

    private static final Properties PROPERTIES = new Properties();

    private PropertiesUtil() {
    }

    static {
        loadProperties();
    }

    private static void loadProperties() {

        try {
            PROPERTIES.load(new FileReader("src\\main\\resources\\application.properties"));
            if (PROPERTIES.isEmpty()){
                throw new IllegalStateException("Указанный properties файл пустой");
            }
        } catch (IOException e) {
            throw new IllegalStateException("Отсутствует указанный properties файл");
        }
    }

    public static String getProperty(String key) {
        return PROPERTIES.getProperty(key);
    }
}
