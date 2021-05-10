package com.mc_3.userCreator;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.common.base.Predicate;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
//import springfox.documentation.swagger2.annotations.PropertySource;
import static springfox.documentation.builders.PathSelectors.regex;
import static com.google.common.base.Predicates.or;

@Configuration
//@PropertySource("classpath:swagger.properties")
@EnableSwagger2
public class SwaggerConfig {

	@Bean
	public Docket postsApi() 
	{
		return new Docket(DocumentationType.SWAGGER_2).groupName("public-api").apiInfo(apiInfo()).select().paths(postPaths()).build();
	}

	private Predicate<String> postPaths() 
	{
		//return or(regex("/api/posts.*"), regex("/api/javainuse.*"));
		return or(regex("/api/posts.*"), regex("/.*"));
	}

	private ApiInfo apiInfo() 
	{
		return new ApiInfoBuilder().title("Puzzle Game API").description("API used for mc_3's Puzzle Game").termsOfServiceUrl("http://javainuse.com").version("1.3").build();
	}

}
