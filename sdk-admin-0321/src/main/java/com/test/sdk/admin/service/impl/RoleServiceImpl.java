package com.test.sdk.admin.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.test.sdk.admin.dao.RoleDAO;
import com.test.sdk.admin.pojo.Role;
import com.test.sdk.admin.service.RoleService;
import com.test.sdk.admin.shiro.MyShiroFilterFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleDAO roleDAO;

    @Override
    public PageInfo<Role> getRoleList(Role role, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return new PageInfo<>(roleDAO.getRoleList(role));
    }

    @Autowired
    private MyShiroFilterFactoryBean shiroFilterFactoryBean;
    @Override
    public void addRoleMenu(Integer roleId, Integer[] menuIds) {
        roleDAO.deleteRoleMenu(roleId);
        for (Integer menuId : menuIds) {
            roleDAO.addRoleMenu(roleId,menuId);
        }
        shiroFilterFactoryBean.update();
    }

    @Override
    public void addRole(Role role) {
        roleDAO.addRole(role);
    }

    @Override
    public void updateRole(Role role) {
        roleDAO.updateRole(role);
    }

    @Override
    public List<Integer> getRoleMenuIds(Integer roleId) {
        return roleDAO.getRoleMenuIds(roleId);
    }

    @Override
    public List<Role> getRoleList(Role role) {
        return roleDAO.getRoleList(role);
    }
}
