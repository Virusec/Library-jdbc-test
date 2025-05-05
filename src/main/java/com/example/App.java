package com.example;

import com.example.dao.LibraryDao;
import com.example.model.Author;
import com.example.model.Book;
import com.example.model.Reader;

import java.sql.SQLException;

/**
 * @author Anatoliy Shikin
 */
public class App {
    public static void main(String[] args) {
        DbManager.init();
        try {
            DbManager.getConnection();
            LibraryDao libraryDao = new LibraryDao();

            Author newAuthor = new Author(0, "Лев", "Толстой");
            libraryDao.addAuthor(newAuthor);

            Book newBook = new Book(0, "Война и мир", 1, 1869, 2);
            libraryDao.addBook(newBook);

            Reader reader = new Reader(0,"Андрей", "Левченко", "alevchenko@mail.ru");

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbManager.close();
        }
    }
}
