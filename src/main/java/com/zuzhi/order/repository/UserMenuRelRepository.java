package com.zuzhi.order.repository;

import com.zuzhi.order.domain.UserMenuRel;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the UserMenuRel entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UserMenuRelRepository extends JpaRepository<UserMenuRel,Long> {

}
