package com.buyme.admin.menu;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.buyme.common.entity.menu.Menu;
import com.buyme.common.entity.menu.MenuType;

public interface MenuRepository extends JpaRepository<Menu, Integer> {

    // list menu items sorted by menu type, then by position
    public List<Menu> findAllByOrderByTypeAscPositionAsc();

    public Long countByType(MenuType type);

    @Query("UPDATE Menu m SET m.enabled = ?2 WHERE m.id = ?1")
    @Modifying
    public void updateEnabledStatus(Integer id, boolean enabled);

    // list menu items of the same type, ordered by ascending position
    public List<Menu> findByTypeOrderByPositionAsc(MenuType type);

    @Query("SELECT COUNT(m.id) FROM Menu m WHERE m.enabled = true")
    Long countEnabledMenu();

    @Query("SELECT COUNT(m.id) FROM Menu m WHERE m.enabled = false")
    Long countDisabledMenu();

    @Query("SELECT COUNT(m.id) FROM Menu m WHERE m.type = 0")
    Long countHeaderMenu();

    @Query("SELECT COUNT(m.id) FROM Menu m WHERE m.type = 1")
    Long countFooterMenu();
}

