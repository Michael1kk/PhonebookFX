package phonebook.phonebookfx;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Phonebook {
    //Объявление свойства
    private final HashMap<String, HashMap<String, String>> book;

    //Инициализация свойства

    /**
     * Конструктор класса, который инициализирует телефонную книгу
     */
    public Phonebook() {
        book = new HashMap<>();
    }

    //Добавление контакта

    /**
     * Функция добавления контакта в телефонную книгу
     *
     * @param name ФИО контакта
     * @param phone номер телефона
     * @param type тип номера телефона
     */
    public void addContact(String name, String phone, String type) {
        HashMap<String, String> number = new HashMap<>();
        number.put(phone, type);
        book.put(name, number);
    }

    //Удаление контакта

    /**
     * Функция удаления контакта из телефонной книги
     *
     * @param name ФИО контакта
     */
    public void delContact(String name) {
        book.remove(name);
    }

    //Добавление номера телефона

    /**
     * Функция добавления номера телефона для определённого контакта
     *
     * @param name ФИО контакта
     * @param phone номер телефона
     * @param type тип номера телефона
     */
    public void addPhone(String name, String phone, String type) {
        if (!book.containsKey(name)) addContact(name, phone, type);
        else book.get(name).put(phone, type);
    }

    //Удаление номера телефона

    /**
     * Функция удаления определённого номера телефона контакта
     *
     * @param name ФИО контакта
     * @param phone номер телефона
     */
    public void delPhone(String name, String phone) {
        book.get(name).remove(phone);
    }

    //Сохранение изменений в телефонной книге

    /**
     * Функция сохранения изменений в телефонной книге в бинарном файле
     *
     * @throws IOException при ненахождении бинарного файла
     */
    public void savePB() throws IOException {
        DataOutputStream writer = new DataOutputStream(new FileOutputStream("phonebook.bin"));
        StringBuilder contacts = new StringBuilder();

        for (String name : book.keySet()) {
            StringBuilder number = new StringBuilder();
            for (String phone : book.get(name).keySet())
                number.append(phone).append(": ").append(book.get(name).get(phone)).append(", ");
            contacts.append(name).append(" - ").append(number.deleteCharAt(number.lastIndexOf(", ")).toString().trim()).append("\n");
        }

        writer.write(contacts.toString().getBytes("UTF-16"));
        writer.flush();
        writer.close();
    }

    //Загрузка телефонной книги

    /**
     * Функция загрузки телефонной книги из бинарного файла
     *
     * @return телефонную книгу в виде словаря
     * @throws IOException при ненахождении бинарного файла
     */
    public HashMap<String, HashMap<String, String>> loadPB() throws IOException {
        DataInputStream reader = new DataInputStream(new FileInputStream("phonebook.bin"));


        byte[] bytes = reader.readAllBytes();
        String phonebook = new String(bytes, "UTF-16");
        String[] contacts = phonebook.split("\n");

        for (String contact : contacts) {
            HashMap<String, String> phone_and_type = new HashMap<>();
            ArrayList<String> phones = new ArrayList<>();
            ArrayList<String> types = new ArrayList<>();

            for (String number : contact.split(" - ")[1].split(", ")) {
                String phone = number.split(": ")[0];
                String type = number.split(": ")[1];

                phones.add(phone);
                types.add(type);
            }

            for (int i = 0; i < phones.size(); i++) {
                phone_and_type.put(phones.get(i), types.get(i));
                book.put(contact.split(" - ")[0], phone_and_type);
            }
        }

        reader.close();
        return book;
    }
}
