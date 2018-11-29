package com.happyhour.myapp.repository;

import com.happyhour.myapp.domain.BookTable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the BookTable entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BookTableRepository extends JpaRepository<BookTable, Long> {

}
