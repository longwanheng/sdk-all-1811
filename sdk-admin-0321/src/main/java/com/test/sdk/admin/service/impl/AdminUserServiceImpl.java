package com.test.sdk.admin.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.test.sdk.admin.dao.AdminUserDAO;
import com.test.sdk.admin.pojo.AdminUser;
import com.test.sdk.admin.service.AdminUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminUserServiceImpl implements AdminUserService {
    @Autowired
    private AdminUserDAO adminUserDAO;


    @Override
    public PageInfo<AdminUser> getUserList(AdminUser user, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        return new PageInfo<>(adminUserDAO.getUserList(user));
    }

    @Override
    public void addUserRole(Integer userId, Integer[] roleIds) {
        adminUserDAO.deleteUserRole(userId);
        for (Integer roleId : roleIds) {
            adminUserDAO.addUserRole(roleId,userId);
        }
    }

    @Override
    public List<Integer> getUserRoleIds(Integer userId) {
        return adminUserDAO.getUserRoleIds(userId);
    }
}
