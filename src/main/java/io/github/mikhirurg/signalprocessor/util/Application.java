package io.github.mikhirurg.signalprocessor.util;

import io.github.mikhirurg.signalprocessor.gui.Oscilloscope;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Locale;
import java.util.Properties;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

public class Application {
    private static final Properties appProperties;
    private static final ResourceBundle resourceBundle;
    private static final ResourceBundle resourceBundleUS;

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
        resourceBundle = PropertyResourceBundle.getBundle("AppBundle");
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
}
