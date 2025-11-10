package in.dinesh.Accounts;

import in.dinesh.Accounts.dto.AccountsContactInfoDto;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl")
@EnableConfigurationProperties(AccountsContactInfoDto.class)
@OpenAPIDefinition(
        info = @Info(
                title = "Accounts API",
                version = "1.0",
                description = "Accounts Microservices REST API's Documentation",
                contact = @Contact(name = "Dinesh", email = "chinthadineshbabu15@gmail.com", url = "https://dinesh.com"),
                license = @License(name = "Dinesh", url = "https://dinesh.com")
        )
)
public class AccountsApplication {

	public static void main(String[] args) {
		SpringApplication.run(AccountsApplication.class, args);
	}

}
