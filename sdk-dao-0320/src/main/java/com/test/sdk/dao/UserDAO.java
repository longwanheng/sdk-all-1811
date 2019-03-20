package com.test.sdk.dao;

import com.test.sdk.common.pojo.NumCode;
import com.test.sdk.common.pojo.User;
import org.apache.ibatis.annotations.Param;

public interface UserDAO {
    Integer getUserIdByName(String name);

    Integer getUserIdByNum(String num);

    User getUserById(Integer id);

    void addUser(User user);

    void addUserName(User user);

    void addUserNum(User user);

    NumCode getNumCode(String num);

    void addNumCode(@Param("num") String num, @Param("code") String code);

    void deleteCodeByNum(String num);
}
