package org.example.util;

import javafx.scene.image.Image;

public class Assets {
    public static final Image APP_LOGO = loadImage("/org/example/images/logo.png");

    private static Image loadImage(String path) {
        return new Image(Assets.class.getResource(path).toExternalForm(), true);
    }
}
