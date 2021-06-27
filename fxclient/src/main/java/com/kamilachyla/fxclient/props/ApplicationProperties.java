package com.kamilachyla.fxclient.props;

import java.io.IOException;
import java.util.Properties;

public class ApplicationProperties {

    private final Properties properties;

    public ApplicationProperties() {
        this.properties = defaultProperties();
        readProperties().forEach(properties::put);
    }

    private static Properties defaultProperties() {
        var dp = new Properties();
        dp.setProperty(Props.GUI_WIDTH.propName, "800");
        dp.setProperty(Props.GUI_HEIGHT.propName, "600");
        dp.setProperty(Props.GUI_COLOR.propName, "hsl(234, 90%, 90%)");
        dp.setProperty(Props.IMAGE_FNAME.propName, "image.png");
        dp.setProperty(Props.GUI_IMAGE_WIDTH.propName, "400");
        dp.setProperty(Props.GUI_IMAGE_HEIGHT.propName, "400");
        return dp;
    }

    private int getIntProp(Props p) {
        System.out.printf("%s%n", p.propName);
        return Integer.parseInt(getStringProp(p));
    }

    private String getStringProp(Props p) {
        return properties.get(p.propName).toString();
    }

    public int getWidth() {
        return getIntProp(Props.GUI_WIDTH);

    }

    public int getHeight() {
        return getIntProp(Props.GUI_HEIGHT);
    }

    public String getColor() {
        return getStringProp(Props.GUI_COLOR);
    }

    public String getFileName() {
        return getStringProp(Props.IMAGE_FNAME);
    }

    public int getImageWidth() {
        return getIntProp(Props.GUI_IMAGE_WIDTH);
    }

    public int getImageHeight() {
        return getIntProp(Props.GUI_IMAGE_HEIGHT);
    }

    private Properties readProperties() {
        Properties props = new Properties();
        try {
            try (var is = getClass().getClassLoader().getResourceAsStream("application.properties")) {
              if (is != null) {
                props.load(is);
              }
            }
        } catch (IOException ignored) {

        }
        return props;
    }

    enum Props {
        GUI_WIDTH("gui.width"),
        GUI_HEIGHT("gui.height"),
        GUI_COLOR("gui.color"),
        IMAGE_FNAME("gui.image.fname"),
        GUI_IMAGE_WIDTH("gui.image.width"),
        GUI_IMAGE_HEIGHT("gui.image.height"),
        ;

        private final String propName;

        Props(String s) {
            this.propName = s;
        }


    }
}
