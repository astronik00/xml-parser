package utils;

import models.MyRuntimeException;

import java.io.File;
import java.net.URL;
import java.util.Objects;

public class FileManager {
    public static File getResourceFile(String name) {
        try {
            URL path = FileManager.class.getResource("/" + name);
            return new File(path.getFile());
        } catch (NullPointerException e) {
            throw new MyRuntimeException("2", "File " + name + " not found");
        }
    }

    public static String getAbsoluteFilePath(String name) {
        File file = getResourceFile(name);
        return file.getAbsolutePath();
    }
}
