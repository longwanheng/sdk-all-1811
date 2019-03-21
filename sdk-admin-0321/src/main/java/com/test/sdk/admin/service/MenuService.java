package com.test.sdk.admin.service;


import com.test.sdk.admin.pojo.Menu;

import java.util.List;

public interface MenuService {
    List<Menu> getMenuTree();

    void deleteMenus(Integer[] ids);

    void addMenu(Menu menu);

    Menu getMenuById(Integer id);

    void updateMenu(Menu menu);

    List<Menu> getUserMenu(Integer userId);

}
