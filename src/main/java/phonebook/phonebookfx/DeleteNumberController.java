package phonebook.phonebookfx;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import java.util.Optional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DeleteNumberController {
    //Инициализация свойств
    private static final Logger logger = LogManager.getLogger(DeleteNumberController.class.getName());

    @FXML
    private TextField entry;

    //Удаление номера телефона

    /**
     * Функция удаления номера телефона и сохранения изменений
     */
    @FXML
    private void delete() {
        entry.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER) {
                if (checkInput()) {
                    Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
                    confirm.setTitle("Подтверждение удаления");
                    confirm.setHeaderText("Вы точно хотите удалить этот номер телефона?");
                    confirm.setContentText(entry.getText());

                    Optional<ButtonType> option = confirm.showAndWait();
                    if (option.isPresent() && option.get() == ButtonType.OK) {
                        try {
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle("Удаление номера телефона");
                            alert.setHeaderText("Удаление прошло успешно");
                            alert.setContentText(entry.getText());

                            /**
                             * Проверка на количество номеров у контакта:
                             * если 1 номер - удаляется контакт; если больше 1 - удаляется только номер телефона
                             */
                            if (App.contacts.get(AppController.stage.getTitle()).keySet().size() == 1) {
                                App.phonebook.delContact(AppController.stage.getTitle());
                                AppController.names.remove(AppController.stage.getTitle());
                                alert.setOnCloseRequest(dialogEvent -> {
                                    ContactController.stage.close();
                                    AppController.stage.close();
                                });
                            }
                            else {
                                App.phonebook.delPhone(AppController.stage.getTitle(), entry.getText());
                                ContactController.phone_numbers.remove(entry.getText());
                                alert.setOnCloseRequest(dialogEvent -> ContactController.stage.close());
                            }
                            App.phonebook.savePB();

                            alert.show();

                            logger.info("Удаление номера телефона прошло успешно");
                        } catch (Exception e) {
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Ошибка сохранения");
                            alert.setHeaderText("Не удалось сохранить изменения. Попробуйте ещё раз.");
                            alert.show();

                            logger.error("Произошла ошибка при удалении номера телефона");
                        }
                    }
                } else {
                    Alert wrong_input = new Alert(Alert.AlertType.ERROR);
                    wrong_input.setTitle("Ошибка ввода");
                    wrong_input.setHeaderText("Не удалось найти данный номер телефона");
                    wrong_input.setContentText("Попробуйте ввести другой");
                    wrong_input.setOnCloseRequest(dialogEvent -> entry.clear());
                    wrong_input.show();

                    logger.error("Произошла ошибка ввода: данного номера телефона не существует");
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

        for (String phone: ContactController.phone_numbers)
            if (phone.split(" - ")[0].equals(entry.getText())) {
                flag = true;
                break;
            }

        return flag;
    }
}
