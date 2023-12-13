package phonebook.phonebookfx;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SearchByPhoneNumberController {
    //Инициализация свойств
    private static final Logger logger = LogManager.getLogger(SearchByPhoneNumberController.class.getName());

    @FXML
    private TextField entry;

    //Поиск контакта по номеру телефона

    /**
     * Функция создания окна контакта по его номеру телефона
     */
    @FXML
    private void getPhone() {
        entry.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER) {
                if (checkInput()) {
                    AppController.contact_stage.setTitle(getName());
                    try {
                        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("contact.fxml"));

                        AppController.contact_stage.setScene(new Scene(fxmlLoader.load()));
                        AppController.contact_stage.setResizable(false);
                        AppController.contact_stage.show();

                        AppController.stage.close();

                        logger.info("Контакт по номеру телефона найден");
                    } catch (Exception e) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Ошибка");
                        alert.setHeaderText("Произошла какая-то ошибка! Попробуйте заново.");
                        alert.show();

                        logger.error("Возникла ошибка при поиске контакта");
                    }
                }
                else {
                    Alert wrong_input = new Alert(Alert.AlertType.ERROR);
                    wrong_input.setTitle("Поиск контакта");
                    wrong_input.setHeaderText("Такого номера телефона не существует в базе данных");
                    wrong_input.setContentText("Если вы уверены, что он есть, то введите подобно образцу");
                    wrong_input.setOnCloseRequest(dialogEvent -> entry.clear());
                    wrong_input.show();

                    logger.error("Введён неправильный номер телефона");
                }
            }
        });
    }

    //Проверка на правильность ввода

    /**
     * Функция проверки на правильность ввода данных контакта
     *
     * @return true, если ввод правильный, иначе false
     */
    private boolean checkInput() {
        boolean flag = false;

        for (String name : App.contacts.keySet())
            if (App.contacts.get(name).containsKey(entry.getText())) {
                flag = true;
                break;
            }

        return flag;
    }

    //Поиск ФИО контакта по его номеру телефона

    /**
     * Функция поиска ФИО контакта по его номеру телефона
     *
     * @return ФИО контакта
     */
    private String getName() {
        String name = "";

        for (String contact : App.contacts.keySet())
            if (App.contacts.get(contact).containsKey(entry.getText())) {
                name = contact;
                break;
            }

        return name;
    }
}
