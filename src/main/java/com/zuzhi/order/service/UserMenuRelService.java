package com.zuzhi.order.service;

import com.zuzhi.order.domain.UserMenuRel;
import com.zuzhi.order.repository.UserMenuRelRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service Implementation for managing UserMenuRel.
 */
@Service
@Transactional
public class UserMenuRelService {

    private final Logger log = LoggerFactory.getLogger(UserMenuRelService.class);

    private final UserMenuRelRepository userMenuRelRepository;

    public UserMenuRelService(UserMenuRelRepository userMenuRelRepository) {
        this.userMenuRelRepository = userMenuRelRepository;
    }

    /**
     * Save a userMenuRel.
     *
     * @param userMenuRel the entity to save
     * @return the persisted entity
     */
    public UserMenuRel save(UserMenuRel userMenuRel) {
        log.debug("Request to save UserMenuRel : {}", userMenuRel);
        return userMenuRelRepository.save(userMenuRel);
    }

    /**
     *  Get all the userMenuRels.
     *
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<UserMenuRel> findAll() {
        log.debug("Request to get all UserMenuRels");
        return userMenuRelRepository.findAll();
    }

    /**
     *  Get one userMenuRel by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public UserMenuRel findOne(Long id) {
        log.debug("Request to get UserMenuRel : {}", id);
        return userMenuRelRepository.findOne(id);
    }

    /**
     *  Delete the  userMenuRel by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete UserMenuRel : {}", id);
        userMenuRelRepository.delete(id);
    }
}
