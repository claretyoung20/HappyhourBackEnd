package com.happyhour.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.happyhour.myapp.service.TableTypeService;
import com.happyhour.myapp.web.rest.errors.BadRequestAlertException;
import com.happyhour.myapp.web.rest.util.HeaderUtil;
import com.happyhour.myapp.web.rest.util.PaginationUtil;
import com.happyhour.myapp.service.dto.TableTypeDTO;
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
 * REST controller for managing TableType.
 */
@RestController
@RequestMapping("/api")
public class TableTypeResource {

    private final Logger log = LoggerFactory.getLogger(TableTypeResource.class);

    private static final String ENTITY_NAME = "tableType";

    private final TableTypeService tableTypeService;

    public TableTypeResource(TableTypeService tableTypeService) {
        this.tableTypeService = tableTypeService;
    }

    /**
     * POST  /table-types : Create a new tableType.
     *
     * @param tableTypeDTO the tableTypeDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new tableTypeDTO, or with status 400 (Bad Request) if the tableType has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/table-types")
    @Timed
    public ResponseEntity<TableTypeDTO> createTableType(@Valid @RequestBody TableTypeDTO tableTypeDTO) throws URISyntaxException {
        log.debug("REST request to save TableType : {}", tableTypeDTO);
        if (tableTypeDTO.getId() != null) {
            throw new BadRequestAlertException("A new tableType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TableTypeDTO result = tableTypeService.save(tableTypeDTO);
        return ResponseEntity.created(new URI("/api/table-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /table-types : Updates an existing tableType.
     *
     * @param tableTypeDTO the tableTypeDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated tableTypeDTO,
     * or with status 400 (Bad Request) if the tableTypeDTO is not valid,
     * or with status 500 (Internal Server Error) if the tableTypeDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/table-types")
    @Timed
    public ResponseEntity<TableTypeDTO> updateTableType(@Valid @RequestBody TableTypeDTO tableTypeDTO) throws URISyntaxException {
        log.debug("REST request to update TableType : {}", tableTypeDTO);
        if (tableTypeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TableTypeDTO result = tableTypeService.save(tableTypeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, tableTypeDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /table-types : get all the tableTypes.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of tableTypes in body
     */
    @GetMapping("/table-types")
    @Timed
    public ResponseEntity<List<TableTypeDTO>> getAllTableTypes(Pageable pageable) {
        log.debug("REST request to get a page of TableTypes");
        Page<TableTypeDTO> page = tableTypeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/table-types");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /table-types/:id : get the "id" tableType.
     *
     * @param id the id of the tableTypeDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the tableTypeDTO, or with status 404 (Not Found)
     */
    @GetMapping("/table-types/{id}")
    @Timed
    public ResponseEntity<TableTypeDTO> getTableType(@PathVariable Long id) {
        log.debug("REST request to get TableType : {}", id);
        Optional<TableTypeDTO> tableTypeDTO = tableTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(tableTypeDTO);
    }

    /**
     * DELETE  /table-types/:id : delete the "id" tableType.
     *
     * @param id the id of the tableTypeDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/table-types/{id}")
    @Timed
    public ResponseEntity<Void> deleteTableType(@PathVariable Long id) {
        log.debug("REST request to delete TableType : {}", id);
        tableTypeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
