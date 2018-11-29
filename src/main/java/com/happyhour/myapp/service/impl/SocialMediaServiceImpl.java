package com.happyhour.myapp.service.impl;

import com.happyhour.myapp.service.SocialMediaService;
import com.happyhour.myapp.domain.SocialMedia;
import com.happyhour.myapp.repository.SocialMediaRepository;
import com.happyhour.myapp.service.dto.SocialMediaDTO;
import com.happyhour.myapp.service.mapper.SocialMediaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing SocialMedia.
 */
@Service
@Transactional
public class SocialMediaServiceImpl implements SocialMediaService {

    private final Logger log = LoggerFactory.getLogger(SocialMediaServiceImpl.class);

    private final SocialMediaRepository socialMediaRepository;

    private final SocialMediaMapper socialMediaMapper;

    public SocialMediaServiceImpl(SocialMediaRepository socialMediaRepository, SocialMediaMapper socialMediaMapper) {
        this.socialMediaRepository = socialMediaRepository;
        this.socialMediaMapper = socialMediaMapper;
    }

    /**
     * Save a socialMedia.
     *
     * @param socialMediaDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public SocialMediaDTO save(SocialMediaDTO socialMediaDTO) {
        log.debug("Request to save SocialMedia : {}", socialMediaDTO);
        SocialMedia socialMedia = socialMediaMapper.toEntity(socialMediaDTO);
        socialMedia = socialMediaRepository.save(socialMedia);
        return socialMediaMapper.toDto(socialMedia);
    }

    /**
     * Get all the socialMedias.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<SocialMediaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all SocialMedias");
        return socialMediaRepository.findAll(pageable)
            .map(socialMediaMapper::toDto);
    }


    /**
     * Get one socialMedia by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<SocialMediaDTO> findOne(Long id) {
        log.debug("Request to get SocialMedia : {}", id);
        return socialMediaRepository.findById(id)
            .map(socialMediaMapper::toDto);
    }

    /**
     * Delete the socialMedia by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete SocialMedia : {}", id);
        socialMediaRepository.deleteById(id);
    }
}
