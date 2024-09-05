package com.springboot.banking_api;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
		info = @Info(
			title = "Banking system api",
				description = "A banking system api with all major functionality",
				version = "v1.0",
				contact = @Contact(
						name = "Md Sajjad Hossin",
						email = "sajjadHossin3749@gmail.com",
						url = "https://github.com/MdSajjadHossin"
				),
				license = @License(
						name = "S.Hossin corporation",
						url = "https://github.com/MdSajjadHossin/Banking-Api"
				)
		),
		externalDocs = @ExternalDocumentation(
				description = "This is a simple banking api with authentication and mail sending features. The api uses mySql data base as database",
				url = "https://github.com/MdSajjadHossin/Banking-Api"

		)
)
public class BankingApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(BankingApiApplication.class, args);

	}

}
