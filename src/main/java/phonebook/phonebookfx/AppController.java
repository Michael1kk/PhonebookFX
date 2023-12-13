package phonebook.phonebookfx;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.util.Callback;
import java.net.URL;
import java.util.ResourceBundle;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AppController implements Initializable {
    //Инициализация необходимых свойств
    private static final Logger logger = LogManager.getLogger(AppController.class.getName());
    protected static Stage contact_stage = new Stage();
    protected static Stage stage = new Stage();
    protected static ObservableList<String> names = FXCollections.observableArrayList(App.contacts.keySet());
    private Alert alert;
    @FXML
    protected ListView<String> listView = new ListView<>();

    //Описание реализации приложения

    /**
     * Функция описания реализации приложения
     */
    @FXML
    private void aboutApp() {
        alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("О приложении");
        alert.setHeaderText("Это телефонный справочник, в котором вы можете хранить номера телефонов \n" +
                "родных, друзей, коллег по работе и просто знакомых.");
        alert.show();
    }

    //Описание разработчика приложения

    /**
     * Функция описания разработчика приложения
     */
    @FXML
    private void aboutDeveloper() {
        alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("О разработчике");
        alert.setHeaderText("""
                Лимонов Михаил Иванович, студент
                Санкт-Петербургского государственного университета телекоммуникаций
                имени профессора М. А. Бонч-Бруевича""");
        alert.show();
    }

    //Поиск контакта по ФИО

    /**
     * Функция создания окна поиска контакта по ФИО
     */
    @FXML
    private void searchByName() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("searchByName.fxml"));

            stage.setScene(new Scene(fxmlLoader.load(), 400, 200));
            stage.setResizable(false);
            stage.setTitle("Поиск по ФИО");
            stage.getIcons().add(new Image("icon.png"));
            stage.show();

            logger.info("Удалось загрузить окно");
        } catch (Exception e) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка");
            alert.setHeaderText("Произошла какая-то ошибка! Попробуйте заново.");
            alert.show();

            logger.error("Не удалось загрузить окно");
        }
    }

    //Поиск контакта по номеру телефона

    /**
     * Функция создания окна поиска контакта по номеру телефона
     */
    @FXML
    private void searchByPhoneNumber() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("searchByPhoneNumber.fxml"));

            stage.setScene(new Scene(fxmlLoader.load(), 400, 200));
            stage.setResizable(false);
            stage.setTitle("Поиск по номеру телефона");
            stage.getIcons().add(new Image("icon.png"));
            stage.show();

            logger.info("Удалось загрузить окно");
        } catch (Exception e) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка");
            alert.setHeaderText("Произошла какая-то ошибка! Попробуйте заново.");
            alert.show();

            logger.error("Не удалось загрузить окно");
        }
    }

    //Добавление контакта

    /**
     * Функция создания окна для добавления контакта
     */
    @FXML
    private void addContact() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("addContact.fxml"));

            stage.setScene(new Scene(fxmlLoader.load(), 500, 450));
            stage.setResizable(false);
            stage.setTitle("Добавить контакт");
            stage.getIcons().add(new Image("icon.png"));
            stage.show();

            logger.info("Удалось загрузить окно");
        } catch (Exception e) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка");
            alert.setHeaderText("Произошла какая-то ошибка! Попробуйте заново.");
            alert.show();

            logger.error("Не удалось загрузить окно");
        }
    }

    //Удаление контакта

    /**
     * Функция создания окна для удаления контакта
     */
    @FXML
    private void deleteContact() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("deleteContact.fxml"));

            stage.setScene(new Scene(fxmlLoader.load(), 400, 200));
            stage.setResizable(false);
            stage.setTitle("Удалить контакт");
            stage.getIcons().add(new Image("icon.png"));
            stage.show();

            logger.info("Удалось загрузить окно");
        } catch (Exception e) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка");
            alert.setHeaderText("Произошла какая-то ошибка! Попробуйте заново.");
            alert.show();

            logger.error("Не удалось загрузить окно");
        }
    }

    //Объявление списка контактов

    /**
     * Функция объявления списка контактов пользователя
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        SortedList<String> sorted_names = new SortedList<>(names);
        sorted_names.setComparator(String::compareToIgnoreCase);
        listView.setItems(sorted_names);
        listView.setEditable(true);
        listView.setCellFactory(new Callback<>() {
            /**
             * Функция присвоения каждой ячейке создание окна со списком номеров
             *
             * @param listView список контактов
             * @return контакт со присвоенным ему списком номеров
             */
            @Override
            public ListCell<String> call(ListView<String> listView) {
                ListCell<String> cell = new ListCell<>() {
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item != null) setText(item);
                        else setText("");
                    }
                };

                cell.setOnMouseClicked(mouseEvent -> {
                    contact_stage.setTitle(cell.getItem());
                    if (!cell.getText().isEmpty()) {
                        try {
                            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("contact.fxml"));

                            contact_stage.setScene(new Scene(fxmlLoader.load()));
                            contact_stage.getIcons().add(new Image("icon.png"));
                            contact_stage.show();

                            logger.info("Удалось загрузить страницу контакта");
                        } catch (Exception e) {
                            alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Ошибка");
                            alert.setHeaderText("Не удалось загрузить страницу контакта. Попробуйте ещё раз");
                            alert.show();

                            logger.error("Не удалось загрузить страницу контакта");
                        }
                    }
                });

                return cell;
            }
        });
    }
}