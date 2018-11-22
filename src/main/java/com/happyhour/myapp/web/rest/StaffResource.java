package com.happyhour.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.happyhour.myapp.domain.User;
import com.happyhour.myapp.repository.UserRepository;
import com.happyhour.myapp.service.MailService;
import com.happyhour.myapp.service.StaffService;
import com.happyhour.myapp.service.UserService;
import com.happyhour.myapp.service.dto.UserDTO;
import com.happyhour.myapp.web.rest.errors.BadRequestAlertException;
import com.happyhour.myapp.web.rest.errors.EmailAlreadyUsedException;
import com.happyhour.myapp.web.rest.errors.InvalidPasswordException;
import com.happyhour.myapp.web.rest.errors.LoginAlreadyUsedException;
import com.happyhour.myapp.web.rest.util.HeaderUtil;
import com.happyhour.myapp.web.rest.util.PaginationUtil;
import com.happyhour.myapp.service.dto.StaffDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Staff.
 */
@RestController
@RequestMapping("/api")
public class StaffResource {

    private final Logger log = LoggerFactory.getLogger(StaffResource.class);

    private static final String ENTITY_NAME = "staff";

    private StaffService staffService;

    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private  MailService mailService;

    @Autowired
    private PasswordEncoder passwordEncoder;


    public StaffResource(StaffService staffService, UserService userService) {
        this.staffService = staffService;
        this.userService = userService;
    }

    /**
     * POST  /staff : Create a new staff.
     *
     * @param userDTO the staffDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new staffDTO, or with status 400 (Bad Request) if the staff has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/staff")
    @Timed
    public ResponseEntity<StaffDTO> createStaff(@Valid @RequestBody UserDTO userDTO) throws URISyntaxException {
        log.debug("REST request to save Staff : {}", userDTO);
        if (userDTO.getId() != null) {
            throw new BadRequestAlertException("A new staff cannot already have an ID", ENTITY_NAME, "idexists");
        }
        // create new user and return the id, then use it to create new staff
        User newUser = userService.createUser(userDTO);


        //create new Staff
        StaffDTO staffDTO = new StaffDTO();

        staffDTO.setStaffCode("STAFF0"+ newUser.getId());
        staffDTO.setUserId(newUser.getId());
        staffDTO.setUserLogin(newUser.getLogin());
        staffDTO.setRestaurantId(Long.parseLong("1"));

        StaffDTO result = staffService.save(staffDTO);

        // send email to staff
        mailService.sendCreationEmail(newUser);
        log.debug("REST request to save Staff : {}", staffDTO);
        if (staffDTO.getId() != null) {
            throw new BadRequestAlertException("A new staff cannot already have an ID", ENTITY_NAME, "idexists");
        }
        return ResponseEntity.created(new URI("/api/staff/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /staff : Updates an existing staff.
     *
     * @param userDTO  the staffDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated staffDTO,
     * or with status 400 (Bad Request) if the staffDTO is not valid,
     * or with status 500 (Internal Server Error) if the staffDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/staff")
    @Timed
    public ResponseEntity<StaffDTO> updateStaff(@Valid @RequestBody UserDTO userDTO) throws URISyntaxException {
        // update user
        // update user and return the id, then use it to update staff
        log.debug("REST request to update User : {}", userDTO);
        Optional<User> existingUser = userRepository.findOneByEmailIgnoreCase(userDTO.getEmail());
        if (existingUser.isPresent() && (!existingUser.get().getId().equals(userDTO.getId()))) {
            throw new EmailAlreadyUsedException();
        }
        existingUser = userRepository.findOneByLogin(userDTO.getLogin().toLowerCase());
        if (existingUser.isPresent() && (!existingUser.get().getId().equals(userDTO.getId()))) {
            throw new LoginAlreadyUsedException();
        }
        Optional<UserDTO> updatedUser = userService.updateUser(userDTO);

        Optional<StaffDTO> staffByUserId = staffService.findByUserId(updatedUser.get().getId());
        StaffDTO staffDTO = new StaffDTO();

        staffDTO.setUserId(staffByUserId.get().getUserId());
        staffDTO.setId(staffByUserId.get().getId());
        staffDTO.setStaffCode(staffByUserId.get().getStaffCode());
        staffDTO.setRestaurantId(staffByUserId.get().getRestaurantId());

        log.debug("REST request to update Staff : {}", staffDTO);
        if (staffDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        StaffDTO result = staffService.save(staffDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, staffDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /staff : get all the staff.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of staff in body
     */
    @GetMapping("/staff")
    @Timed
    public ResponseEntity<List<StaffDTO>> getAllStaff(Pageable pageable) {
        log.debug("REST request to get a page of Staff");
        Page<StaffDTO> page = staffService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/staff");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    @GetMapping("staff/account")
    @Timed
    public ResponseEntity<List<UserDTO>> getAllUsers(Pageable pageable) {
        final Page<UserDTO> page = userService.getAllManagedUsers(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/staff/account");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /staff/:id : get the "id" staff.
     *
     * @param id the id of the staffDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the staffDTO, or with status 404 (Not Found)
     */
    @GetMapping("/staff/{id}")
    @Timed
    public ResponseEntity<StaffDTO> getStaff(@PathVariable Long id) {
        log.debug("REST request to get Staff : {}", id);
        Optional<StaffDTO> staffDTO = staffService.findOne(id);
        return ResponseUtil.wrapOrNotFound(staffDTO);
    }

    /**
     * DELETE  /staff/:id : delete the "id" staff.
     *
     * @param id the id of the staffDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/staff/{id}")
    @Timed
    public ResponseEntity<Void> deleteStaff(@PathVariable Long id) {
        log.debug("REST request to delete Staff : {}", id);
        // delete user
       // userService.deleteUser("");
        // delete staff
        staffService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    // login
    @GetMapping("/staff/user")
    @Timed
    public ResponseEntity<UserDTO> getStafflogin(String login, String password) {
        // get user by login name
        Optional<UserDTO> user = userService.findOneByLogin(login);

        // check if the password is correct
        String currentEncryptedPassword = user.get().getPassword();
        if (!passwordEncoder.matches(password, currentEncryptedPassword)) {
            throw new InvalidPasswordException();
        }

        return ResponseUtil.wrapOrNotFound(user);
    }

    @GetMapping("/staff/user-id/{id}")
    @Timed
    public ResponseEntity<StaffDTO> getStaffByUserId(@PathVariable Long id) {
        log.debug("REST request to get Staff by user id: {}", id);
        Optional<StaffDTO> staffDTO = staffService.findByUserId(id);
        return ResponseUtil.wrapOrNotFound(staffDTO);
    }

}
