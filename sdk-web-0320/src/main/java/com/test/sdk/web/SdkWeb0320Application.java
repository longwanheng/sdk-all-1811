package com.test.sdk.web;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import com.test.sdk.web.exception.SdkExceptionHandler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableDubbo
public class SdkWeb0320Application {

	public static void main(String[] args) {
		SpringApplication.run(SdkWeb0320Application.class, args);
	}
	@Bean
	public SdkExceptionHandler sdkExceptionHandler() {
		return new SdkExceptionHandler();
	}
}
