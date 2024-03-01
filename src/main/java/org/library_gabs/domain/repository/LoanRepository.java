package org.library_gabs.domain.repository;

import org.library_gabs.domain.entity.Loan;
import org.library_gabs.domain.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface LoanRepository extends JpaRepository<Loan, Integer> {

    List<Loan> findByStudent(Student student);

    @Query(value = "select l from Loan l left join fetch l.items where l.id =:id")
    Optional<Loan> findById (@Param("id") Integer id);
}
