package com.lc.system.repository;

import com.lc.system.entity.Menu;

import java.util.List;

public interface MenuDao {

    List<Menu> queryMenu(Menu menu);

    void addMenu(Menu menu);

    void updateMenu(Menu menu);

    void deleteMenu(Long id);

}
