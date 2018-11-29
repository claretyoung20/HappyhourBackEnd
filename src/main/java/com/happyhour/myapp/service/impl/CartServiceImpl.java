package com.happyhour.myapp.service.impl;

import com.happyhour.myapp.service.CartService;
import com.happyhour.myapp.domain.Cart;
import com.happyhour.myapp.repository.CartRepository;
import com.happyhour.myapp.service.dto.CartDTO;
import com.happyhour.myapp.service.mapper.CartMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing Cart.
 */
@Service
@Transactional
public class CartServiceImpl implements CartService {

    private final Logger log = LoggerFactory.getLogger(CartServiceImpl.class);

    private final CartRepository cartRepository;

    private final CartMapper cartMapper;

    public CartServiceImpl(CartRepository cartRepository, CartMapper cartMapper) {
        this.cartRepository = cartRepository;
        this.cartMapper = cartMapper;
    }

    /**
     * Save a cart.
     *
     * @param cartDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public CartDTO save(CartDTO cartDTO) {
        log.debug("Request to save Cart : {}", cartDTO);
        Cart cart = cartMapper.toEntity(cartDTO);
        cart = cartRepository.save(cart);
        return cartMapper.toDto(cart);
    }

    /**
     * Get all the carts.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<CartDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Carts");
        return cartRepository.findAll(pageable)
            .map(cartMapper::toDto);
    }


    /**
     * Get one cart by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<CartDTO> findOne(Long id) {
        log.debug("Request to get Cart : {}", id);
        return cartRepository.findById(id)
            .map(cartMapper::toDto);
    }

    /**
     * Delete the cart by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Cart : {}", id);
        cartRepository.deleteById(id);
    }

    @Override
    public Optional<CartDTO> getByProductIdAndCustomerId(long productId, long customerId) {
        log.debug("Request to get Cart by productId and customerId: {}", productId, customerId);
        return cartRepository.getByProductIdAndCustomerId(productId, customerId)
            .map(cartMapper::toDto);
    }

    @Override
    public Page<CartDTO> getAllByCustomerId(long id, Pageable pageable) {
        log.debug("Request to get Cart by customerId: {}", id);
        return cartRepository.findAllByCustomerId(id, pageable)
            .map(cartMapper::toDto);
    }

    @Override
    public List<CartDTO> findAllByCustomerId(long id) {

        log.debug("Request to get Cart by customerId: {}", id);
       return cartMapper.toDto(cartRepository.findAllByCustomerId(id));
    }


}
