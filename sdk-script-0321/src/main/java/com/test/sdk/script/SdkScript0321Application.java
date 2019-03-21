package com.test.sdk.script;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.interceptor.TransactionInterceptor;

import javax.sql.DataSource;
import java.util.Properties;

@SpringBootApplication
@EnableTransactionManagement//开启事务管理
@MapperScan("com.test.sdk.script.dao")//Mybatis的DAO所在包
@EnableCaching
@EnableDubbo//开启dubbo
public class SdkScript0321Application {

	public static void main(String[] args) {
		SpringApplication.run(SdkScript0321Application.class, args);
	}
	@Autowired
	private DataSource dataSource;

	private String transactionExecution = "execution(* com.test.sdk.script.service..*(..))";

	@Bean
	public DefaultPointcutAdvisor defaultPointcutAdvisor() {
		AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
		pointcut.setExpression(transactionExecution);
		DefaultPointcutAdvisor advisor = new DefaultPointcutAdvisor();
		advisor.setPointcut(pointcut);
		Properties attributes = new Properties();
		attributes.setProperty("get*", "PROPAGATION_SUPPORTS,ISOLATION_DEFAULT,readOnly");
		attributes.setProperty("add*", "PROPAGATION_REQUIRED,-Exception");
		attributes.setProperty("update*", "PROPAGATION_REQUIRED,-Exception");
		attributes.setProperty("delete*", "PROPAGATION_REQUIRED,-Exception");
		TransactionInterceptor txAdvice = new TransactionInterceptor(new DataSourceTransactionManager(dataSource), attributes);
		advisor.setAdvice(txAdvice);
		return advisor;
	}
}
