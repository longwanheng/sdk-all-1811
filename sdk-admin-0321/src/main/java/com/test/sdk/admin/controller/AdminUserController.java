package com.test.sdk.admin.controller;

import com.github.pagehelper.PageInfo;
import com.test.sdk.admin.pojo.AdminUser;
import com.test.sdk.admin.pojo.Role;
import com.test.sdk.admin.service.AdminUserService;
import com.test.sdk.admin.service.RoleService;
import com.test.sdk.admin.util.AjaxMessage;
import com.test.sdk.admin.util.TableData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/adminuser.html")
public class AdminUserController {

    @Autowired
    private AdminUserService adminUserService;
    @Autowired
    private RoleService roleService;

    @RequestMapping
    public String page() {
        return "admin_user";
    }

    @RequestMapping(params = "act=table")
    @ResponseBody
    public TableData table(AdminUser adminUser, Integer page, Integer limit) {
        PageInfo<AdminUser> pageInfo = adminUserService.getUserList(adminUser, page, limit);
        return new TableData(pageInfo.getTotal(), pageInfo.getList());
    }

    @RequestMapping(params = "act=role_list")
    @ResponseBody
    public List<Role> roleList() {
        return roleService.getRoleList(null);
    }

    @RequestMapping(params = "act=user_role")
    @ResponseBody
    public List<Integer> userRole(Integer userId){
        return adminUserService.getUserRoleIds(userId);
    }

    @RequestMapping(params = "act=assign_role")
    @ResponseBody
    public AjaxMessage assignRole(Integer userId, Integer[] roleIds){
        try {
            adminUserService.addUserRole(userId,roleIds);
            return new AjaxMessage(true,"分配成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
         return new AjaxMessage(false,"分配失败");
    }
}
