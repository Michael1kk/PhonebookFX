package phonebook.phonebookfx;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ContactController implements Initializable {
    //Инициализация свойств
    private static final Logger logger = LogManager.getLogger(ContactController.class.getName());
    private final String[] label = AppController.contact_stage.getTitle().split(" ");
    protected static final Stage stage = new Stage();
    protected static ObservableList<String> phone_numbers;
    private final Alert alert = new Alert(Alert.AlertType.ERROR);
    @FXML
    private Label name = new Label();
    @FXML
    private ListView<String> listView = new ListView<>();

    //Добавление номера телефона

    /**
     * Функция создания окна для добавления номера телефона контакта
     */
    @FXML
    private void addPhoneNumber() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("addNumber.fxml"));

            stage.setScene(new Scene(fxmlLoader.load(), 450, 350));
            stage.setResizable(false);
            stage.setTitle("Добавление номера");
            stage.getIcons().add(new Image("icon.png"));
            stage.show();

            logger.info("Удалось загрузить окно");
        } catch (Exception e) {
            alert.setTitle("Ошибка");
            alert.setHeaderText("Произошла какая-то ошибка! Попробуйте заново.");
            alert.show();

            logger.error("Не удалось загрузить окно");
        }
    }

    //Удаление номера телефона

    /**
     * Функция создания окна для удаления номера телефона контакта
     */
    @FXML
    private void deletePhoneNumber() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("deleteNumber.fxml"));

            stage.setScene(new Scene(fxmlLoader.load(), 450, 200));
            stage.setResizable(false);
            stage.setTitle("Удаление номера");
            stage.getIcons().add(new Image("icon.png"));
            stage.show();

            logger.info("Удалось загрузить окно");
        } catch (Exception e) {
            alert.setTitle("Ошибка");
            alert.setHeaderText("Произошла какая-то ошибка! Попробуйте заново.");
            alert.show();

            logger.error("Не удалось загрузить окно");
        }
    }

    //Объявление списка номеров телефонов контакта

    /**
     * Функция объявления списка номеров телефонов контакта
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        List<String> phone_list = new ArrayList<>();

        for (String phone_number : App.contacts.get(AppController.contact_stage.getTitle()).keySet())
            phone_list.add(phone_number + " - " + App.contacts.get(AppController.contact_stage.getTitle()).get(phone_number));

        phone_numbers = FXCollections.observableArrayList(phone_list);
        name.setText(label[0] + " " + label[1] + "\n" + label[2]);
        name.setAlignment(Pos.TOP_CENTER);
        listView.setItems(phone_numbers);
    }
}
