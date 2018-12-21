package com.happyhour.myapp.service.impl;

import com.happyhour.myapp.service.ProductService;
import com.happyhour.myapp.domain.Product;
import com.happyhour.myapp.repository.ProductRepository;
import com.happyhour.myapp.service.dto.ProductDTO;
import com.happyhour.myapp.service.mapper.ProductMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing Product.
 */
@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    private final Logger log = LoggerFactory.getLogger(ProductServiceImpl.class);

    private final ProductRepository productRepository;

    private final ProductMapper productMapper;

    public ProductServiceImpl(ProductRepository productRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    /**
     * Save a product.
     *
     * @param productDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ProductDTO save(ProductDTO productDTO) {
        log.debug("Request to save Product : {}", productDTO);
        Product product = productMapper.toEntity(productDTO);
        product = productRepository.save(product);
        return productMapper.toDto(product);
    }

    /**
     * Get all the products.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ProductDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Products");
        return productRepository.findAll(pageable)
            .map(productMapper::toDto);
    }


    /**
     * Get one product by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ProductDTO> findOne(Long id) {
        log.debug("Request to get Product : {}", id);
        return productRepository.findById(id)
            .map(productMapper::toDto);
    }

    /**
     * Delete the product by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Product : {}", id);
        productRepository.deleteById(id);
    }

    @Override
    public Page<ProductDTO> getAllByCategoryId(long id, Pageable pageable) {
        log.debug("Request to get all Products by Category Id {} ", id);
        return productRepository.findAllByCategoryIdAndIsAvailableTrue(id, pageable)
            .map(productMapper::toDto);
    }

    @Override
    public ProductDTO findById(long id) {
        return productMapper.toDto(productRepository.findById(id));
    }

    @Override
    public Page<ProductDTO> findAllByShowOnHomepageTrue(Pageable pageable) {
        log.debug("Request to get all Products show on homepage {} ");
        return productRepository.findAllByShowOnHomepageTrue(pageable)
            .map(productMapper::toDto);
    }

    @Override
    public Page<ProductDTO> isAvailable(Pageable pageable) {
        log.debug("Request to get all Products show on homepage {} ");
        return productRepository.findAllByIsAvailableTrue(pageable)
            .map(productMapper::toDto);
    }

    @Override
    public Page<ProductDTO> isNotAvailable(Pageable pageable) {
        log.debug("Request to get all Products show on homepage {} ");
        return productRepository.findAllByIsAvailableFalse(pageable)
            .map(productMapper::toDto);
    }


}
