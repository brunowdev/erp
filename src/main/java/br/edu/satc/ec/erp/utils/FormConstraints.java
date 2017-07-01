package br.edu.satc.ec.erp.utils;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;

/**
 * Created by BRUNO-PC on 25/06/2017.
 */
public class FormConstraints {

    public static void maxField(final TextInputControl textField, final Integer length) {
        textField.textProperty().addListener((observableValue, oldValue, newValue) -> {
            if (newValue.length() > length)
                textField.setText(oldValue);
        });
    }

    private static void positionCaret(final TextInputControl textField) {
        Platform.runLater(() ->
                textField.positionCaret(textField.getText().length())
        );
    }

    public static void monetaryField(final TextField textField) {
        textField.setAlignment(Pos.CENTER_RIGHT);
        textField.lengthProperty().addListener((observable, oldValue, newValue) -> {
            String value = textField.getText();
            value = value.replaceAll("[^0-9]", "");
            value = value.replaceAll("([0-9]{1})([0-9]{14})$", "$1.$2");
            value = value.replaceAll("([0-9]{1})([0-9]{11})$", "$1.$2");
            value = value.replaceAll("([0-9]{1})([0-9]{8})$", "$1.$2");
            value = value.replaceAll("([0-9]{1})([0-9]{5})$", "$1.$2");
            value = value.replaceAll("([0-9]{1})([0-9]{2})$", "$1,$2");
            textField.setText(value);
            positionCaret(textField);

            textField.textProperty().addListener((observableValue, oldValue1, newValue1) -> {
                if (newValue1.length() > 17)
                    textField.setText(oldValue1);
            });
        });

        textField.focusedProperty().addListener((observableValue, aBoolean, fieldChange) -> {
            if (!fieldChange) {
                final int length = textField.getText().length();
                if (length > 0 && length < 3) {
                    textField.setText(textField.getText() + "00");
                }
            }
        });
    }

}
