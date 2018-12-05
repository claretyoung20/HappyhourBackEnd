package com.happyhour.myapp.repository;

import com.happyhour.myapp.domain.BookTable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Spring Data  repository for the BookTable entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BookTableRepository extends JpaRepository<BookTable, Long> {

//SELECT  * FROM book_table where id = 3 and persons = 2;
    BookTable findByIdAndPersons(Long id, Integer persons);
//    SELECT * FROM book_table where persons = 2 and id != 3
    Page<BookTable> findAllByIdIsNotAndPersons(Long id, Integer persons, Pageable pageable);

    Page<BookTable> findAllByPersons(Integer persons, Pageable pageable);
}
