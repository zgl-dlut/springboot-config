package com.zgl.springboot;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
@MapperScan(basePackages = "com.zgl.springboot.mapper")
public class SpringBootConfigApplication implements ApplicationRunner {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootConfigApplication.class, args);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		/*new GeneratorServiceEntity().generateByTables("message");*/
	}
}
