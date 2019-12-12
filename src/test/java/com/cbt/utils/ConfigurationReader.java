package com.cbt.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class ConfigurationReader {

    private static Properties configFile;

    static {
        try {   /*  Provides access to a file   */
            FileInputStream input = new FileInputStream("configuration.properties");
            /*  Initialize properties object    */
            configFile = new Properties();
            /*  Load configuration.properties file  */
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
