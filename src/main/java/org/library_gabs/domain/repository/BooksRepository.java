package org.library_gabs.domain.repository;

import org.library_gabs.domain.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BooksRepository extends JpaRepository<Book, Integer> {

    @Query(value = "select * from Book b where b.title like '%:title%' ", nativeQuery = true)
    Book findbyBookName(@Param("title") String title);

    @Query(value = "select * from Book b where b.editor like '%:editor%' ", nativeQuery = true)
    List<Book> findbyEditor(@Param("editor") String editor);

    List<Book> findById (@Param("id") String id);

}
