package org.example.toolsjc;

import javafx.concurrent.Worker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.robot.Robot;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Optional;

public class HelloController {
    @FXML
    private TextField txtCCCD;
    @FXML
    private TextField txtFullName;
    @FXML
    private TextField txtEmail;
    @FXML
    private TextField txtPhone;
    @FXML
    private Button btnRead;
    @FXML
    private AnchorPane myPanel;


    @FXML
    public void readInformation(ActionEvent actionEvent) {
        Stage stage = (Stage) myPanel.getScene().getWindow();
        Alert.AlertType type = Alert.AlertType.CONFIRMATION;
        Alert alert = new Alert(type,"");
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.initOwner(stage);
        StringBuilder txt = new StringBuilder();
        txt.append("CCCD:").append(txtCCCD.getText());
        txt.append("\nFullName:").append(txtFullName.getText());
        txt.append("\nEmail:").append(txtEmail.getText());
        txt.append("\nPhone:").append(txtPhone.getText());
        alert.getDialogPane().setContentText(txt.toString());
        alert.getDialogPane().setHeaderText("Header Information");

        Optional<ButtonType> res = alert.showAndWait();
        if (res.get() == ButtonType.OK){
            fillToPage();
        }
    }

    private void fillToPage(){
        WebView webView = new WebView();
        WebEngine webEngine = webView.getEngine();
        webEngine.getLoadWorker().stateProperty().addListener((observable, oldValue, newValue)->{
            if (newValue == Worker.State.SUCCEEDED) {
                String javascript = "document.getElementById('id_cccd').value = '" + txtCCCD.getText();
                webEngine.executeScript(javascript);
            }
        });
        webEngine.load("https://tructuyen.sjc.com.vn/");
    }


    private void focusChromeWindow(String windowTitle) {
        try {
            Robot robot = new Robot();
            Window chromeWindow = getWindowByTitle(windowTitle);
            if (chromeWindow != null) {
                robot.keyPress(KeyEvent.VK_ALT);
                robot.keyPress(KeyEvent.VK_TAB);
                robot.keyRelease(KeyEvent.VK_TAB);
                robot.keyRelease(KeyEvent.VK_ALT);
            }
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }

    private Window getWindowByTitle(String windowTitle) {
        for (Window window : Window.getWindows()) {
            if (window instanceof Frame && window.getTitle().contains(windowTitle)) {
                return window;
            }
        }
        return null;
    }

    private void fillTextField(TextField textField) {
        try {
            Robot robot = new Robot();
            String text = textField.getText();

            // Focus on the JavaFX window
//            primaryStage.requestFocus();

            // Simulate typing the text
            for (char c : text.toCharArray()) {
                robot.keyPress(KeyEvent.getExtendedKeyCodeForChar(c));
                robot.delay(50); // Delay between key presses (adjust as needed)
                robot.keyRelease(KeyEvent.getExtendedKeyCodeForChar(c));
            }
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }

}