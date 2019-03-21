package com.test.sdk.admin.shiro;

import com.test.sdk.admin.dao.AdminUserDAO;
import com.test.sdk.admin.pojo.AdminUser;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class MyShiroRealm extends AuthorizingRealm {
    @Autowired
    private AdminUserDAO adminUserDAO;


    //请求一个资源的时候使用，比如访问某一个url
    //返回用的权限
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("doGetAuthorizationInfo===========");

        //因为认证方法返回的info对象的principal属性由原先的id变成了user对象，所以此处需要强转
        Integer userId = Integer.parseInt(((AdminUser) principalCollection.getPrimaryPrincipal()).getId().toString());
        //查询出用所有的角色，交给shiro
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        List<Integer> roleIds = adminUserDAO.getUserRoleIds(userId);
        for (Integer roleId : roleIds) {
            info.addRole(roleId.toString());
        }
        return info;
    }

    //登陆的时候使用，验证用户名密码
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        String email = token.getUsername();//登陆时的用户名
        AdminUser user = adminUserDAO.getUserByEmail(email);
        if (user == null) {//用户名错误
            return null;
        }
        //此处返回的对象中的principal 不再是单纯的用户的id，而是用户对象，因为后面在序列化到redis中的时候会调用该对象的getId方法，如果返回id 没有那个方法
        return new SimpleAuthenticationInfo(user, user.getPassword(), super.getName());
    }
}
