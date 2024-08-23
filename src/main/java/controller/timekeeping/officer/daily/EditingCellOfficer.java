package controller.timekeeping.officer.daily;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.TableCell;
import javafx.scene.control.TextField;
import model.logtimekeeping.LogTimekeepingOfficer;

import java.sql.Time;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.function.BiConsumer;

public class EditingCellOfficer extends TableCell<LogTimekeepingOfficer, Time> {
    private TextField textField;
    private BiConsumer<LogTimekeepingOfficer, Time> updateCallback;

    public EditingCellOfficer(BiConsumer<LogTimekeepingOfficer, Time> updateCallback) {
        this.updateCallback = updateCallback;
    }

    @Override
    public void startEdit() {
        super.startEdit();
        if (textField == null) {
            createTextField();
        }
        textField.setDisable(false);
        setGraphic(textField);
        setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        textField.selectAll();
    }

    @Override
    public void cancelEdit() {
        super.cancelEdit();
        if (!isValidTimeFormat(textField.getText())) {
            Platform.runLater(this::showInvalidTimeAlert);
            textField.setText("");
        } else {
            setText(getItem().toString());
            setContentDisplay(ContentDisplay.TEXT_ONLY);
        }
        textField.setDisable(true);
    }

    @Override
    public void updateItem(Time item, boolean empty) {
        super.updateItem(item, empty);
        if (empty) {
            setText(null);
            setGraphic(null);
        } else {
            if (isEditing()) {
                if (textField != null) {
                    textField.setText(getString());
                }
                setGraphic(textField);
                setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
            } else {
                setText(getString());
                setContentDisplay(ContentDisplay.TEXT_ONLY);
            }
        }
    }

    @Override
    public void commitEdit(Time newValue) {
        super.commitEdit(newValue);

        LogTimekeepingOfficer currentOfficer = getTableView().getItems().get(getIndex());
        if (updateCallback != null) {
            updateCallback.accept(currentOfficer, newValue);
        }
    }

    private void createTextField() {
        textField = new TextField(getString());
        textField.setMinWidth(this.getWidth() - this.getGraphicTextGap() * 2);

        textField.setOnAction(e -> {
            String input = textField.getText();
            if (isValidTimeFormat(input)) {
                commitEdit(Time.valueOf(input));
            } else {
                showInvalidTimeAlert();
            }
        });

        textField.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal) {
                String input = textField.getText();
                if (isValidTimeFormat(input)) {
                    commitEdit(Time.valueOf(input));
                }
            }
        });
    }


    private String getString() {
        return getItem() == null ? "" : getItem().toString();
    }

    private boolean isValidTimeFormat(String time) {
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        try {
            LocalTime.parse(time, timeFormatter);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    private void showInvalidTimeAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Lỗi định dạng thời gian");
        alert.setHeaderText(null);
        alert.setContentText("Vui lòng nhập theo định dạng HH:mm:ss.");
        alert.showAndWait();
    }
}
