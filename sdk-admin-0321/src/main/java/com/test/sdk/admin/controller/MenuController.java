package com.test.sdk.admin.controller;

import com.test.sdk.admin.pojo.Menu;
import com.test.sdk.admin.service.MenuService;
import com.test.sdk.admin.util.AjaxMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;


@Controller
@RequestMapping("menu.html")
public class MenuController {
    @Autowired
    private MenuService menuService;

    @RequestMapping
    public String page() {
        return "menu";
    }

    @RequestMapping(params = "act=tree")
    @ResponseBody
    public List<Menu> tree() {
        return menuService.getMenuTree();
    }

    @RequestMapping(params = "act=delete")
    @ResponseBody
    public AjaxMessage delete(Integer[] ids) {
        try {
            menuService.deleteMenus(ids);
            return new AjaxMessage(true);
        } catch (Exception e) {
            e.printStackTrace();
            return new AjaxMessage(true, "删除失败");
        }
    }

    @RequestMapping(params = "act=go_edit")
    public String goEdit(Integer id, Model model) {
        if (id != null) {
            Menu menu = menuService.getMenuById(id);
            model.addAttribute("menu", menu);
        }
        return "menu_edit";
    }

    @RequestMapping(params = "act=edit")
    @ResponseBody
    public AjaxMessage edit(Menu menu) {
        try {
            if (menu.getId() == null) {
                menuService.addMenu(menu);
            } else {
                menuService.updateMenu(menu);
            }
            return new AjaxMessage(true);
        } catch (Exception e) {
            e.printStackTrace();
            return new AjaxMessage(true, "编辑失败");
        }
    }
}
