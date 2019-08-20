package com.zgl.springboot.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zgl
 * @date 2019/8/20 下午5:58
 */
@EnableSwagger2
@Configuration
public class Swagger2Config {

	@Bean
	public Docket creatRestApi(){
		ParameterBuilder headPar = new ParameterBuilder();
		List<Parameter> pars = new ArrayList<>();
		headPar.name("empNo").description("员工编号-header")
				.modelRef(new ModelRef("string")).parameterType("header").defaultValue("1")
				.required(true).build();
		pars.add(headPar.build());
		headPar.name("X-Ca-platform").description("平台码-header")
				.modelRef(new ModelRef("string")).parameterType("header").defaultValue("service")
				.required(true).build();
		pars.add(headPar.build());
		headPar.name("cityCode").description("分校编码-header")
				.modelRef(new ModelRef("string")).parameterType("header").defaultValue("010")
				.required(true).build();
		pars.add(headPar.build());


		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors.any())
				.build()
				.globalOperationParameters(pars)
				.apiInfo(apiInfo());
	}

	private ApiInfo apiInfo(){
		return new ApiInfoBuilder()
				.title("splatApi")
				.contact(new Contact("**平台","",""))
				.termsOfServiceUrl("http://www.baidu.com")
				.version("1.0")
				.description("**相关接口")
				.build();
	}
}