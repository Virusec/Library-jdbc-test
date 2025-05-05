package com.example;

import com.example.dao.LibraryDao;
import com.example.model.Author;
import com.example.model.Book;
import com.example.model.Reader;

import java.sql.SQLException;
import java.util.List;

/**
 * @author Anatoliy Shikin
 */
public class App {
    public static void main(String[] args) {
        DbManager.init();
        try {
            DbManager.getConnection();
            LibraryDao libraryDao = new LibraryDao();

//            Author newAuthor = Author.builder()
//                    .firstName("Федор")
//                    .lastName("Достоевский")
//                    .build();
//            libraryDao.addAuthor(newAuthor);

//            Book newBook = Book.builder()
//                    .title("Записки из подполья")
//                    .authorId(3)
//                    .publishedYear(2011)
//                    .totalCopies(1)
//                    .build();
//            libraryDao.addBook(newBook);

//            Book newBook = Book.builder()
//                    .title("Hdajf")
//                    .authorId(3)
//                    .publishedYear(2022)
//                    .totalCopies(7)
//                    .build();
//            libraryDao.addBook(newBook);

//            Reader reader = Reader.builder()
//                    .firstName("Михаил")
//                    .lastName("Левин")
//                    .email("mlevin@mail.ru")
//                    .build();
//            libraryDao.addReader(reader);

            System.out.println("---Все книги в библиотеке---");
            List<Book> allBooks = libraryDao.getAllBooks();
            printAllBooks(allBooks);

            System.out.println("---Все читатели в библиотеке---");
            libraryDao.getAllReaders().forEach(r -> System.out.println(
                    r.getId() + ": " +
                            r.getFirstName() + " " +
                            r.getLastName() + " " +
                            r.getEmail()
            ));

            System.out.println("---Все авторы в библиотеке---");
            libraryDao.getAllAuthors().forEach(a -> System.out.println(
                    a.getId() + ": " +
                            a.getFirstName() + " " +
                            a.getLastName()
            ));

//TODO: Написать поиск по id и добавить здесь вывод обновленной книги

//            System.out.println("---Обновление информации о книге по её id---");
//            Book bookToUpdate = Book.builder()
//                    .title("Идиот")
//                    .authorId(3)
//                    .publishedYear(2020)
//                    .totalCopies(4)
//                    .build();
//            List<Book> bookList = libraryDao.getAllBooks();
//            if (!bookList.isEmpty()) {
//                libraryDao.updateBookById(4, bookToUpdate);
//                System.out.println("Книга успешно обновлена.");
//            }

//            System.out.println("---Удаление книги по её id---");
//            if (!bookList.isEmpty()) {
//                libraryDao.deleteBookById(5);
//                System.out.println("Книга успешно удалена.");
//            }

            System.out.println("---Добавить книгу и автора в одну транзакцию---");
            libraryDao.addAuthorAndBookTogether(
                    "Ниолай",
                    "Гоголь",
                    "Мертвые души",
                    1917,
                    2
            );
            printAllBooks(libraryDao.searchBooks("Гоголь"));

            System.out.println("---Поиск книг по названию или автору---");
            List<Book> searchBooks = libraryDao.searchBooks("Дост");
            printAllBooks(searchBooks);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbManager.close();
        }
    }

    private static void printAllBooks(List<Book> allBooks) throws SQLException {
        allBooks.forEach(b -> System.out.println(
                b.getId() + ": " +
                        b.getAuthor().getFirstName() + " " +
                        b.getAuthor().getLastName() + " - " +
                        b.getTitle() + " - " +
                        b.getPublishedYear() + " " +
                        " | Total=" + b.getTotalCopies() +
                        " | Borrowed=" + b.getBorrowedCount()
        ));
    }
}
