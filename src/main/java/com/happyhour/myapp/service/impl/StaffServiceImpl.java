package com.happyhour.myapp.service.impl;

import com.happyhour.myapp.domain.User;
import com.happyhour.myapp.service.StaffService;
import com.happyhour.myapp.domain.Staff;
import com.happyhour.myapp.repository.StaffRepository;
import com.happyhour.myapp.service.UserService;
import com.happyhour.myapp.service.dto.StaffDTO;
import com.happyhour.myapp.service.dto.UserDTO;
import com.happyhour.myapp.service.mapper.StaffMapper;
import com.happyhour.myapp.service.mapper.UserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing Staff.
 */
@Service
@Transactional
public class StaffServiceImpl implements StaffService {

    private final Logger log = LoggerFactory.getLogger(StaffServiceImpl.class);

    private final StaffRepository staffRepository;

    private final StaffMapper staffMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserService userService;

    public StaffServiceImpl(StaffRepository staffRepository, StaffMapper staffMapper) {
        this.staffRepository = staffRepository;
        this.staffMapper = staffMapper;
    }

    /**
     * Save a staff.
     *
     * @param staffDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public StaffDTO save(StaffDTO staffDTO) {
        log.debug("Request to save Staff : {}", staffDTO);
        Staff staff = staffMapper.toEntity(staffDTO);
        staff = staffRepository.save(staff);
        return staffMapper.toDto(staff);
    }

    /**
     * Get all the staff.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<StaffDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Staff");
        return staffRepository.findAll(pageable)
            .map(staffMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UserDTO> findAllStaffUser(Pageable pageable) {
        log.debug("Request to get all Staff");


        List<StaffDTO> staffDTOList = staffMapper.toDto(staffRepository.findAll());
        List<UserDTO> userList = new ArrayList<>();

        for ( StaffDTO staffDTO: staffDTOList) {
            Optional<UserDTO> user = userService.findById(staffDTO.getUserId());

            user.ifPresent(userList::add);
        }
        Page<UserDTO> page = new PageImpl<>(userList);
        return page;
    }

    /**
     * Get one staff by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<StaffDTO> findOne(Long id) {
        log.debug("Request to get Staff : {}", id);
        return staffRepository.findById(id)
            .map(staffMapper::toDto);
    }

    @Override
    public Optional<StaffDTO> findByUserId(Long id) {
        log.debug("Request to get Staff by user id : {}", id);
        return staffRepository.findByUserId(id)
            .map(staffMapper::toDto);
    }

    /**
     * Delete the staff by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Staff : {}", id);
        staffRepository.deleteById(id);
    }

    @Override
    public void deleteByUserId(Long id) {
        log.debug("Request to delete Staff : {}", id);
        staffRepository.deleteByUserId(id);
    }
}
