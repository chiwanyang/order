package com.zuzhi.order.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.zuzhi.order.domain.UserMenuRel;
import com.zuzhi.order.service.UserMenuRelService;
import com.zuzhi.order.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing UserMenuRel.
 */
@RestController
@RequestMapping("/api")
public class UserMenuRelResource {

    private final Logger log = LoggerFactory.getLogger(UserMenuRelResource.class);

    private static final String ENTITY_NAME = "userMenuRel";

    private final UserMenuRelService userMenuRelService;

    public UserMenuRelResource(UserMenuRelService userMenuRelService) {
        this.userMenuRelService = userMenuRelService;
    }

    /**
     * POST  /user-menu-rels : Create a new userMenuRel.
     *
     * @param userMenuRel the userMenuRel to create
     * @return the ResponseEntity with status 201 (Created) and with body the new userMenuRel, or with status 400 (Bad Request) if the userMenuRel has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/user-menu-rels")
    @Timed
    public ResponseEntity<UserMenuRel> createUserMenuRel(@Valid @RequestBody UserMenuRel userMenuRel) throws URISyntaxException {
        log.debug("REST request to save UserMenuRel : {}", userMenuRel);
        if (userMenuRel.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new userMenuRel cannot already have an ID")).body(null);
        }
        UserMenuRel result = userMenuRelService.save(userMenuRel);
        return ResponseEntity.created(new URI("/api/user-menu-rels/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /user-menu-rels : Updates an existing userMenuRel.
     *
     * @param userMenuRel the userMenuRel to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated userMenuRel,
     * or with status 400 (Bad Request) if the userMenuRel is not valid,
     * or with status 500 (Internal Server Error) if the userMenuRel couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/user-menu-rels")
    @Timed
    public ResponseEntity<UserMenuRel> updateUserMenuRel(@Valid @RequestBody UserMenuRel userMenuRel) throws URISyntaxException {
        log.debug("REST request to update UserMenuRel : {}", userMenuRel);
        if (userMenuRel.getId() == null) {
            return createUserMenuRel(userMenuRel);
        }
        UserMenuRel result = userMenuRelService.save(userMenuRel);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, userMenuRel.getId().toString()))
            .body(result);
    }

    /**
     * GET  /user-menu-rels : get all the userMenuRels.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of userMenuRels in body
     */
    @GetMapping("/user-menu-rels")
    @Timed
    public List<UserMenuRel> getAllUserMenuRels() {
        log.debug("REST request to get all UserMenuRels");
        return userMenuRelService.findAll();
    }

    /**
     * GET  /user-menu-rels/:id : get the "id" userMenuRel.
     *
     * @param id the id of the userMenuRel to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the userMenuRel, or with status 404 (Not Found)
     */
    @GetMapping("/user-menu-rels/{id}")
    @Timed
    public ResponseEntity<UserMenuRel> getUserMenuRel(@PathVariable Long id) {
        log.debug("REST request to get UserMenuRel : {}", id);
        UserMenuRel userMenuRel = userMenuRelService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(userMenuRel));
    }

    /**
     * DELETE  /user-menu-rels/:id : delete the "id" userMenuRel.
     *
     * @param id the id of the userMenuRel to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/user-menu-rels/{id}")
    @Timed
    public ResponseEntity<Void> deleteUserMenuRel(@PathVariable Long id) {
        log.debug("REST request to delete UserMenuRel : {}", id);
        userMenuRelService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
