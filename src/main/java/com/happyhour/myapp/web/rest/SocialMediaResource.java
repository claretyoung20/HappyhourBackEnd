package com.happyhour.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.happyhour.myapp.service.SocialMediaService;
import com.happyhour.myapp.web.rest.errors.BadRequestAlertException;
import com.happyhour.myapp.web.rest.util.HeaderUtil;
import com.happyhour.myapp.web.rest.util.PaginationUtil;
import com.happyhour.myapp.service.dto.SocialMediaDTO;
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
 * REST controller for managing SocialMedia.
 */
@RestController
@RequestMapping("/api")
public class SocialMediaResource {

    private final Logger log = LoggerFactory.getLogger(SocialMediaResource.class);

    private static final String ENTITY_NAME = "socialMedia";

    private final SocialMediaService socialMediaService;

    public SocialMediaResource(SocialMediaService socialMediaService) {
        this.socialMediaService = socialMediaService;
    }

    /**
     * POST  /social-medias : Create a new socialMedia.
     *
     * @param socialMediaDTO the socialMediaDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new socialMediaDTO, or with status 400 (Bad Request) if the socialMedia has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/social-medias")
    @Timed
    public ResponseEntity<SocialMediaDTO> createSocialMedia(@Valid @RequestBody SocialMediaDTO socialMediaDTO) throws URISyntaxException {
        log.debug("REST request to save SocialMedia : {}", socialMediaDTO);
        if (socialMediaDTO.getId() != null) {
            throw new BadRequestAlertException("A new socialMedia cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SocialMediaDTO result = socialMediaService.save(socialMediaDTO);
        return ResponseEntity.created(new URI("/api/social-medias/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /social-medias : Updates an existing socialMedia.
     *
     * @param socialMediaDTO the socialMediaDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated socialMediaDTO,
     * or with status 400 (Bad Request) if the socialMediaDTO is not valid,
     * or with status 500 (Internal Server Error) if the socialMediaDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/social-medias")
    @Timed
    public ResponseEntity<SocialMediaDTO> updateSocialMedia(@Valid @RequestBody SocialMediaDTO socialMediaDTO) throws URISyntaxException {
        log.debug("REST request to update SocialMedia : {}", socialMediaDTO);
        if (socialMediaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SocialMediaDTO result = socialMediaService.save(socialMediaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, socialMediaDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /social-medias : get all the socialMedias.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of socialMedias in body
     */
    @GetMapping("/social-medias")
    @Timed
    public ResponseEntity<List<SocialMediaDTO>> getAllSocialMedias(Pageable pageable) {
        log.debug("REST request to get a page of SocialMedias");
        Page<SocialMediaDTO> page = socialMediaService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/social-medias");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /social-medias/:id : get the "id" socialMedia.
     *
     * @param id the id of the socialMediaDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the socialMediaDTO, or with status 404 (Not Found)
     */
    @GetMapping("/social-medias/{id}")
    @Timed
    public ResponseEntity<SocialMediaDTO> getSocialMedia(@PathVariable Long id) {
        log.debug("REST request to get SocialMedia : {}", id);
        Optional<SocialMediaDTO> socialMediaDTO = socialMediaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(socialMediaDTO);
    }

    /**
     * DELETE  /social-medias/:id : delete the "id" socialMedia.
     *
     * @param id the id of the socialMediaDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/social-medias/{id}")
    @Timed
    public ResponseEntity<Void> deleteSocialMedia(@PathVariable Long id) {
        log.debug("REST request to delete SocialMedia : {}", id);
        socialMediaService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
