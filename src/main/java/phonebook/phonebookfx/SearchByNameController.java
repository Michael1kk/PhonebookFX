package phonebook.phonebookfx;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SearchByNameController {
    //Инициализация свойств
    private static final Logger logger = LogManager.getLogger(SearchByNameController.class.getName());

    @FXML
    private TextField entry;

    //Поиск контакта по ФИО

    /**
     * Функция создания окна контакта по его ФИО
     */
    @FXML
    private void getName() {
        entry.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER) {
                if (AppController.names.contains(entry.getText())) {
                    AppController.contact_stage.setTitle(entry.getText());
                    try {
                        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("contact.fxml"));

                        AppController.contact_stage.setScene(new Scene(fxmlLoader.load()));
                        AppController.contact_stage.setResizable(false);
                        AppController.contact_stage.show();

                        AppController.stage.close();

                        logger.info("Контакт по ФИО найден успешно");
                    } catch (Exception e) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Ошибка");
                        alert.setHeaderText("Произошла какая-то ошибка! Попробуйте заново.");
                        alert.show();

                        logger.error("Произошла ошибка при поиске контакта");
                    }
                }
                else {
                    Alert wrong_input = new Alert(Alert.AlertType.ERROR);
                    wrong_input.setTitle("Поиск контакта");
                    wrong_input.setHeaderText("Такого контакта не существует в базе данных");
                    wrong_input.setContentText("Если вы уверены, что он есть, то введите подобно образцу");
                    wrong_input.setOnCloseRequest(dialogEvent -> entry.clear());
                    wrong_input.show();

                    logger.error("Введено неправильное ФИО контакта");
                }
            }
        });
    }
}
