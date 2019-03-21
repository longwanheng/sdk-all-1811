package com.test.sdk.admin.controller;

import com.test.sdk.admin.pojo.AdminUser;
import com.test.sdk.admin.pojo.Menu;
import com.test.sdk.admin.service.AdminUserService;
import com.test.sdk.admin.service.MenuService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class SystemController {
    @Autowired
    private MenuService menuService;

    @Autowired
    private AdminUserService adminUserService;

    @RequestMapping("login.html")
    public String login() {
        return "login";
    }

    @RequestMapping("logout.html")
    public String logout() {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return "login";
    }

    @RequestMapping("dologin.html")
    public ModelAndView login(String email, String password, HttpSession session) {
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(email, password);
        try {
            subject.login(token);
        } catch (AuthenticationException e) {
            e.printStackTrace();
            return new ModelAndView("login", "message", "用户名或密码错误");
        }
        System.out.println("成功=================");
        //AdminUser user = adminUserService.doLogin(email, password);
//        if (user == null) {
//            return new ModelAndView("login", "message", "用户名或密码错误");
//        }
//        session.setAttribute(AdminConstants.SESSION_USER, user);
        return new ModelAndView(new RedirectView("index.html"));
    }

    @RequestMapping("index.html")
    public String index(Model model) {
        Subject subject = SecurityUtils.getSubject();
        //因为认证方法返回的info对象的principal属性由原先的id变成了user对象，所以此处需要强转
        List<Menu> menus = menuService.getUserMenu(Integer.parseInt(((AdminUser) subject.getPrincipal()).getId().toString()));
        model.addAttribute("menus", menus);
        return "index";
    }

    @RequestMapping("auth_error.html")
    public String error() {
        return "error";
    }

    @RequestMapping("side.html")
    @ResponseBody
    public List<Menu> getMenuTree() {
        Subject subject = SecurityUtils.getSubject();
        return menuService.getUserMenu(Integer.parseInt(subject.getPrincipal().toString()));
    }
}
