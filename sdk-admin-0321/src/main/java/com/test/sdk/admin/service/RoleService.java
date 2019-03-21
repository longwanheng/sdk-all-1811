package com.test.sdk.admin.service;


import com.github.pagehelper.PageInfo;
import com.test.sdk.admin.pojo.Role;

import java.util.List;

public interface RoleService {
    PageInfo<Role> getRoleList(Role role, Integer pageNum, Integer pageSize);
    List<Role> getRoleList(Role role);
    void addRoleMenu(Integer roleId, Integer[] menuIds);
    List<Integer> getRoleMenuIds(Integer roleId);
    void addRole(Role role);
    void updateRole(Role role);
}
