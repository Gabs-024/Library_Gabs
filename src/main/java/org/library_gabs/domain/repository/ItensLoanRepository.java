package org.library_gabs.domain.repository;

import org.library_gabs.domain.entity.Book;
import org.library_gabs.domain.entity.ItemLoan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ItensLoanRepository extends JpaRepository<ItemLoan, Integer> {

    @Query(value = "select title from Book b where b.id like '%:title%' ", nativeQuery = true)
    Book findbyBookName(@Param("title") String title);
}
