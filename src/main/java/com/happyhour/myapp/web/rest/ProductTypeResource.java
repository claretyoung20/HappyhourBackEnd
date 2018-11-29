package com.happyhour.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.happyhour.myapp.service.ProductTypeService;
import com.happyhour.myapp.web.rest.errors.BadRequestAlertException;
import com.happyhour.myapp.web.rest.util.HeaderUtil;
import com.happyhour.myapp.web.rest.util.PaginationUtil;
import com.happyhour.myapp.service.dto.ProductTypeDTO;
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
 * REST controller for managing ProductType.
 */
@RestController
@RequestMapping("/api")
public class ProductTypeResource {

    private final Logger log = LoggerFactory.getLogger(ProductTypeResource.class);

    private static final String ENTITY_NAME = "productType";

    private final ProductTypeService productTypeService;

    public ProductTypeResource(ProductTypeService productTypeService) {
        this.productTypeService = productTypeService;
    }

    /**
     * POST  /product-types : Create a new productType.
     *
     * @param productTypeDTO the productTypeDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new productTypeDTO, or with status 400 (Bad Request) if the productType has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/product-types")
    @Timed
    public ResponseEntity<ProductTypeDTO> createProductType(@Valid @RequestBody ProductTypeDTO productTypeDTO) throws URISyntaxException {
        log.debug("REST request to save ProductType : {}", productTypeDTO);
        if (productTypeDTO.getId() != null) {
            throw new BadRequestAlertException("A new productType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProductTypeDTO result = productTypeService.save(productTypeDTO);
        return ResponseEntity.created(new URI("/api/product-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /product-types : Updates an existing productType.
     *
     * @param productTypeDTO the productTypeDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated productTypeDTO,
     * or with status 400 (Bad Request) if the productTypeDTO is not valid,
     * or with status 500 (Internal Server Error) if the productTypeDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/product-types")
    @Timed
    public ResponseEntity<ProductTypeDTO> updateProductType(@Valid @RequestBody ProductTypeDTO productTypeDTO) throws URISyntaxException {
        log.debug("REST request to update ProductType : {}", productTypeDTO);
        if (productTypeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ProductTypeDTO result = productTypeService.save(productTypeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, productTypeDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /product-types : get all the productTypes.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of productTypes in body
     */
    @GetMapping("/product-types")
    @Timed
    public ResponseEntity<List<ProductTypeDTO>> getAllProductTypes(Pageable pageable) {
        log.debug("REST request to get a page of ProductTypes");
        Page<ProductTypeDTO> page = productTypeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/product-types");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /product-types/:id : get the "id" productType.
     *
     * @param id the id of the productTypeDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the productTypeDTO, or with status 404 (Not Found)
     */
    @GetMapping("/product-types/{id}")
    @Timed
    public ResponseEntity<ProductTypeDTO> getProductType(@PathVariable Long id) {
        log.debug("REST request to get ProductType : {}", id);
        Optional<ProductTypeDTO> productTypeDTO = productTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(productTypeDTO);
    }

    /**
     * DELETE  /product-types/:id : delete the "id" productType.
     *
     * @param id the id of the productTypeDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/product-types/{id}")
    @Timed
    public ResponseEntity<Void> deleteProductType(@PathVariable Long id) {
        log.debug("REST request to delete ProductType : {}", id);
        productTypeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
