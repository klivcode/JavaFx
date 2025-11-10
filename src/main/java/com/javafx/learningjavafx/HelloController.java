package com.javafx.learningjavafx;

import javafx.fxml.FXML;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

public class HelloController {

    @FXML
    private Slider slider;

    @FXML
    private Text text;

    @FXML
    void updateSliderValue(MouseEvent event) {
        text.setText(String.valueOf((int)slider.getValue()));
    }

}
