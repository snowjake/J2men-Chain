package com.chain.toychain;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@SpringBootApplication
public class ToychainApplication {

	public static void main(String[] args) {
		SpringApplication.run(ToychainApplication.class, args);
	}

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)
				.apiInfo(getApiInfo())
				// .pathMapping("/")// base，最终调用接口后会和paths拼接在一起
				.select()
				// .paths(Predicates.or(PathSelectors.regex("/api/.*")))//过滤的接口
				.apis(RequestHandlerSelectors.basePackage("com.chain.toychain")) // 过滤的接口
				.paths(PathSelectors.any()).build();
	}

	private ApiInfo getApiInfo() {
		// 定义联系人信息
		String contact = "snow";
		return new ApiInfoBuilder()
				.title("区块链测试")
				// 标题
				.description("测试區塊鏈接口")
				// 描述信息
				.version("1.1.2")
				// //版本
				.license("Apache 2.0")
				.licenseUrl("http://www.apache.org/licenses/LICENSE-2.0")
				.contact(contact).build();
	}
}
