package ru.vichukano.crvt_test;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;

import javax.servlet.MultipartConfigElement;

@SpringBootApplication
public class CrvtTestApplication {
	private final static Log log = LogFactory.getLog(CrvtTestApplication.class);

	@Bean
	MultipartConfigElement multipartConfigElement() {
		MultipartConfigFactory factory = new MultipartConfigFactory();
		return factory.createMultipartConfig();
	}

	public static void main(String[] args) {
		SpringApplication.run(CrvtTestApplication.class, args);
		log.info("Application started");
	}

}
