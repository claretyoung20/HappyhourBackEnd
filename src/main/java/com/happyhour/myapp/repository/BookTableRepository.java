package com.happyhour.myapp.repository;

import com.happyhour.myapp.domain.BookTable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the BookTable entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BookTableRepository extends JpaRepository<BookTable, Long> {
    //SELECT  * FROM book_table where id = 3 and persons = 2;
    BookTable findByIdAndTableTypeId(Long id, Long tableTypeId);
    //    SELECT * FROM book_table where persons = 2 and id != 3
    @Query (value = "select * from book_table t where t.table_type_id=:tableTypeId and t.id!=:id",
        nativeQuery = true)
    Page<BookTable> findAllByIdAndTableTypeIdNot(@Param("id") Long id, @Param("tableTypeId") Long tableTypeId, Pageable pageable);

    Page<BookTable> findAllByTableTypeId(Long tableTypeId, Pageable pageable);
}
