package phonebook.phonebookfx;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.io.IOException;
import java.util.Optional;

public class DeleteContactController {
    //Инициализация свойств
    private static final Logger logger = LogManager.getLogger(DeleteContactController.class.getName());

    @FXML
    private TextField entry;

    //Удаление контакта

    /**
     * Функция удаления контакта и сохранения изменений
     */
    @FXML
    private void delete() {
        entry.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER) {
                if (AppController.names.contains(entry.getText())) {
                    Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
                    confirm.setTitle("Подтверждение удаления");
                    confirm.setHeaderText("Вы точно хотите удалить этот контакт?");
                    confirm.setContentText(entry.getText());

                    Optional<ButtonType> option = confirm.showAndWait();
                    if (option.isPresent() && option.get() == ButtonType.OK) {
                        try {
                            App.phonebook.delContact(entry.getText());
                            App.phonebook.savePB();
                            AppController.names.remove(entry.getText());

                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle("Удаление контакта");
                            alert.setHeaderText("Удаление успешно завершено!");
                            alert.setContentText(entry.getText());
                            alert.setOnCloseRequest(dialogEvent -> AppController.stage.close());
                            alert.show();

                            logger.info("Удаление контакта прошло успешно");
                        } catch (IOException e) {
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Ошибка сохранения");
                            alert.setHeaderText("Не удалось сохранить изменения. Попробуйте ещё раз.");
                            alert.show();

                            logger.error("Произошла ошибка при удалении контакта");
                        }
                    }
                }
                else {
                    Alert wrong_input = new Alert(Alert.AlertType.ERROR);
                    wrong_input.setTitle("Ошибка ввода");
                    wrong_input.setHeaderText("Не удалось найти данный контакт");
                    wrong_input.setContentText("Попробуйте ввести ФИО другого контакта");
                    wrong_input.setOnCloseRequest(dialogEvent -> entry.clear());
                    wrong_input.show();

                    logger.error("Произошла ошибка ввода: данного контакта не существует");
                }
            }
        });
    }
}
