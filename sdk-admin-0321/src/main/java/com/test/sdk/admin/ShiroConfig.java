package com.test.sdk.admin;

import com.test.sdk.admin.dao.MenuDAO;
import com.test.sdk.admin.shiro.MyShiroFilter;
import com.test.sdk.admin.shiro.MyShiroFilterFactoryBean;
import com.test.sdk.admin.shiro.MyShiroRealm;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.crazycake.shiro.RedisCacheManager;
import org.crazycake.shiro.RedisManager;
import org.crazycake.shiro.RedisSessionDAO;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {

    /**
     * 创建jedispool对象，用于给redismanager使用
     *
     * @return
     */
    @Bean
    public JedisPool jedisPool() {
        JedisPoolConfig config=new JedisPoolConfig();
        JedisPool jedisPool = new JedisPool(config,"10.9.63.181", 6379,30000,"123456");
        return jedisPool;
    }

    @Bean
    public MyShiroRealm myShiroRealm() {
        return new MyShiroRealm();
    }

    /**
     * @param redisManager 因为该方法需要的参数是另外一个方法，那个方法上面加了Bean注解，所以可以声明
     * @return
     */
    @Bean
    public RedisCacheManager shiroRedisCacheManager(RedisManager redisManager) {
        RedisCacheManager redisCacheManager = new RedisCacheManager();
       // redisCacheManager.setPrincipalIdFieldName("主键的属性名"); //默认在real的 认证方法返回的SimpleAuthenticationInfo(Object principal, Object credentials, String realmName) principal对象必须有id属性，如果没有id而是其他唯一属性，可以设置自己的属性名
        redisCacheManager.setRedisManager(redisManager);//此处直接调用形参就行，不需要调用创建这个对象的方法，因为这个返回redismanager的方法上面有@Bean注解，springboot会自动创建
        return redisCacheManager;
    }

    @Bean
    public RedisManager redisManager(JedisPool jedisPool) {
        RedisManager redisManager = new RedisManager();
        //新版本依赖包使用的是jedispool，没有单独设置redis相关参数的方法了
        redisManager.setJedisPool(jedisPool);
        return redisManager;
    }

    //shiro和redis进行整合，shiro缓存写到redis中
    @Bean
    public RedisSessionDAO redisSessionDAO(RedisManager redisManager) {
        RedisSessionDAO redisSessionDAO = new RedisSessionDAO();
        redisSessionDAO.setRedisManager(redisManager);
        return redisSessionDAO;
    }

    @Bean
    public DefaultWebSecurityManager securityManager(RedisCacheManager shiroRedisCacheManager) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(myShiroRealm());
        securityManager.setCacheManager(shiroRedisCacheManager);
        return securityManager;
    }

    @Bean
    public MyShiroFilterFactoryBean shiroFilter(MenuDAO menuDAO, SecurityManager securityManager) {
        MyShiroFilterFactoryBean factoryBean = new MyShiroFilterFactoryBean();
        factoryBean.setSecurityManager(securityManager);
        factoryBean.setLoginUrl("/login.html");
        factoryBean.setSuccessUrl("/index.html");
        factoryBean.setUnauthorizedUrl("/auth_error.html");
        factoryBean.setMenuDAO(menuDAO);
        factoryBean.setFilterChainDefinitions("/login.html = anon\n/dologin.html = anon\n/static/** = anon");
        Map<String, Filter> map = new HashMap<>();
        map.put("roles", new MyShiroFilter());
        factoryBean.setFilters(map);
        return factoryBean;
    }
}
