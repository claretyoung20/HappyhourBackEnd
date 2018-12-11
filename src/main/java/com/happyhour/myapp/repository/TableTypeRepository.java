package com.happyhour.myapp.repository;

import com.happyhour.myapp.domain.TableType;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the TableType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TableTypeRepository extends JpaRepository<TableType, Long> {

}
