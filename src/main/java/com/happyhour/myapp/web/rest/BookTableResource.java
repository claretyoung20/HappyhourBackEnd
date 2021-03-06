package com.happyhour.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.happyhour.myapp.service.BookTableService;
import com.happyhour.myapp.service.ReservationService;
import com.happyhour.myapp.service.dto.ReservationDTO;
import com.happyhour.myapp.web.rest.errors.BadRequestAlertException;
import com.happyhour.myapp.web.rest.util.HeaderUtil;
import com.happyhour.myapp.web.rest.util.PaginationUtil;
import com.happyhour.myapp.service.dto.BookTableDTO;
import io.github.jhipster.web.util.ResponseUtil;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing BookTable.
 */
@RestController
@RequestMapping("/api")
public class BookTableResource {

    @Autowired
    private ReservationService reservationService;
    private final Logger log = LoggerFactory.getLogger(BookTableResource.class);

    private static final String ENTITY_NAME = "bookTable";

    private final BookTableService bookTableService;

    public BookTableResource(BookTableService bookTableService) {
        this.bookTableService = bookTableService;
    }

    /**
     * POST  /book-tables : Create a new bookTable.
     *
     * @param bookTableDTO the bookTableDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new bookTableDTO, or with status 400 (Bad Request) if the bookTable has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/book-tables")
    @Timed
    public ResponseEntity<BookTableDTO> createBookTable(@Valid @RequestBody BookTableDTO bookTableDTO) throws URISyntaxException {
        log.debug("REST request to save BookTable : {}", bookTableDTO);
        if (bookTableDTO.getId() != null) {
            throw new BadRequestAlertException("A new bookTable cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BookTableDTO result = bookTableService.save(bookTableDTO);
        return ResponseEntity.created(new URI("/api/book-tables/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /book-tables : Updates an existing bookTable.
     *
     * @param bookTableDTO the bookTableDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated bookTableDTO,
     * or with status 400 (Bad Request) if the bookTableDTO is not valid,
     * or with status 500 (Internal Server Error) if the bookTableDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/book-tables")
    @Timed
    public ResponseEntity<BookTableDTO> updateBookTable(@Valid @RequestBody BookTableDTO bookTableDTO) throws URISyntaxException {
        log.debug("REST request to update BookTable : {}", bookTableDTO);
        if (bookTableDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        BookTableDTO result = bookTableService.save(bookTableDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, bookTableDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /book-tables : get all the bookTables.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of bookTables in body
     */
    @GetMapping("/book-tables")
    @Timed
    public ResponseEntity<Page<BookTableDTO>> getAllBookTables(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of BookTables");
        Page<BookTableDTO> page = bookTableService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/book-tables");
        return new ResponseEntity<>(page, headers, HttpStatus.OK);
    }

    /**
     * GET  /book-tables/:id : get the "id" bookTable.
     *
     * @param id the id of the bookTableDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the bookTableDTO, or with status 404 (Not Found)
     */
    @GetMapping("/book-tables/{id}")
    @Timed
    public ResponseEntity<BookTableDTO> getBookTable(@PathVariable Long id) {
        log.debug("REST request to get BookTable : {}", id);
        Optional<BookTableDTO> bookTableDTO = bookTableService.findOne(id);
        return ResponseUtil.wrapOrNotFound(bookTableDTO);
    }

    /**
     * DELETE  /book-tables/:id : delete the "id" bookTable.
     *
     * @param id the id of the bookTableDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/book-tables/{id}")
    @Timed
    public ResponseEntity<Void> deleteBookTable(@PathVariable Long id) {
        log.debug("REST request to delete BookTable : {}", id);
        bookTableService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    @GetMapping("/book-tables/avaliable")
    @Timed
    public ResponseEntity<List<BookTableDTO>> getBookTableAvailable(Long persons, String period, String reserveDate, Pageable pageable) {

//        Reservation reservation = new Reservation();
//        reservation.setReserverDate(reserveDate);

        LocalDate localDate = LocalDate.parse(reserveDate);
        // table to return
        Page<BookTableDTO> page = bookTableService.findAll(pageable);
        // get all reservation with DATE and PERIOD
        List<ReservationDTO> reservationDTOS = reservationService.findAllByPeriodAndReserverDate(period, localDate);

        // if reservation is null, return all table === END
        if(reservationDTOS.size() == 0){
            page = bookTableService.findAllByPersons(persons, pageable);
        }
        // if reservation is not null, move to step 2
        // step 2
        BookTableDTO bookTableDTO = null;
//        BookTableDTO bookTableDTO = new BookTableDTO();
        if(!reservationDTOS.isEmpty()) {
            for (ReservationDTO reservationDTO: reservationDTOS) {
                bookTableDTO = bookTableService.findByIdAndPersons(reservationDTO.getBookTableId(), persons);
                if(bookTableDTO != null) {
                    if(bookTableDTO.getId() != 0){
                        break;
                    }
                }
            }
        }
        // get All table except ABLE TT and where person = PERSON
        if(bookTableDTO != null ) {
            page = bookTableService.findAllByIdIsNotAndPersons(bookTableDTO.getId(), persons, pageable);
        }
        else if(bookTableDTO == null){
            page = bookTableService.findAllByPersons(persons, pageable);
        }

        log.debug("REST request to get a page of BookTables");
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/book-tables");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    @GetMapping("/book-tables/isAvailable")
    @Timed
    public ResponseEntity<List<BookTableDTO>> getAllAvailableBookTables(Pageable pageable) {
        log.debug("REST request to get a page of available BookTables");
        Page<BookTableDTO> page = bookTableService.availableTable(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/book-tables");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
