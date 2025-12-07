package org.example.util;

import javafx.scene.image.Image;

public class Assets {
    public static final Image APP_LOGO = loadImage("/org/example/images/logo.png");
    public static final Image DASHBOARD = loadImage("/org/example/images/dashboard_icon.png");
    public static final Image USER = loadImage("/org/example/images/user_icon.png");
    public static final Image COMPUTER = loadImage("/org/example/images/computer_icon.png");
    public static final Image PERIPHERAL = loadImage("/org/example/images/peripheral_icon.png");
    public static final Image PLUS = loadImage("/org/example/images/plus_icon.png");
    public static final Image USER_PLUS = loadImage("/org/example/images/user-plus_icon.png");
    public static final Image DOWNLOAD = loadImage("/org/example/images/download_icon.png");
    public static final Image ASSIGNDEVICE = loadImage("/org/example/images/assign-device_icon.png");

    private static Image loadImage(String path) {
        return new Image(Assets.class.getResource(path).toExternalForm(), true);
    }
}
