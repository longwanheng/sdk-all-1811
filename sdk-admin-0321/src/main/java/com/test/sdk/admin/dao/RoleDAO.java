package com.test.sdk.admin.dao;

import com.test.sdk.admin.pojo.Role;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RoleDAO {
    List<Role> getRoleList(Role role);

    void addRoleMenu(@Param("roleId") Integer roleId, @Param("menuId") Integer menuId);

    void deleteRoleMenu(Integer roleId);

    List<Integer> getRoleMenuIds(Integer roleId);

    void addRole(Role role);
    void updateRole(Role role);
}
