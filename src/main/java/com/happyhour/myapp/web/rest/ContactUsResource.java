package com.happyhour.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.happyhour.myapp.service.ContactUsService;
import com.happyhour.myapp.web.rest.errors.BadRequestAlertException;
import com.happyhour.myapp.web.rest.util.HeaderUtil;
import com.happyhour.myapp.web.rest.util.PaginationUtil;
import com.happyhour.myapp.service.dto.ContactUsDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing ContactUs.
 */
@RestController
@RequestMapping("/api")
public class ContactUsResource {

    private final Logger log = LoggerFactory.getLogger(ContactUsResource.class);

    private static final String ENTITY_NAME = "contactUs";

    private final ContactUsService contactUsService;

    public ContactUsResource(ContactUsService contactUsService) {
        this.contactUsService = contactUsService;
    }

    /**
     * POST  /contactuses : Create a new contactUs.
     *
     * @param contactUsDTO the contactUsDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new contactUsDTO, or with status 400 (Bad Request) if the contactUs has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/contactuses")
    @Timed
    public ResponseEntity<ContactUsDTO> createContactUs(@Valid @RequestBody ContactUsDTO contactUsDTO) throws URISyntaxException {
        log.debug("REST request to save ContactUs : {}", contactUsDTO);
        if (contactUsDTO.getId() != null) {
            throw new BadRequestAlertException("A new contactUs cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ContactUsDTO result = contactUsService.save(contactUsDTO);
        return ResponseEntity.created(new URI("/api/contactuses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /contactuses : Updates an existing contactUs.
     *
     * @param contactUsDTO the contactUsDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated contactUsDTO,
     * or with status 400 (Bad Request) if the contactUsDTO is not valid,
     * or with status 500 (Internal Server Error) if the contactUsDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/contactuses")
    @Timed
    public ResponseEntity<ContactUsDTO> updateContactUs(@Valid @RequestBody ContactUsDTO contactUsDTO) throws URISyntaxException {
        log.debug("REST request to update ContactUs : {}", contactUsDTO);
        if (contactUsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ContactUsDTO result = contactUsService.save(contactUsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, contactUsDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /contactuses : get all the contactuses.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of contactuses in body
     */
    @GetMapping("/contactuses")
    @Timed
    public ResponseEntity<List<ContactUsDTO>> getAllContactuses(Pageable pageable) {
        log.debug("REST request to get a page of Contactuses");
        Page<ContactUsDTO> page = contactUsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/contactuses");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /contactuses/:id : get the "id" contactUs.
     *
     * @param id the id of the contactUsDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the contactUsDTO, or with status 404 (Not Found)
     */
    @GetMapping("/contactuses/{id}")
    @Timed
    public ResponseEntity<ContactUsDTO> getContactUs(@PathVariable Long id) {
        log.debug("REST request to get ContactUs : {}", id);
        Optional<ContactUsDTO> contactUsDTO = contactUsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(contactUsDTO);
    }

    /**
     * DELETE  /contactuses/:id : delete the "id" contactUs.
     *
     * @param id the id of the contactUsDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/contactuses/{id}")
    @Timed
    public ResponseEntity<Void> deleteContactUs(@PathVariable Long id) {
        log.debug("REST request to delete ContactUs : {}", id);
        contactUsService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
