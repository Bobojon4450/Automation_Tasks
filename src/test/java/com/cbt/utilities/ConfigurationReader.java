package com.cbt.utilities;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class ConfigurationReader {

    private static Properties configFile;

    static {
        try {
            FileInputStream input = new FileInputStream("configuration.properties");
            configFile = new Properties();
            configFile.load(input);
            input.close();
        } catch (FileNotFoundException e) {
            System.out.println("Failed to load properties file");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getProperty(String key) {
        return configFile.getProperty(key);
    }
}
