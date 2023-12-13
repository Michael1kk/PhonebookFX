package phonebook.phonebookfx;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AddContactController {
    //Инициализация свойств
    private static final Logger logger = LogManager.getLogger(AddNumberController.class.getName());

    @FXML
    private TextField entry_name, entry_phone, entry_type;

    //Сохранение контакта

    /**
     * Функция сохранения контакта
     */
    @FXML
    private void combine() {
        if (checkInput())
            try {
                App.phonebook.addContact(entry_name.getText(), entry_phone.getText(), entry_type.getText());
                App.phonebook.savePB();
                AppController.names.add(entry_name.getText());

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Сохранение контакта");
                alert.setHeaderText("Сохранение успешно завершено!");
                alert.setContentText(entry_name.getText());
                alert.setOnCloseRequest(dialogEvent -> AppController.stage.close());
                alert.show();

                logger.info("Сохранение контакта прошло успешно");
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Ошибка сохранения");
                alert.setHeaderText("Не удалось сохранить изменения. Попробуйте ещё раз.");
                alert.show();

                logger.error("Возникла ошибка при сохранении контакта");
            }
        else {
            Alert wrong_input = new Alert(Alert.AlertType.ERROR);
            wrong_input.setTitle("Ошибка ввода");
            wrong_input.setHeaderText("Вводите данные контакта согласно формату");
            wrong_input.setOnCloseRequest(dialogEvent -> {
                entry_name.clear();
                entry_phone.clear();
                entry_type.clear();
            });
            wrong_input.show();

            logger.error("Произошла ошибка ввода: контакт не соответствовал формату");
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

        if (entry_type.getText().isEmpty() || entry_phone.getText().isEmpty() || entry_name.getText().isEmpty()) input_flag = false;
        else if (entry_name.getText().split(" ").length != 3) input_flag = false;
        else {
            for (int i = 0; i < 3; i++)
                if (Character.isLowerCase(entry_name.getText().split(" ")[i].charAt(0))) {
                    input_flag = false;
                    break;
                }
            if (!entry_phone.getText().startsWith("+7") || entry_phone.getText().length() != 12) input_flag = false;
        }

        return input_flag;
    }
}
