package com.test.sdk.admin.controller;

import com.github.pagehelper.PageInfo;
import com.test.sdk.admin.pojo.Menu;
import com.test.sdk.admin.pojo.Role;
import com.test.sdk.admin.service.MenuService;
import com.test.sdk.admin.service.RoleService;
import com.test.sdk.admin.util.AjaxMessage;
import com.test.sdk.admin.util.TableData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("role.html")
public class RoleController {
    @Autowired
    private RoleService roleService;

    @Autowired
    private MenuService menuService;

    @RequestMapping
    public String page() {
        return "role";
    }

    @RequestMapping(params = "act=table")
    @ResponseBody
    public TableData table(Role role, Integer page,Integer limit) {
        PageInfo<Role> pageInfo = roleService.getRoleList(role, page, limit);
        return new TableData(pageInfo.getTotal(), pageInfo.getList());
    }

    @RequestMapping(params = "act=menu_tree")
    @ResponseBody
    public List<Menu> menuTree() {
        return menuService.getMenuTree();
    }
    @RequestMapping(params = "act=go_menu")
    public String menuPage() {
        return "role_menu";
    }
    @RequestMapping(params = "act=assign_menu")
    @ResponseBody
    public AjaxMessage assign(Integer roleId, Integer[] menuIds) {
        try {
            roleService.addRoleMenu(roleId, menuIds);
            return new AjaxMessage(true, "分配成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new AjaxMessage(false, "分配失败");
        }
    }
    @RequestMapping(params = "act=role_menu")
    @ResponseBody
    public List<Integer> roleMenu(Integer roleId) {
        return roleService.getRoleMenuIds(roleId);
    }
    @RequestMapping(params = "act=edit")
    @ResponseBody
    public AjaxMessage edit(Role role) {
        try {
            if (role.getId() == null) {
                roleService.addRole(role);
            } else {
                roleService.updateRole(role);
            }
            return new AjaxMessage(true, "编辑成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new AjaxMessage(false, "编辑失败");
        }
    }
}
