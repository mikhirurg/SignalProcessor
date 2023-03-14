package io.github.mikhirurg.signalprocessor.util;

import io.github.mikhirurg.signalprocessor.gui.Oscilloscope;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

public class Application {
    private static final Properties appProperties;
    private static final ResourceBundle resourceBundle;
    private static final ResourceBundle resourceBundleUS;

    public enum Os {
        WIN, MAC, LINUX, OTHER
    }

    private static Os currentOs;

    static {
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream("app.properties"));
        } catch (IOException e) {
            try {
                properties.load(Oscilloscope.class.getClassLoader().getResourceAsStream("app.properties"));
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
        appProperties = properties;
        ResourceBundle resourceBundleTmp;
        try {
            resourceBundleTmp = PropertyResourceBundle.getBundle("AppBundle");
        } catch (MissingResourceException e) {
            resourceBundleTmp = PropertyResourceBundle.getBundle("AppBundle", Locale.forLanguageTag("en"));
        }
        resourceBundle = resourceBundleTmp;
        resourceBundleUS = PropertyResourceBundle.getBundle("AppBundle", Locale.forLanguageTag("en-us"));
    }

    public static String getString(String key) {
        return resourceBundle.getString(key);
    }

    public static String getUSString(String key) {
        return resourceBundleUS.getString(key);
    }

    public static String getProperty(String key) {
        return appProperties.getProperty(key);
    }

    public static Os getOS() {
        if (currentOs == null) {
            String osStr = System.getProperty("os.name", "generic").toLowerCase(Locale.ENGLISH);
            if ((osStr.contains("mac")) || (osStr.contains("darwin"))) {
                currentOs = Os.MAC;
            } else if (osStr.contains("win")) {
                currentOs = Os.WIN;
            } else if (osStr.contains("nux")) {
                currentOs = Os.LINUX;
            } else {
                currentOs = Os.OTHER;
            }
        }
        return currentOs;
    }
}
