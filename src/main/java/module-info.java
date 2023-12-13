module phonebook.phonebookfx {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.apache.logging.log4j;


    opens phonebook.phonebookfx to javafx.fxml;
    exports phonebook.phonebookfx;
}