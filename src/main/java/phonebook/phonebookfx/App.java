package phonebook.phonebookfx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import java.util.HashMap;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class App extends Application {
    protected static final Phonebook phonebook = new Phonebook();
    protected static HashMap<String, HashMap<String, String>> contacts;
    private static final Logger logger = LogManager.getLogger(App.class.getName());

    @Override
    public void start(Stage stage) {
        try {
            contacts = phonebook.loadPB();

            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("appUI.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 500, 400);
            stage.setTitle("Телефонный справочник");
            stage.getIcons().add(new Image("icon.png"));
            stage.setScene(scene);
            stage.setOnCloseRequest(windowEvent -> System.exit(0));
            stage.show();

            logger.info("Удалось загрузить контакты");
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка");
            alert.setHeaderText("Не удалось загрузить контакты из базы данных. Попробуйте ещё раз.");
            alert.show();

            logger.error("Не удалось загрузить контакты");
        }
    }

    public static void main(String[] args) {
        launch();
    }
}