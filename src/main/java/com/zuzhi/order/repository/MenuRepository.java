package com.zuzhi.order.repository;

import com.zuzhi.order.domain.Menu;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.util.List;


/**
 * Spring Data JPA repository for the Menu entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MenuRepository extends JpaRepository<Menu,Long> {

    List<Menu> findByType(int type);
}
