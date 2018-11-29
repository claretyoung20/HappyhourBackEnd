package com.happyhour.myapp.repository;

import com.happyhour.myapp.domain.ContactUs;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ContactUs entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ContactUsRepository extends JpaRepository<ContactUs, Long> {

}
