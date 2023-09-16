package utils;

import java.io.File;
import java.net.URL;
import java.util.Objects;

public class FileManager {
    public static File getResourceFile(String name) {
        try {
            URL path = FileManager.class.getResource("/" + name);
            return new File(path.getFile());
        } catch (Exception e) {
            System.out.println("error occurred during reading file");
            return null;
        }
    }

    public static String getAbsoluteFilePath(String name) {
        File file = getResourceFile(name);
        return Objects.requireNonNull(file).getAbsolutePath();
    }
}
