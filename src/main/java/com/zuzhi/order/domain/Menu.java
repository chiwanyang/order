package com.zuzhi.order.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A Menu.
 */
@Entity
@Table(name = "menu")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Menu implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final Integer normal = 0;
    public static final Integer special = 1;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "price", nullable = false)
    private Integer price;

    /**
     * type==1 特殊类型，代表点餐，未指定具体类型
     * type==2 普通类型
     */
    @NotNull
    @Column(name = "type", nullable = false)
    private Integer type;

    @NotNull
    @Column(name = "gmt_create", nullable = false)
    private ZonedDateTime gmt_create;

    @NotNull
    @Column(name = "gmt_modified", nullable = false)
    private ZonedDateTime gmt_modified;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Menu name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPrice() {
        return price;
    }

    public Menu price(Integer price) {
        this.price = price;
        return this;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public ZonedDateTime getGmt_create() {
        return gmt_create;
    }

    public Menu gmt_create(ZonedDateTime gmt_create) {
        this.gmt_create = gmt_create;
        return this;
    }

    public void setGmt_create(ZonedDateTime gmt_create) {
        this.gmt_create = gmt_create;
    }

    public ZonedDateTime getGmt_modified() {
        return gmt_modified;
    }

    public Menu gmt_modified(ZonedDateTime gmt_modified) {
        this.gmt_modified = gmt_modified;
        return this;
    }

    public void setGmt_modified(ZonedDateTime gmt_modified) {
        this.gmt_modified = gmt_modified;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Menu menu = (Menu) o;
        if (menu.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), menu.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Menu{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", price=" + price +
            ", type=" + type +
            ", gmt_create=" + gmt_create +
            ", gmt_modified=" + gmt_modified +
            '}';
    }
}
