package org.example;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import org.example.util.Assets;

import java.io.IOException;

public class LoginController {

    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Button signInButton;
    @FXML private Label statusLabel;
    @FXML private ImageView logoView;

    private boolean loginSuccessful = false;

    @FXML
    public void initialize() {
        logoView.setImage(Assets.APP_LOGO);

        usernameField.setOnAction(e -> handleSignIn());
        passwordField.setOnAction(e -> handleSignIn());
    }

    public boolean isLoginSuccessful() {
        return loginSuccessful;
    }

    @FXML
    private void handleSignIn() {
        String user = usernameField.getText();
        String pass = passwordField.getText();

        if (user.equals("admin") && pass.equals("1234")) {
            loginSuccessful = true;
            ((Stage) usernameField.getScene().getWindow()).close();
        } else {
            statusLabel.setText("Hibás felhasználónév vagy jelszó!");
        }
    }
}
