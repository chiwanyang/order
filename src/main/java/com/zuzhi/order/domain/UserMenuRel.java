package com.zuzhi.order.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A UserMenuRel.
 */
@Entity
@Table(name = "user_menu_rel")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class UserMenuRel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "user_id", nullable = false)
    private Long userId;

    @NotNull
    @Column(name = "menu_id", nullable = false)
    private Long menuId;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "menu_name")
    private String menuName;

    @NotNull
    @Column(name = "gmt_create", nullable = false)
    private ZonedDateTime gmtCreate;

    @NotNull
    @Column(name = "gmt_modified", nullable = false)
    private ZonedDateTime gmtModified;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public UserMenuRel userId(Long userId) {
        this.userId = userId;
        return this;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getMenuId() {
        return menuId;
    }

    public UserMenuRel menuId(Long menuId) {
        this.menuId = menuId;
        return this;
    }

    public void setMenuId(Long menuId) {
        this.menuId = menuId;
    }

    public String getUserName() {
        return userName;
    }

    public UserMenuRel userName(String userName) {
        this.userName = userName;
        return this;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMenuName() {
        return menuName;
    }

    public UserMenuRel menuName(String menuName) {
        this.menuName = menuName;
        return this;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public ZonedDateTime getGmtCreate() {
        return gmtCreate;
    }

    public UserMenuRel gmtCreate(ZonedDateTime gmtCreate) {
        this.gmtCreate = gmtCreate;
        return this;
    }

    public void setGmtCreate(ZonedDateTime gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public ZonedDateTime getGmtModified() {
        return gmtModified;
    }

    public UserMenuRel gmtModified(ZonedDateTime gmtModified) {
        this.gmtModified = gmtModified;
        return this;
    }

    public void setGmtModified(ZonedDateTime gmtModified) {
        this.gmtModified = gmtModified;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UserMenuRel userMenuRel = (UserMenuRel) o;
        if (userMenuRel.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), userMenuRel.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "UserMenuRel{" +
            "id=" + getId() +
            ", userId='" + getUserId() + "'" +
            ", menuId='" + getMenuId() + "'" +
            ", userName='" + getUserName() + "'" +
            ", menuName='" + getMenuName() + "'" +
            ", gmtCreate='" + getGmtCreate() + "'" +
            ", gmtModified='" + getGmtModified() + "'" +
            "}";
    }
}
