package phonebook.phonebookfx;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AddNumberController {
    //Инициализация свойств
    private static final Logger logger = LogManager.getLogger(AddNumberController.class.getName());

    @FXML
    private TextField entry_phone, entry_type;

    //Сохранение номера телефона

    /**
     * Функция сохранения номера телефона контакта
     */
    @FXML
    private void add() {
        if (checkInput())
            try {
                App.phonebook.addPhone(AppController.stage.getTitle(), entry_phone.getText(), entry_type.getText());
                App.phonebook.savePB();
                ContactController.phone_numbers.add(entry_phone.getText());

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Сохранение номера телефона");
                alert.setHeaderText("Сохранение успешно завершено!");
                alert.setContentText(entry_phone.getText() + " - " + entry_type.getText());
                alert.setOnCloseRequest(dialogEvent -> AppController.stage.close());
                alert.show();

                logger.info("Сохранение номера телефона прошло успешно");
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Ошибка сохранения");
                alert.setHeaderText("Не удалось сохранить изменения. Попробуйте ещё раз.");
                alert.show();

                logger.error("Возникла ошибка при сохранении номера");
            }
        else {
            Alert wrong_input = new Alert(Alert.AlertType.ERROR);
            wrong_input.setTitle("Ошибка ввода");
            wrong_input.setHeaderText("Вводите номер телефона согласно формату");
            wrong_input.setOnCloseRequest(dialogEvent -> {
                entry_phone.clear();
                entry_type.clear();
            });
            wrong_input.show();

            logger.error("Произошла ошибка ввода: номер телефона не соответствовал формату");
        }
    }

    //Проверка на правильность ввода

    /**
     * Функция проверки на правильность ввода данных контакта
     *
     * @return true, если ввод правильный, иначе false
     */
    private boolean checkInput() {
        boolean input_flag = true;

        if (entry_phone.getText().isEmpty() || entry_type.getText().isEmpty()) input_flag = false;
        else if (!entry_phone.getText().startsWith("+7") || entry_phone.getText().length() != 12) input_flag = false;

        return input_flag;
    }
}