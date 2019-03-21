package com.test.sdk.admin.service.impl;

import com.test.sdk.admin.dao.MenuDAO;
import com.test.sdk.admin.pojo.Menu;
import com.test.sdk.admin.service.MenuService;
import com.test.sdk.admin.shiro.MyShiroFilterFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MenuServiceImpl implements MenuService {
    @Autowired
    private MenuDAO menuDAO;

    @Override
    public List<Menu> getMenuTree() {
        List<Menu> menus = menuDAO.getAllMenu();
        return makeMenuTree(menus);
    }

    private List<Menu> makeMenuTree(List<Menu> menus) {
        List<Menu> firstMenu = new ArrayList<>();
        Map<Integer, Menu> menuMap = new HashMap<>();
        for (Menu menu : menus) {
            menuMap.put(menu.getId(), menu);
            if (menu.getParentId() == null) {//一级菜单
                firstMenu.add(menu);
            }
        }
        for (Menu menu : menus) {
            if (menu.getParentId() != null && menuMap.containsKey(menu.getParentId())) {
                menuMap.get(menu.getParentId()).getChildren().add(menu);
            }
        }
        return firstMenu;
    }

    @Override
    public void deleteMenus(Integer[] ids) {
        for (Integer id : ids) {
            menuDAO.updateParentId(id);//将子菜单变成一级菜单
            menuDAO.deleteMenu(id);
        }
    }

    @Override
    public void addMenu(Menu menu) {
        menuDAO.addMenu(menu);
    }

    @Override
    public Menu getMenuById(Integer id) {
        return menuDAO.getMenuById(id);
    }

    @Autowired
    private MyShiroFilterFactoryBean shiroFilterFactoryBean;

    @Override
    public void updateMenu(Menu menu) {
        menuDAO.updateMenu(menu);
        shiroFilterFactoryBean.update();
    }

    @Override
    public List<Menu> getUserMenu(Integer userId) {
        List<Menu> menus = menuDAO.getUserMenu(userId);
        return makeMenuTree(menus);
    }
}
