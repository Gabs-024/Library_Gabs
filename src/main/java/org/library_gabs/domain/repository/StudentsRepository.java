package org.library_gabs.domain.repository;

import org.library_gabs.domain.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StudentsRepository extends JpaRepository<Student, Integer> {

    @Query(value = "SELECT * FROM Student s where s.enrollment like '%:enrollment%' ", nativeQuery = true)
    List<Student> findbyEnrollment(@Param("enrollment") String enrollment);

    @Query(value = "DELETE FROM Student s WHERE s.enrollment =:enrollment ")
    @Modifying
    void deleteByEnrollment(String enrollment);

    boolean existsByEnrollment(String enrollment);

    @Query(value = "SELECT s FROM Student s LEFT JOIN FETCH s.loans WHERE s.id =:studentId ")
    Student findStudentFetchLoans (@Param("studentId") Integer studentId);
}
