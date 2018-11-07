package com.happyhour.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.happyhour.myapp.service.TimeManagerService;
import com.happyhour.myapp.web.rest.errors.BadRequestAlertException;
import com.happyhour.myapp.web.rest.util.HeaderUtil;
import com.happyhour.myapp.web.rest.util.PaginationUtil;
import com.happyhour.myapp.service.dto.TimeManagerDTO;
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
 * REST controller for managing TimeManager.
 */
@RestController
@RequestMapping("/api")
public class TimeManagerResource {

    private final Logger log = LoggerFactory.getLogger(TimeManagerResource.class);

    private static final String ENTITY_NAME = "timeManager";

    private final TimeManagerService timeManagerService;

    public TimeManagerResource(TimeManagerService timeManagerService) {
        this.timeManagerService = timeManagerService;
    }

    /**
     * POST  /time-managers : Create a new timeManager.
     *
     * @param timeManagerDTO the timeManagerDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new timeManagerDTO, or with status 400 (Bad Request) if the timeManager has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/time-managers")
    @Timed
    public ResponseEntity<TimeManagerDTO> createTimeManager(@Valid @RequestBody TimeManagerDTO timeManagerDTO) throws URISyntaxException {
        log.debug("REST request to save TimeManager : {}", timeManagerDTO);
        if (timeManagerDTO.getId() != null) {
            throw new BadRequestAlertException("A new timeManager cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TimeManagerDTO result = timeManagerService.save(timeManagerDTO);
        return ResponseEntity.created(new URI("/api/time-managers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /time-managers : Updates an existing timeManager.
     *
     * @param timeManagerDTO the timeManagerDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated timeManagerDTO,
     * or with status 400 (Bad Request) if the timeManagerDTO is not valid,
     * or with status 500 (Internal Server Error) if the timeManagerDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/time-managers")
    @Timed
    public ResponseEntity<TimeManagerDTO> updateTimeManager(@Valid @RequestBody TimeManagerDTO timeManagerDTO) throws URISyntaxException {
        log.debug("REST request to update TimeManager : {}", timeManagerDTO);
        if (timeManagerDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TimeManagerDTO result = timeManagerService.save(timeManagerDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, timeManagerDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /time-managers : get all the timeManagers.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of timeManagers in body
     */
    @GetMapping("/time-managers")
    @Timed
    public ResponseEntity<List<TimeManagerDTO>> getAllTimeManagers(Pageable pageable) {
        log.debug("REST request to get a page of TimeManagers");
        Page<TimeManagerDTO> page = timeManagerService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/time-managers");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /time-managers/:id : get the "id" timeManager.
     *
     * @param id the id of the timeManagerDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the timeManagerDTO, or with status 404 (Not Found)
     */
    @GetMapping("/time-managers/{id}")
    @Timed
    public ResponseEntity<TimeManagerDTO> getTimeManager(@PathVariable Long id) {
        log.debug("REST request to get TimeManager : {}", id);
        Optional<TimeManagerDTO> timeManagerDTO = timeManagerService.findOne(id);
        return ResponseUtil.wrapOrNotFound(timeManagerDTO);
    }

    /**
     * DELETE  /time-managers/:id : delete the "id" timeManager.
     *
     * @param id the id of the timeManagerDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/time-managers/{id}")
    @Timed
    public ResponseEntity<Void> deleteTimeManager(@PathVariable Long id) {
        log.debug("REST request to delete TimeManager : {}", id);
        timeManagerService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
